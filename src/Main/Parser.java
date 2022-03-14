package Main;

import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JDialog;

import Command_Management.Command;
import Command_Management.CommandFactory;

public class Parser {

	static Vector<String> userCMD, rwCMD;
	static String usr, pass, name, path, norepname, repname, dirname, arg, perm; 
	
	static UserManagement Um=UserManagement.getInstance();
	static FileManagement Fm=FileManagement.getInstance();
	
	StringTokenizer st;
	String CMD, CONTENT;
	
	static
	{
		userCMD=new Vector<String>();
		userCMD.add("echo");
		userCMD.add("newuser");
		userCMD.add("userinfo");
		userCMD.add("logout");
		userCMD.add("login");
		
		rwCMD=new Vector<String>();
		rwCMD.add("ls");
		rwCMD.add("cd");
		rwCMD.add("cat");
		rwCMD.add("pwd");
		rwCMD.add("upload");
		rwCMD.add("mkdir");
		rwCMD.add("touch");
		rwCMD.add("rm");
		rwCMD.add("sync");
		
		usr=new String("[a-zA-Z][a-zA-Z0-9]*");
		pass=new String("[^ ][^ ]*");
		name=new String("[a-zA-Z]*");
		
		norepname=new String("[a-zA-Z0-9]*(\\.[a-z]{3})?");
		repname=new String("[a-zA-Z0-9][a-zA-Z0-9]*(\\.[a-z0-9]{3})?");
		dirname=new String("[a-zA-Z0-9][a-zA-Z0-9]*");
		path=new String("((\\./)?(\\.\\./?)*/?[a-zA-Z0-9]*(\\.[a-z]{3})?)*");
		arg=new String("(\\-r?a? ?)*");
		perm=new String("( \\-r?w?\\-)?");
	}
	
	public Parser(String content)
	{
		CMD=new String();
		CONTENT=new String(content);
		st=new StringTokenizer(CONTENT," ");
		
		Fm.hasPerm=Fm.isRecursive=Fm.isDetailed=false;
	}
	
	public void parseCommand()
	{
		if(st.hasMoreTokens()==false) {
			Fm.terminal.success=false;
			Fm.terminal.addLabel("<Incorect format!");
			return;
		}
		
		CMD=st.nextToken();
		if( userCMD.contains(CMD) ) parseUserCommand();
		else
		 if( rwCMD.contains(CMD) ) parseRwCommand();
		 else
		 {
			 Fm.terminal.success=false;
			 Fm.terminal.addLabel("<Incorect command!");
		 }
	}
	

	private void parseUserCommand()
	{
		String aux;
		if( CMD.compareTo("echo")==0 )
		{
			if( CONTENT.matches("echo \\-POO .*") )
				Fm.terminal.addDialog( CONTENT.substring(10) );
			else 
			 if( CONTENT.matches("echo .*") )
				 Um.echo( CONTENT.substring(5) );
			 else
			 {
				Fm.terminal.success=false;
				Fm.terminal.addLabel("<Incorect format!");
			 }
			
			return;
		}
		
		if( CMD.compareTo("newuser")==0 )
		{
			if( CONTENT.matches("newuser "+usr+" "+pass+" "+name+" "+name) )
				Um.newUser(st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken());
			else
			{
				Fm.terminal.success=false;
				Fm.terminal.addLabel("<Incorect format!");
			}
			
			return;
		}
		
		if( CMD.compareTo("userinfo")==0 )
		{
			if( CONTENT.matches("userinfo \\-POO "+usr) )
			{
				st.nextToken();
				Um.infoUser(st.nextToken(), true);
			}
			else
			if( CONTENT.matches("userinfo "+usr) )
				Um.infoUser(st.nextToken(), false);
			else
			{
				Fm.terminal.success=false;
				Fm.terminal.addLabel("<Incorect format!");
			}
		
			return;
		}
		
		if( CMD.compareTo("logout")==0 )
		{
			if( CONTENT.matches("logout") )
				Um.logoutUser();
			else
			{	
				Fm.terminal.success=false;
				Fm.terminal.addLabel("<Incorect format!");
			}
			
			return;
		}
		
		if( CMD.compareTo("login")==0 )
		{
			if( CONTENT.matches("login "+usr+" "+pass) )
				Um.loginUser(st.nextToken(), st.nextToken());
			else
			{
				Fm.terminal.success=false;
				Fm.terminal.addLabel("<Incorect format!");
			}
			
			return;
		}
		
	}
	
