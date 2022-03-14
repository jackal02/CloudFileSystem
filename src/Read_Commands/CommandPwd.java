package Read_Commands;

import Command_Management.ReadCommand;
import Main.Directory;
import Main.FileManagement;
import Main.Repository;

public class CommandPwd extends ReadCommand{
	public void execute(Repository rep){
		rep.accept(this);
	}
	
	public void execute(Directory dir){
		FileManagement.getInstance().terminal.addLabel( dir.getFullPath() );
	}
}
