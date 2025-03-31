package Testes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figuras.Ponto;

public class TestesPonto {

    @Test
    public void testeConstrutor() {
        // Testa construtores válidos
        assertDoesNotThrow(() -> new Ponto(0, 0));
        assertDoesNotThrow(() -> new Ponto(3, 4));
        assertDoesNotThrow(() -> new Ponto(-5, 7));

        // Testa valores extremos
        assertDoesNotThrow(() -> new Ponto(Double.MAX_VALUE, Double.MAX_VALUE));
        assertDoesNotThrow(() -> new Ponto(Double.MIN_VALUE, Double.MIN_VALUE));
    }

    @Test
    public void testeDistancia() {
        Ponto p1 = new Ponto(0, 0);
        Ponto p2 = new Ponto(3, 4);
        Ponto p3 = new Ponto(-3, -4);

        // Testa distâncias válidas
        assertEquals(5.0, p1.distancia(p2), 1e-9);
        assertEquals(5.0, p2.distancia(p1), 1e-9);
        assertEquals(10.0, p2.distancia(p3), 1e-9);

        // Testa distâncias inválidas
        assertNotEquals(6.0, p1.distancia(p2));
        assertNotEquals(4.0, p2.distancia(p3));
    }

    @Test
    public void testeTranslate() {
        Ponto p1 = new Ponto(1, 1);

        // Testa deslocamento válido
        Ponto p2 = p1.translate(3, 4);
        assertEquals(4.0, p2.x(), 1e-9);
        assertEquals(5.0, p2.y(), 1e-9);

        // Testa deslocamento negativo
        Ponto p3 = p1.translate(-1, -1);
        assertEquals(0.0, p3.x(), 1e-9);
        assertEquals(0.0, p3.y(), 1e-9);

        // Testa deslocamento com zero
        Ponto p4 = p1.translate(0, 0);
        assertEquals(p1.x(), p4.x(), 1e-9);
        assertEquals(p1.y(), p4.y(), 1e-9);

        // Testa deslocamento extremo
        Ponto p5 = p1.translate(Double.MAX_VALUE, Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE + 1, p5.x(), 1e-9);
        assertEquals(Double.MAX_VALUE + 1, p5.y(), 1e-9);
    }

    @Test
    public void testeToString() {
        Ponto p1 = new Ponto(3, 4);
        Ponto p2 = new Ponto(-5, -7);

        // Testa representação válida
        assertEquals("(3.00,4.00)", p1.toString());
        assertEquals("(-5.00,-7.00)", p2.toString());

        // Testa representação com valores extremos
        Ponto p3 = new Ponto(Double.MAX_VALUE, Double.MIN_VALUE);
        assertDoesNotThrow(() -> p3.toString());
    }

    @Test
    public void testePolarCoordinates() {
        Ponto p1 = new Ponto(3, 4);
        Ponto p2 = new Ponto(-3, -4);

        // Testa valores de r
        assertEquals(5.0, p1.r());
        assertEquals(5.0, p2.r());

        // Testa valores de theta
        assertEquals(Math.atan2(4, 3), p1.theta());
        assertEquals(Math.atan2(-4, -3), p2.theta());

        // Testa valores extremos
        Ponto p3 = new Ponto(Double.MAX_VALUE, Double.MIN_VALUE);
        assertDoesNotThrow(() -> p3.r());
        assertDoesNotThrow(() -> p3.theta());
    }
}