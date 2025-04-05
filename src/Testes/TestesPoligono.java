package Testes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figuras.FiguraGeometrica;
import Figuras.Poligono;
import Figuras.Ponto;

public class TestesPoligono {

    @Test
    public void testPoligonoValido() {
        assertDoesNotThrow(() -> {
            // Quadrado
            Poligono p = new Poligono("4 5 5 8 6 8 7 5 7");
            assertEquals("(5.00,5.00) (8.00,6.00) (8.00,7.00) (5.00,7.00)", p.toString());
        });
        assertDoesNotThrow(() -> {
            // Triangulo
            Poligono p = new Poligono("3 1 1 3 1 2 4");
            assertEquals("(1.00,1.00) (3.00,1.00) (2.00,4.00)", p.toString());
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
        assertEquals("(6.00,8.00) (9.00,9.00) (9.00,10.00) (6.00,10.00) (12.00,15.00) (2.00,8.00)", res.toString());
        assertEquals("(5.00,5.00) (8.00,6.00) (8.00,7.00) (5.00,7.00) (11.00,12.00) (1.00,5.00)", p.toString()); // p nao deve ser modificado

        p = new Poligono("6 5 5 8 6 8 7 5 7 11 12 1 5");
        res = p.translate(0, 0);
        assertEquals("(5.00,5.00) (8.00,6.00) (8.00,7.00) (5.00,7.00) (11.00,12.00) (1.00,5.00)", res.toString());
        assertEquals("(5.00,5.00) (8.00,6.00) (8.00,7.00) (5.00,7.00) (11.00,12.00) (1.00,5.00)", p.toString()); // p nao deve ser modificado
    }

    @Test
    public void testCentroide() {
        // Quadrado
        Poligono p1 = new Poligono("4 0 0 4 0 4 4 0 4");
        Ponto c1 = p1.centroide();
        assertEquals(2.0, c1.x(), 1e-9);
        assertEquals(2.0, c1.y(), 1e-9);

        // Retângulo
        Poligono p2 = new Poligono("4 1 1 5 1 5 3 1 3");
        Ponto c2 = p2.centroide();
        assertEquals(3.0, c2.x(), 1e-9);
        assertEquals(2.0, c2.y(), 1e-9);

        // Triângulo
        Poligono p3 = new Poligono("3 0 0 6 0 3 6");
        Ponto c3 = p3.centroide();
        assertEquals(3.0, c3.x(), 1e-9);
        assertEquals(2.0, c3.y(), 1e-9);
    }

    @Test
    public void testClone() {
        // Quadrado
        Poligono original = new Poligono("4 0 0 4 0 4 4 0 4");
        FiguraGeometrica cloneFigura = original.clone();
        assertNotSame(original, cloneFigura);
        assertEquals(original.toString(), cloneFigura.toString());

        // Triângulo
        Poligono tri = new Poligono("3 0 0 3 0 2 5");
        Poligono triClone = (Poligono) tri.clone();
        assertNotSame(tri, triClone);
        assertEquals(tri.toString(), triClone.toString());

        // Pentágono
        Poligono penta = new Poligono("5 0 0 4 0 6 2 4 4 2 4");
        Poligono pentaClone = (Poligono) penta.clone();
        assertEquals("(0.00,0.00) (4.00,0.00) (6.00,2.00) (4.00,4.00) (2.00,4.00)", penta.toString());
        assertEquals(penta.toString(), pentaClone.toString());

        // Verifica se alterações no original não afetam o clone
        Poligono alterado = penta.translate(1, 1);
        assertNotEquals(alterado.toString(), pentaClone.toString());

        // Criando outro clone
        Poligono outroClone = (Poligono) penta.clone();
        assertEquals(pentaClone.toString(), outroClone.toString());
        assertNotSame(pentaClone, outroClone);
    }

    @Test
    public void testScale() {
        // Escalando por fator 2
        Poligono square = new Poligono("4 0 0 4 0 4 4 0 4");
        Poligono scaledSquare = (Poligono) square.scale(2.0);
        assertEquals("(-2.00,-2.00) (6.00,-2.00) (6.00,6.00) (-2.00,6.00)", scaledSquare.toString());

        // Verifica se o original não foi modificado
        assertEquals("(0.00,0.00) (4.00,0.00) (4.00,4.00) (0.00,4.00)", square.toString());

        // Centroide escalado por 0.5
        Poligono triangle = new Poligono("3 0 0 6 0 3 6");
        Poligono scaledTriangle = (Poligono) triangle.scale(0.5);
        assertEquals("(1.50,1.00) (4.50,1.00) (3.00,4.00)", scaledTriangle.toString());

        // Escala com fator 1 (sem mudanças)
        Poligono sameSize = (Poligono) square.scale(1.0);
        assertEquals(square.toString(), sameSize.toString());

        // Fator nulo ou negativo
        assertThrows(IllegalArgumentException.class, () -> square.scale(0));
    }

    @Test
    public void testRotate() {
        Poligono square = new Poligono("4 0 0 4 0 4 4 0 4");
        Ponto center = new Ponto(2, 2);

        // Rotação de 90 graus em torno do centro (2,2)
        Poligono rotatedSquare90 = (Poligono) square.rotate(90, center);
        assertEquals("(4.00,0.00) (4.00,4.00) (0.00,4.00) (0.00,0.00)", rotatedSquare90.toString());
        // Verifica se o original não foi modificado
        assertEquals("(0.00,0.00) (4.00,0.00) (4.00,4.00) (0.00,4.00)", square.toString());

        // Rotação de 0 graus (sem mudanças)
        Poligono notRotated = (Poligono) square.rotate(0, center);
        assertEquals(square.toString(), notRotated.toString());

        // Rotação de 180 graus em torno do mesmo centro
        Poligono rotatedSquare180 = (Poligono) square.rotate(180, center);
        assertEquals("(4.00,4.00) (0.00,4.00) (-0.00,0.00) (4.00,-0.00)", rotatedSquare180.toString());

        // Rotação de -90 graus
        // Esperado: (2.00,4.00) (0.00,2.00) (2.00,0.00) (4.00,2.00)
        Poligono rotatedSquareNeg90 = (Poligono) square.rotate(-90, center);
        assertEquals("(0.00,4.00) (0.00,0.00) (4.00,0.00) (4.00,4.00)", rotatedSquareNeg90.toString());

        // Rotação em torno da origem (0,0)
        Poligono tri = new Poligono("3 1 1 3 1 2 5");
        Poligono rotatedTri = (Poligono) tri.rotate(90, new Ponto(0, 0));
        assertNotEquals(tri.toString(), rotatedTri.toString()); // Apenas verifica que modificou
    }
}