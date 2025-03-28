package Figuras;
/** 
 * Classe abstrata que representa uma figura geometrica.
 * @author Daniel Pantyukhov a83896
 * @version 1.0 (18/03/25)
 * @inv Todas as figuras geometricas sao nao imutaveis
 * **/
public abstract class FiguraGeometrica {
    
    public abstract FiguraGeometrica translate(double dx, double dy);

    @Override
    public abstract String toString();
    
    public abstract boolean colide(FiguraGeometrica f);
    public abstract boolean colideComCirculo(Circulo c);
    public abstract boolean colideComPoligono(Poligono p);

    public abstract Ponto centroide();
    public abstract FiguraGeometrica clone();

    public abstract FiguraGeometrica scale(double factor);
    public abstract FiguraGeometrica rotate(double angle, Ponto centro);
}