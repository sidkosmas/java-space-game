package org.ergasia;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This class is responsible for the saving data
 * of the high score.
 * 
 * @author Kosmas Sidiropoulos 2114111
 */
public class SaveGame {
	
	/*This variable is used so the game can only save the score once. */
	static private boolean saveOnlyOneTime = false;
	/*In this variable is saved the current hiscore of the game. */
	private int hiscore;
	
	/**
	 * The Constructor.
	 * 
	 * @param hiscore The current hiscore.
	 */
	public SaveGame(int hiscore){
		this.hiscore = hiscore;
		saveScore();
	}
	
	public void saveScore(){
		if(!saveOnlyOneTime && Ufo.getScore() > hiscore){
			try{
				ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("savescore.dat"));
				outputStream.writeInt(Ufo.getScore());
				outputStream.close();		
			}catch(FileNotFoundException e){
				System.out.println("Cannot find file savescore.dat.");
			}catch(IOException e){
				System.out.println("Problems with input from savescore.dat.");
			}
			saveOnlyOneTime = true;
		}
	}
	
}
