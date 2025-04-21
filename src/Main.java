import java.util.List;
import java.util.Scanner;

import Figures.*;
import GameEngine.*;

/**
 * Main class that only reads input data and creates the necessary objects. In this case, it reads the data to create a geometric figure and prints its representation.
 * Author: Daniel Pantyukhov a83896
 * Version: 1.0 (18/03/25)
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
            ITransform transform = new Transform(new Point(x, y), layer, angle, scale);
            
            String colliderLine = sc.nextLine().trim();
            String[] cTokens = colliderLine.split("\\s+");
            GeometricFigure figure;
            if (cTokens.length == 3) {
                figure = new Circle(colliderLine);
            } else {
                int j = cTokens.length / 2;
                String polyStr = j + " " + colliderLine;
                figure = new Polygon(polyStr);
            }
            
            String[] mTokens = sc.nextLine().trim().split("\\s+");
            double dx = Double.parseDouble(mTokens[0]);
            double dy = Double.parseDouble(mTokens[1]);
            int dLayer = Integer.parseInt(mTokens[2]);
            double dAngle = Double.parseDouble(mTokens[3]);
            double dScale = Double.parseDouble(mTokens[4]);
            Movement movement = new Movement(dx, dy, dLayer, dAngle, dScale);
            
            // Creates the GameObject and adds it to the engine (the engine already manages the organization by layer)
            GameObject go = new GameObject(name, transform, figure, movement);
            engine.add(go);
        }
        
        // Simulates the frames (the update of GameObjects already updates the groups if there is a layer change)
        engine.simulateFrames(frames);
        
        // Gets and prints the collision results (only the main performs the prints)
        List<String> collisions = engine.getCollisions();
        for (String res : collisions) {
            System.out.println(res);
        }
        
        sc.close();
    }
}