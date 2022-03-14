package Cloud_Service;

import java.util.HashSet;
import Main.Repository;

public abstract class StoreStation {
	
	int freeSpace;
	HashSet<Repository> container;
	
	public boolean stationHasSpace(int space){
		return (boolean)(this.freeSpace>=space);
	}
	
	public void addRepository(Repository rep)
	{
		this.container.add(rep);
		this.freeSpace-=rep.getItemSize();
		
		CloudManagement.getInstance().updateSpace();
	}
	
	public boolean containsRepository(Repository rep){
		
		for( Repository iter:this.container )
			 if( iter.getName().compareTo(rep.getName())==0 )
				 return true;
			
			return false;
	}
	
	public boolean containsRepository(String name){
		
		for( Repository iter:this.container )
			 if( iter.getName().compareTo(name)==0 )
				 return true;
			
			return false;
	}
	
	public Repository getRepository(Repository rep){
		
		for( Repository iter:this.container )
		 if( iter.getName().compareTo(rep.getName())==0 )
			 return iter;
		
		return null;
	}
	
	public Repository getRepository(String name){
		
		for( Repository iter:this.container )
		 if( iter.getName().compareTo(name)==0 )
			 return iter;
		
		return null;
	}
	
	public void removeRepository(Repository rep)
	{
		for( Repository iter:this.container )
			 if( iter.getName().compareTo(rep.getName())==0 )
			 {
				 this.container.remove(iter);
				 this.freeSpace+=iter.getItemSize();
				 CloudManagement.getInstance().updateSpace();
				 return;
			 }
	}
}
