import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Stack;

/**
 * The Class MyStack.
 * 
 * Implementation of the stack based on standard Stack<> class
 */
public class MyStack {

 /** The coordinates of the base of the stack */
 private int x, y; 	
  
 /** The visible flag - used to determine if the simulation was started */
 boolean visible = false;
 
 /** The stack of green Cubes */
 private Stack<StackCube> stackCubes = new Stack<StackCube>();		

  /**
   * Drawing stack with all its cubes (does NOT draw cubes which are connected to railcars!)
   *
   * @param g2 the g2
   */
  public void draw(Graphics2D g2){
	  
	  Rectangle box = new Rectangle(x,y,70,20); // the base rectangle
	  
	  g2.setColor(Color.BLACK);
	  g2.fill(box);
	    
	  for (int i = 0; i<stackCubes.size(); i++){   // draw all cubes that are actually in stack, one by one
		  
		  stackCubes.get(i).draw(g2); // get a particular cube and draw it

	  }
	  
  }
  
  /**
   * Sets the coordinates of the stack base
   *
   * @param x the x given by the mouse click
   * @param y the y given by the mouse click
   */
  public void setCoord(int x, int y){this.x=x; this.y=y;}  // simple enough for you to get it, eh? 
  
  /**
   * Filling the stack of cubes
   */
  public void fillCubes(){
	  
	  stackCubes.clear(); // first of all clear the stack (used to wash away all previous stuff when simulation is restarted)
	  
	  for (int i =1; i<6; i++) {  // five times push a new cube, each above of the previous, starting from base
	      		 
		  stackCubes.push(new StackCube(x+20, y-30*i-1, i));
     
	  } 
  }
  
  /**
   * Pop one cube
   *
   * @return the stack cube
   */
  public StackCube pop(){
	  
	  if (stackCubes.isEmpty()) return null;  // if the stack is empty, then it's pretty retarded to try to pull something out of it, so... don't do anything
 	  
	  return stackCubes.pop();  // otherwise just return a cube from the top of stack
	  
  }
  
  /**
   * Push one cube
   *
   * @param cube the cube to be pushed into stack
   */
  public void push(StackCube cube){
	  
	  if (!stackCubes.isEmpty()) {   // if stack is not empty
	  
	  StackCube tmp = stackCubes.peek();  // take a look at the top cube of stack
	  
	  cube.setCoord(tmp.x, tmp.y-31);     // and set coordinates of the given cube right above the cube that is on top of the stack
	  } else cube.setCoord(x+20, y-31);  // if stack IS empty, set the coordinates of the given cube right above the base of the stack 
	  
	  
	  stackCubes.push(cube);  // then push the given cube with updated coordinates
	  
  }
  
}
