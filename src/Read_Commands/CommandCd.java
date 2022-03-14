package Read_Commands;

import Command_Management.ReadCommand;
import Main.Directory;
import Main.File;
import Main.FileManagement;
import Main.Repository;

public class CommandCd extends ReadCommand{
	public void execute(Repository rep){
		rep.accept(this);
	}
	
	public void execute(Directory dir){
		FileManagement.getInstance().CurrentDir=dir;
	}
	
	public void execute(File file){
		FileManagement.getInstance().terminal.success=false;
		FileManagement.getInstance().terminal.addLabel("Cannot be used on a file!");
		FileManagement.getInstance().terminal.addLabel("Redirecting to parent folder...");
		
		FileManagement.getInstance().CurrentDir=(Directory)file.findFather();
	}
}
