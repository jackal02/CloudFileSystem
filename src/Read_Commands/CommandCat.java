package Read_Commands;

import Command_Management.ReadCommand;
import Main.Directory;
import Main.File;
import Main.FileManagement;
import Main.Repository;

public class CommandCat extends ReadCommand{
	
	public void execute(Repository rep){
		rep.accept(this);
	}
	
	public void execute(Directory dir){
		FileManagement.getInstance().terminal.success=false;
		FileManagement.getInstance().terminal.addLabel("Cannot be used on a directory!");
	}
	
	public void execute(File file){
		file.showContent();
	}
}
