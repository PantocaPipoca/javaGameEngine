import java.util.List;
import java.util.Scanner;

import Figuras.*;
import GameEngine.*;

/**
 * Classe principal que apenas le os dados de entrada e cria os objetos necessarios neste caso le os dados para criar uma figura geometrica e imprime a sua representacao
 * @author Daniel Pantyukhov a83896
 * @version 1.0 (18/03/25)
 **/
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int frames = Integer.parseInt(sc.nextLine().trim());
        int n = Integer.parseInt(sc.nextLine().trim());
        
        GameEngine engine = new GameEngine();
        
        for (int i = 0; i < n; i++) {
            String name = sc.nextLine().trim();
            
            String line = sc.nextLine().trim();
            String[] tTokens = line.split("\\s+");
            double x = Double.parseDouble(tTokens[0]);
            double y = Double.parseDouble(tTokens[1]);
            int layer = Integer.parseInt(tTokens[2]);
            double angle = Double.parseDouble(tTokens[3]);
            double scale = Double.parseDouble(tTokens[4]);
            ITransform transform = new Transform(new Ponto(x, y), layer, angle, scale);
            
            String colliderLine = sc.nextLine().trim();
            String[] cTokens = colliderLine.split("\\s+");
            FiguraGeometrica figura;
            if (cTokens.length == 3) {
                figura = new Circulo(colliderLine);
            } else {
                int j = cTokens.length / 2;
                String polyStr = j + " " + colliderLine;
                figura = new Poligono(polyStr);
            }
            
            String[] mTokens = sc.nextLine().trim().split("\\s+");
            double dx = Double.parseDouble(mTokens[0]);
            double dy = Double.parseDouble(mTokens[1]);
            int dLayer = Integer.parseInt(mTokens[2]);
            double dAngle = Double.parseDouble(mTokens[3]);
            double dScale = Double.parseDouble(mTokens[4]);
            Movement movement = new Movement(dx, dy, dLayer, dAngle, dScale);
            
            // Cria o GameObject e adiciona-o à engine (a engine já gerencia a organização por layer)
            GameObject go = new GameObject(name, transform, figura, movement);
            engine.add(go);
        }
        
        // Simula os frames (o update dos GameObjects já atualiza os grupos caso haja mudança de layer)
        engine.simulateFrames(frames);
        
        // Obtém e imprime os resultados de colisões (apenas o main realiza os prints)
        List<String> colisoes = engine.getColisoes();
        for (String res : colisoes) {
            System.out.println(res);
        }
        
        sc.close();
    }
}