package ar.com.vga.meli.domain.planet;

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
   * Information of the planets at this instant
   */
  private List<PlanetInstant> planetInstants;

  /**
   * The instant
   */
  private Long day;

  public PlanetarySystemInstant(PlanetarySystem planetarySystem,
                                List<PlanetInstant> planetInstants,
                                Long day) {

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

  public Long getDay() {
    return day;
  }
}
