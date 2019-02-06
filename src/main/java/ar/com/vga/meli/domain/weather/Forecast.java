package ar.com.vga.meli.domain.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Forecast {

  /**
   * Days with drought
   */
  private List<ForecastDay> droughtDays;

  /**
   * Periods with rains
   */
  private List<RainPeriod> rainPeriods;

  /**
   * Days with optimal pressure and temp
   */
  private List<ForecastDay> optimalPTDays;

  /**
   * The total amount of days
   */
  private Integer totalDays;

  public Forecast(List<ForecastDay> droughtDays, List<RainPeriod> rainPeriods, List<ForecastDay> optimalPTDays, Integer totalDays) {
    this.droughtDays = droughtDays;
    this.rainPeriods = rainPeriods;
    this.optimalPTDays = optimalPTDays;
    this.totalDays = totalDays;
  }

  @JsonProperty( "dias_sequia" )
  public List<ForecastDay> getDroughtDays() {
    return droughtDays;
  }

  @JsonProperty( "cantidad_dias_sequia" )
  public Integer getDroughtDaysCount() {
    return droughtDays != null ? droughtDays.size() : 0;
  }

  @JsonProperty( "periodos_lluvia" )
  public List<RainPeriod> getRainPeriods() {
    return rainPeriods;
  }

  @JsonProperty( "cantidad_periodos_lluvia")
  public Integer getRainPeriodsCount() {
    return rainPeriods != null ? rainPeriods.size() : 0;
  }

  @JsonProperty( "dias_pyt_optimos" )
  public List<ForecastDay> getOptimalPTDays() {
    return optimalPTDays;
  }

  @JsonProperty( "cantidad_dias_pyt_optimos" )
  public Integer getOptimalPTDaysCount() {
    return optimalPTDays != null ? optimalPTDays.size() : 0;
  }

  @JsonProperty( "cantidad_dias_total" )
  public Integer getTotalDays() {
    return totalDays;
  }
}
