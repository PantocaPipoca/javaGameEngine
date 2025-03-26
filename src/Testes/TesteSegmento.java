package Testes;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figuras.Circulo;
import Figuras.GeometriaUtils;
import Figuras.Ponto;
import Figuras.Segmento;

public class TesteSegmento {

    @Test
    public void testeConstrutor() {

        assertDoesNotThrow(() -> new Segmento(new Ponto(1, 1), new Ponto(2, 2)));
        assertDoesNotThrow(() -> new Segmento(new Ponto(0, 0), new Ponto(5, 5)));

        assertThrows(IllegalArgumentException.class, () -> new Segmento(new Ponto(1, 1), new Ponto(1, 1)));
        assertThrows(IllegalArgumentException.class, () -> new Segmento(new Ponto(2, 2), new Ponto(2, 2)));
    }

    @Test
    public void testorientation() {

        assertEquals(0, GeometriaUtils.orientation(new Ponto(2, 2), new Ponto(4, 4), new Ponto(6, 6)));

        assertNotEquals(0, GeometriaUtils.orientation(new Ponto(0, 0), new Ponto(4, 4), new Ponto(1, 2)));
        assertNotEquals(2, GeometriaUtils.orientation(new Ponto(2, 2), new Ponto(4, 4), new Ponto(6, 6)));
    }

    @Test
    public void testOnSegment() {

        assertTrue(GeometriaUtils.onSegment(new Ponto(0, 0), new Ponto(2, 2), new Ponto(4, 4)));
        assertTrue(GeometriaUtils.onSegment(new Ponto(0, 0), new Ponto(1, 1), new Ponto(4, 4)));
        assertTrue(GeometriaUtils.onSegment(new Ponto(0, 0), new Ponto(3, 3), new Ponto(4, 4)));

        assertFalse(GeometriaUtils.onSegment(new Ponto(0, 0), new Ponto(5, 5), new Ponto(4, 4)));
    }

    @Test
    public void testIntersetaValido() {
        Circulo c = new Circulo("5 5 3.0");
        Segmento s = new Segmento(new Ponto(2, 5), new Ponto(8, 5));
        assertTrue(s.interseta(c));
    }

    @Test
    public void testIntersetaInvalido() {
        Circulo c = new Circulo("5 5 3.0");
        Segmento s = new Segmento(new Ponto(0, 0), new Ponto(1, 1));
        assertFalse(s.interseta(c));
    }

}