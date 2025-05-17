package GameEngine;

import Figures.Point;

/**
 * Interface for object transformations.
 * Provides methods for moving, rotating, scaling, and accessing transform properties.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (23/03/25)
 */
public interface ITransform {
    void move(Point dPos, int dLayer);
    void rotate(double dTheta);
    void scale(double dScale);
    Point position();
    int layer();
    double angle();
    double scale();
    void setAngle(double angle);
    void setPosition(Point position);
}