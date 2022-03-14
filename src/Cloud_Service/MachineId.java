package Cloud_Service;

import java.util.Vector;
import java.util.Date;
import Command_Management.Command;
import Main.Repository;

public class MachineId implements Repository{
	
	CloudStation station;
	String Name;
	
	public MachineId(CloudStation station, String Name){
		this.station=station;
		this.Name=new String(Name);
	}
	
	@Override
	public String getName() {
		return this.Name;
	}
	
	public CloudStation getStation(){
		return this.station;
	}
	
	public Repository clone(){
		return null;
	}
	
	@Override
	public void accept(Command c) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addPermission() {
		// TODO Auto-generated method stub
	}

	@Override
	public void fillPermission() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean hasReadPermission() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasWritePermission() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void iterateSonsForUpdate() {
		// TODO Auto-generated method stub
	}

	@Override
	public void iterateSonsForPrint(String step) {
		// TODO Auto-generated method stub
	}

	@Override
	public Repository findSon(String Name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Repository findFather() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addRemainingUserPerm() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getFullPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Repository> getSONS() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getItemSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFather(Repository rep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSon(Repository rep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

}