	private void tryCommand(Command c, Repository rep)
	{
		if(c==null) {
			return;
		}
		
		c.execute(rep);
	}
	
	private Repository getRepository(String path, boolean isName)
	{
		Repository rep;
		
		if( isName && path.charAt(0)!='/' && path.charAt(0)!='.' )
			return Fm.CurrentDir.findSon(path);
		
		if( path.compareTo("./")==0 ) return null;
		if( path.compareTo("..")==0 ) return Fm.CurrentDir.findFather();
		
		if( path.substring(0,5).compareTo("/Root")==0 )
		{
			rep=Fm.RootDir;
			if(path.length()>5) path=path.substring(6);
			else return rep;
		}
		else
		 if( path.substring(0,2).compareTo("./")==0 || path.substring(0,3).compareTo("../")==0 )
		 {
			 rep=Fm.CurrentDir;
			 if(path.substring(0,2).compareTo("./")==0)
				 path=path.substring(2);
		 }
		 else return null;
		
		StringTokenizer auxSt=new StringTokenizer(path,"/");
		String auxStr=new String();
		
		while( auxSt.hasMoreTokens() )
		{
			auxStr=auxSt.nextToken("/");
	
			if( auxStr.compareTo("..")==0 ) 
			  rep=rep.findFather();
			else
			  rep=rep.findSon(auxStr);
			
			if( rep==null ) return null;
		}
		
		return rep;
	}
	
	private void parsePermission(String str)
	{
		Fm.hasPerm=true; Fm.pRead=false; Fm.pWrite=false;
		for(int i=1;i<str.length();i++)
		{
			if( str.charAt(i)=='r' ) Fm.pRead=true;
			if( str.charAt(i)=='w' ) Fm.pWrite=true;
		}	
	}
	
	private void parseParams(String str)
	{	
		Fm.isPOO=false;
		for(int i=1;i<str.length();i++)
		{
			if( str.charAt(i)=='r' ) Fm.isRecursive=true;
			if( str.charAt(i)=='a' ) Fm.isDetailed=true;
			if( str.charAt(i)=='P') Fm.isPOO=true;
		}
	}
	
