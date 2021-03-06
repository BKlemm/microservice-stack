package com.avondock.carpark.cqrs.coreapi

import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkStatus
import com.avondock.components.common.util.PatternUtil
import com.avondock.components.common.util.validation.EnumNamePattern
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel
import java.math.BigDecimal
import javax.validation.Valid
import javax.validation.constraints.*

abstract class AbstractCarParkDTO(
    @field:Size(min = 3, max = 3, message = "Iata Code must be a length of 3")
    @JsonProperty("iataCode", required = true)
    open val iataCode: String,
    @field:Size(min = 3, message = "Name has to be a min length of 3")
    @JsonProperty("name", required = true)
    open val name: String,
    @JsonProperty("description")
    open val description: String?,
    @JsonProperty("description", required = true)
    @field:Valid
    open val address: CarParkAddressDTO,
    @field:Email(message = "Support Email should be valid")
    @JsonProperty("supportEmail")
    open val supportEmail: String,
    @field:NotBlank(message = "Support Phone cannot be null")
    @JsonProperty("supportPhone")
    open val supportPhone: String,
    @JsonProperty("tax", required = true)
    @field:Digits(fraction = 0, integer = 2)
    open val tax: BigDecimal,
    @JsonProperty("carParkStatus", required = true)
    @field:EnumNamePattern(regexp = "ACTIVE|INACTIVE|FULL")
    open val carParkStatus: CarParkStatus
): RepresentationModel<AbstractCarParkDTO>()


data class AddCarParkDTO(
    override val iataCode: String,
    override val name: String,
    override val description: String?,
    override val address: CarParkAddressDTO,
    override val supportEmail: String,
    override val supportPhone: String,
    override val tax: BigDecimal,
    override val carParkStatus: CarParkStatus,
): AbstractCarParkDTO(iataCode, name, description, address, supportEmail, supportPhone, tax, carParkStatus)

class ChangeCarParkDTO(
    @field:Pattern(regexp = PatternUtil.UUID, message = "UUID Format error")
    @JsonProperty("carParkId", required = true)
    val id: String,
    override val iataCode: String,
    override val name: String,
    override val description: String?,
    override val address: CarParkAddressDTO,
    override val supportEmail: String,
    override val supportPhone: String,
    override val tax: BigDecimal,
    override val carParkStatus: CarParkStatus
): AbstractCarParkDTO(iataCode, name, description, address, supportEmail, supportPhone, tax, carParkStatus)


class CarParkAddressDTO (
    @field:Size(min = 3, max = 90) val street: String,
    @field:Size(min = 1, max = 10) val number: String,
    @field:Size(min = 2, max = 90) val city: String,
    @field:Size(min = 4, max = 8) val zip: String,
    @field:Size(min = 3, max = 90) val country: String,
    val region: String?,
    @field:Pattern(regexp = PatternUtil.LATITUDE, message = "Must be a valid gps value")
    val latitude: String,
    @field:Pattern(regexp = PatternUtil.LONGITUDE, message = "Must be a valid gps value")
    val longitude: String
)
