package GameEngine;

import Figures.GeometricFigure;

public interface ICollider {

    public String toString();
    public boolean colidesWithCircle(ColliderCircle cc);
    public boolean colidesWithPolygon(ColliderPolygon cp);
    public boolean colides(ICollider other);
    public GeometricFigure getFigure();
}