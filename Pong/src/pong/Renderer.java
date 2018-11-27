package pong;
    
/**
 *
 * @author mandar
 */

//The necessary import for updating graphics and the background panel
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Renderer extends JPanel
{
	@Override //Overriding the superclass method with the local one
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g); //Using the overidden method

		Pong.pong.render((Graphics2D) g); //rendering the graphics
	}

}
