package ar.com.vga.meli.domain;

import java.math.BigDecimal;

/**
 * Model representing a planet
 * @author alejandrovigano
 */
public class Planet {

  /**
   * The name of the planet
   */
  private String name;

  /**
   * Angular velocity magnitude (grad/day)
   */
  private BigDecimal angularVelocity;

  /**
   * Distance to the star
   */
  private BigDecimal distanceToStar;

  public Planet(String name, BigDecimal angularVelocity, BigDecimal distanceToStar) {
    this.name = name;
    this.angularVelocity = angularVelocity;
    this.distanceToStar = distanceToStar;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getAngularVelocity() {
    return angularVelocity;
  }

  public BigDecimal getDistanceToStar() {
    return distanceToStar;
  }

}
