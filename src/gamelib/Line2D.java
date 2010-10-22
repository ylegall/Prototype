
package gamelib;

import java.awt.Point;


/**
 *
 * @author Yann Le Gall
 * ylegall@gmail.com
 * @date Dec 31, 2009
 */
public class Line2D {

    Point2D p1, p2;
    double a, b, c;

    public Line2D(double x1, double y1, double x2, double y2) {
        this(new Point2D(x1,y1), new Point2D(x2,y2));
    }

    public Line2D(Point p1, Point p2) {
        this.p1 = new Point2D(p1);
        this.p2 = new Point2D(p2);
        a = p2.y - p1.y;
        b = p1.x - p2.x;
        c = a*p1.x + b*p1.y;
    }

    public Line2D(Point2D p1, Point2D p2) {
        this.p1 = p1;
        this.p2 = p2;

        a = p2.y - p1.y;
        b = p1.x - p2.x;
        c = a*p1.x + b*p1.y;
    }

    public Point2D intersection(Line2D other) {
        double det = a*other.b - other.a*b;
        Point2D p = null;
        if(det != 0) {
            p = new Point2D((other.b*c - b*other.c)/det, (a*other.c - other.a*c)/det);
        }
        return p;
    }

    public boolean contains(Point2D p) {
        return contains(p.x, p.y);
    }

    public boolean contains(double x, double y) {
        double result;
        if(a != 0) {
            result = (c - b*y)/a;
            return result == x;
        } else {
            result = (c - a*x)/b;
            return result == y;
        }
    }

//    public static void main (String[] args) {
//        Line2D l1 = new Line2D(new Point2D(0,0), new Point2D(4,4));
//        Line2D l2 = new Line2D(new Point2D(0,4), new Point2D(4,0));
//
//        Point2D p = l1.intersection(l2);
//        if(p != null) {
//            System.out.println(String.format("the intersection point is (%.3f, %.3f)", p.x, p.y));
//        } else {
//            System.out.println("no intersection");
//        }
//    }
}
