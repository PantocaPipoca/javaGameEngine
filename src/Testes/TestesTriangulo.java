package Testes;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figuras.Triangulo;

public class TestesTriangulo {

    @Test
    public void testesTrianguloValido() {
        // Testa triângulo válido
        assertDoesNotThrow(() -> new Triangulo("7 1 9 1 9 3"));
        assertDoesNotThrow(() -> new Triangulo("0 0 4 0 2 3"));

        // Testa o ToString
        Triangulo t = new Triangulo("10 10 15 10 12 15");
        String expected = "Triangulo: [(10.0,10.0), (15.0,10.0), (12.0,15.0)]";
        assertEquals(expected, t.toString());
    }

    @Test
    public void testesTrianguloInvalido() {
        // Testa erro com pontos colineares
        assertThrows(IllegalArgumentException.class, () -> {
            new Triangulo("1 1 2 2 3 3");
        });

        // Testa erro com tokens insuficientes
        assertThrows(IllegalArgumentException.class, () -> {
            new Triangulo("1 1 2 2 3");
        });

        // Testa erro ao fornecer tokens nao numericos
        assertThrows(IllegalArgumentException.class, () -> {
            new Triangulo("a b c d e f");
        });
    }


    @Test
    public void testTranslate() {
        Triangulo p = new Triangulo("7 1 9 1 19 2");
        Triangulo res = p.translate(0, -1);
        assertEquals("Triangulo: [(7.0,0.0), (9.0,0.0), (19.0,1.0)]", res.toString());
        assertEquals("Triangulo: [(7.0,1.0), (9.0,1.0), (19.0,2.0)]", p.toString()); // p nao deve ser modificado
    }
}
