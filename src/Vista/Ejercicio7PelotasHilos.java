/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import MODELO.Game;
import java.awt.Color;
import javax.swing.JFrame;

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

        while (true) {
            
            game.move();
            game.repaint();
             
        }
    }

}
