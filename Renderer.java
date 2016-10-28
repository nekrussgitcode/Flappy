package flappyBirds;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Renderer extends JPanel {

	private static final long serialVersionUID = -7653423314957321861L;
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		FlappyBirds.flappyBirds.repaint(g);
	}

}
