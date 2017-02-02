import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
   This class describes a vehicle that looks like a flatbed 
   railcar.  The railcar should be assigned a unique number 
   displayed on its body. 
 */

public class RailCar extends Vehicle
{
    
    /** The Constant UNIT. */
    public static final int UNIT = 10 ;
    
    /** The Constant U6. */
    public static final int U6 = 6 * UNIT ;
    
    /** The Constant U5. */
    public static final int U5 = 5 * UNIT ;
    
    /** The Constant U4. */
    public static final int U4 = 4 * UNIT ;
    
    /** The Constant U3. */
    public static final int U3 = 3 * UNIT ;
    
    /** The Constant U2. */
    public static final int U2 = 2 * UNIT ;
    
    /** The Constant U15. */
    public static final int U15 = UNIT + UNIT / 2 ;
    
    /** The Constant U05. */
    public static final int U05 =  UNIT / 2 ;
    
    /** The Constant BODY_WIDTH. */
    public static final int BODY_WIDTH = U3 ;
    
    /** The Constant BODY_HEIGHT. */
    public static final int BODY_HEIGHT = U2 ;
    
    /**
     * Instantiates a new rail car.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param number the unique number
     */
    public RailCar(int x, int y, int number){ 
    	super(x,y, number);   // calling Vehicle's constructor
    	box = new Rectangle(x,y,67, 31);   // creating the framing box (used to check intersections)
    }
    
    /**
     *        Draw the rail car.
     *
     * @param g2 the graphics context
     */
    public void draw(Graphics2D g2)
    {

    	if (visible == false) return;    // if not visible, DO NOT DRAW IT. Obviously
   
    if (prev != null){   // if this particular RailCar is a trailer for some other RailCar
 
    	if (prev.number==0) setCoord(prev.getX()+98, prev.getY()+2);   // then if previous Vehicle is not the train, set coordinates of this 
    	                                                               // RailCar, so that it visually follows the preceding one
    	else setCoord(prev.getX()+65, prev.getY());     // if previous Vehicle IS a train, then draw this RailCar with a slight adjustment of coordinates
       
    	if (prev.selected) selected = true;           // if the previous Vehicle is selected, that means the entire chain should be selected, so this RailCar should be selected also
    	else selected = false;
    }
    
	int xLeft=getX();
	int yTop=getY();

    
	if (selected) {		  // if the RailCar is selected
		g2.setColor(Color.RED);  // draw it in RED
	} else g2.setColor(Color.BLACK); // otherwise in BLACK
	
	// the following code was given
	
	Rectangle2D.Double body 
	    = new Rectangle2D.Double(getX(), yTop + UNIT, U6, UNIT);      
	Ellipse2D.Double frontTire 
	    = new Ellipse2D.Double(getX() + UNIT, yTop + U2, UNIT, UNIT);
	Ellipse2D.Double rearTire
	    = new Ellipse2D.Double(getX() + U4, yTop + U2, UNIT, UNIT);
	
	// the bottom of the front windshield
	Point2D.Double r1 
	    = new Point2D.Double(getX() + UNIT, yTop + UNIT);
	// the front of the roof
	Point2D.Double r2 
	    = new Point2D.Double(getX() + U2, yTop);
	// the rear of the roof
	Point2D.Double r3 
	    = new Point2D.Double(getX() + U4, yTop);
	// the bottom of the rear windshield
	Point2D.Double r4 
	    = new Point2D.Double(getX() + U5, yTop + UNIT);

	// the right end of the hitch
	Point2D.Double r5 
	    = new Point2D.Double(getX() + U6, yTop + U15);
	// the left end of the hitch
	Point2D.Double r6 
	    = new Point2D.Double(getX() + U6 + U05, yTop + U15);
	
	Line2D.Double frontWindshield 
	    = new Line2D.Double(r1, r2);
	Line2D.Double roofTop 
	    = new Line2D.Double(r2, r3);
	Line2D.Double rearWindshield
	    = new Line2D.Double(r3, r4);
	Line2D.Double hitch
	    = new Line2D.Double(r5, r6);

	g2.draw(body);
	g2.draw(hitch);
	g2.draw(frontTire);
	g2.draw(rearTire);
	g2.draw(body) ;

	g2.drawString("" + getNumber(), getX() + U2, yTop + U2) ;
	
	g2.setColor(Color.BLACK);  
	
	if (cube!= null){  // if there is a Cube connected to the RailCar
		
		cube.setCoord(x+12, y-21);   // then adjust Cube's coordinates according to this RailCar's position
        cube.draw(g2);		 // and draw it
		
	}
	
	RailCar tmp = (RailCar) next;  // temp variable for the next Vehicle
	
	if (tmp!=null) {   // if there actually IS one
		
		tmp.draw(g2);   // then recursively call its drawing method as well 
	}
	
    }
}
    
    