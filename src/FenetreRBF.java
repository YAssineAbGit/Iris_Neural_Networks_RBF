import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JFileChooser;

class FenetreRBF extends JFrame implements ActionListener
{
	//Les FONTs:
	protected KmeansFrame2 fen;
	FileChooserTXT FileChooser = new FileChooserTXT();
	private double  x[][]      = new double  [150][4];
	private int i=0;
	private int j=0;
	
	protected Font comics30 = new Font("Comics Sans MS", Font.BOLD, 30);
	protected Font comics18 = new Font("Comics Sans MS", Font.BOLD, 18);
	protected Font comics40 = new Font("Comics Sans MS", Font.BOLD, 70);
	
	protected Font arial  = new Font("Arial", Font.BOLD, 12);
	protected Font arial10  = new Font("Arial", Font.BOLD, 10);
	protected Font arial14  = new Font("Arial", Font.BOLD, 14);
	protected Font dialog   = new Font("Dialog", Font.BOLD + Font.ITALIC, 15);
	
	private JTextField vectField,fichField;
	private JLabel vectLabel,fichLabel,setoLabel,versLabel,vergLabel,pourcLabel,etoileLabel;
	private JButton V1,Q1,V2,Q2;

	public FenetreRBF()
	{
	setLayout(new FlowLayout());
	
	setTitle("Algorithme RBF");
	setSize(550,550);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	Container c=getContentPane();
	c.setBackground(Color.white);
	initialiserMenu();
	fonctionMENU();
    initPanneaux();

	
    }
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ACTION SUR LES BOUTONS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if((JButton)e.getSource() == V1 && vectField.getText().equals("")==true)
		{
			JOptionPane.showMessageDialog(null,
  		          "Oups Vous n'avez rien  saisi\n" +
  		          "Veuillez  entrer  un  vecteur\n" +
  		          "Merci...",
  		        "Attention", JOptionPane.WARNING_MESSAGE);
		}
		
		else if((JButton)e.getSource() == V1 && vectField.getText().equals("")!=true )
		{
			double X[] = new double[4];
			String text = new String(vectField.getText());
            String[] Tab = text.split(",");
            for(int i=0;i<4;i++)
				try {
					X[i]=Double.parseDouble(Tab[i]);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					JOptionPane.showMessageDialog(null,
			  		          "Vous n'avez pas bien saisi le vecteur.. voir Aide \"?\" \n" +
			  		          "Merci...",
			  		        "Erreur", JOptionPane.ERROR_MESSAGE);
				}
            fen=new  KmeansFrame2();
            
            double y= fen.EntrerX(X);
    		if(y<0.5)
    			{//pourcLabel.setText("");
    			 setoLabel.setText("");
    			 vergLabel.setText("");
    			 versLabel.setText("Setosa");
    		     versLabel.setFont(comics30);}
    		if(0.5<=y && y<1.5)
    			{//pourcLabel.setText("");
    			 setoLabel.setText("");
   			     vergLabel.setText("");
    			 versLabel.setText("Versicolor");
    		     versLabel.setFont(comics30);}
    		if(1.5<=y)
    		    {//pourcLabel.setText("");
    			 setoLabel.setText("");
  			     vergLabel.setText("");
    			 versLabel.setText("Verginica");
    		     versLabel.setFont(comics30);}
            }
		
