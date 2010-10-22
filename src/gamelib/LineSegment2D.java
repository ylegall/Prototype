
package gamelib;

import java.awt.Point;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Jan 5, 2010 2:05:45 PM
 */
public class LineSegment2D extends Line2D {

    public LineSegment2D(double x1, double y1, double x2, double y2) {
        this(new Point2D(x1,y1), new Point2D(x2,y2));
    }

    public LineSegment2D(Point p1, Point p2) {
        super(p1, p2);
    }

    public LineSegment2D(Point2D p1, Point2D p2) {
        super(p1, p2);
    }

    @Override
    public Point2D intersection(Line2D other) {
        double det = a*other.b - other.a*b;
        Point2D p = null;
        if(det != 0) {
            p = new Point2D((other.b*c - b*other.c)/det, (a*other.c - other.a*c)/det);
        }
        if(boundsCheck(p.x, p.y)) {
            return p;
        } else {
            return null;
        }
    }

    public Point2D intersection(LineSegment2D other) {
        double det = a*other.b - other.a*b;
        Point2D p = null;
        if(det != 0) {
            p = new Point2D((other.b*c - b*other.c)/det, (a*other.c - other.a*c)/det);
        }
        
        if(boundsCheck(p.x, p.y) && other.boundsCheck(p.x, p.y)) {
            return p;
        } else {
            return null;
        }
    }

    @Override
    public boolean contains(double x, double y) {
        if(!boundsCheck(x,y)) return false;
        double result;
        if(a != 0) {
            result = (c - b*y)/a;
            return result == x;
        } else {
            result = (c - a*x)/b;
            return  result == y;
        }
    }

    private boolean boundsCheck(double x, double y) {
        if(x < Math.min(p1.x, p2.x) || x > Math.max(p1.x, p2.x)) {
            return false;
        }
        if(y < Math.min(p1.y, p2.y) || y > Math.max(p1.y, p2.y)) {
            return false;
        }
        return true;
    }

//    public static void main(String[] args) {
//        LineSegment2D l1 = new LineSegment2D(0,0,4,4);
//        LineSegment2D l2 = new LineSegment2D(0,4,1,3);
//
//        Point2D p = l1.intersection(l2);
//        if(p != null) {
//            System.out.println(String.format("the intersection point is (%.3f, %.3f)", p.x, p.y));
//        } else {
//            System.out.println("no intersection");
//        }
//    }
}
