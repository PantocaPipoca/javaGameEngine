package Testes;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figuras.Poligono;

public class TestesPoligono {

    @Test
    public void testPoligonoValido() {
        assertDoesNotThrow(() -> {
            // Quadrado
            Poligono p = new Poligono("4 5 5 8 6 8 7 5 7");
            assertEquals("Poligono de 4 vertices: [(5.0,5.0), (8.0,6.0), (8.0,7.0), (5.0,7.0)]", p.toString());
        });
        assertDoesNotThrow(() -> {
            // Triangulo
            Poligono p = new Poligono("3 1 1 3 1 2 4");
            assertEquals("Poligono de 3 vertices: [(1.0,1.0), (3.0,1.0), (2.0,4.0)]", p.toString());
        });
    }

    @Test
    public void testPoligonoInvalido() {
        // Poucos vertices
        assertThrows(IllegalArgumentException.class, () -> {
            new Poligono("2 1 1 2 2");
        });

        // Numero de tokens incorreto
        assertThrows(IllegalArgumentException.class, () -> {
            new Poligono("4 5 5 8 6 8 7 5");
        });

        // Pontos colineares
        assertThrows(IllegalArgumentException.class, () -> {
            new Poligono("4 0 0 2 2 4 4 0 4");
        });

        // Poligono auto-intersectante
        assertThrows(IllegalArgumentException.class, () -> {
            new Poligono("4 0 0 4 4 0 4 4 0");
        });
    }

    @Test
    public void testTranslate() {
        Poligono p = new Poligono("6 5 5 8 6 8 7 5 7 11 12 1 5");
        Poligono res = p.translate(1, 3);
        assertEquals("Poligono de 6 vertices: [(6.0,8.0), (9.0,9.0), (9.0,10.0), (6.0,10.0), (12.0,15.0), (2.0,8.0)]", res.toString());
        assertEquals("Poligono de 6 vertices: [(5.0,5.0), (8.0,6.0), (8.0,7.0), (5.0,7.0), (11.0,12.0), (1.0,5.0)]", p.toString()); // p nao deve ser modificado

        p = new Poligono("6 5 5 8 6 8 7 5 7 11 12 1 5");
        res = p.translate(0, 0);
        assertEquals("Poligono de 6 vertices: [(5.0,5.0), (8.0,6.0), (8.0,7.0), (5.0,7.0), (11.0,12.0), (1.0,5.0)]", res.toString());
        assertEquals("Poligono de 6 vertices: [(5.0,5.0), (8.0,6.0), (8.0,7.0), (5.0,7.0), (11.0,12.0), (1.0,5.0)]", p.toString()); // p nao deve ser modificado
    }
}
