package Figuras;

/**
 * Classe que representa um círculo.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.1 (18/03/25)
 **/
public class Circulo extends FiguraGeometrica {
    private Ponto centro;
    private double raio;

    /**
     * Construtor para um círculo
     * @param s string no formato "cx cy r"
     */
    public Circulo(String s) {
        String[] tokens = s.split(" ");
        if(tokens.length != 3) {
            System.out.println("Circulo:vi");
            throw new IllegalArgumentException();
        }
        if(Double.parseDouble(tokens[2]) < 0) {
            System.out.println("Circulo:vi");
            throw new IllegalArgumentException();
        }

        try {
            double cx = Double.parseDouble(tokens[0]);
            double cy = Double.parseDouble(tokens[1]);
            double r = Double.parseDouble(tokens[2]);
            centro = new Ponto(cx, cy);
            raio = r;
        } catch (NumberFormatException e) {
            System.out.println("Circulo:vi");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Desloca o circulo mais dx unidades no eixo x e dy unidades no eixo y
     * @param dx unidades a serem deslocadas no eixo x
     * @param dy unidades a serem deslocadas no eixo y
     * @return novo círculo deslocado
     */
    @Override
    public Circulo translate(double dx, double dy) {
        Ponto novoCentro = centro.translate(dx, dy);
        return new Circulo(novoCentro.x() + " " + novoCentro.y() + " " + raio);
    }

    /**
     * Calcula o perímetro do círculo
     * @return perímetro do círculo
     */
    public double perimetro() {
        return 2 * Math.PI * raio;
    }

    public Ponto centro() {
        return centro;
    }

    public double raio() {
        return raio;
    }

    /**
     * @return String no formato "Circulo: (x,y) r"
     */
    @Override
    public String toString() {
        return String.format("%s %.2f", centro.toString(), raio);
    }

    /**
     * Verifica se um circulo colide com uma figura geometrica
     * @param f figura geometrica a ser verificada
     * @return true se colidem, false caso contrario
     */
    @Override
    public boolean colide(FiguraGeometrica f) {
        return f.colideComCirculo(this);
    }

    /**
     * Verifica se um circulo colide com um poligono verificando se o circulo esta dentro do poligono ou se algum segmento do poligono interseta o circulo ou se algum ponto do poligono esta dentro do circulo.
     * @param p Poligono a ser verificado
     * @return true se colidem, false caso contrario
     */
     @Override
     public boolean colideComPoligono(Poligono p) {
         if (Poligono.pontoEstaDentroDoPoligono(this.centro, p))
             return true;
         for (Segmento s : p.segmentos) {
             if (s.interseta(this))
                 return true;
         }
         for (Ponto pnt : p.pontos) {
             double dx = this.centro.x() - pnt.x();
             double dy = this.centro.y() - pnt.y();
             if (Math.sqrt(dx * dx + dy * dy) <= this.raio)
                 return true;
         }
         return false;
     }
    /**
     * Verifica se um circulo colide com um outro circulo verificando se a distancia entre os centros dos circulos e menor ou igual a soma dos raios dos circulos.
     * @param c
     * @return true se colidem, false caso contrario
     */
    public boolean colideComCirculo(Circulo c) {
        double distancia = this.centro.distancia(c.centro());
        return distancia <= this.raio + c.raio();
    }

    /**
     * Devolve o ponto que representa o centroide do circulo (o centro do circulo)
     * @return ponto que representa o centroide do circulo
     */
    public Ponto centroide() {
        return centro;
    }

    /**
     * Devolve uma copia do circulo
     * @return copia do circulo
     */
    public Circulo clone() {
        return new Circulo(centro.x() + " " + centro.y() + " " + raio);
    }

    /**
     * Escala o circulo por um fator
     * @param factor fator de escala
     * @return novo circulo igual ao anterior mas, escalado
     */
    public Circulo scale(double factor) {
        return new Circulo(centro.x() + " " + centro.y() + " " + raio * factor);
    }

    /**
     * Roda o circulo por um angulo (No fundo nao faz nada)
     * @param angle angulo de rotacao
     * @param centro ponto em torno do qual o circulo vai rodar
     * @return novo circulo igual ao anterior mas, rodado
     */
    public Circulo rotate(double angle, Ponto centro) {
        return this;
    }
}