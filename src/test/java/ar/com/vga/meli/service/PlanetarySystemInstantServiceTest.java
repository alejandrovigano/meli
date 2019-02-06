package ar.com.vga.meli.service;

import ar.com.vga.meli.domain.planet.Planet;
import ar.com.vga.meli.domain.planet.PlanetInstant;
import ar.com.vga.meli.domain.planet.PlanetarySystem;
import ar.com.vga.meli.domain.planet.PlanetarySystemInstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@SpringBootTest
@RunWith(SpringRunner.class)
public class PlanetarySystemInstantServiceTest {

  @Autowired
  private PlanetarySystemInstantService planetarySystemInstantService;

  @Test
  public void getInstantZeroTest() {

    //given
    Planet planet1 = new Planet.Builder()
      .name("Primero")
      .angularVelocity(new BigDecimal(10))
      .distanceToStar(new BigDecimal(500))
      .degreesAtInstantZero(BigDecimal.ZERO)
      .build();


    Planet planet2 = new Planet.Builder()
      .name("Segundo")
      .angularVelocity(new BigDecimal(10))
      .distanceToStar(new BigDecimal(500))
      .degreesAtInstantZero(BigDecimal.ZERO)
      .build();

    Planet planet3 = new Planet.Builder()
      .name("Tercero")
      .angularVelocity(new BigDecimal(10))
      .distanceToStar(new BigDecimal(500))
      .degreesAtInstantZero(BigDecimal.ZERO)
      .build();


    List<Planet> planets = new ArrayList<>();

    planets.add(planet1);
    planets.add(planet2);
    planets.add(planet3);

    PlanetarySystem planetarySystem = new PlanetarySystem(planets);

    //when
    PlanetarySystemInstant instant = planetarySystemInstantService.getInstant(planetarySystem, 0L);

    //then
    assertEquals(Long.valueOf(0L), instant.getDay());
    assertEquals(planetarySystem, instant.getPlanetarySystem());

    List<PlanetInstant> planetInstants = instant.getPlanetInstants();

    assertTrue(BigDecimal.ZERO.compareTo(planetInstants.get(0).getDegrees())==0);
    assertTrue(new BigDecimal(500).compareTo(planetInstants.get(0).getPosition().getX()) == 0 );
    assertTrue(BigDecimal.ZERO.compareTo(planetInstants.get(0).getPosition().getY()) == 0);

    assertTrue(BigDecimal.ZERO.compareTo(planetInstants.get(1).getDegrees()) ==0 );
    assertTrue(new BigDecimal(500).compareTo(planetInstants.get(1).getPosition().getX()) == 0 );
    assertTrue(BigDecimal.ZERO.compareTo(planetInstants.get(1).getPosition().getY()) == 0 );


    assertTrue(BigDecimal.ZERO.compareTo(planetInstants.get(2).getDegrees()) == 0 );
    assertTrue(new BigDecimal(500).compareTo(planetInstants.get(2).getPosition().getX()) == 0 );
    assertTrue(BigDecimal.ZERO.compareTo(planetInstants.get(2).getPosition().getY()) == 0 );


  }

  @Test
  public void getInstantDay1Test() {
    //given
    Planet planet1 = new Planet.Builder()
      .name("Primero")
      .angularVelocity(new BigDecimal(10))
      .distanceToStar(new BigDecimal(500))
      .degreesAtInstantZero(BigDecimal.ZERO)
      .build();

    Planet planet2 = new Planet.Builder()
      .name("Segundo")
      .angularVelocity(new BigDecimal(15))
      .distanceToStar(new BigDecimal(500))
      .degreesAtInstantZero(new BigDecimal(200))
      .build();

    List<Planet> planets = new ArrayList<>();
    planets.add(planet1);
    planets.add(planet2);

    PlanetarySystem planetarySystem = new PlanetarySystem(planets);

    //when
    PlanetarySystemInstant instant = planetarySystemInstantService.getInstant(planetarySystem, 1L);

    //then
    assertEquals(Long.valueOf(1L), instant.getDay());

    List<PlanetInstant> planetInstants = instant.getPlanetInstants();
    assertTrue(new BigDecimal(10).compareTo(planetInstants.get(0).getDegrees()) == 0);
    assertTrue(new BigDecimal(215).compareTo(planetInstants.get(1).getDegrees()) == 0);
  }

