package ar.com.vga.meli.util;

import ar.com.vga.meli.domain.planet.Position;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author alejandrovigano
 */
public class MathUtils {

  /**
   * Calculates the area of the triangle with vertexes on the given positions
   * @see <a href="https://en.wikipedia.org/wiki/Shoelace_formula">Shoelace_formula </a>
   * @see <a href="https://www.geeksforgeeks.org/check-whether-a-given-point-lies-inside-a-triangle-or-not/">Source</a>
   *
   * @param p1
   * @param p2
   * @param p3
   * @return area
   */
  public static BigDecimal areaOfTriangle(Position p1, Position p2, Position p3) {

    BigDecimal term1 = p1.getX().multiply(p2.getY().subtract(p3.getY()));
    BigDecimal term2 = p2.getX().multiply(p3.getY().subtract(p1.getY()));
    BigDecimal term3 = p3.getX().multiply(p1.getY().subtract(p2.getY()));

    return term1.add(term2).add(term3).abs().divide(new BigDecimal(2), RoundingMode.HALF_UP);
  }

  public static BigDecimal perimeterOfTriangle(Position p1, Position p2, Position p3) {
    BigDecimal p1p2 = p1.distanceTo(p2);
    BigDecimal p2p3 = p2.distanceTo(p3);
    BigDecimal p1p3 = p1.distanceTo(p3);

    return p1p2.add(p2p3).add(p1p3);
  }

  public static BigDecimal getSlope(Position position1, Position position2) {
    BigDecimal deltaY = position2.getY().subtract(position1.getY());
    BigDecimal deltaX = position2.getX().subtract(position1.getX());

    if (deltaX.compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.valueOf(Long.MAX_VALUE);
    }

    return deltaY.divide(deltaX, 2, RoundingMode.HALF_UP);
  }
}
