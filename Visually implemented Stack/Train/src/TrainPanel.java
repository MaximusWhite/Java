import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

/**
 * The Class TrainPanel which is the citadel of this simulator's logic
 * Contains all main objects, Train, RailCars, Stack 
 */
public class TrainPanel extends JPanel {   
    
	/** The started - indicates if the simulation is started (after 7 clicks) */
	boolean started = false; 
	
	/** The caught - indicates if mouse's coordinates are inside the selected Vehicle  */
	boolean caught = false;
	
	/** The click counter - counts clicks */
	int clickCnt=0;
	
	/** The train. */
	TrainEngine train = new TrainEngine();
	
	/** The array list of RailCars */
	ArrayList<RailCar> cars = new ArrayList<RailCar>();
	
	/** My custom stack of cubes */
	MyStack cubes = new MyStack();
	
	/** The selected one - VERY IMPORTANT VARIABLE, points to a Vehicle, which is selected at the moment  */
	Vehicle theSelectedOne = null;
	
	/**
	 * Instantiates a new train panel.
	 * Initializes all main objects, defines and adds all required listeners
	 */
	public TrainPanel(){
	    
		startNew();   // setting up a new simulation
		
		/**
		 * MouseListener listening for mouse clicks and mouse releases
		 */
		class PressListener implements MouseListener{     


			public void mouseClicked(MouseEvent e) {   // so when click on a panel occurs

			    if (!started) {   // if the simulation is not started 
			    	clickCnt++;	   // increment the click counts
			    	switch(clickCnt){   // according to the count do following: create a train, create 5 RailCars and a Stack
			    	                    // each of that at the coordinates where the click had occured
			    	case 1: train.setCoord(e.getX(), e.getY());   // setting coordinates
			    	        train.setVisible(true);	      //   set visibility of an object to true
			    	        repaint();   // repaint the panel
			    	        break;  // exit from switch 
			    	case 2: {RailCar tmp = cars.get(0);     // next 5 cases follow similar logic
			    			tmp.setCoord(e.getX(), e.getY());
			    			tmp.setVisible(true);		         
			    			cars.set(0, tmp);
			    			repaint();
			    	        }  
			    	        break;
			    	case 3: {RailCar tmp = cars.get(1);
			    			tmp.setCoord(e.getX(), e.getY()); 
	    					tmp.setVisible(true);		         
	    					cars.set(1, tmp);
	    					repaint();
	    	        		}  
	    	        		break;
			    	case 4: {RailCar tmp = cars.get(2);
			    			tmp.setCoord(e.getX(), e.getY()); 
			    			tmp.setVisible(true);		         
			    			cars.set(2, tmp);
			    			repaint();
			    			}  
			    			break;
			    	case 5: {RailCar tmp = cars.get(3);
			    			tmp.setCoord(e.getX(), e.getY()); 
							tmp.setVisible(true);		         
							cars.set(3, tmp);
							repaint();
			    			}  
			    			break;
			    	case 6: {RailCar tmp = cars.get(4);
			    			tmp.setCoord(e.getX(), e.getY()); 
							tmp.setVisible(true);		         
							cars.set(4, tmp);
							repaint();
			    			}  
			    			break;
	        		
			    	case 7: cubes.visible=true;   // set the stack visible 
			    	        cubes.setCoord(e.getX(), e.getY());    // set its coordinates
			    	        cubes.fillCubes();  // fill stack with new cubes
			    			started = true;   // the simulation is has begun!
			    			repaint();   // repaint
			    			break;
			    	   
			    	} 	
			    }else  // if the simulation IS started after the click
			    if(started){
			    	
			    	if (train.contains(e.getX(), e.getY())) {   // if the click occurred on Train
			    		
			    		if (!train.selected){     // if Train was NOT selected
			    	    unselectAll();          // unselect all other Vehicles 
			    		train.selected=true;    // set Train selected
			    		theSelectedOne=train;   //  update theSelectedOne -> make it point to the same object as Train points to
 			    		}else {
			    			train.selected=false;    // if the Train was selected, make it now selected
			    			theSelectedOne = null;   // theSelectedOne should not point to any Vehicle at all
			    		}
			    		repaint();  // obviously
			    	} 

			    	for (RailCar c: cars){   // now checking if click occurred on one of the RailCars
			    		 // going through the RailCar list one car at a time
			    		if (c.contains(e.getX(), e.getY())){   // if the current RailCar contains the mouse coordinates during the click
			    			
			    			if (!c.selected ){   // then if the RailCar is NOT selected
			    			 
			    			 theSelectedOne = c;	// then this current RailCar is theSelectedOne
			     			 unselectAll();    // unselect all
			    			 c.selected=true;   //  make the current RailCar selected

			    			}else {             // if the RailCar IS selected
			    				c.selected = false;   // then it is not selected anymore :(
			    				theSelectedOne = null;  // theSelectedOne points to the infinite emptiness of existence 
			    			}
	
			    			repaint();    // obviously
			    		} 
			    	}	   	
			    } 
			}
			public void mouseReleased(MouseEvent e) {     // when mouse released (after dragging)
				
				if (theSelectedOne==null) return;   // THIS is why theSelectedOne variable is so useful! 
				                                    // it is easier to check if this variable points to something or not, 
				                                    // rather than calling some method which would go over ALL THE RAILCARS and the train
													// to figure out the same information
				 									// RESOURCES SAVING, DUH!
				// in any case, ^ if nothing selected, simply do nothing 
				
				
				 if (theSelectedOne!= train && (theSelectedOne.intersects(train) || theSelectedOne.intersects(train.getLast()) )) {
					 // if the Train is selected, and the selected one intersects with either train or the very last element of train list
					 
					Vehicle tmp = train.getLast();   // basically just add theSelectedOne to the end of the list
					 tmp.next=theSelectedOne;        // last element of the list now points to the selected one
					 theSelectedOne.prev = tmp;      // previous element of the selected one is now last element of the train
					
					 cars.remove((RailCar)theSelectedOne);  // REMOVE the ex-selected one from the RailCar array list (this is why isTrailer boolean not needed actually)
					 unselectAll();  // self-explainatory
					 theSelectedOne = null;  // nothing is selected 
					 repaint();   // obviously
					 return;   // no need for further checks
				 }
				
				
				for (int i = 0; i<cars.size(); i++){   // go over all RailCars in the array list
					
					 RailCar c = cars.get(i);  
					 
					 if (c==theSelectedOne || c==null) continue;  // if the RailCar itself is selected, of course it intersects with itself, so skip it
					
					if (theSelectedOne!= train && theSelectedOne!=null && (theSelectedOne.intersects(c) || theSelectedOne.intersects(c.getLast()))) {
						
						// if the selected one is NOT a train and something actually is selected and if theSelectedOne intersects with either current RailCar
						// or the last of the current RailCar's trailer
						
						Vehicle tmp = c.getLast();  // pretty much same logic as for the train part above, except that instead of the train the current RailCar is used 
						 tmp.next=theSelectedOne;
						 theSelectedOne.prev = tmp;
						
						 theSelectedOne.isTrailer=true;
						 cars.remove((RailCar)theSelectedOne);
						 unselectAll();
						 theSelectedOne = null;
						 repaint();	   
					}
				}
			} 
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
		}
		
		/**
		 * The MouseMotionListener, used to track mouse dragging and mouse motion
		 *
		 */
		class DragListener implements MouseMotionListener{   

			public void mouseDragged(MouseEvent e) {  // when dragged

				if (theSelectedOne!=null && caught) {  // if something is selected AND at that very moment mouse is inside a framing box of the selected one
					
				    theSelectedOne.setCoord(e.getX(), e.getY());	// update the selected one's coordinates with the mouse coordinates
				  
					repaint();  // duh
				}
			}
			public void mouseMoved(MouseEvent e) {  // when the mouse is moves
				
				if (theSelectedOne==null ) return;  // if nothing selected, ignore
					
					if (theSelectedOne.contains(e.getX(), e.getY())) {  // otherwise, if the selected one contains the mouse
						caught=true;    // indicate that the mouse is caught by the selected one
				    }else caught=false;  // otherwise not caught
			}
		}
		
		
	this.addMouseListener(new PressListener());	 // add listener to the panel
	this.addMouseMotionListener(new DragListener());	
		
		
	}
    
