package Testes;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figuras.Ponto;
import Figuras.Retangulo;
import Figuras.Segmento;

public class TestesRetangulo {

    @Test
    public void testeConstrutor() {
        // Testes com o novo construtor baseado em strings
        assertDoesNotThrow(() -> new Retangulo("0 0 4 0 4 3 0 3"));
        assertDoesNotThrow(() -> new Retangulo("1 1 5 1 5 4 1 4"));

        assertThrows(IllegalArgumentException.class, () -> new Retangulo("0 0 4 0 4 4 0 2"));
        assertThrows(IllegalArgumentException.class, () -> new Retangulo("1 1 5 1 5 5 1 3"));
    }

    @Test
    public void testCanBeARectangle() {

        assertTrue(Retangulo.canBeARectangle(new Ponto(0, 0), new Ponto(4, 0), new Ponto(4, 3), new Ponto(0, 3)));
        assertTrue(Retangulo.canBeARectangle(new Ponto(1, 1), new Ponto(5, 1), new Ponto(5, 4), new Ponto(1, 4)));

        assertFalse(Retangulo.canBeARectangle(new Ponto(0, 0), new Ponto(4, 0), new Ponto(4, 4), new Ponto(0, 2)));
        assertFalse(Retangulo.canBeARectangle(new Ponto(1, 1), new Ponto(5, 1), new Ponto(5, 5), new Ponto(1, 3)));
    }

    @Test
    public void testIntercessao() {
        // Testes para interseção com segmentos
        Segmento s1 = new Segmento(new Ponto(3, 1), new Ponto(4, 3));
        Retangulo r1 = new Retangulo("4 1 6 1 6 2 4 2");
        assertFalse(r1.intersects(s1));

        Segmento s2 = new Segmento(new Ponto(4, 0), new Ponto(5, 3));
        assertTrue(r1.intersects(s2));

        Segmento s3 = new Segmento(new Ponto(1, 3), new Ponto(3, 3));
        Retangulo r3 = new Retangulo("3 2 4 1 6 3 5 4");
        assertFalse(r3.intersects(s3));

        Segmento s4 = new Segmento(new Ponto(1, 3), new Ponto(6, 4));
        assertTrue(r3.intersects(s4));

        Segmento s5 = new Segmento(new Ponto(1, 3), new Ponto(5, 3));
        assertTrue(r3.intersects(s5));

        Segmento s6 = new Segmento(new Ponto(1, 3), new Ponto(4, 3));
        assertFalse(r3.intersects(s6));

        Segmento s7 = new Segmento(new Ponto(5, 2), new Ponto(5, 10));
        Retangulo r4 = new Retangulo("0 0 5 0 5 5 0 5");
        assertFalse(r4.intersects(s7));
    }

    @Test
    public void testTranslate() {
        Retangulo p = new Retangulo("5 5 6 8 3 9 2 6");
        Retangulo res = p.translate(27, -1);
        assertEquals("Retangulo: [(32.0,4.0), (33.0,7.0), (30.0,8.0), (29.0,5.0)]", res.toString());
        assertEquals("Retangulo: [(5.0,5.0), (6.0,8.0), (3.0,9.0), (2.0,6.0)]", p.toString()); // p nao deve ser modificado

        p = new Retangulo("5 5 6 8 3 9 2 6");
        res = p.translate(-2, 3);
        assertEquals("Retangulo: [(3.0,8.0), (4.0,11.0), (1.0,12.0), (0.0,9.0)]", res.toString());
        assertEquals("Retangulo: [(5.0,5.0), (6.0,8.0), (3.0,9.0), (2.0,6.0)]", p.toString()); // p nao deve ser modificado
    }
}