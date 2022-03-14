package Write_Commands;

import Command_Management.WriteCommand;
import Main.Directory;
import Main.FileManagement;
import Main.Repository;

public class CommandMkdir extends WriteCommand{
	
	public void execute(Repository rep){
		rep.accept(this);
	}
	
	public void execute(Directory dir){
		
		FileManagement Fm=FileManagement.getInstance();
		
		if( Fm.containsName(Fm.currentName) )
		{
			Fm.terminal.success=false;
			Fm.terminal.addLabel("A directory with the same name already exists!");
		}
		else
		{
			Directory currentDir=new Directory(Fm.currentName, false);
			dir.addSon(currentDir);
			dir.updateSize();
			
			Fm.addNameInSet(currentDir.getName());
		}
	}
}
