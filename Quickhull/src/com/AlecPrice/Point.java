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

/** The Point class contains an X and Y double coordinate, used as part
 *  of implementing the QuickHull algorithm.
 */
package com.AlecPrice;
public class Point {
    /** x is the x-coordinate of this Point.*/
    private final double x;
    /** y is the y-coordinate of this Point.*/
    private final double y;

    /** Constructor that takes in an x-y coordinate in double format.
     *
     * @param newX The new X coordinate to store in this Point.
     * @param newY The new Y coordinate to store in this Point.
     */
   public Point(double newX, double newY) {
        x = newX;
        y = newY;
    }

    /** getX returns the x-coordinate of this Point.
     *
     * @return The x attribute.
     */
    double getX() {
        return x;
    }
    /** getY returns the y-coordinate of this Point.
     *
     * @return The y attribute.
     */
    double getY() {
        return y;
    }

    public static boolean isMin(Point p1, Point p2) {
        return p1.getX() < p2.getX();
    }

    public static boolean isMax(Point p1, Point p2) {
        return p1.getX() > p2.getX();
    }

    /** toString() outputs the coordinates of this Point in space-separated format.
     *
     * @return The x attribute, followed by a space, followed by the y attribute.
     */
    @Override
    public String toString() {
        // Print Result
        return  x + ", " + y ;
    }

    /** equals determines whether the point parameter is the same as this Point.
     *
     * @param point The Point to compare to this Point.
     * @return True if the passed-in Point's x and y coordinate values are identical to this Point.
     * @throws IllegalArgumentException If the point parameter is null.
     */
    public boolean equals(Point point) throws IllegalArgumentException{
        // Check that the point parameter is not null.
        if (point == null) {
            throw new IllegalArgumentException("Error while executing equals(Point point) in Point: Parameter point is " + point);
        }
        return Double.compare(x, point.getX()) == 0 && Double.compare(y, point.getY()) == 0;
    }

}

