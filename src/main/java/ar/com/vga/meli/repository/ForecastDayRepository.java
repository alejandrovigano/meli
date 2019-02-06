package ar.com.vga.meli.repository;

import ar.com.vga.meli.domain.weather.ForecastDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ForecastDayRepository extends JpaRepository<ForecastDay, Long> {

  Optional<ForecastDay> findByDay(Long day);

  List<ForecastDay> findAllByOrderByDayDesc();
}
