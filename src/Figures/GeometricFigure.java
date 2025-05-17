package Figures;
import GameEngine.ICollider;
import GameEngine.ITransform;

/** 
 * Abstract class that represents a geometric figure.
 * Provides methods for translation, scaling, rotation, and collider creation.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (12/04/25)
 */
public abstract class GeometricFigure {
    
    /**
     * Moves the geometric figure by the specified amounts along the x and y axes.
     * @param dx amount of movement on the x-axis
     * @param dy amount of movement on the y-axis
     * @return the moved geometric figure
     */
    public abstract GeometricFigure translate(double dx, double dy);

    /**
     * Scales the geometric figure by the specified factor.
     * @param factor scaling factor
     * @return the scaled geometric figure
     */
    public abstract GeometricFigure scale(double factor);

    /**
     * Rotates the geometric figure around a specified center by the given angle.
     * @param angle rotation angle in degrees
     * @param center center of rotation
     * @return the rotated geometric figure
     */
    public abstract GeometricFigure rotate(double angle, Point center);

    /**
     * Creates a collider for the geometric figure.
     * @param t transformation of the figure
     * @return the collider for the geometric figure
     */
    public abstract ICollider colliderInit(ITransform t);

    /**
     * Returns a string representation of the geometric figure.
     * @return string representation of the geometric figure
     */
    @Override
    public abstract String toString();
}