  @Test
  public void getInstantDay15Test() {
    //given
    Planet planet1 = new Planet.Builder()
      .name("Primero")
      .angularVelocity(new BigDecimal(10))
      .distanceToStar(new BigDecimal(500))
      .degreesAtInstantZero(BigDecimal.ZERO)
      .build();

    Planet planet2 = new Planet.Builder()
      .name("Segundo")
      .angularVelocity(new BigDecimal(15))
      .distanceToStar(new BigDecimal(500))
      .degreesAtInstantZero(new BigDecimal(200))
      .build();

    List<Planet> planets = new ArrayList<>();
    planets.add(planet1);
    planets.add(planet2);

    PlanetarySystem planetarySystem = new PlanetarySystem(planets);

    //when
    PlanetarySystemInstant instant = planetarySystemInstantService.getInstant(planetarySystem, 15L);

    //then
    assertEquals(Long.valueOf(15L), instant.getDay());

    List<PlanetInstant> planetInstants = instant.getPlanetInstants();
    assertTrue(new BigDecimal(150).compareTo(planetInstants.get(0).getDegrees()) == 0); //10 * 15 + 0
    assertTrue(new BigDecimal(65).compareTo(planetInstants.get(1).getDegrees()) == 0); // (15 * 15 + 200) % 360
  }

  @Test
  public void getInstantDay366Test() {
    //given
    Planet planet1 = new Planet.Builder()
      .name("Primero")
      .angularVelocity(new BigDecimal(10))
      .distanceToStar(new BigDecimal(500))
      .degreesAtInstantZero(BigDecimal.ZERO)
      .build();

    Planet planet2 = new Planet.Builder()
      .name("Segundo")
      .angularVelocity(new BigDecimal(15))
      .distanceToStar(new BigDecimal(500))
      .degreesAtInstantZero(new BigDecimal(200))
      .build();

    List<Planet> planets = new ArrayList<>();
    planets.add(planet1);
    planets.add(planet2);

    PlanetarySystem planetarySystem = new PlanetarySystem(planets);

    //when
    PlanetarySystemInstant instant = planetarySystemInstantService.getInstant(planetarySystem, 366L);

    //then
    assertEquals(Long.valueOf(366L), instant.getDay());

    List<PlanetInstant> planetInstants = instant.getPlanetInstants();
    assertTrue(new BigDecimal(60).compareTo(planetInstants.get(0).getDegrees()) == 0); //10 * 366 + 0
    assertTrue(new BigDecimal(290).compareTo(planetInstants.get(1).getDegrees()) == 0); // (15 * 15 + 200) % 360
  }

  @Test
  public void getInstantDayNegativeAngularVelTest() {
    //given
    Planet planet1 = new Planet.Builder()
      .name("Primero")
      .angularVelocity(new BigDecimal(-10))
      .distanceToStar(new BigDecimal(500))
      .degreesAtInstantZero(BigDecimal.ZERO)
      .build();

    List<Planet> planets = new ArrayList<>();
    planets.add(planet1);

    PlanetarySystem planetarySystem = new PlanetarySystem(planets);

    //when
    PlanetarySystemInstant instant = planetarySystemInstantService.getInstant(planetarySystem, 10L);

    //then
    assertEquals(Long.valueOf(10L), instant.getDay());

    List<PlanetInstant> planetInstants = instant.getPlanetInstants();
    assertTrue(new BigDecimal(260).compareTo(planetInstants.get(0).getDegrees()) == 0); //10 * 366 + 0
  }

  @Test
  public void getInstantDay365NegativeAngularVelTest() {
    //given
    Planet planet1 = new Planet.Builder()
      .name("Primero")
      .angularVelocity(new BigDecimal(-10))
      .distanceToStar(new BigDecimal(500))
      .degreesAtInstantZero(BigDecimal.ZERO)
      .build();

    List<Planet> planets = new ArrayList<>();
    planets.add(planet1);

    PlanetarySystem planetarySystem = new PlanetarySystem(planets);

    //when
    PlanetarySystemInstant instant = planetarySystemInstantService.getInstant(planetarySystem, 365L);

    //then
    assertEquals(Long.valueOf(365L), instant.getDay());

    List<PlanetInstant> planetInstants = instant.getPlanetInstants();
    assertTrue(new BigDecimal(310).compareTo(planetInstants.get(0).getDegrees()) == 0); //10 * 366 + 0
  }

}

