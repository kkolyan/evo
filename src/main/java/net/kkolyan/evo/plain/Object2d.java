package net.kkolyan.evo.plain;

/**
 * @author nplekhanov
 */
public abstract class Object2d {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double distanceTo(Object2d o) {
        return vectorTo(o).magnitude();
    }

    public double magnitude() {
        return Math.sqrt(getX() * getX() + getY() * getY());
    }

    public void transpose(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void transpose(Object2d o) {
        this.x += o.getX();
        this.y += o.getY();
    }

    public Vector vectorTo(Object2d o) {
        return new Vector(o.getX() - getX(), o.getY() - getY());
    }

    public void multiply(double factor) {
        this.x *= factor;
        this.y *= factor;
    }
}
