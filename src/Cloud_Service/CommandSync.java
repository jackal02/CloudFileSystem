package Cloud_Service;

import Command_Management.Command;
import Command_Management.WriteCommand;
import Main.Directory;
import Main.File;
import Main.FileManagement;
import Main.Repository;

public class CommandSync extends WriteCommand implements Command{

	public void execute(Repository rep){
		rep.accept(this);
	}
	
	public void execute(Directory dir){
		CloudService.sync(dir);
		((Directory)dir.findFather()).updateSize();
	}
	
	public void execute(File file){
		FileManagement.getInstance().terminal.success=false;
		FileManagement.getInstance().terminal.addLabel("Cannot be used on a file!");
	}
}
