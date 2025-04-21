package Figures;
import GameEngine.ICollider;
import GameEngine.ITransform;

/** 
 * Class that represents a geometric figure.
 * Author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * Version: 1.0 (12/04/25)
 **/
public abstract class GeometricFigure {
    
    /**
     * Moves the geometric figure according to the dPos vector.
     * @param dx amount of movement on the x-axis
     * @param dy amount of movement on the y-axis
     * @return the moved geometric figure
     */
    public abstract GeometricFigure translate(double dx, double dy);

    /**
     * Scales the geometric figure according to dScale.
     * @param factor scaling factor
     * @return the scaled geometric figure
     */
    public abstract GeometricFigure scale(double factor);

    /**
     * Rotates the geometric figure according to dTheta.
     * @param angle rotation angle
     * @param center center of rotation
     * @return the rotated geometric figure
     */
    public abstract GeometricFigure rotate(double angle, Point center);

    /**
     * Creates a collider for the geometric figure.
     * @param t transformation of the figure
     * @return the geometric figure with the center
     */
    public abstract ICollider colliderInit(ITransform t);

    @Override
    public abstract String toString();
}