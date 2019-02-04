package ar.com.vga.meli.domain;

import java.util.List;

/**
 * Representation of a, single start, planetary system.
 * Orbits are perfect circles
 */
public class PlanetarySystem {

  /**
   * Planets of the planetary system
   */
  private List<Planet> planets;

  public PlanetarySystem(List<Planet> planets) {
    this.planets = planets;
  }

  public List<Planet> getPlanets() {
    return planets;
  }

}
