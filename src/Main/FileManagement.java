package Main;

import java.util.HashSet;
import java.util.Vector;

public class FileManagement {
	
	public static final FileManagement INSTANCE=new FileManagement();
	
	public Main terminal;
	public Directory RootDir, CurrentDir; 
	
	public String currentName;
	public int currentSize;
	public boolean isRecursive, isDetailed, isPOO;
	public boolean isText, hasPerm, pRead, pWrite;
	
	public Vector< Vector<String> > rows;
	public Vector<String> columnNames;
	
	HashSet<String> diffNames;
	
	
	private FileManagement(){	
	
		this.hasPerm=false;
		this.RootDir=new Directory("Root", true);
		this.CurrentDir=this.RootDir;
		
		this.diffNames=new HashSet<String>();
		this.diffNames.add("Root");
	}

	public static final FileManagement getInstance(){
		return INSTANCE;
	}
	
	public void userUpdate(Repository rep)
	{
		rep.addPermission();
		rep.iterateSonsForUpdate();
	}
	
	public void removeFileSystem(Repository rep)
	{
		removeNameInSet(rep.getName());
		
		Vector<Repository> vect=rep.getSONS();
		if( vect==null ){
			((Directory)rep.findFather()).eraseSon( rep.getName() );
			return;
		}
		
		for(int i=0;i<vect.size();i++)
		 removeFileSystem(vect.get(i));
		
		((Directory)rep.findFather()).eraseSon( rep.getName() );
	}
	
	public void printTree(Repository rep,String step)
	{
		if( !isPOO )
		 this.terminal.addLabel(step+"-"+rep.toString());
		else
		{
			 rows.add(new Vector<String>());
			 rows.get( rows.size()-1 ).add(step+"-"+rep.getName());
			 
			 if( isDetailed ){
				 rows.get( rows.size()-1 ).add(rep.getSize()+"");
				 rows.get( rows.size()-1 ).add(rep.getDate()+"");
			 }
		}
		rep.iterateSonsForPrint(step+"|");
	} 
	
	public void addNameInSet(String Name){
		this.diffNames.add(Name);
	}
	
	public void removeNameInSet(String Name){
		this.diffNames.remove(Name);
	}
	
	public boolean containsName(String Name){
		return this.diffNames.contains(Name);
	}
}
