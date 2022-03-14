package Cloud_Service;

import Command_Management.ReadCommand;
import Main.Directory;
import Main.File;
import Main.FileManagement;
import Main.Repository;

public class CommandUpload extends ReadCommand{
	
	public void execute(Repository rep){
		rep.accept(this);
	}
	
	public void execute(Directory dir){
		CloudService.update(dir);
	}
	
	public void execute(File file){
		FileManagement.getInstance().terminal.success=false;
		FileManagement.getInstance().terminal.addLabel("Cannot be used on a file!");
	}
}
