package com.avondock.carpark.gateway;

import com.avondock.carpark.cqrs.command.factory.CarParkCommandFactory;
import com.avondock.carpark.cqrs.coreapi.AddCarPark;
import com.avondock.carpark.cqrs.coreapi.AddCarParkDTO;
import com.avondock.carpark.cqrs.coreapi.ChangeCarPark;
import com.avondock.carpark.cqrs.coreapi.ChangeCarParkDTO;
import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkId;
import com.avondock.components.shared.gateway.CommandEndpoint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@Profile("rest")
@RestController
@RequestMapping("api/v1/carparks")
@Api(consumes = "Set of WRITE endpoints to create and change carparks")
public class CarParkCommandEndpoint extends CommandEndpoint {

    CarParkCommandFactory commandFactory;

    @Autowired
    public CarParkCommandEndpoint(CommandGateway commandGateway, CarParkCommandFactory commandFactory) {
        super(commandGateway);
        this.commandFactory = commandFactory;
    }

    @PostMapping
    @ApiOperation("Add a carpark in the system")
    public ResponseEntity<?> create(@Valid @RequestBody AddCarParkDTO request) {
        AddCarPark command = (AddCarPark) commandFactory.create(request, AddCarPark.class, new CarParkId());
        return sendCreate(command, request);
    }

    @PutMapping("/{id}")
    @ApiOperation("Change a carparks in the system")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody ChangeCarParkDTO request) {
        ChangeCarPark command = (ChangeCarPark) commandFactory.create(request, ChangeCarPark.class, new CarParkId(id));
        return sendUpdate(command, request);
    }
}
