package com.avondock.components.shared.gateway;

import com.avondock.components.common.util.UUIDConverter;
import com.avondock.components.shared.gateway.contracts.Query;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class QueryEndpoint {

    protected final QueryGateway queryGateway;
    protected final ObjectMapper mapper;

    public QueryEndpoint(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
        this.mapper = new ObjectMapper();
    }

    protected <T> CompletableFuture<CollectionModel<List<T>>> list(Query query, Class<T> readModel) {
        return queryGateway
                .query(query, ResponseTypes.multipleInstancesOf(readModel))
                .thenApply(this::wrapResultList);
    }

    @NotNull
    protected <T> CollectionModel<T> wrapResultList(T result) {
        return CollectionModel.of(List.of(result));
    }

    protected <T> CompletableFuture<ResponseEntity<T>> get(Query query, Class<T> readModel) {
        return queryGateway.query(query, ResponseTypes.instanceOf(readModel)).thenApply(this::wrapResult);
    }

    @NotNull
    protected <T> ResponseEntity<T> wrapResult(T result) {
        return wrapResult(Objects::isNull, result);
    }

    @NotNull
    protected <T> ResponseEntity<T> wrapResult(Predicate<T> assertResult, T result) {
        MultiValueMap<String, String> header = new HttpHeaders();
        header.add("Request-Id", UUIDConverter.toBase64());
        return assertResult.test(result) ? ResponseEntity.notFound().build() : new ResponseEntity<>(
                result, header, HttpStatus.OK
        );
    }

    protected <T> T map(String content, Class<T> valueType) {
        try {
            return mapper.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
