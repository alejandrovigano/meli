package ar.com.vga.meli.domain.planet;

import java.math.BigDecimal;

/**
 * Representation of an instant of a planet in a planetary system
 */
public class PlanetInstant {

  /**
   * The planet
   */
  private Planet planet;

  /**
   * Degrees from the origin and the planet actual position. The star is the vertex
   */
  private BigDecimal degrees;

  private Position position;

  public PlanetInstant(Planet planet, BigDecimal degrees) {
    this.planet = planet;
    this.degrees = degrees;
    this.position = Position.from(degrees, getPlanet().getDistanceToStar());
  }

  public Planet getPlanet() {
    return planet;
  }

  public BigDecimal getDegrees() {
    return degrees;
  }

  public Position getPosition() {
    return position;
  }

}
