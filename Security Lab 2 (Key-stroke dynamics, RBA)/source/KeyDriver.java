

import java.io.*;

/**
 * Created by mikef on 26-Oct-2016.
 *
 * Main class
 */
public class KeyDriver {


    final static int FLY_TIME = 2;   // constant for ly time index
    final static int FIRST_DWELL = 3;  // constant for first dwell time index
    final static int SAMPLE_SIZE = 500;
    static double THRESHOLD; // = 80.0;    // for frr
    static double THRESHOLD2; // = 68.0;   // for far

    static double frr;
    static double far;

    public static void main(String[] args) throws IOException {

        // default values for variables, in case no arguments passed
        User activeUser = new User("User2");
        User claimedUser = new User("User2");
        String access ="rx";
        String filename = "File1";
        ACL acl = new ACL(false);
        String role = "Sales";

        // if argument list of main program is not empty

        if(args.length != 0) {             // java User1 rx File2

                // if flagged with -ACL, update ACL with provided info

                if (args[0].equals("-ACL")) {          // java KeyDriver -ACL User1 rx File2

                    System.out.println("\n\nOLD ACL\n"); // show old permissions
                    acl.showACL();
                    acl.updateACL(args[1], args[2], args[3]);  // update permissions for given user and file
                    System.out.println("\n\nUPDATED ACL\n"); // show updated permissions
                    acl.showACL();

                    System.exit(0);   // quit
                }

                // indicates if want to run program up to section 3.1 or 3.2 of lab description
                // if last argument is -final flag, then section = 3.2
                if (args[args.length-1].equals("-final")) {

                    acl= new ACL(true); //true - 3.2


                }
                    // if no -ACL flag, read values as follows

                    activeUser = new User(args[0]);   // active user is who we take monitored sample from
                                                      // claimed user is who active user pretends to be (i.e. whose access he tries to get)
                                                      // in this lab we say that activeUser is claimedUser to proceed
                    claimedUser = new User(args[0]);   // MAKE CLAIMED USER DIFFERENT TO FAIL AUTHENTICATION ( pass "User*" as parameter, * belongs to {1-5} )

                    access = args[1];

                    filename = args[2];

        } else {  // if no arguments passed, warn user that default values are used
            System.out.println("ATTENTION! No arguments given, proceeding with: \n* User = " + activeUser.name + " \n* Permissions = " + access + " \n* File = " + filename+ "\n");
        }

        User users[] = new User[5];  // create and fill list of users

        for(int i = 0; i<5; i++ ){

            users[i] = new User("User"+(i+1));

        }

        // create threshold containing thresholds corresponding to a claimed user
        Threshold threshold = new Threshold(claimedUser.name);

        THRESHOLD = threshold.frr_thresh;
        THRESHOLD2 = threshold.far_thresh;

                // calculate FRR
                frr = calculateFRR(activeUser);
                // calculate FAR
                far = calculateFAR(users, activeUser);

                // if FRR == FAR
                if(frr == far) {
                    // complete authentification and pass user info to access control module
                    System.out.println("\nAUTHENTICATION SUCCESSFUL\n");
                    acl.showACL();

                    // if access control module figures that requested permissions match actual ones - grant access
                    if(acl.checkAccess(activeUser, access, filename)) {

                       System.out.println("\nYour '" + access + "' access to file '" + filename +"' is granted!");

                   }else{  // if not - infom user about insufficient access to requested file

                       System.out.println("\nYou only have '" + acl.getAccess(activeUser,filename) + "' access to file '" + filename +"'");
                   }

                // if FAR and FRR differ, inform user about failed access and do not proceed
                }else {
                    System.out.println("\nACCESS FAILED");
                }

    }

    /**
     * Calculating the deviation between monitored sample and one of the target samples according to given formula
     * @param mon - monitored sample set
     * @param ref - target sample set
     * @return deviation value
     */
    private static double calcD(Sample mon, Sample ref){

        double digraphSum=0;
        double monographSum=0;
        double result;
        double t_ref, t_mon;

        // for 500 entries find deviation between 1st kew dwell times
        for(int row=0; row<SAMPLE_SIZE; row++){


            t_ref = ref.table[row][FIRST_DWELL] != 0? ref.table[row][FIRST_DWELL] : 7;
            t_mon = mon.table[row][FIRST_DWELL] != 0? mon.table[row][FIRST_DWELL] : 7;

            if(t_mon !=0)
            monographSum = monographSum + Math.abs(t_ref - t_mon) / t_ref;

            // for 499 entries find deviation between fly times

            if (row<499) {
                t_ref = ref.table[row][FLY_TIME]!= 0? ref.table[row][FLY_TIME] : 7;
                t_mon = mon.table[row][FLY_TIME]!= 0? mon.table[row][FLY_TIME] : 7;

                if (t_mon != 0)
                    digraphSum = digraphSum + Math.abs(t_ref - t_mon) / t_ref;
            }
        }

        // perform rest of the formula and return result
        result = (monographSum/(double)(SAMPLE_SIZE) + digraphSum/(double)(SAMPLE_SIZE -1 )) * 50;

        return result;
    }

    /**
     * Calculates FRR for actie user
     * @param active - the active user
     * @return calculated FRR
     */
    public static double calculateFRR(User active){

        double res = 0;
        for(int i =0; i<5; i++){
            res += calcD(active.monitored,active.target[i]) >= THRESHOLD ? 1 : 0;
        }

        res /= 5.0;
      return res;
    }

    /**
     * Calculates FAR using reference samples from other users
     * @param users - users to compare against
     * @param active - active user
     * @return calculated FAR
     */
    public static double calculateFAR(User[] users, User active){

        double tmp=0;
        for(int j = 0; j<5; j++){

            if(users[j].name == active.name) continue;

            for(int i =0; i<5; i++) {

                tmp += calcD(active.monitored, users[j].target[i]) <= THRESHOLD2 ? 1 : 0;

            }
        }
        tmp /= 20.0;

        return tmp;
    }
}