		/*if((JButton)e.getSource() == V2 && fichField.getText().equals("")==true)
		{
			JOptionPane.showMessageDialog(null,
					"Oups Vous n'avez rien  saisi\n" +
			  		"Veuillez Parcourir  un  fichier\n" +
  		            "Merci...",
  		        "Attention", JOptionPane.WARNING_MESSAGE);
		}*/
	    if((JButton)e.getSource() == V2 )
		{
			String chemin = FileChooser.Chemin();
			String nomFichier =FileChooser.NomFich();
			//System.out.println(nomFichier);
			//System.out.println(chemin);
			if(chemin != null)
			 {  
				i=0;
			   try{
			 
			   BufferedReader buff = new BufferedReader(new FileReader(chemin));

			   try {
			   String ligne;
			   while ((ligne = buff.readLine()) != null)
			   {
			 
		       StringTokenizer st = new StringTokenizer(ligne,"\t");
		       
		       int j=0;
		       while (st.hasMoreTokens() && j<4)
		       {
		    	   try {
					x[i][j]=Float.valueOf(st.nextToken());
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					JOptionPane.showMessageDialog(null,
			  		          "Vous n'avez choisi le bon fichier.. voir Aide \"?\" \n" +
			  		          "Merci...",
			  		        "Erreur", JOptionPane.ERROR_MESSAGE);
				}
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
		   

            fen=new  KmeansFrame2();

        	fen.Calcul();
        	
        	int a=0,b=0,c=0,z=0;
        	
        	
        	for(int i=0;i<150;i++)
        	{
        		
        		fen.generationRi(x[i]);
        		double y=fen.getW()[0]*fen.getR()[0]+fen.getW()[1]*fen.getR()[1]+fen.getW()[2]*fen.getR()[2];
        		if(y<0.5)
        			{//System.out.println("-----------> SETOSA");
        		    a++;}
        		if(0.5<=y && y<1.5)
        			{//System.out.println("+++++++++++> VERSICOLOR");
        		    b++;}
        		if(1.5<=y)
        			{//System.out.println("===========> VERGINICA");
        		    c++;}
        			//System.out.println("y--> "+y);
        	}
        	//System.out.println("SITO--> "+a);
        	//System.out.println("VERS--> "+b);
        	//System.out.println("VERG--> "+c);
        	z=Math.abs(a-50)+Math.abs(b-50)+Math.abs(c-50);
        	z=z*100/150;
        	//System.out.println("Fautes --> "+z+" %");
			
            setoLabel.setText("Setosa     : "+a+" fleurs");
            setoLabel.setFont(comics18);
			versLabel.setText("Versicolor : "+b+" fleurs");
			versLabel.setFont(comics18);
			vergLabel.setText("Verginica  : "+c+" fleurs");
			vergLabel.setFont(comics18);
		    fichField.setText(nomFichier);
		    fichField.setEditable(false);
			pourcLabel.setText(" "+z+"% ");
			
			//versLabel = new JLabel("Versicolor : 53 fleurs ");
			//vergLabel = new JLabel("Verginica  : 40 fleurs ");
		    //pourcLabel.setText(" "+16% ");
		}
		
		if((JButton)e.getSource() == Q1)
		{
			JOptionPane.showMessageDialog(null,
					"Entrer un Vecteur qui a les caractéristique d'une fleur. \n" +
					"Les valeurs sont séparées par des virgules \",\". \n" +
					"Exemple:   6.7,3.1,4.4,1.4",
  		        "Aide!",JOptionPane.QUESTION_MESSAGE);
			
		}
		
		if((JButton)e.getSource() == Q2)
		{
			JOptionPane.showMessageDialog(null,
					"Vous pouvez entrer un fichier tout entier. \n" +
					"Un fichier contenant des vecteurs de fleurs. \n" +
					"Exemple:   iris_data.txt",
  		        "Aide!",JOptionPane.QUESTION_MESSAGE);
		}
		
	}

	
    // BARRE DE MENU:
    private JMenuBar menuBarre = new JMenuBar();
   
    private JMenu   //Aide = new JMenu("Aide!"),
    		        aPropos = new JMenu("À propos");
    
    private JMenuItem   //AideItem1 = new JMenuItem("Voir Explications "),
			            //AideItem2 = new JMenuItem("Quitter"),
			            aProposItem = new JMenuItem("    ?");
    
    private void initialiserMenu(){
    	
    	//Menu Aide:
    	/*Aide.add(AideItem1);
    	Aide.addSeparator();
    	Aide.add(AideItem2);
    	//Aide.setEnabled(false);
    	*/
    	//menu à propos
    	aPropos.add(aProposItem);
    	
    	//Ajout des menus dans la barre de menus
    	//menuBarre.add(Aide);
    	menuBarre.add(aPropos);
    	
    	//Ajout de la barre de menus sur la fenêtre
    	this.setJMenuBar(menuBarre);
    }
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // LES PANNEAUX des MENUS
    public void fonctionMENU()
    {

    	aProposItem.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent e){
    		JOptionPane.showMessageDialog(null,
						    		          "Créateur      : ABBAZI Yassine\n" +
						    		          "Master         : SécuRISE\n" +
						    		          "Professeur : M. JEDRA",
						    		          "Informations", JOptionPane.NO_OPTION);
    		
    	}
    });
    
    }
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
	// CREATION des PANNEAUX:
    
	void initPanneaux()
	{
     //pan1 : panneau vecteur
		JPanel pan1 = new JPanel();
		pan1.setBackground(Color.white);
		pan1.setPreferredSize(new Dimension(500,100));
		vectField = new JTextField();
		vectField.setFont(arial14);
		vectField.setForeground(Color.BLUE);
		vectField.setPreferredSize(new Dimension(205, 25));
		pan1.setBorder(BorderFactory.createTitledBorder("Entrer Une Fleur "));
		pan1.setFont(comics18);
		//pan1.se
		//pan1.setFont(comics20);
		vectLabel = new JLabel("Vecteur :");
		vectLabel.setFont(comics18);
		V1=new JButton("Valider");
		V1.setFont(arial);
		Q1=new JButton(" ? ");
		Q1.setFont(arial);
		pan1.add(vectLabel);
		pan1.add(vectField);
		pan1.add(V1);
		pan1.add(Q1);
		//pan1.setBorder(new LineBorder(Color.blue, 5));
		
	 //pan2 : panneau ficher
		JPanel pan2 = new JPanel();
		pan2.setBackground(Color.white);
		pan2.setPreferredSize(new Dimension(500,100));
		pan2.setBorder(BorderFactory.createTitledBorder("Entrer Un Fichier de Fleurs "));
		fichField = new JTextField("");
		fichField.setFont(arial14);
		fichField.setForeground(Color.BLUE);
		
		fichField.setPreferredSize(new Dimension(200, 25));
		fichLabel = new JLabel("Fichier :");
		fichLabel.setFont(comics18);
		V2=new JButton("Parcourir");
		V2.setFont(arial);
		Q2=new JButton(" ? ");
		Q2.setFont(arial);
		pan2.add(fichLabel);
		pan2.add(fichField);
		pan2.add(V2);
		pan2.add(Q2);
		
	 //pan3 : panneau Affichage
		JPanel pan3 = new JPanel();
		pan3.setBackground(Color.white);
		pan3.setPreferredSize(new Dimension(500, 180));
		pan3.setBorder(BorderFactory.createTitledBorder("Affichage des Résultats "));
	 
	 //panInter1 : panneau vecteurs
		JPanel panInter1 = new JPanel();
		panInter1.setBackground(Color.white);
		panInter1.setPreferredSize(new Dimension(250, 130));
		panInter1.setBorder(BorderFactory.createTitledBorder("Le(s) Vecteur(s) est(sont): "));
		setoLabel = new JLabel("");
		versLabel = new JLabel("");
		vergLabel = new JLabel("");
		setoLabel.setFont(comics18);
		versLabel.setFont(comics18);
		vergLabel.setFont(comics18);
		panInter1.add(setoLabel);//, BorderLayout.NORTH);
		panInter1.add(versLabel);//, BorderLayout.CENTER);
		panInter1.add(vergLabel);//, BorderLayout.SOUTH);
		
	 //panInter2 : panneau poucentage
		JPanel panInter2 = new JPanel();
		panInter2.setBackground(Color.white);
		panInter2.setPreferredSize(new Dimension(200,130));
		panInter2.setBorder(BorderFactory.createTitledBorder("Fautes Commises en % (*)"));
		pourcLabel = new JLabel("");
		pourcLabel.setFont(comics40);
		panInter2.add(pourcLabel);//,BorderLayout.CENTER);

	// l'Ajout des panneaux au panneaux :	
		pan3.add(panInter1, BorderLayout.EAST);
		pan3.add(panInter2, BorderLayout.WEST);
		
		etoileLabel= new JLabel("(*) Le programme donne une chance 1/4 pour une valeur entre 22% et 17% les autres entre 16% et 9%.");
		etoileLabel.setFont(arial10);
		
		
		JLabel TitreLabel  = new JLabel   ("Algorithme RBF");
	    TitreLabel.setHorizontalAlignment(JLabel.CENTER);
		TitreLabel.setFont(comics30);
		
		this.getContentPane().add(TitreLabel);
		this.getContentPane().add(pan1, BorderLayout.NORTH);
		this.getContentPane().add(pan2, BorderLayout.CENTER);
		this.getContentPane().add(pan3, BorderLayout.SOUTH);
		getContentPane().add(etoileLabel, BorderLayout.SOUTH);
		
		V1.addActionListener(this);
		V2.addActionListener(this);
		Q1.addActionListener(this);
		Q2.addActionListener(this);
		
		
	}
	

}
