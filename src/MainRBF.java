import javax.swing.UIManager;



public class MainRBF {
	public static void main(String[] args) {

		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		}
		catch(Exception e){

		}
		// TODO Auto-generated method stub
		FenetreRBF fen=new  FenetreRBF();
		fen.setVisible(true);
		// KmeansFrame2 f=new  KmeansFrame2();
		//f.Calcul();
		// double x[] ={5.2,2.7,3.9,1.4};
		// f.EntrerX(x);
		//f.shitTest();
		// f.EntrerListX();
	}
}
