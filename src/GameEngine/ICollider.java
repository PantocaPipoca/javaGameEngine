package GameEngine;

import Figuras.Ponto;

public interface ICollider {
    Ponto centroid();
    public String toString();
}