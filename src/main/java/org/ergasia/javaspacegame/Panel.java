package org.ergasia.javaspacegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
* The JPanel that contains the game.
* This class acts also as the game manager.
*
* The purpose of this class is to print the graphics,
* controlling and implement the game logic
*
* @author Kosmas Sidiropoulos 2114111.
*
*/
@SuppressWarnings("serial")
public class Panel extends JPanel implements ActionListener, KeyListener {

	/*This timer is used as a thread for our game */
    private Timer timer;
    /*Creating a LoadGame that loads from binary file the hi-score */
    private LoadGame lg = new LoadGame();
    /*Our Ship Object (to an8rwpaki) */
    private Spaceship ship;
    /*The time remaining of the UFOs on the every stage. It slowly decreases stage after stage. */
    private int timeRemaining;
    /*The stayTime of the UFOs on the first stage */
    private final int stayTime = 20;
	/*The UFOs number per stage */
    private final int numUfo = 10;
	/*This List contains the Ufo objects */
    private ArrayList <Ufo> ufos = new ArrayList <>(numUfo);
	/*This List contains the Ammo objects */
    private ArrayList <Ammo> ammos = new ArrayList <>(50);
	/*Keeps the starting system time  */
    private long startTime;
	/*The ongoing system time */
    private long endTime;
	/*The duration between the starting and ongoing time */
    private long duration;
	/*The stage level */
    private int stageNo = 1;
	/*A counter used for the Ammo objects */
    private int index = 0;
	/*A counter used for the Ammo objects*/
    private int indexOfFirst = 0;
	/*Hi - Score from binary file */
    private int hiscore;
	/*The ammunitions available */
    private final int ammoAvail = 50;
	/*After a stage is beaten this variable is used so we can continue on the next stage */
    private boolean restart = false;
	/*Time Restart */
    private boolean timeRestart = false;
	/*Time is up */
    private boolean timeIsUp = false;
	/*Gives the "signal" to start the next stage */
    private boolean contin = false;
    /*This variable disables youLost method*/
    private boolean endGame = false;
	/*Image of the background */
    private Image bg;


