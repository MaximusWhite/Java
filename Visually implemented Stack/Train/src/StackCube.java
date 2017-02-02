import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * The Class of the Cube used in stack (that green box)
 */
public class StackCube {
		  
			/** Coordinates of the cube */
			int x; 
			int y;  
			
			/** The width of the cube */
			final int WIDTH = 30;
			
			/** The letter to be displayed inside the cube */
			char letter;
			
			/** The box to be actually drawn */
			Rectangle box;
			
			/**
			 * Instantiates a new stack cube.
			 *
			 * @param x the x coordinate
			 * @param y the y coordinate
			 * @param l the l integer representing the letter to be drawn inside a cube
			 */
			public StackCube(int x, int y, int l){ 
				this.x = x; 
				this.y=y; 
				
				switch(l){  // setting the letter
				          // tried it with char, but it was messed up, s I decided to follow the easy path
				case 1: this.letter='A';
				break;
				case 2: this.letter='B';
		        break;
				case 3: this.letter='C';
		        break;
				case 4: this.letter='D';
		        break;
				case 5: this.letter='E';
		        break;
				}
				
				box = new Rectangle(x,y, WIDTH, WIDTH);  // creating a box according to given coordinates
			}
			
			
			/**
			 * Drawing the cube
			 *
			 * @param g2 the g2
			 */
			public void draw(Graphics2D g2){ // fairly obvious
				                        
				g2.setColor(Color.GREEN);
				g2.draw(box);
				
				g2.setColor(Color.BLACK);
				g2.drawString("" + letter,x+12,y+17) ;  //drawing the letter
				
			}
			
			
			/**
			 * Sets the coordinates of the cube
			 *
			 * @param x the x coordinate
			 * @param y the y coordinate
			 */
			public void setCoord(int x, int y){
				this.x = x; // updating the coordinates of the cube
				this.y = y;
				box.setLocation(x, y); // updating the coordinates of the box to be drawn
			
			}
}
