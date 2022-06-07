package org.ergasia.javaspacegame;

import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This class is the ammunition of the
 * spaceship.
 *
 * @author Kosmas Sidiropoulos 2114111
 *
 */
public class Ammo {
	/*X dimension of the object */
	private int x;
	/*Y dimension of the object */
    private int y;
    /*The speed of the object for the x-dimension */
    private int dx;
    /*The speed of the object for the y-dimension */
	private int dy = 0;
	/*The width of the object */
    private int width;
    /*The height of the object */
    private int height;
    /*The image of the object */
    private Image image;
    /*This boolean variable is used for the shot system*/
    private boolean check = false;

    /**
     * The Constructor.
     * Sets the position and the image of the object.
     *
     * @param ship The Spaceship object.
     */
    public Ammo(Spaceship ship){
		ImageIcon ii = null;
		try {
			ii = new ImageIcon(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ammo.png"))));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		image = ii.getImage();
    	x = ship.getX() + 8;
		y = ship.getY() - 20;

		width = image.getWidth(null);
        height = image.getHeight(null);

    }

    /**
     * The update method which will move the object.
     *
     */
    public void update(Panel pn, Spaceship ship){
		if(!check){
			x += ship.getDx();
		}else if(check){
			y -= dy;
		}

	}

    /**
     * Returns if the ammo is out of the window(true)
     * or still inside(false).
     *
     * @return Boolean if the ammo is out of the window.
     */
    public boolean outOfRange(){
		if(y < 0 - 20){
			return true;
		}else return false;

	}

    /**
     * Enable fire.
     */
    public void fire(){
		check = true;
		dy = 5;
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
	 * Get the y-dimension value.
	 *
	 * @return The y-dimension value.
	 */
    public int getY() {
		return y;
	}

    /**
     * Get the x-dimension speed of the object.
     *
     * @return The x-dimension speed of the object.
     */
    public int getDy() {
		return dy;
	}

    /**
     * Get the y-dimension speed of the object.
     *
     * @return The y-dimension speed of the object.
     */
    public int getDx() {
		return dx;
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
     * Get the width size of the object.
     *
     * @return The Width size of the object.
     */
    public int getWidth() {
		return width;
	}

    /**
     * Get the width size of the object.
     *
     * @return The Width size of the object.
     */
    public int getHeight() {
		return height;
	}
}
