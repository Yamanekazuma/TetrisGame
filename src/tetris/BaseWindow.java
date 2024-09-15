package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class BaseWindow extends JPanel {
	public static final int TITLE_WINDOW = 0;
	public static final int SGAME_WINDOW = 1;
	public static final int VSCOM_WINDOW = 2;
	public static final int VS2PL_WINDOW = 3;
	public static final int RANKING_WINDOW = 4;
	public static final int REGISTER_WINDOW = 5;

	public BaseWindow() {
	}

	public abstract void paint(Graphics g);

	protected void showFPS(Graphics buffer, Dimension size) {
		buffer.setColor(Color.GRAY);
		buffer.setFont(new Font(Font.SERIF, Font.PLAIN, CommonField.CELL_SIZE));
		buffer.drawString(String.format("%.2fFPS", CommonField.fr.getFrameRate()),
				(int) (size.width - CommonField.CELL_SIZE * 4.5), size.height - CommonField.CELL_SIZE);
	}
}
