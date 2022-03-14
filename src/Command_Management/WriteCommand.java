package Command_Management;

import Cloud_Service.CommandSync;
import Main.Directory;
import Main.File;
import Main.FileManagement;
import Main.Repository;
import Write_Commands.CommandMkdir;
import Write_Commands.CommandRm;
import Write_Commands.CommandTouch;

public abstract class WriteCommand implements Command{
	
	public void execute(Repository rep) {}
	public void execute(Directory dir) {}
	public void execute(File file) {}
	
	//Return an object of the instance of the specific write-command class
	public static Command getCommand(String cmd, Repository rep)
	{
		if( !rep.hasWritePermission() )
		{
			FileManagement.getInstance().terminal.success=false;
			FileManagement.getInstance().terminal.addLabel("<User has no write permission!");
			return null;
		}
		
		if( cmd.compareTo("mkdir")==0 ) return new CommandMkdir();
		if( cmd.compareTo("touch")==0 ) return new CommandTouch();
		if( cmd.compareTo("rm")==0 ) return new CommandRm();
		if( cmd.compareTo("sync")==0 ) return new CommandSync();
		
		return null;
	}
}
