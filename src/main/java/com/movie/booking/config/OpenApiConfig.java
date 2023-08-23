package com.movie.booking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Movie Booking App")
                        .description("Movie Booking App is an application that allows the users to register and login and search movies released. " +
                                "User can book the tickets for the movies they wish for. Admin shall view the tickets booked and update the pending " +
                                "tickets available to the system.")
                        .version("1.0"));
    }
}
