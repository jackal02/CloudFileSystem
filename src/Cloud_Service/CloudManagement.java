package Cloud_Service;

import java.util.Vector;
import Main.Repository;

public class CloudManagement {
	
public static final CloudManagement INSTANCE=new CloudManagement();
	
	public int freeSpace;
	public CloudStation cloudStation[]; 
	
	private CloudManagement(){
		
		this.cloudStation=new CloudStation[3];
		
		for(int i=0;i<3;i++)
		 cloudStation[i]=new CloudStation();
		
		this.freeSpace=30*1024;
	}
	
	//Space management ----------------------------------------
	
	public boolean cloudHasSpace(int space){
		return (boolean)(this.freeSpace>=space);
	}
	
	public void updateSpace()
	{
		this.freeSpace=0;
		for(int i=0;i<3;i++)
		 this.freeSpace+=this.cloudStation[i].freeSpace;
	}
	//---------------------------------------------------------
	
	//Station of a repository operations ----------------------
	public CloudStation getRepositoryStation(Repository rep)
	{
		for(int i=0;i<3;i++)
		 if( this.cloudStation[i].containsRepository(rep) )
			 return this.cloudStation[i];
		 
		return null;
	}
	
	public CloudStation getRepositoryStation(String name)
	{
		for(int i=0;i<3;i++)
		 if( this.cloudStation[i].containsRepository(name) )
			 return this.cloudStation[i];
		 
		return null;
	}
	
	public CloudStation getFreeStation(Repository rep)
	{
		for(int i=0;i<3;i++)
			 if( this.cloudStation[i].stationHasSpace( rep.getItemSize() ) )
				 return this.cloudStation[i];
		
		return null;
	}
	//-------------------------------------------------------
	
	
	//Clone, store & restore a file System ------------------
	public void cloneFileSystem(Repository rep)
	{
		Vector<Repository> vect=rep.getSONS();
		if(vect==null) return;
		
		for(int i=0;i<vect.size();i++)
		{
			Repository newson=vect.get(i).clone();
			newson.setFather(rep);
			
			vect.set(i, newson);
			cloneFileSystem(newson);
		}
	}
	
	public void stationStore(Repository rep, CloudStation station)
	{
		CloudStation current=getRepositoryStation(rep);
		
		if( current!=station && current!=null){
			stationStore(rep, current);
			return;
		}
		
		if( current==station ) station.removeRepository(rep);
		station.addRepository(rep);
		
		Vector<Repository> vect=rep.getSONS();
		if(vect==null) return;
		
		for(int i=0;i<vect.size();i++)
		{
			Repository son=vect.get(i);
			
			current=getRepositoryStation(son);
			if(current==null)
			{
				if( station.stationHasSpace( son.getItemSize() ))
					stationStore(son, station);
				else
				{
					current=getFreeStation(son);
					stationStore(son, current);
					vect.set(i, new MachineId(current, son.getName()) );
				}
			}
			else
			{
				stationStore(son, current);
				vect.set(i, new MachineId(current, son.getName()) );
			}
		}
	}
	
	public void restoreFileSystem(Repository rep)
	{
		rep.addRemainingUserPerm();
		
		Vector<Repository> vect=rep.getSONS();
		if(vect==null) return;
		
		for(int i=0;i<vect.size();i++)
		{
			Repository son=vect.get(i), newson;
			
			if( son instanceof MachineId ){
				CloudStation current=((MachineId)son).station;
				newson=current.getRepository( son.getName() );	
			}
			else
			  newson=son.clone();
			
			newson.setFather(rep);
			vect.set(i, newson);
			
			restoreFileSystem(newson);
		}
	}
	//------------------------------------------------
	
	public static final CloudManagement getInstance(){
		return INSTANCE;
	}
}
