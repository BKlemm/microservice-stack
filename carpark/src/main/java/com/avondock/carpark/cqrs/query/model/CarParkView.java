package com.avondock.carpark.cqrs.query.model;

import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkAddress;
import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkId;
import com.avondock.carpark.cqrs.coreapi.valueobjects.CarParkStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "car_park")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarParkView implements Serializable, com.avondock.components.shared.domain.contracts.Entity {

    @EmbeddedId
    CarParkId carParkId;

    @Column(name = "iata_code", length = 3)
    String iataCode;
    String name;
    String description;

    String supportEmail;
    String supportPhone;

    @Embedded
    CarParkAddress address;

    @Enumerated(EnumType.STRING)
    CarParkStatus carParkStatus;
    BigDecimal tax;

    public CarParkView(
            CarParkId id, String iataCode, String name, String description,
            CarParkAddress address, String supportEmail, String supportPhone,
            BigDecimal tax, CarParkStatus state
    ) {
        this.carParkId = id;
        this.iataCode = iataCode;
        this.name = name;
        this.description = description;
        this.address = address;
        this.supportEmail = supportEmail;
        this.supportPhone = supportPhone;
        this.tax = tax;
        this.carParkStatus = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CarParkView that = (CarParkView) o;
        return Objects.equals(carParkId, that.carParkId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carParkId);
    }

    @Override
    public String getIdentity() {
        return carParkId.getIdentity();
    }
}
