package Write_Commands;

import Command_Management.WriteCommand;
import Main.Directory;
import Main.File;
import Main.FileManagement;
import Main.Repository;

public class CommandTouch extends WriteCommand{
	public void execute(Repository rep){
		rep.accept(this);
	}
	
	public void execute(Directory dir){
		
		FileManagement Fm=FileManagement.getInstance();
		
		if( Fm.containsName(Fm.currentName) ){
			Fm.terminal.success=false;
			Fm.terminal.addLabel("A file with the same name already exists!");
		}
		else
		{
			File currentFile=new File(Fm.currentName, Fm.currentSize, Fm.isText);
			dir.addSon(currentFile);
			dir.updateSize();
			
			Fm.addNameInSet(currentFile.getName());
		}
	}
}
