package Main;

import java.util.Date;
import java.util.Vector;

import Command_Management.Command;

public interface Repository {
	
	public void accept(Command c);
	
	public void addPermission();
	public void fillPermission();
	
	public boolean hasReadPermission();
	public boolean hasWritePermission();
	
	public String toString();
	public String getName();
	public int getSize();
	public void iterateSonsForUpdate();
	public void iterateSonsForPrint(String step);
	public Repository findSon(String Name);
	public Repository findFather();
	
	public void addRemainingUserPerm();
	public Repository clone();
	public String getFullPath();
	public Vector<Repository> getSONS();
	
	public int getItemSize();
	public void setFather(Repository rep);
	public void setSon(Repository rep);
	public Date getDate();
}
