/* Author:     Alec Price
 * Class:      CSC 482: Algorithms & Computation
 * Assignment: Divide and Conquer Assignment Finding a convex hull with the quickhull algorithm
 */
/**
 * 1.	Find the leftmost point A and rightmost point B.  Add these two points to the convex hull.
 * 2.	The line formed by these two points separate the rest of the points above and below the line.  These two sets will be processed recursively.
 * 3.	Find the point farthest from the line C and add it to the convex hull.
 * 4.	This new point will form a triangle with the previous two points.
 * 5.	You may ignore all points inside the triangle as they will not be part of the convex hull.
 * 6.	You will now have two new lines formed by AC and BC as well as a new set of points to the left and right of the triangle.
 * 7.	Repeat steps 3 – 6 on these new lines and continue until no more points are left.
 * 8.	Return your convex hull.
 */
package com.AlecPrice;
import java.util.ArrayList;
import java.util.List;

public class QuickHull {

    // // Initialize Result contains the resulting Points that make up the convex hull of the original list of Points.
    private final List<Point> convexHull;

    QuickHull() {
        this.convexHull = new ArrayList<>();
    }
// Add point to hull
    void add(Point p) {
        this.convexHull.add(p);
    }
// Print cords of the points
    void print() {
        for (Point p: convexHull)
            System.out.println(p.toString());
    }


    void quickHull(List<Point> points, Point min, Point max) {
        /*
         * Points in rightSide are on the right side of the oriented line AB.
         * Points in leftSide are on the left side of the oriented line AB.
         */
        List<Point> leftSide = new ArrayList<>();
        List<Point> rightSide = new ArrayList<>();
        // For each point in the list determine which side of the AB line they are on
        for (Point p: points) {
            if (getSide(min, max, p) == 1) {
                // Add current point to result
                leftSide.add(p);
            }else if (getSide(min, max, p) == -1) {
                // Add current point to result
                rightSide.add(p);
            }
        }
        // Recursion call to find the quickHull on each side of the AB segment.
        findHull(leftSide, min, max);
        findHull(rightSide, max, min);
    }

    private void findHull(List<Point> points, Point p, Point q) throws IllegalArgumentException{
        // First check that points, p and q are not null.
        if (points == null) {
            throw new IllegalArgumentException("Error while executing findHull(List<Point> points, Point p, Point q) in QuickHull: Parameter points is: " + points);
        }
        else if (p == null) {
            throw new IllegalArgumentException("Error while executing findHull(List<Point> points, Point p, Point q) in QuickHull: Parameter p is: " + p);
        }
        else if (q == null) {
            throw new IllegalArgumentException("Error while executing findHull(List<Point> points, Point p, Point q) in QuickHull: Parameter q is: " + q);
        }
        // If points are available run, else Skip
        if (!points.isEmpty()) {


            Point farthestPoint = getFarthestPoint(points, p, q);
            convexHull.add(farthestPoint);
            points.remove(farthestPoint);
            // Lists to store which side a point is on
            List<Point> s1 = new ArrayList<>();
            List<Point> s2 = new ArrayList<>();
            // For each loop to go through the points and only keep the outer most points
            for (Point point : points) {
//                if (isInTriangle(p, q, farthestPoint, point))
//                    points.remove(point);
                //else {
                    if (getSide(p, farthestPoint, point) > 0)
                        s1.add(point);
                    else if (getSide(q, farthestPoint, point) < 0)
                        s2.add(point);
                //}
            }
            findHull(s1, p, farthestPoint);
            findHull(s2, farthestPoint, q);
        }
    }


    private double getSlope(Point a, Point b) {
        return (b.getY() - a.getY()) / (b.getX() - a.getX());
    }

    private double getIntercept(Point a, Point b) {
        return a.getY() - getSlope(a, b) * a.getX();
    }

    private Point getFarthestPoint(List<Point> points, Point p, Point q) {
        Point farthest = points.get(0);
        double farthestDistance = getDistance(p, q, farthest);
        for (int i = 1; i < points.size(); i++) {
            Point testPoint = points.get(i);
            double distance = getDistance(p, q, testPoint);
            if (distance > farthestDistance) {
                farthest = testPoint;
                farthestDistance = distance;
            }
        }
        //System.out.println(farthest);
        return farthest;
    }

    /** getDistance return the distance a point is from an AB line
     *
     * @param a & b make up the slope for the calculation of distance from p to line AB
     * @param p The Point from which to find its distance from a given line.
     * @return Distance between the points to use as a helper method
     */
    private double getDistance(Point a, Point b, Point p) throws IllegalArgumentException  {
        // First check that the point parameter is not null.
        if (p == null) {
            throw new IllegalArgumentException("Error while executing getDistance(Point a, Point b, Point p) in QuickHull: Parameter Point p is: " + p);
        }
//        double slope = getSlope(a, b);
//        double yIntercept = getIntercept(a, b);
//        double distance = ((-1 * slope * p.getX()) + (p.getY() - yIntercept) / Math.sqrt(slope * slope + 1)); //Math.sqrt isn't needed but was a convention I saw while looking into the math for this algorithm
//        if (distance < 0) {
//            distance = -distance;
//        }

//        Double slope = (b.getY() - a.getY()) / (b.getX() - a.getX());
//        Double intercept = a.getY() - ((slope) * a.getX());
//        return ((-slope * p.getX()) + (p.getY() - intercept)) / Math.sqrt((slope * slope) + 1);

        double X1 = a.getX();
        double Y1 = a.getY();
        double X2 = b.getX();
        double Y2 = b.getY();
        double X0 = p.getX();
        double Y0 = p.getY();

        double num = Math.abs(((Y2 - Y1)* X0) - ((X2 - X1) * Y0) + (X2*Y1) - (Y2 * X1));
        double distance = Math.sqrt(Math.pow((Y2-Y1),2) + Math.pow((X2-X1),2));

        return num / distance;

        // Return the distance the Point is from the line.
        // ((-m * P.x) + P.y - b)/sqrt(m^2 + 1)
        //System.out.println("Distance is:" + distance);
        //return distance;
    }


