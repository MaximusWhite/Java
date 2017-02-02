
import java.io.*;
import java.net.URL;

/**
 * Created by mikef on 30-Oct-2016.
 *
 * Handles Acces Control List operations
 *
 */
public class ACL {


    boolean final_mode;   // if true - do everything all the way to section 3.2 in lab description
                          // if false - perform operations up to section 3.1

    int table[][] = new int[5][6];   // ACL table for User permissions

    int role_assign[][] = new int[5][1];  // table containing roles assigned to users (from Roles.txt)
    int role_table[][] = new int [3][6];  // table containing permissions assigned to roles

    /**
     * Instantiates ACL according to final_mode
     *
     * @param final_mode - indicates which part of lab to perform ( 3.1 or 3.2)
     * @throws IOException
     */
    public ACL(boolean final_mode) throws IOException {

        this.final_mode = final_mode;

        String filename = final_mode ? "ACLRole.txt" : "ACL.txt" ;  // choose between ACL for users and ACL for roles

        BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
        String tmp;
        if(!final_mode) {        // if section 3.1

            for (int i = 0; i < 5; i++) {

                if ((tmp = br.readLine()) != null) {   // read lines from file and translate them into data for acces control matrix

                    String[] values = tmp.split("[|]", 6);

                    for (int j = 0; j < 6; j++) {

                        table[i][j] = Integer.parseInt(values[j]);

                    }
                }
            }
            br.close();

        }else{       // if section 3.2

            // first read from ACL for roles

            for (int i = 0; i < 3; i++) {

                if ((tmp = br.readLine()) != null) {

                    String[] values = tmp.split("[|]", 6);

                    for (int j = 0; j < 6; j++) {

                        role_table[i][j] = Integer.parseInt(values[j]); // and store inside table for permissions for roles

                    }
                }
            }

            // then read from the file distributing roles among the users

            br = new BufferedReader(new FileReader(new File("Roles.txt")));

            for (int i = 0; i < 5; i++) {

                if ((tmp = br.readLine()) != null) {

                    String[] values = tmp.split("[|]", 2);

                        role_assign[i][0] = Integer.parseInt(values[1]);

                }
            }

            br.close();

        }
    }


    /**
     * Converts give number into permission string
     *
     * Each permission has a canonical value:
     * r = 4
     * w = 2
     * x = 1
     *
     * Therefore, a set of permissions can be represented as a single number = sum of those above
     * Ex.: -wx = 4 * 0 + 2 * 1 + 1 * 1 = 3
     *
     * @param number - a number representing permissions
     * @return  a permission set in form of string
     */
    public String convertToString(int number){

        switch(number){
            case 0:
                return "---";
            case 1:
                return "--x";
            case 2:
                return "-w-";
            case 3:
                return "-wx";
            case 4:
                return "r--";
            case 5:
                return "r-x";
            case 6:
                return "rw-";
            case 7:
                return "rwx";
        }

       return "";
    }

    /**
     * Converts access requested by user into a number corresponding to it
     *
     * @param access - a string containing set of permissions requested by user
     * @return - a number representing given permission string
     */
    public int convertToNum(String access){

        int res = 0;
        if(access.contains("r")) res+=4;
        if(access.contains("w")) res+=2;
        if(access.contains("x")) res+=1;

        return res;
    }


    /**
     *
     * Shows the ACL corresponding to the current session (either for section 3.1 or 3.2 of lab)
     */
    public void showACL(){

        if(!final_mode) {   // if section 3.1

            System.out.println("\tF1\tF2\tF3\tF4\tF5\tF6");   // print the table nicely with headers
            for (int i = 0; i < 5; i++) {
                System.out.print("User " + (i + 1));
                for (int j = 0; j < 6; j++) {
                    System.out.print("\t" + convertToString(table[i][j]));
                }
                System.out.println();
            }

        }else {       // if section 3.2

            // print the table with users and their roles
            System.out.println("Roles: ");

            for(int i = 0; i< 5; i++){

                System.out.println("User "+(i+1) + "\t" +getRoleString(role_assign[i][0]));
            }
            // then print ACL for roles
            System.out.println("\nAccess by roles: \n\tF1\tF2\tF3\tF4\tF5\tF6");

            for (int i = 0; i < 3; i++) {
                System.out.print(getRoleString(i));
                for (int j = 0; j < 6; j++) {
                    System.out.print("\t" + convertToString(role_table[i][j]));
                }
                System.out.println();
            }

        }
    }

