package Read_Commands;

import java.util.Vector;

import javax.swing.JTable;

import Command_Management.ReadCommand;
import Main.Directory;
import Main.File;
import Main.FileManagement;
import Main.Repository;

public class CommandLs extends ReadCommand{
	
	public void execute(Repository rep){
		rep.accept(this);
	}
	
	public void execute(Directory dir){
		
		FileManagement Fm=FileManagement.getInstance();
		
		if( Fm.isPOO ){
		
			Fm.rows=new Vector< Vector<String> >();
			Fm.columnNames=new Vector<String>();
		
			Fm.columnNames.add("Name");
			
			if( Fm.isDetailed ){
				Fm.columnNames.add("Size");
				Fm.columnNames.add("Date");
			}
		}
		
		if( Fm.isRecursive ) Fm.printTree(dir,"");
		else dir.printSons();
		
		if( Fm.isPOO ) Fm.terminal.addTable(Fm.rows, Fm.columnNames);
	}
	
	public void execute(File file){

		FileManagement Fm=FileManagement.getInstance();
		
		if( !Fm.isPOO )
		{
			if( !Fm.isDetailed )
				Fm.terminal.addLabel(file.getName());
			else
				Fm.terminal.addLabel(file.toString());
		}
		else
		{
			Fm.rows=new Vector< Vector<String> >();
			Fm.columnNames=new Vector<String>();
			
			Fm.rows.add(new Vector<String>());
			if( !Fm.isDetailed )
			{
				Fm.rows.get(0).add(file.getName());
				Fm.columnNames.add("Name");
			}
			else
			{
				Fm.rows.get(0).add(file.getName());
				Fm.rows.get(0).add(file.getSize()+"");
				Fm.rows.get(0).add(file.getDate()+"");
				
				Fm.columnNames.add("Name");
				Fm.columnNames.add("Size");
				Fm.columnNames.add("Date");
			}
			
			Fm.terminal.addTable(Fm.rows, Fm.columnNames);
		}
	}
}
