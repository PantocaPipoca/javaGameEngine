package Testes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figuras.Circulo;


public class TestesCirculo {

    @Test
    public void testesCirculoValido() {
        // Testa o construtor valido
        assertDoesNotThrow(() -> {
            Circulo c = new Circulo("5 5 2.0");
            assertEquals(5, c.centro().x());
            assertEquals(5, c.centro().y());
            assertEquals(2.0, c.raio());
            assertEquals("Circulo: (5.0,5.0) 2", c.toString());
            assertEquals(2 * Math.PI * 2.0, c.perimetro(), 1e-9);
        });
    }

    @Test
    public void testesCirculoInvalido() {
        // Testa erro dum raio igual a zero.
        assertThrows(IllegalArgumentException.class, () -> {
            new Circulo("5 5 0");
        });

        // Testa erro dum raio negativo.
        assertThrows(IllegalArgumentException.class, () -> {
            new Circulo("5 5 -1.0");
        });

        // Testa erro quando o circulo nao esta totalmente contido no primeiro quadrante.
        assertThrows(IllegalArgumentException.class, () -> {
            new Circulo("2 2 3.0");
        });
    }

    @Test
    public void testTranslateValid() {
        Circulo c = new Circulo("5 5 2.0");
        Circulo translated = c.translate(3, 4);
        assertEquals(8, translated.centro().x());
        assertEquals(9, translated.centro().y());
        assertEquals(2.0, translated.raio(), 1e-9);
        assertEquals("Circulo: (8.0,9.0) 2", translated.toString());
    }

    @Test
    public void testTranslateInvalid() {
        //Dps da translacao o circulo fica no primeiro quadrante
        Circulo c = new Circulo("5 5 2.0");
        assertThrows(IllegalArgumentException.class, () -> {
            c.translate(-10, 0);
        });
    }
}