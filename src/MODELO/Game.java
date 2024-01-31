package MODELO;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {
    
    private boolean pausa = true;

    Corredor ball = new Corredor(this, 1, 80, 1);
    Corredor ball2 = new Corredor(this, 1, 125, 2);
    Corredor ball3 = new Corredor(this, 1, 185, 3);
    Corredor ball4 = new Corredor(this, 1, 240, 4);

    Thread corredorThread = new Thread(ball);
    Thread corredorThread2 = new Thread(ball2);
    Thread corredorThread3 = new Thread(ball3);
    Thread corredorThread4 = new Thread(ball4);
    boolean inicio = true;

    public Game() {
    addKeyListener(new KeyListener() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                togglePausa();
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }  
    });
    setFocusable(true);
}

    public void move() {
    try {
        if (inicio && !pausa) {
            corredorThread.start();
            corredorThread2.start();
            corredorThread3.start();
            corredorThread4.start();
            inicio = false;
        }

        if (!pausa) {
            ball.move();
            ball2.move();
            ball3.move();
            ball4.move();
        }
    } catch (InterruptedException ex) {
        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    public void togglePausa() {
    pausa = !pausa;
}

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        ball.paint(g2d);
        ball2.paint(g2d);
        ball3.paint(g2d);
        ball4.paint(g2d);

    }

    public Corredor getFirstToReachWidth() {
        Corredor[] bolas = {ball, ball2, ball3, ball4};
        int contador = 0;

        for (Corredor bola : bolas) {

            if (bola.getX() >= getWidth() - Corredor.DIAMETER) {

                return bola;

            }

        }

        return null; // Ninguna ha llegado al ancho todavía
    }

    public void gameOver() {

        Corredor bolaganadora = getFirstToReachWidth();

        corredorThread.stop();
        corredorThread2.stop();
        corredorThread3.stop();
        corredorThread4.stop();

        JOptionPane.showMessageDialog(this, "Ha ganado el caracol numero: " + bolaganadora.getNumero(), " Felicidades ", JOptionPane.YES_NO_OPTION);

        System.exit(ABORT);
    }

}
