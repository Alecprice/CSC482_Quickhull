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
import java.io.*;
import java.util.ArrayList;

public class Main {

    static Point min = null;
    static Point max = null;

    public static void main(String[] args) {

        ArrayList<Point> points = getPoints();

        QuickHull convex = new QuickHull();
        convex.add(min);
        convex.add(max);
        points.remove(min);
        points.remove(max);
        convex.quickHull(points, min, max);
        convex.print();
    }

    // Reads in the points from a file and finds the min & max points
    public static ArrayList<Point> getPoints() {

        ArrayList<Point> points = new ArrayList<>();
        // Reads in file from path specified
        File myFile = new File("src/com/AlecPrice/input.txt");
        try {
            // BufferedReader was chosen since its faster than Javas scanner with similar functionality
            BufferedReader reader = new BufferedReader(new FileReader(myFile));
            String line;
            String[] first = reader.readLine().split(" ");
            // Start first point at min
            min = new Point(Double.parseDouble(first[0]), Double.parseDouble(first[1]));
            max = min;
            points.add(min);
            // Read file as long as the file has input to read
            while ((line = reader.readLine()) != null) {
                String[] coords = line.split(" ");
                //System.out.println("Coordinates: " + coords.length); // Making sure we only get 2 points at a time
                Point p = new Point(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
                //System.out.println("New Point: " + p);
                points.add(p);
                if (Point.isMin(p, min)) {
                    min = p;
                    //System.out.println("min: " + min);
                }
                if (Point.isMax(p, max)) {
                    max = p;
                    //System.out.println("max: " + max);
                }
            }
            // IOException will catch exceptions other than File error exceptions
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File does not exist");
        }
        return points;
    }


}

