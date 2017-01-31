1. To compile the program, make sure to be in the "source" folder with all the source files. 
Then use the following command in the command line:

javac KeyDriver.java User.java Sample.java Threshold.java ACL.java

2. Upon compilation, there are multiple ways to run the program:

	*******************************************************************************************************
	NOTE: Username is one of {"User1", "User2", "User3", "User4", "User5"}

	      Permissions is a string containing one or few of the permissions {r, w, x}. Ex.: "rx", "w", "--x"
	      The order does not matter, the program just checks the presence or "r", "w" or "x".

	      Filename is one of {"File1", "File2", "File3", "File4", "File5", "File6"} 	

	IMPORTANT: The order of arguments fed to the program matters!
	*******************************************************************************************************

	a. java [username] [permissions] [filename]

	Running the program with this line will run it up to the form described in section 3.1 in lab manual.
	That means, the program will check if authentication is successful and then check if the user has given permissions to given file.
	
	b. java -ACL [username] [permissions] [filename]

	Running program with this line will enable the editing mode for ACL table saved in the file "ACL.txt"
	That means, that after authentication the program will find the permissions of the given user for a given file and overwrite them with the given 	permissions. After that it will update ACL.txt with new matrix.

	c. java [username] [permissions] [filename] -final

	Running program with this line will run it in the form described in section 3.2 of the lab manual.
	That is, with implemented Role-based Access.
	In the source folder there are two files or this part:
 
		* "Roles.txt" - contains the list of users and roles assigned to them 
		* "ACLRole.txt" - contains the Access Control Matrix for ROLES instead of separate users (unlike "ACL.txt")

	After authentication the program will first check which role the user is assigned (via "Roles.txt"), then check what access permissions correspond 	to user's role for a given file and then see if requested permissions match the actual role's permissions.