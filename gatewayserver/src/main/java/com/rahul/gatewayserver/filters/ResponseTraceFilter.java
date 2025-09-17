package com.rahul.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Configuration
public class ResponseTraceFilter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);

    private final FilterUtility filterUtility;

    public ResponseTraceFilter(FilterUtility filterUtility) {

        this.filterUtility = filterUtility;

    }

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) ->
                chain.filter(exchange)
                        .then(Mono.fromRunnable(() ->
                                Optional.ofNullable(filterUtility.getCorrelationId(exchange.getRequest().getHeaders()))
                                        .filter(correlationId -> !exchange.getResponse().getHeaders()
                                                .containsKey(FilterUtility.CORRELATION_ID))
                                        .ifPresent(correlationId -> {
                                            exchange.getResponse().getHeaders()
                                                    .add(FilterUtility.CORRELATION_ID, correlationId);
                                            logger.debug("Added correlation id to response headers: {}", correlationId);
                                        })
                        ));
    }

}
