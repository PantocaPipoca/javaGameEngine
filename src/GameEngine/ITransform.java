package GameEngine;

import Figures.Point;

public interface ITransform {
    void move(Point dPos, int dlayer);
    void rotate(double dTheta);
    void scale(double dScale);
    Point position();
    int layer();
    double angle();
    double scale();
}