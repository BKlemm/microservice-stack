package com.avondock.carpark.infrastucture.factory;


import com.avondock.carpark.cqrs.command.factory.CarParkCommandFactory;
import com.avondock.carpark.cqrs.coreapi.AbstractCarParkDTO;
import com.avondock.carpark.cqrs.coreapi.CarParkCommand;
import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkAddress;
import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkId;
import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkStatus;
import com.avondock.components.shared.infrastructure.factory.CommandFactoryImpl;

import java.math.BigDecimal;

public class CarParkCommandFactoryImpl extends CommandFactoryImpl implements CarParkCommandFactory {

    public CarParkCommand create(AbstractCarParkDTO request, Class<?> command, CarParkId id) {
        this.construct(command,
            CarParkId.class, String.class, String.class, String.class,
            CarParkAddress.class, String.class, String.class, BigDecimal.class, CarParkStatus.class
        );

        CarParkAddress address = mapper.map(request.getAddress(), CarParkAddress.class);

        return (CarParkCommand) this.createInstance(
            id,
            request.getIataCode(),
            request.getName(),
            request.getDescription(),
            address,
            request.getSupportEmail(),
            request.getSupportPhone(),
            request.getTax(),
            request.getCarParkStatus()
        );
    }
}
