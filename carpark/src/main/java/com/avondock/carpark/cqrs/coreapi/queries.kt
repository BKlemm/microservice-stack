package com.avondock.carpark.cqrs.coreapi

import com.avondock.carpark.cqrs.query.model.CarParkView
import com.avondock.components.common.http.HttpFilter
import com.avondock.components.shared.gateway.contracts.Query
import lombok.Value
import org.springframework.hateoas.RepresentationModel
import java.util.*

abstract class CarParkResultQuery:RepresentationModel<ListCarParksResult>()

@Value class GetCarPark(val id: String, val expand: Optional<String>) : Query

@Value class ListCarParks(val filter: HttpFilter, val expand: Optional<String>) : Query
@Value class ListCarParksResult(val carParks: List<CarParkView>): CarParkResultQuery()


