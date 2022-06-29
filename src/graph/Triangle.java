package graph;

import java.awt.*;

public class Triangle {
    private Point p1;
    private Point p2;
    private Point p3;

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public Point getP3() {
        return p3;
    }

    public void setP3(Point p3) {
        this.p3 = p3;
    }

    public Triangle(Point p1, Point p2, Point p3)
    {
        this.setP1(p1);
        this.setP2(p2);
        this.setP3(p3);
    }

    public boolean contains(Point point) {
        double areaABC = this.getArea();

        var trianglePBC = new Triangle(point, this.getP2(), this.getP3());
        double areaPBC = trianglePBC.getArea();
        var trianglePAC = new Triangle(this.getP1(), point, this.getP3());
        double areaPAC = trianglePAC.getArea();
        var trianglePAB = new Triangle(this.getP1(), this.getP2(), point);
        double areaPAB = trianglePAB.getArea();

        return (areaABC == (areaPAB + areaPAC + areaPBC));
    }

    private double getArea() {
        return Math.abs(
                (getP1().getX() * (getP2().getY() - getP3().getY())
                        + getP2().getX() * (getP3().getY() - getP1().getY())
                        + getP3().getX() * (getP1().getY() - getP2().getY()))
                        /2.0);
    }
}
