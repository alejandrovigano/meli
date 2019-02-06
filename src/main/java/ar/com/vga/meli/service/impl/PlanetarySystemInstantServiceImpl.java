package ar.com.vga.meli.service.impl;

import ar.com.vga.meli.domain.planet.Planet;
import ar.com.vga.meli.domain.planet.PlanetInstant;
import ar.com.vga.meli.domain.planet.PlanetarySystem;
import ar.com.vga.meli.domain.planet.PlanetarySystemInstant;
import ar.com.vga.meli.service.PlanetarySystemInstantService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of PlanetarySystemInstantService
 * @see ar.com.vga.meli.service.PlanetarySystemInstantService
 */
@Service
public class PlanetarySystemInstantServiceImpl implements PlanetarySystemInstantService {

  public static final BigDecimal MAX_DEGREE = new BigDecimal(360);

  @Override
  public PlanetarySystemInstant getInstant(PlanetarySystem planetarySystem, Long instant) {

    List<PlanetInstant> planetInstants = planetarySystem.getPlanets().stream()
      .map((planet) -> getInstant(planet, instant))
      .collect(Collectors.toList());

    return new PlanetarySystemInstant(planetarySystem, planetInstants, instant);
  }

  private PlanetInstant getInstant(Planet planet, Long instant) {

    BigDecimal startPosition = planet.getDegreesAtInstantZero();
    BigDecimal velocity = planet.getAngularVelocity();

    BigDecimal finalPosition = velocity.multiply(new BigDecimal(instant)).add(startPosition);

    BigDecimal normalizedPosition = finalPosition.remainder(MAX_DEGREE);

    BigDecimal absolutePosition = normalizedPosition.compareTo(BigDecimal.ZERO) == -1
      ? normalizedPosition.add(MAX_DEGREE)
      : normalizedPosition;

    return new PlanetInstant(planet, absolutePosition);
  }

}