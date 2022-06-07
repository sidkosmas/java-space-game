package org.ergasia.javaspacegame;

import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This class is our spaceship(an8rwpaki) object
 * and its mission is to move right or left firing
 * UFOs.
 *
 * @author Kosmas Sidiropoulos 2114111
 *
 */
public class Spaceship {
	/*X dimension of the object */
    private int x;
    /*Y dimension of the object */
    private int y;
    /*The speed of the object for the x-dimension */
    private int dx;
    /*The image of the object */
    private Image image;

    /**
     * The Constructor.
     * Sets the position and the image of the object
     */
    public Spaceship() {
		ImageIcon ii = null;
		try {
			ii = new ImageIcon(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ship.png"))));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		image = ii.getImage();
        x = 380;
		y = 555;
    }

    /**
     * The update method which will move the object
     * depending of the dx value.
     */
    public void update() {
    	x += dx;
    }

    /**
     * if the player typed the right keyboard button
     * the dx value increases, so the object moves right.
     *
     */
    public void moveRight(){
		if(dx < 5){
			dx = 1;
			dx += 4;
		}
	}

    /**
     * if the player typed the left keyboard button
     * the dx value decreases, so the object moves left.
     *
     */
    public void moveLeft(){
		if(x > 40){
			if(dx > -5){
				dx = -1;
				dx -= 4;
			}
		}
	}

    /**
     * If the key pressed by the player is released
     * the dx value is eventually set to 0 so the object
     * has no speed.
     */
	public void stopMove(){
		if(dx < 0){
			while(dx!=0){
				dx++;
			}
		}
		if (dx > 0){
			while(dx!=0){
				dx--;
			}
		}
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
     * Get the speed of the object.
     *
     * @return The speed of the object.
     */
    public int getDx() {
		return dx;
	}

    /**
     * Get the y-dimension value.
     *
     * @return The y-dimension value.
     */
    public int getY() {
        return y;
    }

    /**
     * Get the image of the object.
     *
     * @return The image of the object.
     */
    public Image getImage() {
        return image;
    }

}
