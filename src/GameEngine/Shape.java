package GameEngine;

import javax.swing.*;
import java.awt.*;

public class Shape {
    private Image imagem;
    private int largura = 100;
    private int altura = 100;

    public Shape(String nomeDoObjeto) {
        this.imagem = carregarImagem(nomeDoObjeto);
    }

    private Image carregarImagem(String nome) {
        String base = nome.replaceAll("_[0-9]+$", "");
        String caminho = "sprites/" + base.toLowerCase() + ".png";
        Image img = new ImageIcon(caminho).getImage();

        if (img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
            System.err.println("Imagem não carregada: " + caminho);
            return null;
        }

        return img;
    }

    public void render(Graphics g, ITransform t) {
        if (imagem == null) return;

        int x = (int) t.position().x();
        int y = (int) t.position().y();

        g.drawImage(imagem, x - largura / 2, y - altura / 2, largura, altura, null);
    }
}
