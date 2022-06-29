package graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.geom.Point2D.distance;

public class DelaunayTriangulation {

    public static void triangulateGraphVertices(AbstractGraph graph)
    {
        java.util.List<Triangle> triangleList = new ArrayList<>();
        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
            var vertexA = graph.getVertices().get(i);
            var pointA = ((Room)vertexA).getPoint();
            for (int j = i+1; j < graph.getNumberOfVertices(); j++) {
                var vertexB = graph.getVertices().get(j);
                var pointB = ((Room)vertexB).getPoint();
                for (int k = j+1; k < graph.getNumberOfVertices(); k++) {
                    var isTriangle = true;
                    var vertexC = graph.getVertices().get(k);
                    var pointC = ((Room)vertexC).getPoint();
                    var triangle = new Triangle(pointA, pointB, pointC);
                    isTriangle = hasNoVerticesInTriangle(graph, triangle);
                    if(isTriangle && triangleDoesNotOverlap(triangle, triangleList))
                    {
                        float weight;
                        weight = (float) distance(triangle.getP1().getX(), triangle.getP1().getY(),
                                triangle.getP2().getX(), triangle.getP2().getY());
                        graph.addEdge(vertexA, vertexB, (int)weight);
                        weight = (float) distance(triangle.getP1().getX(), triangle.getP1().getY(),
                                triangle.getP3().getX(), triangle.getP3().getY());
                        graph.addEdge(vertexA, vertexC, (int)weight);
                        weight = (float) distance(triangle.getP2().getX(), triangle.getP2().getY(),
                                triangle.getP3().getX(), triangle.getP3().getY());
                        graph.addEdge(vertexB, vertexC, (int)weight);
                        triangleList.add(triangle);
                    }
                }
            }
        }
    }

    private static boolean triangleDoesNotOverlap(Triangle newTriangle, List<Triangle> existingTriangles)
    {
        var pointsNewTriangle = new Point[]{newTriangle.getP1(), newTriangle.getP2(),
                newTriangle.getP3(), newTriangle.getP1()};
        for (Triangle triangle : existingTriangles)
        {
            var pointsCurrentTriangle = new Point[]{triangle.getP1(), triangle.getP2(), triangle.getP3(), triangle.getP1()};
            for (var i = 0; i < 3; i++)
            {
                for (var j = 0; j < 3; j++)
                {
                    if(isCrossIntersect(pointsNewTriangle[i], pointsNewTriangle[i+1],
                            pointsCurrentTriangle[j], pointsCurrentTriangle[j+1]))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //Check if two points from different line segments intersect
    private static boolean isCrossIntersect(Point p1, Point p2, Point p3, Point p4) {
        int z1 = (int)((p3.getX() - p1.getX()) * (p2.getY() - p1.getY())
                - (p3.getY() - p1.getY()) * (p2.getX() - p1.getX()));
        int z2 = (int)((p4.getX() - p1.getX()) * (p2.getY() - p1.getY())
                - (p4.getY() - p1.getY()) * (p2.getX() - p1.getX()));
        if (productsTNotIntersect(z1, z2))
        {
            return false;
        }
        int z3 = (int)((p1.getX() - p3.getX()) * (p4.getY() - p3.getY()) - (p1.getY() - p3.getY()) * (p4.getX() - p3.getX()));
        int z4 = (int)((p2.getX() - p3.getX()) * (p4.getY() - p3.getY()) - (p2.getY() - p3.getY()) * (p4.getX() - p3.getX()));
        return productsUNotIntersect(z3, z4);
    }

    private static boolean productsUNotIntersect(int z3, int z4)
    {
        return (z3 >= 0 || z4 >= 0) && (z3 <= 0 || z4 <= 0) && z3 != 0 && z4 != 0;
    }

    private static boolean productsTNotIntersect(int z1, int z2)
    {
        return z1 < 0 && z2 < 0 || z1 > 0 && z2 > 0 || z1 == 0 || z2 == 0;
    }

    private static boolean hasNoVerticesInTriangle(AbstractGraph graph, Triangle triangle) {
        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
            var point = ((Room) graph.getVertices().get(i)).getPoint();
            if(isNotTriangleVertex(triangle, point) && triangle.contains(point))
            {
                return false;
            }
        }
        return true;
    }

    private static boolean isNotTriangleVertex(Triangle triangle, Point point) {
        if(!point.equals(triangle.getP1()) && !point.equals(triangle.getP2()))
        {
            return !point.equals(triangle.getP3());
        }
        return false;
    }
}
