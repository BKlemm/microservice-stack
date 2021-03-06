package com.avondock.carpark.cqrs.coreapi

import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkAddress
import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkId
import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkStatus
import com.avondock.components.shared.gateway.contracts.Command
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.math.BigDecimal

abstract class CarParkCommand(@TargetAggregateIdentifier open val carParkId: CarParkId): Command<CarParkId> {
    override fun getIdentity(): CarParkId {
        return carParkId
    }
}

class AddCarPark(
    override val carParkId: CarParkId,
    val iataCode: String,
    val name: String,
    val description: String?,
    val address: CarParkAddress,
    val supportEmail: String,
    val supportPhone: String,
    val tax: BigDecimal,
    val state: CarParkStatus,
) : CarParkCommand(carParkId)

class ChangeCarPark(
    override val carParkId: CarParkId,
    val iataCode: String,
    val name: String,
    val description: String?,
    val address: CarParkAddress,
    val supportEmail: String,
    val supportPhone: String,
    val tax: BigDecimal,
    val state: CarParkStatus
) : CarParkCommand(carParkId)
