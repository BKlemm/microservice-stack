package com.avondock.components.shared.gateway.contracts;

public interface Command<T> {
    T getIdentity();
}
