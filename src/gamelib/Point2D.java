package gamelib;

import java.awt.Point;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *
 * Implementation of a 2D point and useful operations
 */
public class Point2D {

    public double x, y;

    public Point2D() {
    }

    /**
     * Creates a new Point2D
     * equal to <tt>other</tt>
     * @param other
     */
    public Point2D(final Point2D other) {
        x = other.x;
        y = other.y;
    }

    public Point2D(final Point other) {
        x = other.x;
        y = other.y;
    }

    public Point2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Point2D other) {
        double dx = (other.x - x);
        double dy = (other.y - y);
        return (double) Math.sqrt(dx * dx + dy * dy);
    }

    public double distanceTo(Point other) {
        double dx = (other.x - x);
        double dy = (other.y - y);
        return (double) Math.sqrt(dx * dx + dy * dy);
    }

    public Point getPoint() {
        return new Point((int) x, (int) y);
    }

    public void moveBy(Vector2D vector) {
        x += vector.x;
        y += vector.y;
    }

    public static double distanceBetween(final Point2D a, final Point2D b) {
        double dx = (b.x - a.x);
        double dy = (b.y - a.y);
        return (double) Math.sqrt(dx * dx + dy * dy);
    }
}