	/**
	 *The Constructor with the Start-Game Settings.
	 *
	 */
    public Panel() {
    	addKeyListener(this); //Gives the ability to use the keyboard.
    	setFocusable(true);//Indicates whether a component can gain the focus if it is requested to do so.

    	hiscore = lg.getHiscore();//Loads from LoadGame Class the hiscore.
    	startTime = System.currentTimeMillis();//Setting up the starting time.
    	timeRemaining = stayTime;

		ImageIcon ii = null;//Loads background image
		try {
			ii = new ImageIcon(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/bg.png"))));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		bg = ii.getImage(); //Saves the background image to image variable.

        ship = new Spaceship(); //Creating the Ship Object (an8rwpaki)
        for(int i=0; i<ammoAvail; i++){
			ammos.add(new Ammo(ship)); //Creating the Ammo Objects
		}
        for(int i=0; i<numUfo; i++){
        	ufos.add(new Ufo()); //Creating the Ufo Objects
        }

        timer = new Timer(16, this);// ~= 60fps.
        timer.start();//Starting the thread.

    }

    /**
     * paintComponent method is similar to paint but due to we are
     * using JPanel the paint method is called us paintCompoment.
     *
     * Basically this method is responsible for the drawing.
     *
     * @param g Is our Graphics variable that is responsible of the painting.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
        youWon(g);
		youLost(g);

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * This method contains the drawing of the main part of the game (UFOs, Ammos, Ship, Details like score etc).
     *
     * @param g Is our Graphics variable that is responsible of the painting.
     */
    private void doDrawing(Graphics g) {

    	g.drawImage(bg, 0, 0, this); //draws the background.

    	g.setColor(Color.RED); //sets g color to Red.

    	String stage = Integer.toString(stageNo); //converts the integer variable to String so it can be painted.
    	g.drawString("Stage : " + stage, getWidth() - 120 , 45);
		g.drawString("Stage : " + stage, getWidth() - 121 , 45); //makes string bold without using Font.

    	String hiscoreString = Integer.toString(hiscore);
    	g.drawString("Hiscore : " + hiscoreString, getWidth() - 120 , 60);
    	g.drawString("Hiscore : " + hiscoreString, getWidth() - 121 , 60);

		String totalScore = Integer.toString(Ufo.getScore());
		g.drawString("Total Score : " + totalScore, getWidth() - 120 , 75);
		g.drawString("Total Score : " + totalScore, getWidth() - 121 , 75);

		String score = Integer.toString(Ufo.getStageScore());
		g.drawString("Stage Score : " + score, getWidth() - 120 , 90);
		g.drawString("Stage Score : " + score, getWidth() - 121 , 90);

		//time refresh and time restart after stage is beaten.
    	if(!timeRestart){
    		duration = (endTime - startTime) / 1000;
    	}else if(timeRestart){
    		duration = 0;
    	}

    	if(!timeIsUp){
		String time = Long.toString(timeRemaining - duration);
    	g.drawString("Time : " + time, getWidth() - 120 , 105);
    	g.drawString("Time : " + time, getWidth() - 121 , 105);
    	}else{
    		g.drawString("Time : 0", getWidth() - 120 , 105);
    		g.drawString("Time : 0", getWidth() - 121 , 105);
    	}

    	//for 2-dimensional shapes(images).
    	Graphics2D g2d = (Graphics2D) g;

    	//if time is up the remaining UFOs stop being drawn.
    	if(!timeIsUp){
    		for(int i=0; i<ufos.size(); i++){
    			g2d.drawImage(ufos.get(i).getImage(), ufos.get(i).getX(), ufos.get(i).getY(), this);
    		}
    	}

    	for(int i=0; i<ammos.size(); i++){
    		g2d.drawImage(ammos.get(i).getImage(), ammos.get(i).getX(), ammos.get(i).getY(), this);
		}

        g2d.drawImage(ship.getImage(), ship.getX(), ship.getY() - 25/*the height of the image*/, this);

    }

    /**
     * This is the method that is being called by the timer every 16 milliseconds.
     * It's basically the code that makes the game "runnable".
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    	//if stage is beaten this if statement is responsible for the setup for the next stage.
    	if(restart){
			for(int i=0; i<numUfo; i++){
				ufos.add(new Ufo());
			}
			Ufo.setStageScore(0);
			stageNo++;
			timeRemaining = (int) (stayTime / ((stageNo * .1) + 1));
			timeRestart = false;
			restart = false;
		}

    	endTime = System.currentTimeMillis();

        /*
         * Object update methods.
         * Every Object in this game that is a paint component has an update
         * method in which the movement and the collision are being used.
         */
    	ship.update();
        if(!timeIsUp){
			for(int i=0; i<ufos.size(); i++){
				ufos.get(i).update(this, ammos);
			}
		}
        for(int i=0; i<ammos.size(); i++){
			ammos.get(i).update(this, ship);
		}

        //remove the Ammo Objects that are out of the window.
        if(ammos.get(index).outOfRange()){
        	ammos.remove(index);
        	ammos.add(new Ammo(ship));
			indexOfFirst++;
			if(indexOfFirst > ammos.size()){
				indexOfFirst = 0;
			}
		}

        //checks if the Ufo object has been destroyed.
        for(int i=0; i<ufos.size(); i++){
			if(ufos.get(i).ufoCrushedCheck()){
				ufos.remove(i);
			}
		}


        repaint();//Calls the paintComponent method.
    }

	/**
	 * This method is being called only when the time is up
	 * and the game has ended.
	 *
	 * @param g Is our Graphics variable that is responsible of the painting.
	 */
	public void youLost(Graphics g){
		if(duration >= timeRemaining && !endGame){
			String text = "You lost the game! Your score is " + Ufo.getScore();
			g.setColor(Color.WHITE);
			g.drawString(text, 280, 300);
			String text2 = "Press ESC to exit";
			g.setColor(Color.WHITE);
			g.drawString(text2, 280, 315);
			timeIsUp = true;
			new SaveGame(hiscore);
		}
	}

	/**
	 * This method is being called when the player had beaten
	 * a stage of this game.
	 *
	 * @param g Is our Graphics variable that is responsible of the painting.
	 */
	public void youWon(Graphics g){
		if(Ufo.getStageScore() == 100 && Ufo.getScore() != 1000){
			String text = "Congratulations You Had Beaten Stage " + stageNo;
			g.setColor(Color.WHITE);
			g.drawString(text, 280, 300);
			String text2 = "Press any key to continue";
			g.setColor(Color.WHITE);
			g.drawString(text2, 280, 315);

			timeRestart = true;
			if(contin){
				restart = true;
				Ufo.setStageScore(0);
				startTime = System.currentTimeMillis();
				contin = false;
			}
		}else if(Ufo.getStageScore() == 100 && Ufo.getScore() == 1000){
			String text = "Congratulations You Won the Game";
			g.setColor(Color.WHITE);
			g.drawString(text, 280, 300);
			String text2 = "Press ESC to exit";
			g.setColor(Color.WHITE);
			g.drawString(text2, 280, 315);
			timeIsUp = true;
			endGame = true;
			new SaveGame(hiscore);
		}
	}

	/**
	 * Notification that a key has been pressed.
	 *
	 * @param e The details of the key that was pressed
	 */
    public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_ESCAPE:
				new SaveGame(hiscore);
				System.exit(0);
				break;
			case KeyEvent.VK_LEFT:
				ship.moveLeft();
				for(int i=0; i<ammos.size(); i++){
					ammos.get(i).moveLeft();
				}
				break;
			case KeyEvent.VK_RIGHT:
				ship.moveRight();
				for(int i=0; i<ammos.size(); i++){
					ammos.get(i).moveRight();
				}
				break;
			case KeyEvent.VK_SPACE:
				if(index == 0){
					ammos.get(index).fire();
					index++;
				}
				if(ammos.get(index - 1).getY() < 460){
					ammos.get(index).fire();
					index++;
				}
				if(index == ammos.size()){
					index = 0;
				}
				break;
			default :
				if(Ufo.getStageScore() == 100){
					contin = true;
				}
		}
	}

    /**
	 * Notification that a key has been released.
	 *
	 * @param e The details of the key that was pressed
	 */
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT:
				ship.stopMove();
				for(int i=0; i<ammos.size(); i++){
					ammos.get(i).stopMove();
				}
				break;
			case KeyEvent.VK_RIGHT:
				ship.stopMove();
				for(int i=0; i<ammos.size(); i++){
					ammos.get(i).stopMove();
				}
				break;
		}
	}

	/**
	 * Notification that a key has been typed.
	 *
	 * @param e The details of the key that was pressed
	 */
	public void keyTyped(KeyEvent e) {
		//Not used in this game.
	}

}
