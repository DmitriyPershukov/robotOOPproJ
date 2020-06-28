package gui;

public class RobotCoordinate{
    public RobotCoordinate(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    double x;
    double y;

    public RobotCoordinate rotate(double angle)
    {
        return new RobotCoordinate(x * Math.cos(angle) - y * Math.sin(angle), x * Math.sin(angle) + y * Math.cos(angle));
    }
}