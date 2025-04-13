package Testes;

import static org.junit.jupiter.api.Assertions.*;

import Figuras.Poligono;
import org.junit.jupiter.api.Test;

import Figuras.Circulo;
import Figuras.Ponto;
import GameEngine.*;

public class ColliderCirculoTest {

    @Test
    public void doisCirculosQueColidem() {
        Circulo c1 = new Circulo("0 0 2");
        Circulo c2 = new Circulo("3 0 2");

        ITransform t1 = new Transform(new Ponto(0, 0), 0, 0, 1);
        ITransform t2 = new Transform(new Ponto(3, 0), 0, 0, 1);

        ColliderCirculo collider1 = new ColliderCirculo(c1, t1);
        ColliderCirculo collider2 = new ColliderCirculo(c2, t2);

        assertTrue(collider1.colide(collider2));
    }

    @Test
    public void doisCirculosQueNaoColidem() {
        Circulo c1 = new Circulo("0 0 1");    // raio 1
        Circulo c2 = new Circulo("5 0 1");    // raio 1

        ITransform t1 = new Transform(new Ponto(0, 0), 0, 0, 1);
        ITransform t2 = new Transform(new Ponto(5, 0), 0, 0, 1);

        ColliderCirculo collider1 = new ColliderCirculo(c1, t1);
        ColliderCirculo collider2 = new ColliderCirculo(c2, t2);

        assertFalse(collider1.colide(collider2));
    }

    @Test
    public void getFigura() {
        Circulo original = new Circulo("1 1 1"); // raio 1
        ITransform t = new Transform(new Ponto(3, 3), 0, 0, 2); // escala ×2

        ColliderCirculo collider = new ColliderCirculo(original, t);
        Circulo resultado = collider.getFigura();

        assertEquals(2, resultado.raio(), 0.0001);
    }

    @Test
    public void centroid() {
        Circulo c = new Circulo("2 2 1");
        ITransform t = new Transform(new Ponto(5, 5), 0, 0, 1);

        ColliderCirculo collider = new ColliderCirculo(c, t);
        Ponto centro = collider.centroid();

        assertEquals(5.0, centro.x(), 0.001);
        assertEquals(5.0, centro.y(), 0.001);
    }

    @Test
    public void colideComPoligono() {
        Circulo c = new Circulo("4 4 1");
        ITransform tC = new Transform(new Ponto(4, 4), 0, 0, 1);
        ColliderCirculo collider = new ColliderCirculo(c, tC);

        Poligono p = new Poligono("4 3 3 5 3 5 5 3 5");
        ITransform tP = new Transform(new Ponto(4, 4), 0, 0, 1);
        ColliderPoligono colliderPol = new ColliderPoligono(p, tP);

        assertTrue(collider.colide(colliderPol));
    }

    @Test
    public void NaocolideComPoligono() {
        Circulo c = new Circulo("4 4 1");
        ITransform tC = new Transform(new Ponto(4, 4), 0, 0, 1);
        ColliderCirculo collider = new ColliderCirculo(c, tC);

        Poligono p = new Poligono("4 3 3 5 3 5 5 3 5");
        ITransform tP = new Transform(new Ponto(0, 0), 0, 0, 1);
        ColliderPoligono colliderPol = new ColliderPoligono(p, tP);

        assertFalse(collider.colide(colliderPol));
    }
}
