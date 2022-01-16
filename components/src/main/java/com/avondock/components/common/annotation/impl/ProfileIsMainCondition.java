package com.avondock.components.common.annotation.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

public class ProfileIsMainCondition implements Condition {

    @Override
    public boolean matches(@NotNull ConditionContext conditionContext, @NotNull AnnotatedTypeMetadata annotatedTypeMetadata) {
        return Objects.equals(System.getProperty("spring.profiles.active"), "main");
    }
}
