package Command_Management;
import java.util.Vector;

import Main.Repository;

public abstract class CommandFactory {
	
	static Vector<String> readCmd;
	static Vector<String> writeCmd;
	
	static
	{
		readCmd=new Vector<String>();
		readCmd.add("ls");
		readCmd.add("cd");
		readCmd.add("cat");
		readCmd.add("pwd");
		readCmd.add("upload");
		
		
		writeCmd=new Vector<String>();
		writeCmd.add("mkdir");
		writeCmd.add("touch");
		writeCmd.add("rm");
		writeCmd.add("sync");
	}
	
	//Separates read commands from write commands
	public static Command getCommand(String cmd, Repository rep)
	{
		if( readCmd.contains( cmd ) )
		 return ReadCommand.getCommand(cmd, rep);
		else
		 if( writeCmd.contains( cmd ))
			 return WriteCommand.getCommand(cmd, rep);
		
		return null;
	} 
}
