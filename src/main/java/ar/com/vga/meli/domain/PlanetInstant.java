package ar.com.vga.meli.domain;

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

  /**
   * The position of the planet in the planetary system
   */
  private Position position;

  public PlanetInstant(Planet planet, BigDecimal degrees, Position position) {
    this.planet = planet;
    this.degrees = degrees;
    this.position = position;
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
