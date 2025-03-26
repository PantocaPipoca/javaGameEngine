package Testes;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figuras.Ponto;

public class TestesPonto {

    @Test
    public void testeConstrutor() {
        assertDoesNotThrow(() -> new Ponto(0, 0));
        assertDoesNotThrow(() -> new Ponto(3, 4));

        assertThrows(IllegalArgumentException.class, () -> new Ponto(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Ponto(-1, -3));
    }

    @Test
    public void testeConstrutor2() {
        assertDoesNotThrow(() -> new Ponto(2, 4));

        assertThrows(IllegalArgumentException.class, () -> new Ponto(-23, 0));
        assertThrows(IllegalArgumentException.class, () -> new Ponto(0, -1));
    }

    @Test
    public void testeDistancia() {
        Ponto p1 = new Ponto(0, 0);
        Ponto p2 = new Ponto(3, 4);

        assertEquals(5.0, p1.distancia(p2));
        assertEquals(5.0, p2.distancia(p1));

        assertNotEquals(6.0, p1.distancia(p2));
        assertNotEquals(4.0, p2.distancia(p1));
    }
}