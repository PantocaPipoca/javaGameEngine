import java.lang.reflect.Constructor;
import java.util.ArrayList;
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

        // Leitura do nome do GameObject
        String name = sc.nextLine().trim();

        // Leitura dos dados da Transform: x y layer angle scale
        String[] tTokens = sc.nextLine().trim().split(" ");
        double x = Double.parseDouble(tTokens[0]);
        double y = Double.parseDouble(tTokens[1]);
        int layer = Integer.parseInt(tTokens[2]);
        double angle = Double.parseDouble(tTokens[3]);
        double scale = Double.parseDouble(tTokens[4]);
        ITransform transform = new Transform(new Ponto(x, y), layer, angle, scale);

        // Leitura dos dados do Collider
        String colliderLine = sc.nextLine().trim();
        String[] cTokens = colliderLine.split(" ");
        FiguraGeometrica figura;
        if (cTokens.length == 3) {
            // Se for círculo: "cx cy r"
            figura = new Circulo(colliderLine);
        } else {
            // Se for polígono: monta a string com o número de vértices no início
            int n = cTokens.length / 2;
            String polyStr = n + " " + colliderLine;
            figura = new Poligono(polyStr);
        }

        // Cria o GameObject (que internamente cria o Collider com a figura transformada)
        GameObject obj = new GameObject(name, transform, figura);
        System.out.println(obj.toString());

        sc.close();
    }
}