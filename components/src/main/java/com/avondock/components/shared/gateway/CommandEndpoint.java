package com.avondock.components.shared.gateway;

import com.avondock.components.common.util.UUIDConverter;
import com.avondock.components.shared.gateway.contracts.Command;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.CompletableFuture;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

abstract public class CommandEndpoint {

    protected final CommandGateway commandGateway;

    public CommandEndpoint(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    protected <T> CompletableFuture<ResponseEntity<?>> send(Command<T> command) {
        return commandGateway.send(command).thenApply(ResponseEntity::ok);
    }

    protected <T> ResponseEntity<?> sendCreate(Command<T> command, RepresentationModel<?> model) {
        Object identity = commandGateway.sendAndWait(command);
        return createdResponse(model, identity.toString());
    }

    protected <T> ResponseEntity<?> sendUpdate(Command<T> command, RepresentationModel<?> model) {
        commandGateway.sendAndWait(command);
        return acceptedResponse(model, command.getIdentity().toString());
    }

    protected ResponseEntity<?> acceptedResponse(RepresentationModel<?> model, String identity) {
        model.add(linkTo(this.getClass()).slash(identity).withSelfRel());
        return response(model, HttpStatus.ACCEPTED);
    }

    protected ResponseEntity<?> createdResponse(RepresentationModel<?> model, String identity)  {
        model.add(linkTo(this.getClass()).slash(identity).withSelfRel());
        return response(model, HttpStatus.CREATED);
    }

    protected ResponseEntity<?> response(RepresentationModel<?> model, HttpStatus status) {
        MultiValueMap<String, String> header = new HttpHeaders();
        header.add("Request-Id", UUIDConverter.toBase64());
        return model == null  ? ResponseEntity.notFound().build() : new ResponseEntity<>(
                model, header, status
        );
    }
}
