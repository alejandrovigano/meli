package ar.com.vga.meli.service;

import ar.com.vga.meli.domain.planet.PlanetarySystem;
import ar.com.vga.meli.domain.planet.PlanetarySystemInstant;

/**
 * Service layer, contains the logic related to the planetary system at specific instants
 */
public interface PlanetarySystemInstantService {

  /**
   * Gets an instant of a planetary system
   * @param planetarySystem the planetary system
   * @param day the day
   * @return the instant
   */
  PlanetarySystemInstant getInstant(PlanetarySystem planetarySystem, Long day);

}
