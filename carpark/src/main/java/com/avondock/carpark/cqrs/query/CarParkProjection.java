package com.avondock.carpark.cqrs.query;


import com.avondock.carpark.cqrs.coreapi.CarParkAdded;
import com.avondock.carpark.cqrs.coreapi.CarParkChanged;
import com.avondock.carpark.cqrs.coreapi.GetCarPark;
import com.avondock.carpark.cqrs.coreapi.ListCarParks;
import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkAddress;
import com.avondock.carpark.cqrs.query.model.CarParkView;
import com.avondock.carpark.cqrs.query.response.CarParkResponse;
import com.avondock.carpark.infrastucture.assembler.CarParkAssembler;
import com.avondock.carpark.infrastucture.service.CarParkService;
import com.avondock.components.common.http.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
// Uncomment to activate profile
//@Profile("query")
// Uncomment to specify a specific processing group for your query models
//@ProcessingGroup("query")
public class CarParkProjection {

    private final CarParkAssembler assembler;
    private final CarParkService   carParkService;

    @Autowired
    public CarParkProjection(CarParkService carParkService, CarParkAssembler assembler) {
        this.carParkService = carParkService;
        this.assembler = assembler;
    }


    @EventHandler
    public void on(CarParkAdded e) {
        log.debug("PROJECTION {}", e);

        CarParkView carpark = new CarParkView(
                e.getCarParkId(),
                e.getIataCode(),
                e.getName(),
                e.getDescription(),
                new CarParkAddress(
                        e.getAddress().getStreet(),
                        e.getAddress().getNumber(),
                        e.getAddress().getCity(),
                        e.getAddress().getZip(),
                        e.getAddress().getCountry(),
                        e.getAddress().getRegion(),
                        e.getAddress().getLatitude(),
                        e.getAddress().getLongitude()
                ),
                e.getSupportEmail(),
                e.getSupportPhone(),
                e.getTax(),
                e.getState()
        );

        carParkService.addCarpark(carpark);
    }

    @EventHandler
    public void on(CarParkChanged e) {
        log.debug("PROJECTION {}", e);
        CarParkView carpark = carParkService.carparkById(e.getCarParkId());

        carpark.setAddress(e.getAddress());
        carpark.setSupportEmail(e.getSupportEmail());
        carpark.setName(e.getName());
        carpark.setSupportPhone(e.getSupportPhone());
        carpark.setDescription(e.getDescription());
        carpark.setIataCode(e.getIataCode());
        carpark.setTax(e.getTax());
        carpark.setCarParkStatus(e.getState());

        carParkService.addCarpark(carpark);

    }

    @QueryHandler
    public CollectionModel<CarParkResponse> handle(ListCarParks query) {
        Pageable request = Pagination.of(query.getFilter());
        return assembler
                .setExpand(query.getExpand())
                .toCollectionModel(carParkService.carparks(request, query.getFilter().getFilter()));
    }

    @QueryHandler
    public CarParkResponse handle(GetCarPark query) {
        return assembler.setExpand(query.getExpand()).toModel(carParkService.carparkById(query.getId()));
    }
}
