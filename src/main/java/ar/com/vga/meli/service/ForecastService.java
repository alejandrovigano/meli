package ar.com.vga.meli.service;

import ar.com.vga.meli.domain.weather.Forecast;
import ar.com.vga.meli.domain.weather.ForecastDay;

import java.util.Optional;

public interface ForecastService {

  Forecast calculateAndAnalyzeForecast();

  void calculateAndSaveForecastAsync();

  Optional<ForecastDay> getForecastDay(Long day);
}
