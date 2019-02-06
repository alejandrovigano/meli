package ar.com.vga.meli.domain.planet;

import java.math.BigDecimal;

/**
 * Model representing a planet
 *
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

  /**
   *
   */
  private BigDecimal degreesAtInstantZero;

  /**
   * Planet builder
   */
  private Planet(Builder builder) {
    this.name = builder.name;
    this.angularVelocity = builder.angularVelocity;
    this.distanceToStar = builder.distanceToStar;
    this.degreesAtInstantZero = builder.degreesAtInstantZero;
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

  public BigDecimal getDegreesAtInstantZero() {
    return degreesAtInstantZero;
  }

  public static class Builder {

    private String name;
    private BigDecimal angularVelocity;
    private BigDecimal distanceToStar;
    private BigDecimal degreesAtInstantZero;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder angularVelocity(BigDecimal angularVelocity) {
      this.angularVelocity = angularVelocity;
      return this;
    }

    public Builder distanceToStar(BigDecimal distanceToStar) {
      this.distanceToStar = distanceToStar;
      return this;
    }

    public Builder degreesAtInstantZero(BigDecimal degreesAtInstantZero) {
      this.degreesAtInstantZero = degreesAtInstantZero;
      return this;
    }

    public Planet build() {
      assert name != null : "name is null";
      assert angularVelocity != null : "angularVelocity is null";
      assert distanceToStar != null : "distanceToStar is null";
      assert degreesAtInstantZero != null : "degreesAtInstantZero is null";
      return new Planet(this);
    }

  }

}
