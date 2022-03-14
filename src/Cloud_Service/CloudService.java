package Cloud_Service;

import Main.Directory;
import Main.FileManagement;

public abstract class CloudService {

	public static void update(Directory dir)
	{
		CloudManagement Cm=CloudManagement.getInstance();
		FileManagement Fm=FileManagement.getInstance();
		
		if( !Cm.cloudHasSpace(dir.getSize()) )
		{
			Fm.terminal.success=false;
			Fm.terminal.addLabel("Not enough space on the cloud server!");
			Fm.terminal.addLabel("FileSystemSize: "+dir.getSize()+" | "+"CloudFreeSpace: "+Cm.freeSpace);
			return;
		}
		
		//Clone the file system
		Directory newdir=(Directory)dir.clone();
		Cm.cloneFileSystem(newdir);
		
		//Store the file system
		Cm.stationStore(newdir, Cm.getFreeStation(newdir));	
	}
	
	public static void sync(Directory dir)
	{
		CloudManagement Cm=CloudManagement.getInstance();
		FileManagement Fm=FileManagement.getInstance();
		
		boolean directoryFound=false;
		for(int i=0;i<3;i++)
		 if( Cm.cloudStation[i].containsRepository(dir) )
			 directoryFound=true;
		
		if( !directoryFound ){
			Fm.terminal.success=false;
			Fm.terminal.addLabel("Directory not found!");
			return;
		}
		
		//Get the station of the directory and the directory itself 
		CloudStation station=Cm.getRepositoryStation(dir);
		Directory cntdir=(Directory)station.getRepository(dir);
		
		//Clone the directory and restore the file system
		Directory newdir=(Directory)cntdir.clone();		
		Cm.restoreFileSystem(newdir);
		
		newdir.findFather().setSon(newdir);
		
		if(Fm.CurrentDir==dir) Fm.CurrentDir=newdir;
		else Fm.CurrentDir=Fm.RootDir;
	}
}
