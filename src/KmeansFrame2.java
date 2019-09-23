import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class KmeansFrame2 
{
	int i=0;
	int j=0;

	private double  x[][]       = new double  [150][4];
	private double  c[][]       = new double [3][4];
	private double  Cnext[][]   = new double [3][4];
	private double  Sigma2[]    = new double [3];
	protected double  R[]         = new double [3];
	protected double  W[]         = new double [3];


	private double  O[][][]     = new double [3][150][4];
	private int     m[]         = new int    [3];


	private double epsilon      = 0.1 ;
	private double µ            = 0.02;
	private int    d;              
	private double e            = 0;
	private double eQmoy        = 10;

	FileChooserTXT FileChooser=new FileChooserTXT();

	public KmeansFrame2()
	{
		Cnext=new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0}};

		InputStream in = getClass().getResourceAsStream( "/iris_data.txt" );

		if(in != null)
		{  
			try{

				BufferedReader buff = new BufferedReader(new InputStreamReader(in)); 

				try {
					String ligne;
					while ((ligne = buff.readLine()) != null)
					{

						StringTokenizer st = new StringTokenizer(ligne,"\t");

						int j=0;
						while (st.hasMoreTokens() && j<4)
						{
							x[i][j]=Float.valueOf(st.nextToken());
							j++;       
						}
						i++;
					}// fin grand While

				}
				finally {

					buff.close();
				}
			} catch (IOException ioe) {
				// erreur de fermeture des flux
				System.out.println("Erreur --" + ioe.toString());
			}

		}// fin if
	}

	/////////////////////////////////////////////////////////////////////////////
	///    Calcul des class kmeans:
	////////////////////////////////////////////////////////////////////////////

	public int MinDistance(double [][]c,double []v)
	{
		double T[]= new double[]{0,0,0};

		double min=0;
		int position=0;

		for(int i=0;i<3;i++)
		{
			T[i]= (double) Math.sqrt(Math.pow((c[i][0] - v[0]),2) + Math.pow((c[i][1] - v[1]),2) + Math.pow((c[i][2] - v[2]),2) + Math.pow((c[i][3] - v[3]),2));
		}

		min =T[0];

		for(int i=1;i<3;i++)
		{
			if(min>T[i])
			{
				min=T[i];
				position=i;

			}
		}
		return position;
	}


	public void Calcul()
	{
		// 1 - initialisation des centre ci=

		c[0]=x[Aleatoire(0, 50)];
		c[1]=x[Aleatoire(50, 100)];
		c[2]=x[Aleatoire(100, 150)];
		/*
				c[0]=x[24];
				c[1]=x[74];
				c[2]=x[124];
		 */		
		//	c=new double[][]{{4.6,3.4,1.4,0.3},{6.0,2.2,4.0,1.0},{6.5,3.0,5.2,2.0}};

		Affectation(c);
		Cnext=NouveauxCentres(c,O);

		//System.out.println(Cnext[0][0]+"\t"+Cnext[0][1]+"\t"+Cnext[0][2]+"\t"+Cnext[0][3]);
		j=0;				
		while(!(c[0][0]==Cnext[0][0] && c[0][1]==Cnext[0][1] && c[0][2]==Cnext[0][2] && c[0][3]==Cnext[0][3] &&
				c[1][0]==Cnext[1][0] && c[1][1]==Cnext[1][1] && c[1][2]==Cnext[1][2] && c[1][3]==Cnext[1][3] &&
				c[2][0]==Cnext[2][0] && c[2][1]==Cnext[2][1] && c[2][2]==Cnext[2][2] && c[2][3]==Cnext[2][3])   )
		{
			c = Cnext;
			Affectation(c);

			Cnext = NouveauxCentres(c,O);

			System.out.println("J'ai reussi ");
			System.out.println("m0 = "+m[0]+"\n m1 = "+m[1]+"\n m2 = "+m[2]);

			j++;	  
		} 

		System.out.println("j "+j);	

		///// calcul des champs receptifs:

		ChampsRes();


		//////////////////////////      Apprentissage  //////////////////////////////////

		// 1. initialisation des poids W:
		for( int i=0 ; i<3 ; i++)
			getW()[i]=Math.random();

		i=1;

		//System.out.println("eQmoy --> "+eQmoy);

		while(Math.abs(eQmoy/i)>epsilon)
		{
			// int k=0;
			// 2. choix d'un exemple (R,d);

			int k=Aleatoire(0, 150); // 0 inclu 150 exclu
			if (k<50)
				d=0;
			else if (k>=100)
				d=2;
			else
				d=1;

			generationRi(x[k]);
			//3. calcul de Y et E et W:
			YEW();


			i++;

			// System.out.println("i --> "+i);
		}// fin while;


	}// FIN DE LA methode Calcul


	// 2 - Affectation des xj a Oi

	public boolean Affectation(double Centre[][])
	{

		m=new int[]{0,0,0};

		for(int i=0;i<150;i++)
		{
			int pos= MinDistance(Centre,x[i]);
			O[pos][m[pos]++]= x[i];
		}
		return true;
	}

	// 3 - Calcul des nouveau centres

	public double[][] NouveauxCentres(double c[][],double O[][][])
	{
		double Nouv[][]=new double[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0}};

		for (int i=0;i<3;i++)
		{
			for (int j=0;j<m[i];j++)
			{
				for(int k=0;k<4;k++)
				{
					Nouv[i][k]+=O[i][j][k]/m[i];
				}
			}
		}
		return Nouv;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	///////////                    LMS                     /////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	public int Aleatoire(int min, int max)
	{
		int posA=0;
		posA = (int)(Math.random() * (max - min) + min);
		return posA;
	}


	public void ChampsRes()
	{
		Sigma2  = new double []{0,0,0};

		for(int i=0;i<3;i++)
		{  // System.out.println("Oi "+O[i].length);
			for(int j=0;j<m[i];j++)
			{
				for(int k=0;k<4;k++)
				{
					Sigma2[i]+=(O[i][j][k]-c[i][k])*(O[i][j][k]-c[i][k]);


				}
			}
			Sigma2[i]=Sigma2[i]/m[i];
			//System.out.println("sigma --> "+Sigma2[i]);
		}

		//return Sigma2;
	}

	public void generationRi(double x[])
	{
		setR(new double[]{0,0,0});

		for(int i=0;i<3;i++)
		{
			for(int k=0; k<4; k++)
				getR()[i] += (x[k]-c[i][k])*(x[k]-c[i][k]);
			getR()[i] = Math.exp(-getR()[i]/(2*Sigma2[i])); 
		}

	}

	public void YEW()
	{
		double y =0;
		for(int i=0;i<3;i++)
			y += getW()[i]*getR()[i];
		e= d-y;
		eQmoy += e;

		for(int i=0;i<3;i++)   
			getW()[i]=getW()[i]+µ*e*getR()[i];


	}

	///////////////////             fonction pour utilisateur               //////////////////
	public double EntrerX (double x[])
	{
		Calcul();
		generationRi(x);
		double y=getW()[0]*getR()[0]+getW()[1]*getR()[1]+getW()[2]*getR()[2];
		return y;
		//System.out.println("y--> "+y);
	}
	/*
      public void EntrerListX (double x[][])
{
	Calcul();
	int a=0,b=0,c=0,z=0;

	for(int i=0;i<150;i++)
	{

		generationRi(x[i]);
		double y=W[0]*R[0]+W[1]*R[1]+W[2]*R[2];
		if(y<0.5)
			{System.out.println("-----------> SETOSA");
		    a++;}
		if(0.5<=y && y<1.5)
			{System.out.println("+++++++++++> VERSICOLOR");
		    b++;}
		if(1.5<=y)
			{System.out.println("===========> VERGINICA");
		    c++;}
			//System.out.println("y--> "+y);
	}
	System.out.println("SITO--> "+a);
	System.out.println("VERS--> "+b);
	System.out.println("VERG--> "+c);
	z=Math.abs(a-50)+Math.abs(b-50)+Math.abs(c-50);
	z=z*100/150;
	System.out.println("Fautes --> "+z+" %");
}
	 */

	public double[] getW() {
		return W;
	}

	public void setW(double w[]) {
		W = w;
	}

	public double[] getR() {
		return R;
	}

	public void setR(double r[]) {
		R = r;
	}

}