    /** getSide determines which side of a line a Point is on.
     *
     * @param a A Point on the line.
     * @param b The second Point on the line.
     * @param p The point to check.
     * @return If the line is to the left return 1,
     * If the line is to the right return -1,
     * If on the line return 0
     * @throws IllegalArgumentException If point, a, or b is null or the same Point (address or coordinate).
     */
    private double getSide(Point a, Point b, Point p) throws IllegalArgumentException {
        // First check that point, a, and b are not null.
        if (p == null) {
            throw new IllegalArgumentException("Error while executing getSide(Point a, Point b, Point p) in QuickHull: Parameter Point p is: " + p);
        }
        else if (a == null) {
            throw new IllegalArgumentException("Error while executing getSide(Point a, Point b, Point p) in QuickHull: Parameter Point a is: " + a);
        }
        else if (b == null) {
            throw new IllegalArgumentException("Error while executing getSide(Point a, Point b, Point p) in QuickHull: Parameter Point b is: " + b);
            // Next, check that none of the points are duplicates.
        }
        else if (p == a) {
            throw new IllegalArgumentException("Error while executing getSide(Point a, Point b, Point p) in QuickHull: Parameters Point p and Point a are the same address!");
        }
        else if (p.equals(a)) {
            throw new IllegalArgumentException("Error while executing getSide(Point a, Point b, Point p) in QuickHull: Parameters Point p and Point a are the same coordinate!");
        }
        else if (p == b) {
            throw new IllegalArgumentException("Error while executing getSide(Point a, Point b, Point p) in QuickHull: Parameters Point p and Point b are the same address!");
        }
        else if (p.equals(b)) {
            throw new IllegalArgumentException("Error while executing getSide(Point a, Point b, Point p) in QuickHull: Parameters Point p and Point b are the same coordinate!");
        }
        else if (a == b) {
            throw new IllegalArgumentException("Error while executing getSide(Point a, Point b, Point p) in QuickHull: Parameters Point a and Point  b are the same address!");
        }
        else if (a.equals(b)) {
            throw new IllegalArgumentException("Error while executing getSide(Point a, Point b, Point p) in QuickHull: Parameters Point a and Point b are the same coordinate!");
        }
        double result = (b.getX() - a.getX()) * (p.getY() - a.getY()) - (b.getY() - a.getY()) * (p.getX() - a.getX());
        if (result > 0) {
            return 1;
        }
        else if (result < 0) {
            return -1;
        }
        return 0;
    }

    /** isInTriangle determines whether a given point is within a triangle
     *
     * @param a The first of three Points that make up the triangle.
     * @param b The second of three Points that make up the triangle.
     * @param c The third Point that makes up the triangle.
     * @param p The Point to check whether it is in the triangle.
     * @return True if point is inside the triangle, else false.
     * @throws IllegalArgumentException If Point's a, b, c or p is null or the same Point (address or coordinate).
     */
    private boolean isInTriangle(Point a, Point b, Point c, Point p) {

        // First check that point, a, b, and c are not null.
        if (p == null) {
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameter Point p is: " + p);
        } else if (a == null){
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameter Point a is: " + a);
    }else if (b == null){
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameter Point b is: " + b);
       } else if (c == null) {
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameter Point c is: " + c);
            // Next check that none of the points are duplicates.
        }else if (p == a) {
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameters Point p and Point a are the same address!");
        }else if (p.equals(a)) {
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameters Point p and Point a are the same coordinate!");
        }else if (p == b) {
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameters Point p and Point b are the same address!");
        }else if (p.equals(b)) {
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameters Point p and Point b are the same coordinate!");
        }else if (p == c) {
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameters Point p and Point c are the same address!");
        }else if (p.equals(c)) {
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameters Point p and Point c are the same coordinate!");
        }else if (a == b) {
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameters Point a and Point b are the same address!");
        }else if (a.equals(b)) {
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameters Point a and Point b are the same coordinate!");
        }else if (a == b) {
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameters Point a and Point b are the same address!");
        }else if (a.equals(b)) {
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameters Point a and Point b are the same coordinate!");
        }else if (b == c) {
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameters Point b and Point c are the same address!");
        }else if (b.equals(c)){
            throw new IllegalArgumentException("Error while executing isInTriangle(Point a, Point b, Point c, Point p) in QuickHull: Parameters Point b and Point c are the same coordinate!");
        }

        double result1 = (p.getX() - a.getX()) * (b.getY() - a.getY()) - (p.getY() - a.getY()) * (b.getX() - a.getX());
        double result2 = (p.getX() - b.getX()) * (c.getY() - b.getY()) - (p.getY() - b.getY()) * (c.getX() - b.getX());
        double result3 = (p.getX() - c.getX()) * (a.getY() - c.getY()) - (p.getY() - c.getY()) * (a.getX() - c.getX());

        if(result1 > 0 && result2 > 0 && result3 > 0) {
            return true;
        } else if(result1 < 0 && result2 < 0 && result3 < 0) {
            return true;
        } else {
            return false;
        }
    }


}

