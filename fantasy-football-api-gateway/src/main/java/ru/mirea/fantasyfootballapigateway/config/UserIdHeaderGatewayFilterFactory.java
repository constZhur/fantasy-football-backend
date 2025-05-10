package ru.mirea.fantasyfootballapigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.mirea.fantasyfootballapigateway.dto.AuthCheckDTO;

import java.util.Objects;

@Component
public class UserIdHeaderGatewayFilterFactory extends
        AbstractGatewayFilterFactory<UserIdHeaderGatewayFilterFactory.Config> {

    @Value("${api.auth.host}")
    private String checkUri;

    private final WebClient.Builder webClientBuilder;

    UserIdHeaderGatewayFilterFactory(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public String name() {
        return "UserIdHeader";
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            HttpCookie cookie = exchange.getRequest().getCookies().getFirst("JSESSIONID");
            if (cookie != null) {
                return webClientBuilder.build()
                        .get()
                        .uri(checkUri + "/api/auth/check") // URL другого микросервиса
                        .cookie("JSESSIONID", Objects.requireNonNull(exchange.getRequest().getCookies().getFirst("JSESSIONID")).getValue())
                        .retrieve()
                        .toEntity(AuthCheckDTO.class)
                        .flatMap(responseEntity -> {
                            if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                return exchange.getResponse().setComplete();
                            }
                            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                                AuthCheckDTO result = responseEntity.getBody();
                                exchange.getRequest().mutate()
                                        .header("X-User-Id", result.userId())
                                        .header("X-Token", result.token())
                                        .build();
                                return chain.filter(exchange);
                            }
                            return chain.filter(exchange);
                        });
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }

    public static class Config {

    }
}
