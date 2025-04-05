package Testes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figuras.Circulo;
import Figuras.Ponto;


public class TestesCirculo {

    @Test
    public void testesCirculoValido() {
        // Testa o construtor valido
        assertDoesNotThrow(() -> {
            Circulo c = new Circulo("5 5 2.0");
            assertEquals(5, c.centro().x());
            assertEquals(5, c.centro().y());
            assertEquals(2.0, c.raio());
            assertEquals("(5.00,5.00) 2.00", c.toString());
            assertEquals(2 * Math.PI * 2.0, c.perimetro(), 1e-9);
        });
    }

    @Test
    public void testesCirculoInvalido() {
        // Testa erro dum raio negativo.
        assertThrows(IllegalArgumentException.class, () -> {
            new Circulo("5 5 -1.0");
        });
    }

    @Test
    public void testTranslateValid() {
        Circulo c = new Circulo("5 5 2.0");
        Circulo translated = c.translate(3, 4);
        assertEquals(8, translated.centro().x());
        assertEquals(9, translated.centro().y());
        assertEquals(2.0, translated.raio(), 1e-9);
        assertEquals("(8.00,9.00) 2.00", translated.toString());
    }

    @Test
    public void testClone() {
        Circulo c = new Circulo("5 5 2.0");
        
        // Testa se o clone não lança exceções
        assertDoesNotThrow(() -> {
            Circulo clone = c.clone();
            assertEquals(c.centro().x(), clone.centro().x());
            assertEquals(c.centro().y(), clone.centro().y());
            assertEquals(c.raio(), clone.raio());
            assertNotSame(c, clone); // Verifica que não é o mesmo objeto
        });

        // Testa se o clone é independente do original
        Circulo clone = c.clone();
        Circulo scaled = c.scale(2.0);
        assertNotEquals(clone.raio(), scaled.raio());
    }

    @Test
    public void testScale() {
        Circulo c = new Circulo("5 5 2.0");

        // Testa escala válida
        assertDoesNotThrow(() -> {
            Circulo scaled = c.scale(2.0);
            assertEquals(4.0, scaled.raio());
            assertEquals(c.centro().x(), scaled.centro().x());
            assertEquals(c.centro().y(), scaled.centro().y());
        });

        // Testa escala com fator 1 (deve ser igual ao original)
        Circulo scaled = c.scale(1.0);
        assertEquals(c.raio(), scaled.raio());

        // Testa escala com fator negativo (raio inválido)
        assertThrows(IllegalArgumentException.class, () -> {
            c.scale(-1.0);
        });
    }

    @Test
    public void testRotate() {
        Circulo c = new Circulo("5 5 2.0");
        Ponto centroRotacao = new Ponto(0, 0);

        // Testa rotação válida (não faz nada, mas não deve lançar exceções)
        assertDoesNotThrow(() -> {
            Circulo rotated = c.rotate(90, centroRotacao);
            assertEquals(c.centro().x(), rotated.centro().x());
            assertEquals(c.centro().y(), rotated.centro().y());
            assertEquals(c.raio(), rotated.raio());
        });

        // Testa rotação com ângulo 0 (deve ser igual ao original)
        Circulo rotated = c.rotate(0, centroRotacao);
        assertEquals(c.centro().x(), rotated.centro().x());
        assertEquals(c.centro().y(), rotated.centro().y());
        assertEquals(c.raio(), rotated.raio());

        // Testa rotação com ângulo negativo (não deve lançar exceções)
        assertDoesNotThrow(() -> {
            Circulo rotatedNegative = c.rotate(-45, centroRotacao);
            assertEquals(c.centro().x(), rotatedNegative.centro().x());
            assertEquals(c.centro().y(), rotatedNegative.centro().y());
            assertEquals(c.raio(), rotatedNegative.raio());
        });

        // Testa rotação com ponto de rotação diferente (não deve lançar exceções)
        Ponto outroCentro = new Ponto(10, 10);
        assertDoesNotThrow(() -> {
            Circulo rotatedOutroCentro = c.rotate(45, outroCentro);
            assertEquals(c.centro().x(), rotatedOutroCentro.centro().x());
            assertEquals(c.centro().y(), rotatedOutroCentro.centro().y());
            assertEquals(c.raio(), rotatedOutroCentro.raio());
        });
    }
}