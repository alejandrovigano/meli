package ar.com.vga.meli.domain.weather;

import java.math.BigDecimal;

/**
 * Representation of the weather at a specific instant
 */
public class Weather {

  private static Weather DROUGHT = new Weather(WeatherStatus.DROUGHT);
  private static Weather NORMAL = new Weather(WeatherStatus.NORMAL);
  private static Weather OPTIMAL_P_T = new Weather(WeatherStatus.OPTIMAL_P_T);

  private WeatherStatus status;
  private BigDecimal rainfallIntensity = BigDecimal.ZERO;

  public Weather() {
  }

  private Weather(WeatherStatus status) {
    this.status = status;
  }

  private Weather(WeatherStatus status, BigDecimal rainfallIntensity) {
    this.status = status;
    this.rainfallIntensity = rainfallIntensity;
  }

  public static Weather rainy(BigDecimal rainfallIntensity){
    return new Weather(WeatherStatus.RAINY, rainfallIntensity);
  }

  public static Weather drought() {
    return DROUGHT;
  }

  public static Weather normal() {
    return NORMAL;
  }

  public static Weather optimalPressureAndTemp() {
    return OPTIMAL_P_T;
  }

  public WeatherStatus getStatus() {
    return status;
  }

  public BigDecimal getRainfallIntensity() {
    return rainfallIntensity;
  }

  public enum WeatherStatus {
    DROUGHT("Sequía"),
    NORMAL("Normal"),
    RAINY("Lluvia"),
    OPTIMAL_P_T("Condiciones óptimas de presión y temperatura");

    private String description;

    WeatherStatus(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }

}
