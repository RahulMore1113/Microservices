package com.rahul.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class FilterUtility {

    public static final String CORRELATION_ID = "eazybank-correlation-id";

    public String getCorrelationId(HttpHeaders headers) {

        return headers.getFirst(CORRELATION_ID); // concise & null-safe

    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {

        return exchange.mutate()
                .request(r -> r.header(name, value))
                .build();

    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {

        return setRequestHeader(exchange, CORRELATION_ID, correlationId);

    }
}
