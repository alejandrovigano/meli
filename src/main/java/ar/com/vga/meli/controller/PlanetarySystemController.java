package ar.com.vga.meli.controller;

import ar.com.vga.meli.domain.planet.PlanetarySystem;
import ar.com.vga.meli.domain.planet.PlanetarySystemInstant;
import ar.com.vga.meli.service.PlanetarySystemInstantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller made to debug purposes
 */
@RestController
@RequestMapping("/sistema-planetario")
public class PlanetarySystemController {

  @Autowired
  private PlanetarySystem planetarySystem;

  @Autowired
  private PlanetarySystemInstantService planetarySystemInstantService;

  @GetMapping
  public PlanetarySystemInstant getPlanetarySystemInstant(@RequestParam Long dia) {
    return planetarySystemInstantService.getInstant(planetarySystem, dia);
  }

}
