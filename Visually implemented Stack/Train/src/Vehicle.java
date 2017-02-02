import java.awt.Rectangle;

/**
 * The Class Vehicle - super class of both RailCar and TrainEngine.
 */
public abstract class Vehicle {
	
	/** The x coordinate */
	int x;
	
	/** The y coordinate */
	int y;
	
	/** The unique number */
	int number;
	
	/** The selected - shows if the Vehicle is selected by the user */
	boolean selected =false;
	
	/** The is trailer - used in RailCars to indicate if the RailCar if a trailer or not (to determine to call its draw method or not) */
	boolean isTrailer=false;     // actually never even used it, because if a RailCar becomes a trailer, I simple delete it from the array list 
	
	/** The next Vehicle in the linked list */
	Vehicle next;
	
	/** The prev Vehicle in the linked list */
	Vehicle prev;
	
	/** The cube connected to the Vehicle */
	StackCube cube = null;
	
	/** The visible - indicates the visibility of the Vehicle*/
	protected  boolean visible = false;
	
	/** The box - the framing box used to check intersections */
	Rectangle box;
  
  /**
   * Instantiates a new vehicle.
   */
  public  Vehicle(){}	
 	
  /**
   * Instantiates a new vehicle with parameters.
   *
   * @param x the x coordinate
   * @param y the y coordinate
   * @param number the unique number
   */
  public Vehicle(int x, int y, int number){ this.x =x; this.y = y; this.number = number; }   // Captain Obvious was here.	
	
  /**
   * Gets the x coordinate of the Vehicle
   *
   * @return the x coordinate
   */
  public int getX()	{return x;}
  
  /**
   * Gets the y coordinate of the Vehicle
   *
   * @return the y coordinate
   */
  public int getY() {return y;} 
  
  /**
   * Sets the coordinates of the Vehicle
   *
   * @param x the x coordinate
   * @param y the y coordinate
   */ 
  public void setCoord(int x, int y){
	  this.x=x;   // updating coordinates of the Vehicle
	  this.y=y;
	  box.setLocation(x, y);  // updating coordinates of the framing box
  }
	
  /**
   * Checks if the Vehicle contains given coordinates
   *
   * @param x the x coordinate
   * @param y the y coordinate
   * @return true, if contains
   */
  public boolean contains(int x, int y){
	  
	 if (box.contains(x, y)) {
		 
		 return true;
	 }
	 
	 return false; 
  }
  
  /**
   * Checks if the framing box of the Vehicle intersects with the framing box of another Vehicle (the one that is dragged to this one)
   *
   * @param with the Vehicle to check intersection with
   * @return true, if intersection occurs
   */
  public boolean intersects(Vehicle with ){
	  
	  if (this.box.intersects(with.box)) return true;  // just using intersects() method of the Rectangle class
	  return false;
  }
  
  /**
   * Sets the visibility
   *
   * @param b the new visibility value
   */
  public void setVisible(boolean b) {this.visible = b;}


  /**
   * Gets the unique number
   *
   * @return the unique number
   */
  public int getNumber(){return number;}
  
  /**
   * Gets the last Vehicle in the chain (whether it is list or just a RailCar with its trailers)
   *
   * @return the last - the object reference to a very last Vehicle in the chain
   */
  public Vehicle getLast(){
	    
		
	  Vehicle tmp = this;   // temp Vehicle
	  
	  while (tmp.next!=null) {tmp=tmp.next;}   // simple "traverse through linked list" algorithm
	
	return tmp;  // return final element
}
  
}
