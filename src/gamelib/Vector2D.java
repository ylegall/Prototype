package gamelib;

/**
 *
 * @author ylegall
 * ylegall@gmail.com
 *
 * Implementation of a 2D vector and useful operations
 */
public class Vector2D
{

    // fields
    public double x, y;

    /**
     * Creates a new Vector2D; x=0.0, y=0.0
     */
    public Vector2D() {
    }

    /**
     * Creates a new Vector2D
     * equal to <tt>other</tt>
     * @param other
     */
    public Vector2D(final Vector2D other) {
        x = other.x;
        y = other.y;
    }

    public Vector2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds two vectors and returns the sum as a new Vector2D.
     * @param other
     * @return the sum of this Vector2D and <tt>other</tt>
     */
    public Vector2D plus(final Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    /**
     * Adds <tt>other</tt> to this Vector2D.
     * @param other
     */
    public void add(final Vector2D other) {
        x += other.x;
        y += other.y;
    }

    /**
     * Subtracts two vectors and returns the difference as a new Vector2D.
     * @param other
     * @return the sum of this Vector2D and <tt>other</tt>
     */
    public Vector2D minus(final Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    /**
     * Subtracts <tt>other</tt> from this Vector2D.
     * @param other
     */
    public void subtract(final Vector2D other) {
        x -= other.x;
        y -= other.y;
    }

    /**
     * Calculate the magnitude of this vector.
     * @return
     */
    public double magnitude() {
        return (double) Math.sqrt(this.dot(this));
    }

    /**
     * Calculates the dot product of this Vector2D
     * and <tt>other</tt>
     * @param other
     * @return
     */
    public double dot(final Vector2D other) {
        return (x * other.x + y * other.y);
    }

    /**
     * Calculates the scalar product of this
     * Vector2D and a scalar.
     * @param constant
     * @return
     */
    public Vector2D getScaledVector(final double constant) {
        return new Vector2D(x * constant, y * constant);
    }

    /**
     * Scales this Vector2D by a scalar.
     * @param constant
     */
    public void scaleBy(final double constant) {
        x *= constant;
        y *= constant;
    }

    /**
     * Get the vector that is perpendicular
     * to this Vector2D.
     * @return
     */
    public Vector2D perpendicular() {
        return new Vector2D(y, x);
    }

    /**
     * gets the signed angle between 2 vectors
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the signed angle in radians between the two vectors
     */
    public static final double signedAngle(Vector2D v1, Vector2D v2) {
        double perpDot = v1.x * v2.y - v1.y * v2.x;
        return Math.atan2(perpDot, v1.dot(v2));
    }

    /**
     * Calculates the unit vector in the direction
     * of this Vector2D.
     * @return
     */
    public Vector2D direction() {
        double mag = magnitude();
        return (mag == 0) ? new Vector2D() : getScaledVector(1.0f / mag);
    }

    /**
     * Calculates the distance between this Vector2D
     * and <tt>other</tt>
     * @param other
     * @return
     */
    public double distanceTo(final Vector2D other) {
        return minus(other).magnitude();
    }

    /**
     * Get the string representation of this Vector2D
     * @return a string representing this Vector2D
     */
    @Override
    public String toString() {
        return String.format("Vector2D[ %f, %f]", x, y);
    }
}

