package ar.com.vga.meli.service;

import ar.com.vga.meli.domain.weather.Weather;

/**
 * Weather prediction service
 */
public interface WeatherService {

  /**
   * Calculates the weather with the given day
   * @param day the day
   * @return the weather
   */
  Weather calculateWeather(Long day);


}
