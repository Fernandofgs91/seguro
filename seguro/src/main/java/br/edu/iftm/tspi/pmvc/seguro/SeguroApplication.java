package br.edu.iftm.tspi.pmvc.seguro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeguroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeguroApplication.class, args);
	}

}
@Configuration

public static class CorsConfiguracao {

@Bean

public WebMvcConfigurer corsConfigurer() {

	return new WebMvcConfigurer() {

	@Override

public void addCorsMappings(CorsRegistry registry) {

	registry.addMapping("/**")

	.allowedMethods("HEAD","GET","PUT","POST","DELETE","PATCH");

				}

			};

		}

	}
}
