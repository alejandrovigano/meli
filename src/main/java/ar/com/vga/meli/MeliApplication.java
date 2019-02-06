package ar.com.vga.meli;

import ar.com.vga.meli.domain.planet.Planet;
import ar.com.vga.meli.domain.planet.PlanetarySystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories
public class MeliApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(MeliApplication.class, args);
  }

  @Bean
  public PlanetarySystem planetarySystem() {
    Planet ferengi = new Planet.Builder()
      .name("Ferengi")
      .distanceToStar(new BigDecimal(500))
      .angularVelocity(new BigDecimal(1))
      .degreesAtInstantZero(BigDecimal.ZERO)
      .build();

    Planet betasoide = new Planet.Builder()
      .name("Betasoide")
      .distanceToStar(new BigDecimal(2000))
      .angularVelocity(new BigDecimal(3))
      .degreesAtInstantZero(BigDecimal.ZERO)
      .build();

    Planet vulcano = new Planet.Builder()
      .name("Vulcano")
      .distanceToStar(new BigDecimal(1000))
      .angularVelocity(new BigDecimal(-5))
      .degreesAtInstantZero(BigDecimal.ZERO)
      .build();

    List<Planet> planets = new ArrayList<>();
    planets.add(ferengi);
    planets.add(betasoide);
    planets.add(vulcano);

    return new PlanetarySystem(planets);
  }

}

