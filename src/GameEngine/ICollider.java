package GameEngine;

import Figures.GeometricFigure;

/**
 * Interface for colliders in the game engine.
 * Provides methods for collision detection and access to the underlying geometric figure.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public interface ICollider {
    String toString();
    boolean colidesWithCircle(ColliderCircle cc);
    boolean colidesWithPolygon(ColliderPolygon cp);
    boolean colides(ICollider other);
    GeometricFigure figure();
}