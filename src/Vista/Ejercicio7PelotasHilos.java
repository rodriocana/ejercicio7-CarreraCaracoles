/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import MODELO.Game;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Rodri
 */
public class Ejercicio7PelotasHilos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        JFrame frame = new JFrame("Carrera Bolas");
        Game game = new Game();
        frame.add(game);
        frame.setBackground(Color.BLACK);
        frame.setSize(1500, 400);
        
        
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         JLabel backgroundLabel = new JLabel();
        
        // Carga la imagen desde un archivo (asegúrate de tener la imagen en el directorio correcto)
        ImageIcon backgroundImage = new ImageIcon("carretera.jpg");

        // Establece la imagen de fondo en el JLabel
        backgroundLabel.setIcon(backgroundImage);

        // Establece el diseño del JFrame para que pueda contener el JLabel
        frame.setLayout(new BorderLayout());
        
        // Añade el JLabel al centro del JFrame
        frame.add(backgroundLabel, BorderLayout.CENTER);
        
               


        while (true) {

            game.move();
            game.repaint();

        }
    }

}
