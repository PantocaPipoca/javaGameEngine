package Testes;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figuras.Circulo;
import Figuras.Poligono;
import Figuras.Ponto;
import Figuras.Retangulo;
import Figuras.Triangulo;

public class TestesColisoes {

    @Test
    public void testesSemColisoes() {
        Poligono p = new Poligono("4 5 5 8 6 8 7 5 7");
        Triangulo t = new Triangulo("7 1 9 1 9 3");
        Circulo c = new Circulo("2 2 1");

        // Usando o método colide da figura (double dispatch)
        assertFalse(c.colide(p));
        assertFalse(p.colide(c));
        assertFalse(t.colide(p));
        assertFalse(p.colide(t));
        assertFalse(t.colide(c));
        assertFalse(c.colide(t));
    }

    @Test
    public void testesComColisoes() {
        Poligono f1 = new Poligono("4 5 5 8 6 8 7 5 7");
        Retangulo f2 = new Retangulo("4 4 6 4 6 6 4 6");
        assertTrue(f1.colide(f2));
    }

    @Test
    public void testColideCirculoCirculoCollision() {
        Circulo c1 = new Circulo("5 5 3.0");
        Circulo c2 = new Circulo("7 5 3.0");
        assertTrue(c1.colide(c2));
    }

    @Test
    public void testColideCirculoCirculoNoCollision() {
        Circulo c1 = new Circulo("5 5 3.0");
        Circulo c2 = new Circulo("20 20 3.0");
        assertFalse(c1.colide(c2));
    }

    @Test
    public void testColideCirculoPoligonoCollision() {
        Circulo c = new Circulo("5 5 1.0");
        Poligono p = new Poligono("3 4 4 6 4 5 6");
        assertTrue(c.colide(p));
        assertTrue(p.colide(c));
    }

    @Test
    public void testColideCirculoPoligonoNoCollision() {
        Circulo c = new Circulo("1 1 1.0");
        Poligono p = new Poligono("3 10 10 12 10 11 12");
        assertFalse(c.colide(p));
        assertFalse(p.colide(c));
    }

    @Test
    public void testColidePoligonoPoligonoCollision() {
        Poligono p1 = new Poligono("4 0 0 2 0 2 2 0 2");
        Poligono p2 = new Poligono("4 1 1 3 1 3 3 1 3");
        assertTrue(p1.colide(p2));
    }

    @Test
    public void testColidePoligonoPoligonoNoCollision() {
        Poligono p1 = new Poligono("4 0 0 2 0 2 2 0 2");
        Poligono p2 = new Poligono("4 10 10 12 10 12 12 10 12");
        assertFalse(p1.colide(p2));
    }

    ///////////////////////////////
    
    // Os métodos auxiliares "pontoEstaDentroDoPoligono" e "rayInterssetaSegmento"
    // foram passados a ser públicos na classe Poligono para fins de teste.
    
    @Test
    public void testPontoEstaDentroDoPoligonoValido() {
        Poligono p = new Poligono("4 0 0 4 0 4 4 0 4");
        Ponto pnt = new Ponto(2, 2);
        assertTrue(Poligono.pontoEstaDentroDoPoligono(pnt, p));
    }

    @Test
    public void testPontoEstaDentroDoPoligonoInvalido() {
        Poligono p = new Poligono("4 0 0 4 0 4 4 0 4");
        Ponto pnt = new Ponto(5, 5);
        assertFalse(Poligono.pontoEstaDentroDoPoligono(pnt, p));
        
        p = new Poligono("4 0 0 4 0 4 4 0 4");
        pnt = new Ponto(0, 2);
        assertFalse(Poligono.pontoEstaDentroDoPoligono(pnt, p));
    }

    @Test
    public void testRayInterssetaSegmentoTrue() {
        Ponto p = new Ponto(2, 2);
        Ponto a = new Ponto(0, 0);
        Ponto b = new Ponto(4, 4);
        assertTrue(Poligono.rayInterssetaSegmento(p, a, b));
    }

    @Test
    public void testRayInterssetaSegmentoFalse() {
        Ponto p = new Ponto(5, 5);
        Ponto a = new Ponto(0, 0);
        Ponto b = new Ponto(4, 4);
        assertFalse(Poligono.rayInterssetaSegmento(p, a, b));
    }
}
