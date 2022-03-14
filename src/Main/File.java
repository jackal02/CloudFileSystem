package Main;
import java.util.Date;
import java.util.Vector;

import Command_Management.Command;

public class File implements Repository{
	
	int Size;
	String Name;
	boolean Type;
	byte[] Content;
	
	Date CreationDate;
	Vector<Permission> PERM;
	
	Directory Father;
	
	public File(){
	}
	
	public File(String Name, int Size,boolean Type)
	{
		this.Name=new String(Name);
		this.Size=Size;
		this.Type=Type;
		
		this.Content=RandomBytes.getRandomContent(Size);
		this.CreationDate=new Date();
		
		this.fillPermission();
		this.Father=FileManagement.getInstance().CurrentDir;
	}
	
	public String getFullPath()
	{
		String result=new String();
		
		result="/"+this.Name+result;
		Directory cnt=this.Father;
		
		while(cnt!=null)
		{
			result="/"+cnt.Name+result;
			cnt=(Directory)cnt.findFather();
		}
		return result;
	}
	
	@Override
	public void addPermission() {
		this.PERM.add(new Permission(false,false));
	}
	
	public void fillPermission()
	{
		UserManagement Um=UserManagement.getInstance();
		FileManagement Fm=FileManagement.getInstance();
		
		this.PERM=new Vector<Permission>( UserManagement.getInstance().USERS.size() );
		this.PERM.add(0, new Permission(true, true));
		this.PERM.add(1, new Permission(false, false));
		
		if(Fm.hasPerm){
		 for(int i=2;i<this.PERM.size();i++)
		  if( Um.USERS.get(i).equals(Um.LoggedUser) )
			 this.PERM.add(i, new Permission(Fm.pRead, Fm.pWrite));
		  else
			 this.PERM.add(i, new Permission(false, false));
		}
		else
		{
			for(int i=2;i<this.PERM.size();i++)
			 this.PERM.add(i, new Permission(true, true));
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
	
	
	public Repository findSon(String Name){
		return null;
	}
	
	public Repository findFather(){
		return this.Father;
	}
	
	@Override
	public void iterateSonsForUpdate() {
		return;
	}

	@Override
	public void iterateSonsForPrint(String step) {
		return;
	}
	
	public String getName(){
		return this.Name;
	}
	
	public int getSize(){
		return this.Size;
	}
	
	public void showContent()
	{
		String labelStr="";
		
		FileManagement Fm=FileManagement.getInstance();
		for(int i=0;i<this.Size;i+=Fm.terminal.panel.getWidth()/8)
		{
			for(int j=i;j<this.Size && j<=i+Fm.terminal.panel.getWidth()/8;j++)
				labelStr=labelStr+(char)this.Content[j];
			
			Fm.terminal.addLabel(labelStr);
			labelStr="";
		}
		
	}
	
	public String toString(){
		if( FileManagement.getInstance().isDetailed )
			return "Name: "+this.Name+" | Size: "+this.Size+" | Date: "+this.CreationDate;
		else 
			return this.Name;
	}
	
	@SuppressWarnings("unchecked")
	public File clone()
	{
		File file=new File();
		file.Size=this.Size;
		file.Type=this.Type;
		file.Name=this.Name;
		file.CreationDate=this.CreationDate;
		file.Father=this.Father;
		file.PERM=(Vector<Permission>)this.PERM.clone();
		file.Content=this.Content.clone();
		
		return file;
	}
	
	@Override
	public void accept(Command c) {
		c.execute(this);
	}

	@Override
	public Vector<Repository> getSONS() {
		return null;
	}

	@Override
	public int getItemSize() {
		return this.Size;
	}
	
	public void setFather(Repository rep) {
		this.Father=(Directory)rep;
	}

	@Override
	public void setSon(Repository rep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getDate() {
		return this.CreationDate;
	}
}
