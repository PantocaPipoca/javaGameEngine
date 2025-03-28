import java.util.Scanner;

import Figuras.*;
import GameEngine.GameObject;
import GameEngine.ITransform;
import GameEngine.Transform;

/**
 * Classe principal que apenas le os dados de entrada e cria os objetos necessarios neste caso le os dados para criar uma figura geometrica e imprime a sua representacao
 * @author Daniel Pantyukhov a83896
 * @version 1.0 (18/03/25)
 **/
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String name = sc.nextLine().trim();

        String[] tTokens = sc.nextLine().trim().split(" ");
        double x = Double.parseDouble(tTokens[0]);
        double y = Double.parseDouble(tTokens[1]);
        int layer = Integer.parseInt(tTokens[2]);
        double angle = Double.parseDouble(tTokens[3]);
        double scale = Double.parseDouble(tTokens[4]);
        ITransform transform = new Transform(new Ponto(x, y), layer, angle, scale);

        String colliderLine = sc.nextLine().trim();
        String[] cTokens = colliderLine.split(" ");
        FiguraGeometrica figura;
        // Cria uma preset para a figura do collider
        if (cTokens.length == 3) {
            figura = new Circulo(colliderLine);
        } else {
            int n = cTokens.length / 2;
            String polyStr = n + " " + colliderLine;
            figura = new Poligono(polyStr);
        }

        GameObject obj = new GameObject(name, transform, figura);

        while(sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            String[] tokens = line.split(" ");
            String op = tokens[0];
            switch (op) {
                case "move":
                if(tokens.length == 4) {
                    double dx = Double.parseDouble(tokens[1]);
                    double dy = Double.parseDouble(tokens[2]);
                    int dlayer = Integer.parseInt(tokens[3]);
                    transform.move(new Ponto(dx, dy), dlayer);
                }
                break;

                case "rotate":
                if(tokens.length == 2) {
                    double dTheta = Double.parseDouble(tokens[1]);
                    transform.rotate(dTheta);
                }
                break;

                case "scale":
                if(tokens.length == 2) {
                    double dScale = Double.parseDouble(tokens[1]);
                    transform.scale(dScale);
                }
                break;

                default:
                break;
            }
        }
        obj = new GameObject(name, transform, figura);
        System.out.println(obj);

        sc.close();
    }
}