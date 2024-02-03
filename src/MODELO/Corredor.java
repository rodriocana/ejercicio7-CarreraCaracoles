package MODELO;

import java.awt.Color;
import static java.awt.Color.blue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

//public class Corredor implements Runnable {
public class Corredor extends Thread {

    static final int DIAMETER = 45;
    int x = 0;
    int y = 0;
    int vx = 1;
    int vy = 1;
    int numero;
    public Game game;
    private Image ballImage;

    public Corredor(Game game, int x, int y, int numero) {
        this.game = game;
        this.x = x;
        this.y = y;
        ballImage = new ImageIcon("caracol.png").getImage();
        this.numero = numero;

    }

    @Override
    public void run() {
        while (true) {
            try {
                move();

            } catch (InterruptedException ex) {
                Logger.getLogger(Corredor.class.getName()).log(Level.SEVERE, null, ex);
            }
            game.repaint();  // Vuelve a pintar el componente después de cada movimiento

        }
    }

    /*void move() {
		if (x + vx < 0)
			vx = 1;
		if (x + vx > game.getWidth() - DIAMETER)
			vx = -1;
		if (y + vy < 0)
			vy = 1;
		if (y + vy > game.getHeight() - DIAMETER)
			game.gameOver();
		if (collision()){
			vy = -1;
			y = game.racquet.getTopY() - DIAMETER;
		}
		x = x + vx;
		y = y + vy;
	}*/
    void move() throws InterruptedException {

        int contador = 0;
        Thread.sleep((long) (Math.random() * 19));  // este hilo hace que cada caracol se mueva distinto
        if (x + vx > game.getWidth() - DIAMETER) {  // esto es para cuando colisiona con la pared final

            game.gameOver();

        }

        for (Bomba bomba : game.getBombas()) {  // aqui es la colision
            if (getBounds().intersects(bomba.getBounds())) {
                // Detener el hilo del corredor

                return;
            }

        }

        x = x + 2; // esto para moverse hacia la derecha

    }

  

    public void paint(Graphics g) {
        // Dibuja la imagen solo si está cargada correctamente
        if (ballImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(ballImage, x, y, DIAMETER, DIAMETER, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, DIAMETER, DIAMETER);
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    
      public int getY() {
        return y;
    }

    void move(double elapsedTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
