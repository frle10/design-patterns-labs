package hr.fer.zemris.ooup.lab3.guibasics;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class GUIDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public GUIDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("GUI Demo");
		setLocation(20, 20);
		setSize(800, 600);
		initGUI();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		cp.add(new BasicGUI(), BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GUIDemo guiDemo = new GUIDemo();
				guiDemo.setVisible(true);
			}
		});
	}

}
