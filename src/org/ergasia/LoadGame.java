package org.ergasia;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This class loads from file the hiscore
 * then returns it to the game.
 * 
 * @author Kosmas Sidiropoulos 2114111
 *
 */
public class LoadGame {

	/*In this variable will be saved the hiscore from the file */
	private int hiscore;
	
	/**
	 * The Constructor.
	 * Sets the hiscore.
	 */
	public LoadGame(){
		hiscore = readScore();
	}
	
	/**
	 * This method reads the binary file then
	 * returns the hiscore. 
	 * 
	 * @return the hiscore from file.
	 */
	public int readScore(){
		try{
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("savescore.dat"));
			hiscore = inputStream.readInt();
			inputStream.close();
		}catch(IOException e){
			 
		}
		
		return hiscore;
	}
	
	/**
	 * Get the hiscore from file.
	 * 
	 * @return the hiscore from file.
	 */
	public int getHiscore() {
		return hiscore;
	}
	
}
