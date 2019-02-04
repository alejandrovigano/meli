package ar.com.vga.meli.domain;

import java.math.BigInteger;
import java.util.List;

/**
 * Representation of an instant of a planetary system
 */
public class PlanetarySystemInstant {

  /**
   * The planetary system
   */
  private PlanetarySystem planetarySystem;

  /**
   * Information of the planets in the instant
   */
  private List<PlanetInstant> planetInstants;

  /**
   * The day of the instant
   */
  private BigInteger day;

  public PlanetarySystemInstant(PlanetarySystem planetarySystem,
                                List<PlanetInstant> planetInstants,
                                BigInteger day) {

    this.planetarySystem = planetarySystem;
    this.planetInstants = planetInstants;
    this.day = day;
  }

  public PlanetarySystem getPlanetarySystem() {
    return planetarySystem;
  }

  public List<PlanetInstant> getPlanetInstants() {
    return planetInstants;
  }

  public BigInteger getDay() {
    return day;
  }
}
