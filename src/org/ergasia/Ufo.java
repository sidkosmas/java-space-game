package org.ergasia;

import java.awt.Image;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * This class makes the enemies of this
 * game.
 * 
 * @author Kosmas Sidiropoulos 2114111
 *
 */
public class Ufo {
	/*X dimension of the object */ 
	private int x;
	/*Y dimension of the object */ 
	private int y;
	/*The width of the object */
    private int width;
	/*The height of the object */
    private int height;
    /*Boolean variable that checks if an Ufo is crushed or not */
	private boolean ufoCrushed = false;
	/*The image of the object */
    private Image image;
    /*In this variable is saved the total score */
	private static int score = 0;
	/*In this variable is saved the stage score */
	private static int stageScore = 0;
	/*Random object */
	private Random rand;
	
	/**
	 * The Constructor.
	 * Sets the position and the image of the object.
	 */
	public Ufo() {
		ImageIcon ii = new ImageIcon(getClass().getResource("/resources/ufo.png"));
        image = ii.getImage();
        rand = new Random();
        
		do{
			x = 40 + rand.nextInt(720);
			y = 30 + rand.nextInt(440);
		}while(x > 642 && y < 137);
		
		width = image.getWidth(null);
        height = image.getHeight(null);
	}
	
	/**
	 * The update method which will check for collision.
	 * 
	 * @param pn The game panel.
	 * @param ammo The Ammo object that we want to check for collision.
	 */
	public void update(Panel pn, ArrayList<Ammo> ammo){			
		checkForCollision(ammo);
		
	}
	
	/**
	 * This method checks for collision and adds the score.
	 * 
	 * @param ammos The ammunition of the spaceship(An8rwpaki).
	 */
	private void checkForCollision(ArrayList<Ammo> ammos) {
		for(int i=0; i<ammos.size(); i++){
		
			int ammoX = ammos.get(i).getX();
			int ammoY = ammos.get(i).getY();
			int ammoWidth = ammos.get(i).getWidth();
			int ammoHeight = ammos.get(i).getHeight();
		
			if(ammoY + ammoHeight > y && ammoY + ammoHeight < y + height){
				if(ammoX + ammoWidth > x && ammoX < x + width){
					image = null;
					ufoCrushed = true;
					score+=10;
					stageScore += 10;
				}
			}
		
		}
		
	}
	
	/**
	 * Get the ufo status. Crushed or no-crushed.
	 * 
	 * @return Returns true is the UFO is crushed.
	 */
	public boolean ufoCrushedCheck(){
		return ufoCrushed;
	}
	
	/**
	 * Sets the stage score
	 * 
	 * @param stageScore the stage score.
	 */
	public static void setStageScore(int stageScore) {
		Ufo.stageScore = stageScore;
	}

	/**
	 * Get the total score.
	 * 
	 * @return Returns the total score.
	 */
	public static int getScore() {
		return score;
	}
	
	/**
	 * Get the stage score.
	 * 
	 * @return Return the stage score.
	 */
	public static int getStageScore() {
		return stageScore;
	}
	
	/**
     * Get the image of the object.
     * 
     * @return The image of the object.
     */
	public Image getImage() {
		return image;
	}
	
	/**
	 * Get the x-dimension value.
	 * 
	 * @return The x-dimension value.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the y-dimension value.
	 * 
	 * @return The y-dimension value.
	 */
	public int getY() {
		return y;
	}
	
	public static void setScore(int score) {
		Ufo.score = score;
	}
	
	
}
