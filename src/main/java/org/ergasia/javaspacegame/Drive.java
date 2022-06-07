package org.ergasia.javaspacegame;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * This is the class that creates our window and
 * the JPanel which contains our game.
 *
 * Games was first written in 2014
 *
 * @author Kosmas Sidiropoulos 2114111
 *
 */

@SuppressWarnings("serial")
public class Drive extends JFrame {

	public Drive() {

		//Adds panel class to the JFrame Window.
		add(new Panel());

		//Window Settings.
		setSize(800, 600); //Window Size.
		setResizable(false); //Makes the window not resizable.
		setTitle("Space Defender II"); //Title of the Window. Just a random name.
		setLocationRelativeTo(null); //Window is positioned in the middle of the screen.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //When we close the window the application stops.
    }

	/**
	 * The main method.
	 *
	 * @param args The arguments that are passed into our game
	 */
    public static void main(String[] args) {

    	/*
    	 * This is an anonymous inner class that derives from Runnable
    	 * It is basically a thread that continuously polls data from this queue
    	 *
    	 */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

            	Drive ex = new Drive();
                ex.setVisible(true);
            }
        });
    }
}