   /**
    * Overriding paintComponent method of JPanel class
    */
    public void paintComponent(Graphics g){
		super.paintComponent(g); 
		Graphics2D g2 = (Graphics2D) g; 
		train.draw(g2);  // draw a train (and recursively draw its trailer and its trailer and its trailer and its trailer... :) )
		
		if (cubes.visible==true) cubes.draw(g2);  // if cube stack is supposed to be visible -> draw it
		
		for (RailCar c : cars){  // draw all RailCars from the array list, which are not trailers
			
			if (!c.isTrailer) c.draw(g2);   // actually it is not needed to check if a car is a trailer, because when a car becomes a trailer, it is simply removed from the array list right away 
		}
		
	}
	
	/**
	 * Removes the first Vehicle from the linked list
	 */
	public void removeFirst(){
		
		
		if (theSelectedOne== train && train.next!=null){  // do it if the train is selected and if train is not a lonely Vehicle
		
		Random rand = new Random();
		
		Vehicle tmp = train.next;   // re-managing the object references of the RailCars and the train
		train.next = tmp.next;
		tmp.prev = null;
		tmp.next = null;
		tmp.selected=false;
		tmp.setCoord(rand.nextInt(200)+30, rand.nextInt(200)+30);  // the RailCar which is being removed is given some random coordinates within a small area in upper left corner of the frame
		tmp.isTrailer=false;  // it is not a trailer anymore (as if anyone cared)
		
		cars.add((RailCar)tmp);  // now add the removed RailCar to the array list 
		
		if (train.next!=null) train.next.prev = train;  // if now train still has a trailer, than trailer's previous Vehicle is the Train
		repaint();  // duh
		}
	}
	
