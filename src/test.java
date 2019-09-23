import java.math.BigInteger;
import java.util.Random;

public class test {
	
	
	public static float Aleatoire(int min, int max)
	{
		float posA=0;
		posA = (float)(Math.random() * (max - min) + min);
		return posA;
	}
	
	
	public static void main(String[] args) {
		//int t[][]=new int[][]{{2,2,2,2},{1,1,1,1}};
		//int t2[]=new int[]{2,2,2,2};
		//int t3[][]=t;
		
		//if(t2 == t)
			//System.out.println("C vrai");
		//else
		
		int t=0;
	while(t<100)
	{
		System.out.println("t -> "+t%4);
		t++;
		
	}
	fonction();
	/*	Random r = new Random();
		float valeur = 0 + r.nextFloat();
	
		int random = (int)(Math.random() * (100)); // retourn une valeur   90 inclu  100 exclu
		
		double x = (double) Math.random(); // generation d'un nombre aleatoire entre 0 et 1
*/
		float pos = Aleatoire(-100,100);
			System.out.println("----> "+pos);
		   /*System.out.println(t3[0][0]+"\t"+t3[0][1]+"\t"+t3[0][2]+"\t"+t3[0][3]);
		   System.out.println(t3[1][0]+"\t"+t3[1][1]+"\t"+t3[1][2]+"\t"+t3[1][3]);
		   */
	}
	
	public static void fonction()
	{
		String s = Integer.toBinaryString(Float.floatToIntBits(new Float("100.7")));
		String r = Integer.toBinaryString(Float.floatToIntBits(new Float("-100.9")));
		s="0"+s;
		String AaS3os ="0111111001001100110101111111110";
		//System.out.println("s+++> "+s);
		//System.out.println("r+++> "+r);
	    // System.out.println("longueur s+++> "+s.length());
	      
	     System.out.println("longueur s+++> "+AaS3os.length());
	     BigInteger bi = new BigInteger(AaS3os,2);
	     System.out.println("Decimal  "+Float.intBitsToFloat(bi.intValue()));
	     
	     

	}
}
