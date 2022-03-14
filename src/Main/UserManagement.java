package Main;

import java.util.Date;
import java.util.Vector;

public class UserManagement {
	
	public static final UserManagement INSTANCE=new UserManagement();
	
	Vector<User> USERS;
	public User LoggedUser=null;
	
	private UserManagement(){
		
		USERS=new Vector<User>();
		USERS.add(new User("root","rootpass","Admin","Admin"));
		USERS.add(new User("guest","guestpass","Guest","Guest"));
		
		LoggedUser=USERS.get(1);
	}
	
	public static final UserManagement getInstance(){
		return INSTANCE;
	}
	
	private int contains(String Name)
	{
		for(int i=0;i<USERS.size();i++)
		 if( USERS.get(i).Username.compareTo(Name)==0 )
			 return i;
		
		return -1;
	}
	
	public void newUser(String UserName, String Password, String FirstName, String SecondName)
	{
		if( contains(UserName)!=-1 )
		{
			FileManagement.getInstance().terminal.success=false;
			FileManagement.getInstance().terminal.addLabel("<Username already exists!");
			return;
		}
		
		User newUser=new User(UserName, Password, FirstName, SecondName);
		USERS.add(newUser);
	
		FileManagement Fm=FileManagement.getInstance();
		Fm.userUpdate(Fm.RootDir);
		
		Fm.RootDir.PERM.remove(Fm.RootDir.PERM.size()-1);
		Fm.RootDir.PERM.add(new Permission(true, true));
	}
	
	public void loginUser(String UserName, String Password)
	{
		if( this.LoggedUser.Username.compareTo("guest")!=0 )
		{
			FileManagement.getInstance().terminal.success=false;
			FileManagement.getInstance().terminal.addLabel("<Please log out first!");
			return;
		}
		
		int Index=contains(UserName);
		if( Index==-1 )
		{
			FileManagement.getInstance().terminal.success=false;
			FileManagement.getInstance().terminal.addLabel("<No user found!");
			return;
		}
		
		if( USERS.get(Index).Passowrd.compareTo(Password)!=0 )
		{
			FileManagement.getInstance().terminal.success=false;
			FileManagement.getInstance().terminal.addLabel("<Wrong password!");
			return;
		}
		
		LoggedUser=USERS.get( Index );
		LoggedUser.LastLogin=new Date();
		FileManagement.getInstance().terminal.addLabel("<Redirecting to root folder...");
		FileManagement.getInstance().CurrentDir=FileManagement.getInstance().RootDir;
	}
	
	public void logoutUser()
	{
		if( LoggedUser.Username.compareTo("guest")==0 )
		{
			FileManagement.getInstance().terminal.success=false;
			FileManagement.getInstance().terminal.addLabel("<No user logged in!");
			return;
		}
		LoggedUser=USERS.get(1);
		FileManagement.getInstance().terminal.addLabel("<Redirecting to root folder...");
		FileManagement.getInstance().CurrentDir=FileManagement.getInstance().RootDir;
	}
	
	public void infoUser(String UserName, boolean isPOO)
	{
		int Index=contains(UserName);
		if( Index==-1 ) 
		{
			FileManagement.getInstance().terminal.success=false;
			FileManagement.getInstance().terminal.addLabel("<No user found!");
			return;
		}
		
		User Cnt=USERS.get(Index);
		
		if( !isPOO )
		{
		FileManagement.getInstance().terminal.addLabel("USER: "+Cnt.Username+" | FirstName: "
		+Cnt.FirstName+" | SecondName: "+Cnt.SecondName);
		
		FileManagement.getInstance().terminal.addLabel("Creation: "+Cnt.Registration+
		" | LastLogged: "+Cnt.LastLogin);
		}
		else
		{
			Vector<String> vect=new Vector<String>();
			vect.add("USER: "+Cnt.Username);
			vect.add("FirstName: "+Cnt.FirstName);
			vect.add("SecondName: "+Cnt.SecondName);
			vect.add("Creation: "+Cnt.Registration);
			vect.add("LastLogged: "+Cnt.LastLogin);
			
			FileManagement.getInstance().terminal.addList(vect);
		}
	}
	
	public void echo(String message){
		FileManagement.getInstance().terminal.addLabel(message);
	}
}