    /**
     * Finds out what access permissions given user has to given file
     *
     * @param user - given user
     * @param file - file for which to check permissions
     * @return a string containing the permission set for given user
     */
    public String getAccess(User user, String file){

        if(!final_mode) {  // if section 3.1 of lab
            return convertToString(table[convertUser(user.name)][convertFile(file)]);  // just go to ACL and find permissions
        }else{    // if section 3.2

            // go to role table,
            // find role of given user,
            // go to ACL for roles,
            // find and return  the permissions corresponding to user's role
            return convertToString(role_table[role_assign[convertUser(user.name)][0]][convertFile(file)]);
        }
    }

    /**
     * Checks if permissions requested by user match permissions that given user actually has
     * @param user - user requesting permissions
     * @param access - requested access
     * @param file - file whic user wants to access
     * @return true if permissions match
     */
    public boolean checkAccess(User user, String access, String file){

        // if section 3.1 of lab, get permissions from ACL.txt
        // if section 3.2, get permissions of a role of the user
        String perm = final_mode ? convertToString(role_table[role_assign[convertUser(user.name)][0]][convertFile(file)]) : convertToString(table[convertUser(user.name)][convertFile(file)]);

            // check which permissions the user requests
            boolean r = access.contains("r");
            boolean w = access.contains("w");
            boolean x = access.contains("x");

            // check if requested permissions match permissions retrieved from ACL
            if (r == false || (r && perm.contains("r") == true))
                if (w == false || (w && perm.contains("w") == true))
                    if (x == false || (x && perm.contains("x") == true))
                        return true;

        return false;
    }

    /**
     * Updates ACL entry for given user.
     *
     * @param user - user for which the changes to be made
     * @param newAccess - string with new permissions for user
     * @param file - file name for which new permissions should be assigned
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public void updateACL(String user, String newAccess, String file) throws FileNotFoundException, UnsupportedEncodingException {

        // convert given permissions to numbaer and put them into table storing permissions for users
        table[convertUser(user)][convertFile(file)] = convertToNum(newAccess);

        PrintWriter writer = new PrintWriter(new File("ACL.txt"), "UTF-8"); // open ACL file

        // reqrite the file with updated table
        for (int i = 0; i< 5; i++) {

            writer.println(table[i][0]+"|"+table[i][1]+"|"+table[i][2]+"|"+table[i][3]+"|"+table[i][4]+"|"+table[i][5]);

        }
        writer.close();
    }

    /**
     * Convert given file name into in index for the Access Control Matrix
     * @param file - file name to convert
     * @return an index corresponding to given file in the Access Control Matrix
     */
    public int convertFile(String file){
        switch (file){

            case "File1":
                return 0;
            case "File2":
                return 1;
            case "File3":
                return 2;
            case "File4":
                return 3;
            case "File5":
                return 4;
            case "File6":
                return 5;


        }
        return -1;
    }

    /**
     * Convert user name (id) into an index corresponding to it in ACL
     * @param name - name of user
     * @return an index corresponding to given user in ACL
     */
    public int convertUser(String name){
        switch (name){

            case "User1":
                return 0;
            case "User2":
                return 1;
            case "User3":
                return 2;
            case "User4":
                return 3;
            case "User5":
                return 4;

        }
        return -1;
    }

    /**
     * Converts given number from Roles.txt into a string indicating the role
     * @param number - number retrieved from file storing roles assigned to users
     * @return a role description in form of string
     */
    public String getRoleString(int number){

        switch(number){

            case 0:
                return "Sales";
            case 1:
                return "Tech";
            case 2:
                return "Manager";

        }
        return "Something went wrong";
    }

    /**
     * Get the index corresponding to a role in ACL for roles
     * @param role - a role to get index for
     * @return an index corresponding to a role in ACL for roles
     */
    public  int getRoleNumber(String role){

        switch(role){

            case "Sales":
                return 0;

            case "Tech":
                return 1;

            case "Manager":
                return 2;

        }
        return -1;
    }
}
