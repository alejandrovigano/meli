package ar.com.vga.meli.domain.planet;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a 2D position
 */
public class Position {

  private BigDecimal x;
  private BigDecimal y;
  public static Position ZERO = new Position(BigDecimal.ZERO, BigDecimal.ZERO);

  private Position(BigDecimal x, BigDecimal y) {
    this.x = x;
    this.y = y;
  }

  public static Position from(BigDecimal degrees, BigDecimal distance) {
    BigDecimal x = new BigDecimal(Math.cos(Math.toRadians(degrees.doubleValue())))
      .setScale(2, RoundingMode.HALF_UP)
      .multiply(distance);

    BigDecimal y = new BigDecimal(Math.sin(Math.toRadians(degrees.doubleValue())))
      .setScale(2, RoundingMode.HALF_UP)
      .multiply(distance);

    return new Position(x, y);
  }

  public BigDecimal getX() {
    return x;
  }

  public BigDecimal getY() {
    return y;
  }

  public BigDecimal distanceTo(Position other) {
    BigDecimal xTerm = other.getX().subtract(this.getX()).pow(2);
    BigDecimal yTerm = other.getY().subtract(this.getY()).pow(2);

    double mod = Math.sqrt(xTerm.add(yTerm).doubleValue());

    return new BigDecimal(mod).setScale(2, RoundingMode.HALF_UP);
  }

  @Override
  public String toString() {
    return "Position{" +
      "x=" + x +
      ", y=" + y +
      '}';
  }
}