	private void parseRwCommand()
	{
		Command c;
		Repository rep;
		String aux;
		
		if( CMD.compareTo("mkdir")==0 )
		{
			if( CONTENT.matches("mkdir "+dirname+perm) ){
				
				rep=Fm.CurrentDir;
				c=CommandFactory.getCommand(CMD, rep);
				
				Fm.currentName=st.nextToken();
				if(!st.hasMoreTokens()) Fm.hasPerm=false;
				else parsePermission(st.nextToken());
				
				this.tryCommand(c, rep);
			}
			else
			{
				Fm.terminal.success=false;
				Fm.terminal.addLabel("<Incorect format!");
			}
			
			return;
		}
	
		if( CMD.compareTo("touch")==0 )
		{
			if( CONTENT.matches("touch "+repname+" [0-9]*"+perm) ){
				
				rep=Fm.CurrentDir;
				c=CommandFactory.getCommand(CMD, rep);
				
				Fm.currentName=st.nextToken();
				Fm.currentSize=Integer.parseInt(st.nextToken());
				
				if(!st.hasMoreTokens()) Fm.hasPerm=false;
				else parsePermission(st.nextToken());
				
				this.tryCommand(c, rep);
			}
			else
			{
				Fm.terminal.success=false;
				Fm.terminal.addLabel("<Incorect format!");
			}
			
			return;
		}
		
		if( CMD.compareTo("rm")==0 )
		{
			if( CONTENT.matches("rm "+"(\\-r )?"+repname) || CONTENT.matches("rm "+"(\\-r )?"+path) ){
				
				aux=st.nextToken();
				if( aux.charAt(0)=='-') 
				{
					parseParams(aux);
					rep=getRepository(st.nextToken(), true);
				}
				else rep=getRepository(aux, true);
				
				if( rep==null ) {
					Fm.terminal.success=false;
					Fm.terminal.addLabel("<No repository with that name found!");
					return;
				}
				
				c=CommandFactory.getCommand(CMD, rep);
				this.tryCommand(c, rep);
			}
			else
			{
				Fm.terminal.success=false;
				Fm.terminal.addLabel("<Incorect format!");
			}
			
			return;
		}
		
		if( CMD.compareTo("ls")==0 )
		{
			if( CONTENT.matches("ls "+arg+"(\\-POO )?"+norepname) 
					|| CONTENT.matches("ls "+arg+"(\\-POO )?"+path)
					|| CONTENT.compareTo("ls")==0){
				
				if( CONTENT.compareTo("ls")==0 ) rep=Fm.CurrentDir;
				else
				{
					if( !st.hasMoreTokens() ) rep=Fm.CurrentDir;
					else
					{
						aux=st.nextToken();
						while( aux.charAt(0)=='-' )
						{
							parseParams(aux);
							if( !st.hasMoreTokens() ) break;
							
							aux=st.nextToken();
						}
						
						if( aux.charAt(0)=='-' ) rep=Fm.CurrentDir;
						else
						 rep=getRepository(aux, true);
					}
				}
				
				if( rep==null ) {
					Fm.terminal.success=false;
					Fm.terminal.addLabel("<No repository with that name found!");
					return;
				}
				
				c=CommandFactory.getCommand(CMD, rep);
				this.tryCommand(c, rep);
			}
			else
			{
				Fm.terminal.success=false;
				Fm.terminal.addLabel("<Incorect format!");
			}
			
			return;
		}
		
		if( CMD.compareTo("cd")==0 )
		{
			if( CONTENT.matches("cd "+path) || CONTENT.matches("cd "+repname) ||
					CONTENT.compareTo("cd ..")==0 ){
				
				rep=this.getRepository(st.nextToken(), true);
				
				if( rep==null ) {
					Fm.terminal.success=false;
					Fm.terminal.addLabel("<No repository with that name found!");
					return;
				}
				
				c=CommandFactory.getCommand(CMD, rep);
				this.tryCommand(c, rep);
			}
			else
			{
				Fm.terminal.success=false;
				Fm.terminal.addLabel("<Incorect format!");
			}
			
			return;
		}
		
		if( CMD.compareTo("cat")==0 )
		{
			if( CONTENT.matches("cat "+repname) ){
				
				rep=getRepository(st.nextToken(), true);
				
				if( rep==null ) {
					Fm.terminal.success=false;
					Fm.terminal.addLabel("<No repository with that name found!");
					return;
				}
				
				c=CommandFactory.getCommand(CMD, rep);
				this.tryCommand(c, rep);
			}
			else
			{
				Fm.terminal.success=false;
				Fm.terminal.addLabel("<Incorect format!");
			}
			
			return;
		}
		
		if( CMD.compareTo("pwd")==0 )
		{
			if( CONTENT.matches("pwd") ){
				
				rep=Fm.CurrentDir;
				c=CommandFactory.getCommand(CMD, rep);
				this.tryCommand(c, rep);
			}
			else
			{
				Fm.terminal.success=false;
				Fm.terminal.addLabel("<Incorect format!");
			}
			
			return;
		}
		
		if( CMD.compareTo("upload")==0 )
		{
			if( CONTENT.matches("upload "+dirname) || CONTENT.matches("upload "+path)){
				rep=getRepository(st.nextToken(), true);
				
				if( rep==null ) {
					Fm.terminal.success=false;
					Fm.terminal.addLabel("<No repository with that name found!");
					return;
				}
				
				c=CommandFactory.getCommand(CMD, rep);
				this.tryCommand(c, rep);
			}
			else
			{
				Fm.terminal.success=false;
				Fm.terminal.addLabel("<Incorect format!");
			}
			
			return;
		}
		
		if( CMD.compareTo("sync")==0 )
		{
			if( CONTENT.matches("sync "+dirname) || CONTENT.matches("sync "+path)){
				rep=getRepository(st.nextToken(), true);
				
				if( rep==null ) {
					Fm.terminal.success=false;
					Fm.terminal.addLabel("<No repository with that name found!");
					return;
				}
				
				c=CommandFactory.getCommand(CMD, rep);
				this.tryCommand(c, rep);
			}
			else
			{
				Fm.terminal.success=false;
				Fm.terminal.addLabel("<Incorect format!");
			}
			
			return;
		}
		
	}
}
