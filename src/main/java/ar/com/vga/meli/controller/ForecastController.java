package ar.com.vga.meli.controller;

import ar.com.vga.meli.domain.weather.Forecast;
import ar.com.vga.meli.domain.weather.ForecastDay;
import ar.com.vga.meli.resource.WeatherResource;
import ar.com.vga.meli.service.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/clima")
public class ForecastController {

  @Autowired
  private ForecastService forecastService;

  @GetMapping("/procesar")
  //@PostMapping
  public void runJob() {
    forecastService.calculateAndSaveForecastAsync();
  }

  @GetMapping("/analizar")
  public Forecast analyze() {
    return forecastService.calculateAndAnalyzeForecast();
  }

  @GetMapping()
  public ResponseEntity<WeatherResource> getWeather(Long dia) {
    Optional<ForecastDay> forecastDayOPt = forecastService.getForecastDay(dia);

    return forecastDayOPt
      .map(fDay -> new WeatherResource(String.valueOf(fDay.getDay()), fDay.getWeather().getStatus().getDescription()))
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

}
