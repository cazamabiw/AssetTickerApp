import javax.swing.SwingUtilities;


public class Main {

	public static void main(String[] args) {
		//Use SwingUtilities.invokeLater to ensure the GUI updates are done on the Event Dispatch Thread
	    SwingUtilities.invokeLater(() -> {
	    //Create an instance of AssetTickerApp and make it visible
	        new AssetTickerApp().setVisible(true);
	    });
	}

}