	/**
	 * Removes the last Vehicle from the linked list
	 */
	public void removeLast(){
		
		if (theSelectedOne== train && train.next!=null){ // same conditions as previously
			
			Random rand = new Random();
			
			Vehicle tmp = train.getLast();  // similar logic as in removeFirst method, but here the operations are made on the LAST element of the linked list
			
			tmp.prev.next = null;
			tmp.prev = null;
			tmp.selected=false;
			tmp.setCoord(rand.nextInt(200)+30, rand.nextInt(200)+30);
			tmp.isTrailer=false;
			
			cars.add((RailCar)tmp);
			repaint();
		}
	}
	
	/**
	 * Adds the Vehicle at the beginning of the linked list
	 */
	public void addFirst(){
		
		
		if (theSelectedOne!= null && theSelectedOne!=train){  // if something is selected and it's not a train
			
		  if (theSelectedOne.next==null){ 	   // if the selected RailCar has no trailer
			
            if (train.next==null) {           // if train has no trailer so far
            	
            	train.next = theSelectedOne;   // manage the object references in a certain way 
            	theSelectedOne.prev = train;
            	theSelectedOne.next = null;
            	cars.remove((RailCar)theSelectedOne);  // remove the RailCar which is being added to the linked list from the array list
            	theSelectedOne=null;
            	repaint();
            	return;
            }
            
			theSelectedOne.next = train.next;  // if train already has a trailer, manage object references a bit differently
			
			train.next.prev=theSelectedOne;
			
			train.next = theSelectedOne;
			
			theSelectedOne.prev = train;
			
			cars.remove((RailCar)theSelectedOne);
			
			theSelectedOne=null;
			
			repaint();
		  } else {           //  if the selected one HAS a trailer 
			  
			  if (train.next==null) {   // same logic if the Train has no trailer
	            	
	            	train.next = theSelectedOne;
	            	theSelectedOne.prev = train;
	            	//theSelectedOne.next = null;
	            	cars.remove((RailCar)theSelectedOne);
	            	theSelectedOne=null;
	            	repaint();
	            	return;
	          } else {     // if the Train has a trailer already
	        	
	        	
	        	  Vehicle tmp = theSelectedOne.getLast();   // manage object reference differently
	        	  
	        	  tmp.next = train.next;  
	        	  
	        	  train.next.prev = tmp;
	        	  
	        	  train.next = theSelectedOne;
	        	  
	        	  theSelectedOne.prev = train;
	        	  
	        	  cars.remove((RailCar)theSelectedOne);
	    			
	        	  theSelectedOne=null;
	    			
	        	  repaint();
	        	  
	          }
		  }
		  
		  
		}
	}
	
