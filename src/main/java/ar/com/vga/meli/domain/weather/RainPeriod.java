package ar.com.vga.meli.domain.weather;

import java.math.BigDecimal;

public class RainPeriod {

  private Long rainDayStart;
  private Long rainDayEnd;
  private BigDecimal maxRainfallIntensity;
  private Long rainDayWithMaxRainfallIntensity;

  public RainPeriod(ForecastDay forecastDay) {
    this.rainDayStart = forecastDay.getDay();
    this.rainDayEnd = forecastDay.getDay();
    this.maxRainfallIntensity = forecastDay.getWeather().getRainfallIntensity();
    this.rainDayWithMaxRainfallIntensity = forecastDay.getDay();
  }

  public Long getRainDayStart() {
    return rainDayStart;
  }

  public Long getRainDayEnd() {
    return rainDayEnd;
  }

  public BigDecimal getMaxRainfallIntensity() {
    return maxRainfallIntensity;
  }

  public Long getRainDayWithMaxRainfallIntensity() {
    return rainDayWithMaxRainfallIntensity;
  }

  public void addDay(ForecastDay forecastDay) {
    this.rainDayEnd = forecastDay.getDay();
    if (this.maxRainfallIntensity.compareTo(forecastDay.getWeather().getRainfallIntensity()) < 0) {
      this.maxRainfallIntensity = forecastDay.getWeather().getRainfallIntensity();
      this.rainDayWithMaxRainfallIntensity = forecastDay.getDay();
    }
  }
}
