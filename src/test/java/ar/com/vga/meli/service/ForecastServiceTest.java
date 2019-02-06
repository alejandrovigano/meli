package ar.com.vga.meli.service;


import ar.com.vga.meli.domain.weather.Forecast;
import ar.com.vga.meli.domain.weather.ForecastDay;
import ar.com.vga.meli.domain.weather.Weather;
import ar.com.vga.meli.repository.ForecastDayRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ForecastServiceTest {

  @Autowired
  private ForecastService forecastService;

  @MockBean
  private ForecastDayRepository forecastDayRepository;

  @Test
  public void test() {

    //given
    Weather weatherRainy = Weather.rainy(new BigDecimal(100));
    Weather weatherMoreRainy = Weather.rainy(new BigDecimal(500));
    Weather weatherDrought = Weather.drought();
    Weather weatherPT = Weather.optimalPressureAndTemp();
    Weather weatherNormal = Weather.normal();

    List<ForecastDay> days = Arrays.asList(
      new ForecastDay(0L, weatherRainy),
      new ForecastDay(1L, weatherMoreRainy),
      new ForecastDay(2L, weatherRainy),
      new ForecastDay(3L, weatherDrought),
      new ForecastDay(4L, weatherPT),
      new ForecastDay(5L, weatherNormal));

    given(forecastDayRepository.findAllByOrderByDayDesc())
      .willReturn(days);

    //when
    Forecast forecast = forecastService.calculateAndAnalyzeForecast();

    //then
    assertEquals(1, forecast.getDroughtDays().size());
    assertEquals(1, forecast.getOptimalPTDays().size());
    assertEquals(1, forecast.getRainPeriods().size());
    assertEquals(Long.valueOf(1L), forecast.getRainPeriods().get(0).getRainDayWithMaxRainfallIntensity());
  }

}
