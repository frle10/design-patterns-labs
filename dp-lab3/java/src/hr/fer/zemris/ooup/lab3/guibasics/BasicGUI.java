package hr.fer.zemris.ooup.lab3.guibasics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class BasicGUI extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	public BasicGUI() {
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					SwingUtilities.windowForComponent(BasicGUI.this).dispose();
				}
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		g2d.drawLine(10, 10, getWidth() - 10, 10);
		g2d.drawLine(10, 20, 10, getHeight() - 20);
		
		g2d.setColor(Color.BLACK);
		String firstString = "Ja sam Ivan";
		String secondString = "Ovo je drugi tekst";
		g2d.drawString(firstString, 20, 50);
		g2d.drawString(secondString, 20, 70);
	}
	
}
