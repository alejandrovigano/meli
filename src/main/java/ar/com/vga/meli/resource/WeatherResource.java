package ar.com.vga.meli.resource;

public class WeatherResource {

  private String dia;
  private String clima;

  public WeatherResource(String dia, String clima) {
    this.dia = dia;
    this.clima = clima;
  }

  public String getDia() {
    return dia;
  }

  public String getClima() {
    return clima;
  }
}
