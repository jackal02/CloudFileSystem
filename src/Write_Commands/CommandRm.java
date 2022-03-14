package Write_Commands;

import Command_Management.WriteCommand;
import Main.Directory;
import Main.File;
import Main.FileManagement;
import Main.Repository;

public class CommandRm extends WriteCommand{
	public void execute(Repository rep){
		rep.accept(this);
	}
	
	public void execute(Directory dir){
		
		FileManagement Fm=FileManagement.getInstance();
		
		if( dir.getSONS().size()!=0 && !Fm.isRecursive )
		{ 
			Fm.terminal.success=false;
			Fm.terminal.addLabel("<Cannot be used on a non-empty directory!");
			return;
		}
		
		if(dir==Fm.RootDir)
		{
			Fm.terminal.success=false;
			Fm.terminal.addLabel("<Cannot delete root folder!");
			return;
		}
		
		if( !Fm.isRecursive )
		  ((Directory)dir.findFather()).eraseSon(dir.getName());
		else
		  Fm.removeFileSystem(dir);
		
		((Directory)dir.findFather()).updateSize();
		
		Fm.removeNameInSet(dir.getName());
	}
	
	public void execute(File file)
	{
		FileManagement Fm=FileManagement.getInstance();
		
		((Directory)file.findFather()).eraseSon(file.getName());
		((Directory)file.findFather()).updateSize();
		
		Fm.removeNameInSet(file.getName());
	}
}
