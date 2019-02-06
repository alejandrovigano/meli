package ar.com.vga.meli.service.impl;

import ar.com.vga.meli.domain.planet.PlanetInstant;
import ar.com.vga.meli.domain.planet.PlanetarySystem;
import ar.com.vga.meli.domain.planet.PlanetarySystemInstant;
import ar.com.vga.meli.domain.planet.Position;
import ar.com.vga.meli.domain.weather.Weather;
import ar.com.vga.meli.service.PlanetarySystemInstantService;
import ar.com.vga.meli.service.WeatherService;
import ar.com.vga.meli.util.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * WeatherService implementation
 *
 * @see WeatherService
 */
@Service
public class WeatherServiceImpl implements WeatherService {

  public static final BigDecimal DEGREE_180 = new BigDecimal(180);
  private final PlanetarySystem planetarySystem;
  private final PlanetarySystemInstantService planetarySystemInstantService;

  @Autowired
  public WeatherServiceImpl(PlanetarySystemInstantService planetarySystemInstantService, PlanetarySystem planetarySystem) {
    this.planetarySystemInstantService = planetarySystemInstantService;
    this.planetarySystem = planetarySystem;
  }

  @Override
  public Weather calculateWeather(Long day) {

    PlanetarySystem planetarySystem = getPlanetarySystem();
    PlanetarySystemInstant planetarySystemInstant = planetarySystemInstantService.getInstant(planetarySystem, day);

    Weather weather = Weather.normal();
    if (isDrought(planetarySystemInstant)) {
      weather = Weather.drought();
    } else if (isOptimalPressureAndTemp(planetarySystemInstant)) {
      weather = Weather.optimalPressureAndTemp();
    } else {
      BigDecimal rainfallIntensity = calculateRainfallIntensity(planetarySystemInstant);
      if (rainfallIntensity.compareTo(BigDecimal.ZERO) != 0) { //is raining
        weather = Weather.rainy(rainfallIntensity);
      }
    }
    return weather;
  }

  private boolean isDrought(PlanetarySystemInstant planetarySystemInstant) {
    List<BigDecimal> allDegrees = planetarySystemInstant.getPlanetInstants().stream()
      .map(PlanetInstant::getDegrees)
      .map(degree -> degree.compareTo(DEGREE_180) >= 0 ? degree.subtract(DEGREE_180) : degree) //move all to I & II quadrant
      .collect(Collectors.toList()); //collect all

    BigDecimal firstDegree = allDegrees.get(0);
    return allDegrees.stream().allMatch(degree -> degree.compareTo(firstDegree) == 0);
  }

  private boolean isOptimalPressureAndTemp(PlanetarySystemInstant planetarySystemInstant) {
    List<Position> planetsPositions = planetarySystemInstant.getPlanetInstants().stream()
      .map(PlanetInstant::getPosition)
      .collect(Collectors.toList());

    //FIXME: HECHO SOLO PARA 3 PLANETAS, PARA MAS DEBERIA USARSE OTRO ALGORITMO (RAYCAST  X EJ)
    Position position1 = planetsPositions.get(0);
    Position position2 = planetsPositions.get(1);
    Position position3 = planetsPositions.get(2);

    //obtengo la pendiente de la recta formada por p1p2 y p2p3
    BigDecimal slopeP1P2 = MathUtils.getSlope(position1, position2);
    BigDecimal slopeP1P3 = MathUtils.getSlope(position2, position3);

    //SCALE 2, 0,01 ERROR PROB
    return slopeP1P2.subtract(slopeP1P3).abs().compareTo(new BigDecimal("0.01")) <= 0;
  }

  private boolean isRainy(Position position1, Position position2, Position position3) {
    // IF areaOf(sun, planet1, planet2) + areaOf(sun, planet2, planet3) + areaOf(sun, planet1, planet3)
    // EQUALS triangle(planet1, planet2, planet3) THEN
    // THEN SUN is inside p1,p2,p3

    BigDecimal areaSunP1P2 = MathUtils.areaOfTriangle(Position.ZERO, position1, position2);
    BigDecimal areaSunP2P3 = MathUtils.areaOfTriangle(Position.ZERO, position2, position3);
    BigDecimal areaSunP1P3 = MathUtils.areaOfTriangle(Position.ZERO, position1, position3);
    BigDecimal areaP1P2P3 = MathUtils.areaOfTriangle(position1, position2, position3);

    BigDecimal summedAreas = areaSunP1P2.add(areaSunP2P3).add(areaSunP1P3);

    return areaP1P2P3.compareTo(summedAreas) == 0;
  }

  private BigDecimal calculateRainfallIntensity(PlanetarySystemInstant planetarySystemInstant) {
    List<Position> planetsPositions = planetarySystemInstant.getPlanetInstants().stream()
      .map(PlanetInstant::getPosition)
      .collect(Collectors.toList());

    //FIXME: HECHO SOLO PARA 3 PLANETAS, PARA MAS DEBERIA USARSE OTRO ALGORITMO (RAYCAST  X EJ)
    Position position1 = planetsPositions.get(0);
    Position position2 = planetsPositions.get(1);
    Position position3 = planetsPositions.get(2);

    boolean isRainy = isRainy(position1, position2, position3);

    BigDecimal rainfallIntensity = BigDecimal.ZERO;
    if (isRainy) {
      rainfallIntensity = MathUtils.perimeterOfTriangle(position1, position2, position3);
    }

    return rainfallIntensity;
  }

  private PlanetarySystem getPlanetarySystem() {
    return planetarySystem;
  }

}