package gui;

import gui.Observer;
import gui.RobotCoordinate;

import java.awt.*;

public class RobotLogic {


    private volatile RobotCoordinate m_robotPosition = new RobotCoordinate(100, 100);
    private volatile RobotCoordinate m_robotDirection = new RobotCoordinate(1, 0);
    private volatile RobotCoordinate m_targetPosition = new RobotCoordinate(150, 100);

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.03;



    public void setTargetPosition(Point p) {
        m_targetPosition = new RobotCoordinate(p.x, p.y);
    }

    public static int round(double value) {
        return (int) (value + 0.5);
    }

    public RobotCoordinate getRobotPos() {
        return m_robotPosition;
    }
    public RobotCoordinate getTargetPos() {
        return m_targetPosition;
    }
    public RobotCoordinate getDirection()
    {
        return m_robotDirection;
    }

    private static double distance(double x1, double y1, double x2, double y2) {

    double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(RobotCoordinate from, RobotCoordinate to, RobotCoordinate dir) {
        RobotCoordinate rot = new RobotCoordinate(to.x - from.x, to.y - from.y).rotate(-Math.atan2(dir.y, dir.x));
        return Math.atan2(rot.y, rot.x);
    }

    public void onModelUpdateEvent() {
        double distance = distance(m_targetPosition.x, m_targetPosition.y, m_robotPosition.x, m_robotPosition.y);
        if (distance < 0.5)
            return;

        double angleToTarget = angleTo(m_robotPosition, m_targetPosition, m_robotDirection);
        double angularVelocity = angleToTarget >= 0 ? maxAngularVelocity : -maxAngularVelocity;
        moveRobot(maxVelocity, angularVelocity, 10);

        Observer.notifyModelUpdate(new Point(round(m_robotPosition.x), round(m_robotPosition.y)));
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        velocity = applyLimits(velocity, 0, maxVelocity);
        double moveX = m_robotDirection.x * velocity * duration, moveY = m_robotDirection.y * velocity * duration;
        m_robotPosition.x += moveX;
        m_robotPosition.y += moveY;
        m_robotDirection = m_robotDirection.rotate(angularVelocity * duration);
        //notify.notifyAll();
    }

}
