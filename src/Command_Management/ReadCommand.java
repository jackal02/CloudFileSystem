package Command_Management;
import Cloud_Service.CommandUpload;
import Main.Directory;

import Main.File;
import Main.FileManagement;
import Main.Repository;
import Read_Commands.CommandCat;
import Read_Commands.CommandCd;
import Read_Commands.CommandLs;
import Read_Commands.CommandPwd;

public abstract class ReadCommand implements Command {

	public void execute(Repository rep) {}
	public void execute(Directory dir) {}
	public void execute(File file) {}
	
	//Return an object of the instance of the specific read-command class
	public static Command getCommand(String cmd, Repository rep)
	{
		if( !rep.hasReadPermission() )
		{
			FileManagement.getInstance().terminal.success=false;
			FileManagement.getInstance().terminal.addLabel("<User has no read permission!");
			return null;
		}
		
		if( cmd.compareTo("ls")==0 ) return new CommandLs();
		if( cmd.compareTo("cd")==0 ) return new CommandCd();
		if( cmd.compareTo("cat")==0 ) return new CommandCat();
		if( cmd.compareTo("pwd")==0 ) return new CommandPwd();
		if( cmd.compareTo("upload")==0 ) return new CommandUpload();
		
		
		return null;
	}
}
