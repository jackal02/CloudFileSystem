package Cloud_Service;
import java.util.HashSet;

import Main.Repository;

public class CloudStation extends StoreStation{
	
	public CloudStation()
	{
		this.freeSpace=10240;
		this.container=new HashSet<Repository>();
	}
}
