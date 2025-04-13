package Testes;

import static org.junit.jupiter.api.Assertions.*;

import GameEngine.*;
import Figuras.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GameEngineTest {

    @Test
    public void testAddGameObject() {
        GameEngine engine = new GameEngine();
        GameObject go = new GameObject("Objeto1", new Transform(new Ponto(0, 0), 1, 0, 1), new Circulo("0 0 1"), new Movement(0, 0, 0, 0, 0));

        engine.add(go);

        assertEquals(1, engine.getGameObjects().size()); // Verifica se o objeto foi adicionado
    }

    @Test
    public void testDestroyGameObject() {
        GameEngine engine = new GameEngine();
        GameObject go = new GameObject("Objeto1", new Transform(new Ponto(0, 0), 1, 0, 1), new Circulo("0 0 1"), new Movement(0, 0, 0, 0, 0));

        engine.add(go);
        engine.destroy(go);

        assertTrue(engine.getColisoes().isEmpty()); // Verifica se o objeto foi removido
    }

    @Test
    public void testSimulateFrames() {
        GameEngine engine = new GameEngine();
        GameObject go1 = new GameObject("Objeto1", new Transform(new Ponto(0, 0), 1, 0, 1), new Circulo("0 0 1"), new Movement(1, 0, 0, 0, 0));
        GameObject go2 = new GameObject("Objeto2", new Transform(new Ponto(5, 0), 1, 0, 1), new Circulo("5 0 1"), new Movement(-1, 0, 0, 0, 0));

        engine.add(go1);
        engine.add(go2);

        engine.simulateFrames(3);

        // Após 3 frames, os objetos devem colidir
        List<String> colisoes = engine.getColisoes();
        assertEquals(2, colisoes.size());
        assertTrue(colisoes.get(0).contains("Objeto1"));
        assertTrue(colisoes.get(0).contains("Objeto2"));
    }

    @Test
    public void testGetColisoes() {
        GameEngine engine = new GameEngine();
        GameObject go1 = new GameObject("Objeto1", new Transform(new Ponto(0, 0), 1, 0, 1), new Circulo("0 0 1"), new Movement(0, 0, 0, 0, 0));
        GameObject go2 = new GameObject("Objeto2", new Transform(new Ponto(1, 0), 1, 0, 1), new Circulo("1 0 1"), new Movement(0, 0, 0, 0, 0));

        engine.add(go1);
        engine.add(go2);

        List<String> colisoes = engine.getColisoes();

        // Verifica se a colisão foi detectada
        assertEquals(2, colisoes.size());
        assertTrue(colisoes.get(0).contains("Objeto2"));
        assertTrue(colisoes.get(1).contains("Objeto1"));
    }

    @Test
    public void testUpdateObjectLayer() {
        GameEngine engine = new GameEngine();
        GameObject go = new GameObject("Objeto1", new Transform(new Ponto(0, 0), 1, 0, 1), new Circulo("0 0 1"), new Movement(0, 0, 1, 0, 0));

        engine.add(go);
        engine.simulateFrames(1);

        // Verifica se o objeto foi movido para o novo layer
        assertEquals(2, go.transform().layer());
    }
}