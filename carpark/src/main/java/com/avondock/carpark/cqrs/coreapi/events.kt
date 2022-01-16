package com.avondock.carpark.cqrs.coreapi

import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkAddress
import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkId
import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkStatus
import lombok.Value
import org.axonframework.serialization.Revision
import java.math.BigDecimal

abstract class CarParkEvent(open val carParkId: CarParkId)

abstract class AbstractCarParkEvent(
    override val carParkId: CarParkId,
    open val address: CarParkAddress,
    open val name: String,
    open val iataCode: String,
    open val supportEmail: String,
    open val supportPhone: String,
    open val tax: BigDecimal,
    open val description: String?,
    open val state: CarParkStatus
): CarParkEvent(carParkId)

@Value
@Revision("1") //that's your event revision, if some parts changed on future, you have to upcast your event
class CarParkAdded(
    override val carParkId: CarParkId,
    override val address: CarParkAddress,
    override val name: String,
    override val iataCode: String,
    override val supportEmail: String,
    override val supportPhone: String,
    override val tax: BigDecimal,
    override val description: String?,
    override val state: CarParkStatus
) : AbstractCarParkEvent(carParkId, address, name, iataCode, supportEmail, supportPhone, tax, description, state)

@Value
class CarParkChanged(
    override val carParkId: CarParkId,
    override val address: CarParkAddress,
    override val name: String,
    override val iataCode: String,
    override val supportEmail: String,
    override val supportPhone: String,
    override val tax: BigDecimal,
    override val description: String?,
    override val state: CarParkStatus
) : AbstractCarParkEvent(carParkId, address, name, iataCode, supportEmail, supportPhone, tax, description, state)
