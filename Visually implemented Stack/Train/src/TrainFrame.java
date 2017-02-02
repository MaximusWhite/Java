import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
   This frame has a menu with commands to manipulate the list and stack parts of the program as well as to handle
   a simple implementation of "File" menu (New and Exit options)
*/
public class TrainFrame extends JFrame
{
   
   /** The Constant FRAME_WIDTH. */
   private static final int FRAME_WIDTH = 800;
   
   /** The Constant FRAME_HEIGHT. */
   private static final int FRAME_HEIGHT = 600;
   
   /** The panel where all the simulation logic happens */
   TrainPanel panel = new TrainPanel();
   /**
      Constructs the frame with the menu bar containing File, Stack and List submenus
   */
   public TrainFrame()
   {  
        
	   
      JMenuBar menuBar = new JMenuBar();     
      setJMenuBar(menuBar);
      menuBar.add(createFileMenu());
      menuBar.add(createStackMenu());
      menuBar.add(createListMenu()); 
      
      setSize(FRAME_WIDTH, FRAME_HEIGHT);
      
      this.add(panel);
      
   }

   /**
    ActionListener for the Exit option of File submenu  
    */
   class ExitItemListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         System.exit(0);  // simply quit
      }
   }      
   
   /**
      Creates the File menu.
      @return the menu with needed options and proper listener
   */
   public JMenu createFileMenu()
   {
      JMenu menu = new JMenu("File");  // creating menu
      JMenuItem newItem = new JMenuItem("New");  // creating one option
      JMenuItem exitItem = new JMenuItem("Exit");  // other option
      
      /**
       * ActionListener for New option of the File menu
       * 
       */
      class NewListener implements ActionListener
      {
    	  
		public void actionPerformed(ActionEvent e) {
			
	        	panel.startNew();  // simply panel's method to handle creating a new simulation
			
		}
    	  
      }
      newItem.addActionListener(new NewListener()); // adding the listener to the New menu item
      ActionListener listener = new ExitItemListener();  // listener for Exit item
      exitItem.addActionListener(listener); // adding listener to Exit item
      menu.add(newItem); // adding items to the File menu
      menu.add(exitItem);
      return menu;  // return
   }

   /**
    * Creates the stack menu.
    *
    * @return the menu with needed options and proper listener
    */
   public JMenu createStackMenu()  // same as File menu
   {
	   JMenu menu = new JMenu("Stack");
	   JMenuItem popItem = new JMenuItem("Pop");
	   class PopListener implements ActionListener
	   {
		   public void actionPerformed(ActionEvent e) {
		     panel.popStack();
		   }
 
	   }
	   
	   popItem.addActionListener(new PopListener());
	   
	   JMenuItem pushItem = new JMenuItem("Push");
	   class PushListener implements ActionListener
	   {
		   public void actionPerformed(ActionEvent e) {
             panel.pushStack();
		   }
 
	   }
	   
	   pushItem.addActionListener(new PushListener());
	   
       menu.add(popItem);
       menu.add(pushItem);  
	   return menu; 
   }  

   
   /**
    * Creates the list menu.
    *
    * @return the menu with needed options and proper listener
    */
   public JMenu createListMenu()  // same as previous
   {
	   JMenu menu = new JMenu("List");
	   JMenuItem addFItem = new JMenuItem("AddFirst");
	   class AddFListener implements ActionListener
	   {
		   public void actionPerformed(ActionEvent e) {
		   panel.addFirst();   // ADDING TO THE BEGINNING OF THE LIST
		   }
 
	   }
	   
	   addFItem.addActionListener(new AddFListener());
	   
	   JMenuItem addLItem = new JMenuItem("AddLast");
	   class AddLListener implements ActionListener
	   {
		   public void actionPerformed(ActionEvent e) {
           panel.addLast();   // ADDING AT THE END OF THE LIST
		   }
 
	   }
	   
	   addLItem.addActionListener(new AddLListener());
	   
	   JMenuItem rmFItem = new JMenuItem("Remove first");
	   class RemoveFListener implements ActionListener
	   {
		   public void actionPerformed(ActionEvent e) {
		       panel.removeFirst();  // REMOVE THE FIRST ITEM FROM THE LIST 
		   }
 
	   }
	   
	   rmFItem.addActionListener(new RemoveFListener());
	   
	   JMenuItem rmLItem = new JMenuItem("Remove last");
	   class RemoveLListener implements ActionListener
	   {
		   public void actionPerformed(ActionEvent e) {
               panel.removeLast();   // REMOVE THE LAST ITEM FROM THE LIST
		   }
 
	   }
	   
	   rmLItem.addActionListener(new RemoveLListener());
	   
	   
       menu.add(addFItem);
       menu.add(addLItem); 
       menu.add(rmFItem);
       menu.add(rmLItem);
       
	   return menu; 
   } 
}
