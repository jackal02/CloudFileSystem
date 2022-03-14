package Main;

import java.util.Random;

public class RandomBytes {
	static public byte[] getRandomContent(int Size)
	{
		Random rand=new Random();
		byte v[]=new byte[Size];
		for(int i=0;i<Size;i++)
			v[i]=(byte) ((char)rand.nextInt(128));
		
		return v;
	}
}
