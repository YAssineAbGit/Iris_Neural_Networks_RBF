import java.io.File;

import javax.swing.JFileChooser;


public class FileChooserTXT
{
	public String chemin=null;
	JFileChooser chooser;
	File selection;

	public  FileChooserTXT()
	{}
	public String Chemin()
	{
		javax.swing.filechooser.FileFilter E =  new FiltrageTXT("Fichiers txt",".txt");
		chooser = new JFileChooser(".");

		chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(E);
		chooser.setMultiSelectionEnabled(false);


		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			selection = chooser.getSelectedFile();
			chemin =  selection.getAbsolutePath();
			//System.out.print(chemin);

		}
		return(chemin);
	}
	public String NomFich()
	{
		String nomFich=null;
		nomFich=selection.getName();
		return nomFich;

	}
}
