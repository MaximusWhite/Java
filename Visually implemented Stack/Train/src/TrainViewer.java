/**
 * Train Viewer the main program
 * 
 * @author Mikhail Korchevskiy
 * @date April 7th
 */


import javax.swing.JFrame;

/**
* This is the main class   
*/
public class TrainViewer
{  
   
   /**
    * The main method.
    *
    * @param args the cmd arguments
    */
   public static void main(String[] args)
   {  
      JFrame frame = new TrainFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setTitle("Train list stack whatever thing batman");
      frame.setVisible(true);
   }
}
