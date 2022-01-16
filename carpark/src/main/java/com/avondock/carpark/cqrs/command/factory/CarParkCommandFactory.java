package com.avondock.carpark.cqrs.command.factory;

import com.avondock.carpark.cqrs.coreapi.AbstractCarParkDTO;
import com.avondock.carpark.cqrs.coreapi.CarParkCommand;
import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkId;
import org.springframework.stereotype.Component;

@Component
public interface CarParkCommandFactory {
    CarParkCommand create(AbstractCarParkDTO request, Class<?> command, CarParkId id);
}
