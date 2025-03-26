import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Scanner;

import Figuras.*;

/**
 * Classe principal que apenas le os dados de entrada e cria os objetos necessarios neste caso le os dados para criar uma figura geometrica e imprime a sua representacao
 * @author Daniel Pantyukhov a83896
 * @version 1.0 (18/03/25)
 **/
public class Main {
    public static String capital(String s) {
        if (s == null || s.isEmpty())
            return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<FiguraGeometrica> figuras = new ArrayList<>();
        while(sc.hasNextLine()){
            String linha = sc.nextLine().trim();
            if(linha.isEmpty()){
                continue;
            }
            String[] tokens = linha.split(" ", 2);
            if(tokens.length < 2){
                continue;
            }
            String className = "Figuras." + capital(tokens[0]);
            try {
                Class<?> cl = Class.forName(className);
                Constructor<?> cons = cl.getConstructor(String.class);
                FiguraGeometrica figura = (FiguraGeometrica) cons.newInstance(tokens[1]);
                for (int i = 0; i < figuras.size(); i++) {
                    if(figura.colide(figuras.get(i))){
                        System.out.println("Colisao na posicao " + i);
                        sc.close();
                        System.exit(0);
                    }
                }
                figuras.add(figura);
            } catch (Exception e) {
                sc.close();
                System.exit(0);
            }
        }
        System.out.println("Sem colisoes");
        sc.close();
    }
}