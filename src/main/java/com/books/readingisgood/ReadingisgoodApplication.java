package com.books.readingisgood;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(
		name = "Bearer Auth",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
@OpenAPIDefinition(
		info = @Info(title = "Reading Is Good API", version = "v1"),
		tags = {@Tag(name = "Book Service",description = "Create new books and update their stocks"),
				@Tag(name = "Authentication Service", description = "Register as a new customer and " +
																	"Login to generate JWT to token " +
																	"To use in all subsequent requests"),
				@Tag(name = "Statistics Service", description = "Retrieve monthly order statistics of a customer"),
				@Tag(name = "Order Service", description = "Place an Order, keep track of new orders and retrieve" +
						                                   "Orders between a date interval"),
				@Tag(name = "Customer Service", description = "Get all paginated orders of a customer")
		}
)
public class ReadingisgoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReadingisgoodApplication.class, args);
	}

}
