package ar.com.vga.meli.service.impl;

import ar.com.vga.meli.domain.weather.Forecast;
import ar.com.vga.meli.domain.weather.ForecastDay;
import ar.com.vga.meli.domain.weather.RainPeriod;
import ar.com.vga.meli.domain.weather.Weather;
import ar.com.vga.meli.repository.ForecastDayRepository;
import ar.com.vga.meli.service.ForecastService;
import ar.com.vga.meli.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.stream.LongStream;

@Service
public class ForecastServiceImpl implements ForecastService {

  private static final Logger LOGGER = Logger.getLogger(ForecastServiceImpl.class.getName());

  public static final int N_THREADS = 4;

  private WeatherService weatherService;
  private ForecastDayRepository forecastDayRepository;
  private ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
  private Long YEARS_TO_CALCULATE = 10L;

  @Autowired
  public ForecastServiceImpl(WeatherService weatherService, ForecastDayRepository forecastDayRepository) {
    this.weatherService = weatherService;
    this.forecastDayRepository = forecastDayRepository;
  }

  @Override
  public Forecast calculateAndAnalyzeForecast() {

    //start and end dats
    LocalDate now = LocalDate.now();
    LocalDate tenYearsFromNow = now.plus(YEARS_TO_CALCULATE, ChronoUnit.YEARS);

    //calculate forecasts days
    List<ForecastDay> forecastDays = forecastDayRepository.findAllByOrderByDayDesc();

    return analyzeForecast(forecastDays);
  }

  @Override
  public void calculateAndSaveForecastAsync() {

    //start and end dats
    LocalDate now = LocalDate.now();
    LocalDate tenYearsFromNow = now.plus(YEARS_TO_CALCULATE, ChronoUnit.YEARS);

    //delete all
    forecastDayRepository.deleteAll();

    //calculate forecasts days
    LongStream.range(0L, ChronoUnit.DAYS.between(now, tenYearsFromNow) + 1)
      .boxed()
      .parallel() //may be not needed, but will work
      .forEach(day -> executor.execute(() -> {
        ForecastDay forecastDay = calculateForecastDay(day);
        LOGGER.fine("Saving day:" + day);
        forecastDayRepository.save(forecastDay);
      }));
  }

  @Override
  public Optional<ForecastDay> getForecastDay(Long day) {
    return forecastDayRepository.findByDay(day);
  }

  private ForecastDay calculateForecastDay(Long day) {
    Weather weather = weatherService.calculateWeather(day);
    return new ForecastDay(day, weather);
  }

  private Forecast analyzeForecast(List<ForecastDay> forecastDays) {
    //forecast lists
    List<ForecastDay> droughtDays = new ArrayList<>();
    List<RainPeriod> rainPeriods = new ArrayList<>();
    List<ForecastDay> optimalPTDays = new ArrayList<>();

    //analyze forecast
    RainPeriod rainPeriodAux = null;
    for (ForecastDay forecastDay : forecastDays) {
      Weather weather = forecastDay.getWeather();
      switch (weather.getStatus()) {
        case DROUGHT:
          if (rainPeriodAux != null) {
            rainPeriods.add(rainPeriodAux);
            rainPeriodAux = null;
          }

          droughtDays.add(forecastDay);
          break;
        case OPTIMAL_P_T:
          if (rainPeriodAux != null) {
            rainPeriods.add(rainPeriodAux);
            rainPeriodAux = null;
          }

          optimalPTDays.add(forecastDay);
          break;
        case RAINY:
          if (rainPeriodAux == null) {
            rainPeriodAux = new RainPeriod(forecastDay);
          } else {
            rainPeriodAux.addDay(forecastDay);
          }
          break;
        case NORMAL:
          if (rainPeriodAux != null) {
            rainPeriods.add(rainPeriodAux);
            rainPeriodAux = null;
          }
          break;
        default:
          throw new IllegalStateException();
      }
    }

    return new Forecast(droughtDays, rainPeriods, optimalPTDays, forecastDays.size());
  }

}
