package ar.com.vga.meli.domain.weather;

import javax.persistence.*;

/**
 * Model representing a day
 */
@Entity
public class ForecastDay {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private Long day;

  @Embedded
  private Weather weather;

  public ForecastDay() {
  }

  public ForecastDay(Long day, Weather weather) {
    this.day = day;
    this.weather = weather;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getDay() {
    return day;
  }

  public void setDay(Long day) {
    this.day = day;
  }

  public Weather getWeather() {
    return weather;
  }

  public void setWeather(Weather weather) {
    this.weather = weather;
  }
}
