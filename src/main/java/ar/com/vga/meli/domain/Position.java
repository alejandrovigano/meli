package ar.com.vga.meli.domain;

import java.math.BigDecimal;

/**
 * Represents a 2D position
 */
public class Position {

  private BigDecimal x;
  private BigDecimal y;

  public Position(BigDecimal x, BigDecimal y) {
    this.x = x;
    this.y = y;
  }

  public BigDecimal getX() {
    return x;
  }

  public BigDecimal getY() {
    return y;
  }
}
