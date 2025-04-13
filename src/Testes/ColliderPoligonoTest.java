package Testes;

import static org.junit.jupiter.api.Assertions.*;

import Figuras.Poligono;
import Figuras.Ponto;
import Figuras.Circulo;
import GameEngine.*;
import org.junit.jupiter.api.Test;

public class ColliderPoligonoTest {

    @Test
    public void doisPoligonosQueColidem() {
        Poligono p1 = new Poligono("4 0 0 4 0 4 4 0 4");
        Poligono p2 = new Poligono("4 3 3 5 3 5 5 3 5");

        ITransform t1 = new Transform(new Ponto(0, 0), 0, 0, 1);
        ITransform t2 = new Transform(new Ponto(3, 3), 0, 0, 1);

        ColliderPoligono collider1 = new ColliderPoligono(p1, t1);
        ColliderPoligono collider2 = new ColliderPoligono(p2, t2);

        assertTrue(collider1.colide(collider2));
    }

    @Test
    public void doisPoligonosQueNaoColidem() {
        Poligono p1 = new Poligono("4 0 0 4 0 4 4 0 4");
        Poligono p2 = new Poligono("4 10 10 14 10 14 14 10 14");

        ITransform t1 = new Transform(new Ponto(0, 0), 0, 0, 1);
        ITransform t2 = new Transform(new Ponto(10, 10), 0, 0, 1);

        ColliderPoligono collider1 = new ColliderPoligono(p1, t1);
        ColliderPoligono collider2 = new ColliderPoligono(p2, t2);

        assertFalse(collider1.colide(collider2));
    }

    @Test
    public void poligonoColideComCirculo() {
        Poligono p = new Poligono("4 0 0 4 0 4 4 0 4");
        Circulo c = new Circulo("2 2 1");

        ITransform tP = new Transform(new Ponto(0, 0), 0, 0, 1);
        ITransform tC = new Transform(new Ponto(2, 2), 0, 0, 1);

        ColliderPoligono colliderPol = new ColliderPoligono(p, tP);
        ColliderCirculo colliderCir = new ColliderCirculo(c, tC);

        assertTrue(colliderPol.colide(colliderCir));
    }

    @Test
    public void poligonoNaoColideComCirculo() {
        Poligono p = new Poligono("4 0 0 4 0 4 4 0 4");
        Circulo c = new Circulo("10 10 1");

        ITransform tP = new Transform(new Ponto(0, 0), 0, 0, 1);
        ITransform tC = new Transform(new Ponto(10, 10), 0, 0, 1);

        ColliderPoligono colliderPol = new ColliderPoligono(p, tP);
        ColliderCirculo colliderCir = new ColliderCirculo(c, tC);

        assertFalse(colliderPol.colide(colliderCir));
    }

    @Test
    public void pontoDentroDoPoligono() {
        Poligono p = new Poligono("4 0 0 4 0 4 4 0 4");
        Ponto inside = new Ponto(2, 2);
        Ponto outside = new Ponto(5, 5);

        assertTrue(ColliderPoligono.pontoEstaDentroDoPoligono(inside, p));
        assertFalse(ColliderPoligono.pontoEstaDentroDoPoligono(outside, p));
    }

    @Test
    public void centroidDoPoligono() {
        Poligono p = new Poligono("4 0 0 4 0 4 4 0 4");
        ITransform t = new Transform(new Ponto(2, 2), 0, 0, 1);

        ColliderPoligono collider = new ColliderPoligono(p, t);
        Ponto centroide = collider.centroid();

        assertEquals(2.0, centroide.x(), 0.001);
        assertEquals(2.0, centroide.y(), 0.001);
    }

    @Test
    public void rayIntersectsSegment() {
        // Ray intersects the segment
        Ponto p1 = new Ponto(2, 2);
        Ponto a1 = new Ponto(1, 1);
        Ponto b1 = new Ponto(3, 3);
        assertTrue(ColliderPoligono.rayInterssetaSegmento(p1, a1, b1));

        // Ray intersects a vertical segment
        Ponto p2 = new Ponto(2, 2);
        Ponto a2 = new Ponto(2, 1);
        Ponto b2 = new Ponto(2, 3);
        assertTrue(ColliderPoligono.rayInterssetaSegmento(p2, a2, b2));

        // Ray touches the endpoint of the segment
        Ponto p4 = new Ponto(3, 3);
        Ponto a4 = new Ponto(1, 1);
        Ponto b4 = new Ponto(3, 3);
        assertTrue(ColliderPoligono.rayInterssetaSegmento(p4, a4, b4));

        // Ray intersects the segment at an exact point
        Ponto p5 = new Ponto(2, 2);
        Ponto a5 = new Ponto(1, 1);
        Ponto b5 = new Ponto(3, 3);
        assertTrue(ColliderPoligono.rayInterssetaSegmento(p5, a5, b5));
    }

    @Test
    public void rayDoesNotIntersectSegment() {
        // Ray is above the segment
        Ponto p1 = new Ponto(2, 4);
        Ponto a1 = new Ponto(1, 1);
        Ponto b1 = new Ponto(3, 3);
        assertFalse(ColliderPoligono.rayInterssetaSegmento(p1, a1, b1));

        // Ray is below the segment
        Ponto p2 = new Ponto(2, 0);
        Ponto a2 = new Ponto(1, 1);
        Ponto b2 = new Ponto(3, 3);
        assertFalse(ColliderPoligono.rayInterssetaSegmento(p2, a2, b2));

        // Ray does not intersect a vertical segment
        Ponto p3 = new Ponto(3, 2);
        Ponto a3 = new Ponto(2, 1);
        Ponto b3 = new Ponto(2, 3);
        assertFalse(ColliderPoligono.rayInterssetaSegmento(p3, a3, b3));

        // Ray is parallel to the segment and does not intersect
        Ponto p5 = new Ponto(2, 2);
        Ponto a5 = new Ponto(1, 3);
        Ponto b5 = new Ponto(3, 3);
        assertFalse(ColliderPoligono.rayInterssetaSegmento(p5, a5, b5));
    }
}