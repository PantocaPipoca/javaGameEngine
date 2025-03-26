package Figuras;
public interface ITransform {
    void move(Ponto dPos, int dlayer);
    void rotate(double dTheta);
    void scale(double dScale);
    Ponto position();
    int layer();
    double angle();
    double scale();
}