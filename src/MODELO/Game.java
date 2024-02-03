package MODELO;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public final class Game extends JPanel {

    private boolean pausa = true;

    private ImageIcon backgroundImage;

    private Clip inicioSoundClip;
    private Clip soundCameraEffect;

    private long lastTime = System.nanoTime();
    private double fps;

    List<Bomba> bombas = new ArrayList();
    List<Corredor> listaCaracoles = new ArrayList();

    Corredor ball = new Corredor(this, 1, 80, 1);
    Corredor ball2 = new Corredor(this, 1, 125, 2);
    Corredor ball3 = new Corredor(this, 1, 185, 3);
    Corredor ball4 = new Corredor(this, 1, 240, 4);

    boolean inicio = true;
    private Throwable thrwbl;

    public Game() {
        this.listaCaracoles = new ArrayList();
        this.bombas = new ArrayList();
        backgroundImage = new ImageIcon("carretera.jpg");

        añadirCorredorAlista();

        try {
            File soundFile = new File("bennyyyywav.wav"); // Reemplaza con la ruta de tu archivo de sonido
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            inicioSoundClip = AudioSystem.getClip();
            inicioSoundClip.open(audioIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File soundFile2 = new File("camerashutterwav.wav"); // Reemplaza con la ruta de tu archivo de sonido
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile2);
            soundCameraEffect = AudioSystem.getClip();
            soundCameraEffect.open(audioIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public void playInicioSound() {
        if (inicioSoundClip != null) {
            inicioSoundClip.setFramePosition(0); // Reinicia la posición del clip
            inicioSoundClip.start(); // Inicia la reproducción del sonido
        }
    }

    public void stopInicioSound() {
        if (inicioSoundClip != null && inicioSoundClip.isRunning()) {
            inicioSoundClip.stop();
        }
    }

    public void playCameraSoundEffect() {

        if (soundCameraEffect != null) {
            soundCameraEffect.setFramePosition(0); // Reinicia la posición del clip
            soundCameraEffect.start(); // Inicia la reproducción del sonido
        }

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibuja la imagen de fondo en el panel
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public void añadirCorredorAlista() {

        listaCaracoles.add(ball);
        listaCaracoles.add(ball2);
        listaCaracoles.add(ball3);
        listaCaracoles.add(ball4);

    }

    // al pulsar espacio se activa todo esto
    public void move() throws InterruptedException {

        //System.out.println("FPS: " + fps);  // si comento esto salen miles de bombas
        if (inicio && !pausa) {
            // Iniciar los hilos de los corredores

            ball.start();
            ball2.start();
            ball3.start();
            ball4.start();
            inicio = false;
            playInicioSound();

        }

        // Añadir bombas aleatoriamente
        if (Math.random() < 0.00000001) { // Ajusta el valor según la frecuencia deseada, si pongo los move de arriba en el if y subo el 0,01 a 0,00001
            // funcionaria bien pero al llegar a la linea de meta no sale el mensaje
            Bomba bomba = new Bomba(this, (int) (Math.random() * getWidth()), (int) (Math.random() * getHeight()));
            bomba.start();
            bombas.add(bomba);

        }

    }

    public void capturarPantalla(Corredor ganador) {
        try {
            // Crear una instancia de la clase Robot
            Robot robot = new Robot();

            // Capturar toda la pantalla
            Rectangle pantalla = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            BufferedImage captura = robot.createScreenCapture(pantalla);

            // Definir las coordenadas y dimensiones de la región (relativas al panel)
            int xRegion = 30; // Comienzo de la región en el eje X
            int yRegion = 0; // Comienzo de la región en el eje Y
            int anchoRegion = getWidth(); // Ancho de la región igual al ancho del panel
            int altoRegion = getHeight(); // Alto de la región igual al alto del panel

            // Crear una subimagen (captura de la región específica)
            BufferedImage capturaRegion = captura.getSubimage(xRegion, yRegion, anchoRegion, altoRegion);

            // Guardar la captura de pantalla en un archivo
            File archivoCaptura = new File("captura_ganador.png");
            ImageIO.write(capturaRegion, "png", archivoCaptura);

            SwingUtilities.invokeLater(() -> {
                ImageIcon iconoCaptura = new ImageIcon(archivoCaptura.getAbsolutePath());

                JOptionPane.showMessageDialog(this, "Ha ganado el caracol numero: " + ganador.getNumero(), " FOTO FINISH!!! ", JOptionPane.YES_NO_OPTION, iconoCaptura);
            });

            System.out.println("Captura de pantalla de la región del ganador guardada en: " + archivoCaptura.getAbsolutePath());

        } catch (AWTException | IOException e) {
            e.printStackTrace();
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

        for (Bomba bomba : bombas) {

            bomba.paint(g2d);
        }

        g.setColor(Color.WHITE);
        //g.drawString("FPS: " + String.format("%.2f", fps), 10, 20);

    }

    public Corredor getFirstToReachWidth() {
        Corredor[] bolas = {ball, ball2, ball3, ball4};
        int contador = 0;
        Corredor bolaganadora = null;

        for (Corredor bola : bolas) {
            if (bola.getX() >= getWidth() - Corredor.DIAMETER) {
                contador++;
                bolaganadora = bola;
            }
        }

        if (contador == 1) {
            return bolaganadora;
        } else {
            return null;
        }
    }

    public void gameOver() {
        Corredor bolaganadora = getFirstToReachWidth();

        if (bolaganadora != null) {

            SwingUtilities.invokeLater(() -> {
                capturarPantalla(bolaganadora);
                playCameraSoundEffect();
                stopInicioSound();
                JOptionPane.showMessageDialog(this, "Ha ganado el caracol numero: " + bolaganadora.getNumero(), " Felicidades ", JOptionPane.YES_NO_OPTION);

                System.exit(0);
            });
        }

        ball.stop();
        ball2.stop();
        ball3.stop();
        ball4.stop();

        // Cierra el programa después de detener las bolas
        SwingUtilities.invokeLater(() -> System.exit(0));
    }

    // hay que parar como sea los hilos.
    public List<Bomba> getBombas() {
        return bombas;
    }

}
