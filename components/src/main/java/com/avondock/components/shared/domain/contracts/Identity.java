package com.avondock.components.shared.domain.contracts;

import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.MappedSuperclass;
import java.util.UUID;

@ToString
@MappedSuperclass
abstract public class Identity {

    protected String id;

    public Identity() {
        this(UUID.randomUUID().toString());
    }

    public Identity(String id) {
        Assert.notNull(id, "Identifier may not be null");
        this.id = id;
    }


}
