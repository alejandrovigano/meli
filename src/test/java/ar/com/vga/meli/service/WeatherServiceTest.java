package ar.com.vga.meli.service;

import ar.com.vga.meli.domain.planet.Planet;
import ar.com.vga.meli.domain.planet.PlanetInstant;
import ar.com.vga.meli.domain.planet.PlanetarySystem;
import ar.com.vga.meli.domain.planet.PlanetarySystemInstant;
import ar.com.vga.meli.domain.weather.Weather;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WeatherServiceTest {

  @Autowired
  private WeatherService weatherService;

  @MockBean
  private PlanetarySystemInstantService planetarySystemInstantService;

  @Test
  public void droughtTest() {


    List<PlanetInstant> instants = new ArrayList<>();
    PlanetInstant planetInstant = new PlanetInstant(dummyPlanet(), new BigDecimal(180));
    PlanetInstant planetInstant1 = new PlanetInstant(dummyPlanet(), new BigDecimal(0));
    PlanetInstant planetInstant2 = new PlanetInstant(dummyPlanet(), new BigDecimal(180));

    instants.add(planetInstant);
    instants.add(planetInstant1);
    instants.add(planetInstant2);
    PlanetarySystemInstant instant = new PlanetarySystemInstant(dummySystem(), instants, 0L);

    //given
    given(planetarySystemInstantService.getInstant(any(), anyLong())).willReturn(instant);

    //when
    Weather weather = weatherService.calculateWeather(0L);

    //then
    assertEquals(Weather.WeatherStatus.DROUGHT, weather.getStatus());
  }

  @Test
  public void drought2Test() {


    List<PlanetInstant> instants = new ArrayList<>();
    PlanetInstant planetInstant = new PlanetInstant(dummyPlanet(), new BigDecimal(30));
    PlanetInstant planetInstant1 = new PlanetInstant(dummyPlanet(), new BigDecimal(30));
    PlanetInstant planetInstant2 = new PlanetInstant(dummyPlanet(), new BigDecimal(210));

    instants.add(planetInstant);
    instants.add(planetInstant1);
    instants.add(planetInstant2);
    PlanetarySystemInstant instant = new PlanetarySystemInstant(dummySystem(), instants, 0L);

    //given
    given(planetarySystemInstantService.getInstant(any(), anyLong())).willReturn(instant);

    //when
    Weather weather = weatherService.calculateWeather(0L);

    //then
    assertEquals(Weather.WeatherStatus.DROUGHT, weather.getStatus());
  }

  @Test
  public void rainyTest() {
    List<PlanetInstant> instants = new ArrayList<>();
    PlanetInstant planetInstant = new PlanetInstant(dummyPlanet(new BigDecimal(100)), new BigDecimal(0));
    PlanetInstant planetInstant1 = new PlanetInstant(dummyPlanet(new BigDecimal(100)), new BigDecimal(120));
    PlanetInstant planetInstant2 = new PlanetInstant(dummyPlanet(new BigDecimal(100)), new BigDecimal(270));

    instants.add(planetInstant);
    instants.add(planetInstant1);
    instants.add(planetInstant2);
    PlanetarySystemInstant instant = new PlanetarySystemInstant(dummySystem(), instants, 0L);

    //given
    given(planetarySystemInstantService.getInstant(any(), anyLong())).willReturn(instant);

    //when
    Weather weather = weatherService.calculateWeather(0L);

    //then
    assertEquals(Weather.WeatherStatus.RAINY, weather.getStatus());
  }

  @Test
  public void noRainyTest() {
    List<PlanetInstant> instants = new ArrayList<>();
    PlanetInstant planetInstant = new PlanetInstant(dummyPlanet(new BigDecimal(100)), new BigDecimal(30));
    PlanetInstant planetInstant1 = new PlanetInstant(dummyPlanet(new BigDecimal(150)), new BigDecimal(60));
    PlanetInstant planetInstant2 = new PlanetInstant(dummyPlanet(new BigDecimal(200)), new BigDecimal(90));

    instants.add(planetInstant);
    instants.add(planetInstant1);
    instants.add(planetInstant2);
    PlanetarySystemInstant instant = new PlanetarySystemInstant(dummySystem(), instants, 0L);

    //given
    given(planetarySystemInstantService.getInstant(any(), anyLong())).willReturn(instant);

    //when
    Weather weather = weatherService.calculateWeather(0L);

    //then
    assertEquals(Weather.WeatherStatus.NORMAL, weather.getStatus());
  }

  @Test
  public void optimalPressureAndTempTest() {
    List<PlanetInstant> instants = new ArrayList<>();
    PlanetInstant planetInstant = new PlanetInstant(dummyPlanet(new BigDecimal(200)), new BigDecimal(60));
    PlanetInstant planetInstant1 = new PlanetInstant(dummyPlanet(new BigDecimal(174)), new BigDecimal(90));
    PlanetInstant planetInstant2 = new PlanetInstant(dummyPlanet(new BigDecimal(200)), new BigDecimal(120));

    instants.add(planetInstant);
    instants.add(planetInstant1);
    instants.add(planetInstant2);
    PlanetarySystemInstant instant = new PlanetarySystemInstant(dummySystem(), instants, 0L);

    //given
    given(planetarySystemInstantService.getInstant(any(), anyLong())).willReturn(instant);

    //when
    Weather weather = weatherService.calculateWeather(0L);

    //then
    assertEquals(Weather.WeatherStatus.OPTIMAL_P_T, weather.getStatus());
  }

  //UTIL

  private PlanetarySystem dummySystem() {
    return new PlanetarySystem(new ArrayList<>());
  }

  private Planet dummyPlanet(BigDecimal distanceToSta) {
    return new Planet.Builder()
      .angularVelocity(BigDecimal.ZERO)
      .degreesAtInstantZero(BigDecimal.ZERO)
      .distanceToStar(distanceToSta)
      .name("Dummy")
      .build();
  }

  private Planet dummyPlanet() {
    return dummyPlanet(BigDecimal.ZERO);
  }

}
