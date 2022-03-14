package Main;
import java.util.Date;
import java.util.Vector;

import Command_Management.Command;

public class Directory implements Repository{
	
	int Size;
	String Name;
	Date CreationDate;
	
	Vector<Repository> SONS;
	Vector<String> NAMES;
	Vector<Permission> PERM;
	
	Directory Father;
	
	public Directory(){
	}
	
	public Directory(String Name, boolean isBase)
	{
		this.Name=new String(Name);
		this.Size=0;
		
		this.SONS=new Vector<Repository>();
		this.NAMES=new Vector<String>();
		this.CreationDate=new Date();
		
		if( isBase )
		{
			this.PERM=new Vector<Permission>();
			this.PERM.add(new Permission(true, true));
			this.PERM.add(new Permission(false, false));
			this.Father=null;
			
			return;
		}
		
		this.fillPermission();
		this.Father=FileManagement.getInstance().CurrentDir;
	}
	
	public String getFullPath()
	{
		String result=new String();
		Directory cnt=this;
		
		while(cnt!=null)
		{
			result="/"+cnt.Name+result;
			cnt=(Directory)cnt.findFather();
		}
		return result;
	}
	
	public void eraseSon(String Name)
	{
		for(int i=0;i<this.SONS.size();i++)
		 if( this.SONS.get(i).getName().compareTo(Name)==0 )
		 {
			 this.SONS.remove(i);
			 this.NAMES.remove(i);
			 return;
		 }
	}
	
	@Override
	public void addPermission() {
		this.PERM.add(new Permission(false, false));	
	}
	
	public void fillPermission()
	{
		UserManagement Um=UserManagement.getInstance();
		FileManagement Fm=FileManagement.getInstance();
		
		this.PERM=new Vector<Permission>();
		this.PERM.add(new Permission(true, true));
		this.PERM.add(new Permission(false, false));
		
		if(Fm.hasPerm){
		 for(int i=2;i<Um.USERS.size();i++)
		  if( Um.USERS.get(i).equals(Um.LoggedUser) )
			 this.PERM.add(new Permission(Fm.pRead, Fm.pWrite));
		  else
			 this.PERM.add(new Permission(false, false));
		}
		else
		{
			for(int i=2;i<Um.USERS.size();i++)
			 this.PERM.add(new Permission(true, true));
		}
	}
	
	public void addRemainingUserPerm()
	{
		UserManagement Um=UserManagement.getInstance();
		
		while(this.PERM.size()<Um.USERS.size())
		 this.PERM.add( new Permission(false,false) );
	}
	
	@Override
	public boolean hasReadPermission() {
		UserManagement Um=UserManagement.getInstance();
		
		User Current=Um.LoggedUser;
		return this.PERM.get( Um.USERS.indexOf(Current) ).Read;
	}

	@Override
	public boolean hasWritePermission() {
		UserManagement Um=UserManagement.getInstance();
		
		User Current=Um.LoggedUser;
		return this.PERM.get( Um.USERS.indexOf(Current) ).Write;
	}
	
	public void addSon(Repository rep)
	{
		this.SONS.add(rep);
		this.NAMES.add(FileManagement.getInstance().currentName);
	}
	
	public void printSons()
	{ 	
		FileManagement Fm=FileManagement.getInstance();
		
		for(int i=0;i<this.SONS.size();i++)
		 if( !Fm.isPOO )
			FileManagement.getInstance().terminal.addLabel( this.SONS.get(i).toString());
		 else
		 {
			 Fm.rows.add(new Vector<String>());
			 Fm.rows.get( Fm.rows.size()-1 ).add(this.SONS.get(i).getName());
			 
			 if( Fm.isDetailed ){
				 Fm.rows.get( Fm.rows.size()-1 ).add(this.SONS.get(i).getSize()+"");
				 Fm.rows.get( Fm.rows.size()-1 ).add(this.SONS.get(i).getDate()+"");
			 }
		 }
	}
	
	@Override
	public void iterateSonsForUpdate() {
		for(int i=0;i<this.SONS.size();i++)
		 FileManagement.getInstance().userUpdate(this.SONS.get(i));
	}
	
	@Override
	public void iterateSonsForPrint(String step) {
		for(int i=0;i<this.SONS.size();i++)
		{
			FileManagement.getInstance().printTree(this.SONS.get(i), step+"  ");
		}
	}
	
	public Repository findSon(String Name)
	{
		for(int i=0;i<this.SONS.size();i++)
			 if( this.NAMES.get(i).compareTo(Name)==0 )
				 return this.SONS.get(i);
		return null;
	}
	
	public void setSon(Repository rep)
	{
		for(int i=0;i<this.SONS.size();i++)
			 if( this.NAMES.get(i).compareTo( rep.getName() )==0 )
				 this.SONS.set(i, rep);
	}
	
	public Repository findFather(){
		return this.Father;
	}
	
	public void updateSize()
	{
		this.Size=0;
		for(int i=0;i<this.SONS.size();i++)
			this.Size+=this.SONS.get(i).getSize();
		
		if( this.Father!=null ) Father.updateSize();
	}
	
	public String getName(){
		return this.Name;
	}
	
	public int getSize(){
		return this.Size;
	}
	
	public Vector<Repository> getSONS()	{
		return this.SONS;
	}
	
	public String toString()
	{
		if( FileManagement.getInstance().isDetailed )
			return "Name: "+this.Name+" | Size: "+this.Size+" | Date: "+this.CreationDate;
		else 
			return this.Name;
	}
	
	@SuppressWarnings("unchecked")
	public Repository clone()
	{
		Directory dir=new Directory();
		dir.Size=this.Size;
		dir.Name=this.Name;
		dir.CreationDate=this.CreationDate;
		dir.Father=this.Father;
		dir.NAMES=(Vector<String>)this.NAMES.clone();
		dir.PERM=(Vector<Permission>)this.PERM.clone();
		dir.SONS=(Vector<Repository>)this.SONS.clone();
		
		return dir;
	}
	
	@Override
	public void accept(Command c) {
		c.execute(this);
	}

	@Override
	public int getItemSize() {
		return 0;
	}

	@Override
	public void setFather(Repository rep) {
		this.Father=(Directory)rep;
	}

	@Override
	public Date getDate() {
		return this.CreationDate;
	}
	
} 
