package com.avondock.carpark.gateway;


import com.avondock.carpark.cqrs.coreapi.GetCarPark;
import com.avondock.carpark.cqrs.coreapi.ListCarParks;
import com.avondock.carpark.cqrs.query.response.CarParkResponse;
import com.avondock.components.common.http.HttpFilter;
import com.avondock.components.shared.gateway.QueryEndpoint;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

//@Profile("rest")
@RestController
@RequestMapping(value = "api/v1/carparks", produces = { MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE })
@Api(consumes = "Set of GET endpoints to retrieving carparks")
public class CarParkQueryEndpoint extends QueryEndpoint {

    @Autowired
    public CarParkQueryEndpoint(QueryGateway queryGateway) {
        super(queryGateway);
    }

    @GetMapping
    @ApiOperation("Returns sorted,filtered and pagable list of carparks in the system")
    public CompletableFuture<CollectionModel<List<CarParkResponse>>> listCarParks(
            @RequestParam(name = "filter") String filterParam,
            @RequestParam(name = "expand") Optional<String> expand
    ) {
        HttpFilter filter = map(filterParam, HttpFilter.class);
        return list(new ListCarParks(filter, expand), CarParkResponse.class);
    }

    @GetMapping("/{id}")
    @ApiOperation("Return one carpark")
    public CompletableFuture<ResponseEntity<CarParkResponse>> getCarPark(
            @PathVariable String id,
            @RequestParam(name = "expand") Optional<String> expand
    ) {
        return get(new GetCarPark(id, expand), CarParkResponse.class);
    }
}
