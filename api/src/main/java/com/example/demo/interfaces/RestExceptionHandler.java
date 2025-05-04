package com.example.demo.interfaces;

import com.example.demo.domain.exception.InvalidTokenException;
import com.example.demo.domain.exception.PostNotFoundException;
import com.example.demo.domain.exception.TokenExpiredException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(-2)
@Slf4j
@RequiredArgsConstructor
public class RestExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof WebExchangeBindException) {
            return handleValidationException(exchange, (WebExchangeBindException) ex);
        } else if (ex instanceof PostNotFoundException) {
            return handleNotFoundException(exchange);
        } else if (ex instanceof InvalidTokenException) {
            return handleTokenException(exchange, (InvalidTokenException) ex);
        } else if (ex instanceof TokenExpiredException) {
            return handleTokenExpiredException(exchange, (TokenExpiredException) ex);
        }
        return Mono.error(ex);
    }

    private Mono<Void> handleValidationException(ServerWebExchange exchange, WebExchangeBindException ex) {
        log.debug("errors:" + ex.getFieldErrors());
        var errors = new Errors("validation_failure", "Validation failed.");
        ex.getFieldErrors().forEach(e -> errors.add(e.getField(), e.getCode(), e.getDefaultMessage()));

        log.debug("handled errors::" + errors);
        return writeErrorResponse(exchange, HttpStatus.UNPROCESSABLE_ENTITY, errors);
    }

    private Mono<Void> handleNotFoundException(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> handleTokenException(ServerWebExchange exchange, InvalidTokenException ex) {
        var errors = new Errors("invalid_token", ex.getMessage());
        return writeErrorResponse(exchange, HttpStatus.BAD_REQUEST, errors);
    }

    private Mono<Void> handleTokenExpiredException(ServerWebExchange exchange, TokenExpiredException ex) {
        var errors = new Errors("token_expired", ex.getMessage());
        errors.add("token", "expired", "The verification token has expired");
        return writeErrorResponse(exchange, HttpStatus.BAD_REQUEST, errors);
    }

    private Mono<Void> writeErrorResponse(ServerWebExchange exchange, HttpStatus status, Errors errors) {
        try {
            exchange.getResponse().setStatusCode(status);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            var db = new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(errors));
            return exchange.getResponse().writeWith(Mono.just(db));
        } catch (JsonProcessingException e) {
            log.error("Error writing error response", e);
            return Mono.empty();
        }
    }
}

@Getter
@ToString
class Errors implements Serializable {
    private String code;
    private String message;
    private List<Error> errors = new ArrayList<>();

    @JsonCreator
    Errors(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void add(String path, String code, String message) {
        this.errors.add(new Error(path, code, message));
    }
}

@Getter
@ToString
class Error implements Serializable {
    private String path;
    private String code;
    private String message;

    @JsonCreator
    Error(String path, String code, String message) {
        this.path = path;
        this.code = code;
        this.message = message;
    }
}