	/**
	 * Adds the Vehicle to the end of the linked list
	 */
	public void addLast(){
		
		if (theSelectedOne!=null && theSelectedOne!=train){   // if something is selected and it is not Train
		
		theSelectedOne.prev = train.getLast();      // very easy, list's last Vehicle points now to the selected one, and the selected one has list's last Vehicle as its previous Vehicle
		train.getLast().next = theSelectedOne;
		
		cars.remove((RailCar)theSelectedOne);
		theSelectedOne=null;
		repaint();
		}
	}
	
	/**
	 * Push one cube from the the selected RailCar into the stack
	 */
	public void pushStack(){
		
		if (theSelectedOne==null) return;  // if nothing selected , exit method
		
        Vehicle tmp = theSelectedOne.getLast();    // get the very last Vehicle in the chain headed by the selected one
		
        while (tmp!=null && tmp.cube==null) {	  // traverse backwards until the first RailCar with a cube on it is found
        	tmp = tmp.prev;
        }
        
        if (tmp== null || tmp==train) return;  // if at the end no cubes were found in the chain, exit method
        
		cubes.push(tmp.cube);	// otherwise push the found cube into the stack
		
		tmp.cube=null;   // set the selected one's cube to be non-existing
		
		repaint();
		
	}
	
	/**
	 * Pop one cube from the stack and connect it to the selected Vehicle
	 */
	public void popStack(){
		
		if (theSelectedOne==null) return;  // if nothing selected, exit method
        
		Vehicle tmp; 
		if (theSelectedOne==train) tmp = train.next; 
		else tmp = theSelectedOne;   // tmp should be the head of the selected chain, but not Train

		while(tmp!=null && tmp.cube!=null){	   // traverse through the Vehicles in the chain until found the one without the cube
			tmp = tmp.next;		
		}
		
		if (tmp==null) return;  // if turns out all RailCars in the chain have cubes -> exit method
		
		tmp.cube = cubes.pop();  // otherwise, pop one cube from the stack and connect it to the selected RailCar
	
		repaint();
		
	}
	
	
	/**
	 * Unselects all Vehicles
	 */
	public void unselectAll(){
		
		train.selected = false;   // pretty obvious
		
		for (RailCar car : cars){
			car.selected=false;
		}
		repaint();
		
	}
	
	/**
	 * Set up new simulation
	 */
	public void startNew(){
         
		cars.clear();    // make array list to be empty
        clickCnt=0; 	// count of clicks is reset
        started=false;   // simulation is not started
        train.visible = false; // train not visible
        train.selected=false;  // train not selected
        train.next=null;  // train has not trailer
        cubes.visible=false;  // stack is not visible
		for (int i =1 ; i<6; i++) {  // create new RailCars in the array list
			cars.add(new RailCar(0,0, i));
		}
		repaint();  // yup, again
	}
}
	
