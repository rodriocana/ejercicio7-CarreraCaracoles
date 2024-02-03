/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODELO;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Bomba extends Thread {

    static final int DIAMETER = 40;

    private int x;
    private int y;
    private int vx;
    private int vy;
    private Game game;
    private Image bombaImage;

    public Bomba(Game game, int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.vx = 5;
        this.vy = 1;
        this.bombaImage = new ImageIcon("bombita.png").getImage();

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, DIAMETER, DIAMETER);
    }

    public void paint(Graphics g) {
        if (bombaImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(bombaImage, x, y, DIAMETER, DIAMETER, null);
        }
    }

    @Override
    public void run() {
        // Lógica de movimiento de la bomba
        while (true) {
            move();
            try {
                Thread.sleep(10); // Ajusta la velocidad de movimiento según tus necesidades de las bombas
            } catch (InterruptedException ex) {
                // Maneja la excepción
            }
        }
    }

    //Método de movimiento de la bomba
    void move() {
        // Mover la bomba
        x += vx;
        y += vy;

        // Rebotar en los bordes horizontales
        if (x <= 0 || x >= game.getWidth() - DIAMETER) {  // colisiones
            vx = -vx;
        }

        // Rebotar en los bordes verticales
        if (y <= 0 || y >= game.getHeight() - DIAMETER) {  // colisiones
            vy = -vy;
        }
    }

}
