import java.awt.geom.* ;
import java.awt.* ;

/**
 *    Train Engine is a vehicle that can pull a chain of railcars.
 */
public class TrainEngine extends Vehicle   // subclass of Vehicle
{
    
    /**        Constants used for drawing the train (were given) */
    private static final double WIDTH = 35 ;
    
    /** The Constant UNIT. */
    private static final double UNIT = WIDTH / 5 ;
    
    /** The Constant LENGTH_FACTOR. */
    private static final double LENGTH_FACTOR = 14 ; // length is 14U
    
    /** The Constant HEIGHT_FACTOR. */
    private static final double HEIGHT_FACTOR = 5 ; // height is 5U
    
    /** The Constant U_3. */
    private static final double U_3 = 0.3 * UNIT ; 
    
    /** The Constant U2_5. */
    private static final double U2_5 = 2.5 * UNIT ; 
    
    /** The Constant U3. */
    private static final double U3 = 3 * UNIT ; 
    
    /** The Constant U4. */
    private static final double U4 = 4 * UNIT ; 
    
    /** The Constant U5. */
    private static final double U5 = 5 * UNIT ; 
    
    /** The Constant U10. */
    private static final double U10 = 10 * UNIT ; 
    
    /** The Constant U10_7. */
    private static final double U10_7 = 10.7 * UNIT ; 
    
    /** The Constant U12. */
    private static final double U12 = 12 * UNIT ; 
    
    /** The Constant U13. */
    private static final double U13 = 13 * UNIT ; 
    
    /** The Constant U14. */
    private static final double U14 = 14 * UNIT ; 
    
  /**
  * Instantiates a new train engine.
  */
 public TrainEngine(){
    	
    	box = new Rectangle(0,0, 98, 35); // just create a framing box
        number = 0;	// Train's number is never used, so let it be 0
    }
    
    /**
     *        Draws the train engine.
     *
     * @param g2 the graphics context
     */
    
    public void draw(Graphics2D g2)
    {
	
    if (visible == false) return;	  // if train should not be visible -> forget about drawing (used when simulation is not yet started or restarted)
    	
	int x1 = getX() ;   // the following code of drawing was given
	int y1 = getY() ;
	Rectangle2D.Double hood = new Rectangle2D.Double(x1, y1 + UNIT, 
							 U3, U3 ) ;
	g2.setColor(Color.blue) ;
	g2.fill(hood) ;

	Rectangle2D.Double body = new Rectangle2D.Double(x1 + U3,
							 y1,
							 U10, U4) ;
	g2.setColor(Color.blue) ;
	g2.fill(body) ;

	Line2D.Double hitch = new Line2D.Double(x1 + U13,
						y1 + U2_5,
						x1 + U14, 
						y1 + U2_5) ;
	g2.setColor(Color.black) ;


	if (selected) {          // if the train is selected, draw wheels and the hitch in RED, otherwise -> in BLACK 
		
		g2.setColor(Color.RED);
		
	} else if (!selected) g2.setColor(Color.black);
	
	g2.draw(hitch) ;
	
	Ellipse2D.Double wheel1 = new Ellipse2D.Double(x1 + U_3, 
						       y1 + U4, 
							 UNIT, UNIT) ;
	//g2.setColor(Color.black) ;
	g2.fill(wheel1) ;

	Ellipse2D.Double wheel2 = new Ellipse2D.Double(x1 + 1.3 * UNIT, 
						       y1 + U4, 
							 UNIT, UNIT) ;
	//g2.setColor(Color.black) ;
	g2.fill(wheel2) ;

	Ellipse2D.Double wheel3 = new Ellipse2D.Double(x1 + 2.3 * UNIT, 
						       y1 + 4 * UNIT, 
							 UNIT, UNIT) ;
	//g2.setColor(Color.black) ;
	g2.fill(wheel3) ;

	Ellipse2D.Double wheel4 = new Ellipse2D.Double(x1 + U10_7, 
						       y1 + U4, 
							 UNIT, UNIT) ;
	//g2.setColor(Color.black) ;
	g2.fill(wheel4) ;

	Ellipse2D.Double wheel5 = new Ellipse2D.Double(x1 + U12, 
						       y1 + U4, 
							 UNIT, UNIT) ;
	//g2.setColor(Color.black) ;
	g2.fill(wheel5) ;
	
	Ellipse2D.Double wheel6 = new Ellipse2D.Double(x1 + 9.7 * UNIT, 
		       y1 + U4, 
			 UNIT, UNIT) ;
	//g2.setColor(Color.black) ;
	g2.fill(wheel6) ;
	
    
	// HERE IS THE IMPLEMENTATION OF RECURSIVE DRAWING FOR TRAIN'S TRAILER
	
	if (next!=null) {    // if train has a trailer
    		
		((RailCar)next).draw(g2);  // call the draw method of that trailer
    	
    }

    }
    

}