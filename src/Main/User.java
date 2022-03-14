package Main;
import java.util.Date;

public class User {
	
	String Username, Passowrd;
	String FirstName, SecondName;
	Date Registration, LastLogin;
	
	public User(String Username, String Password, String FirstName, String SecondName)
	{
		this.Username=new String(Username);
		this.Passowrd=new String(Password);
		
		this.FirstName=new String(FirstName);
		this.SecondName=new String(SecondName);
		
		this.Registration=new Date();
		this.LastLogin=new Date();
	}
	
}
