package Command_Management;

import Main.Directory;
import Main.File;
import Main.Repository;

public interface Command {
	
	public void execute(Repository rep);
	public void execute(Directory dir);
	public void execute(File file);
}
