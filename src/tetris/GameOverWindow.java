package tetris;

import java.awt.Color;
import java.awt.Graphics;

public class GameOverWindow extends BaseWindow {

	@Override
	public void paint(Graphics g) {
		if(CommonField.GAME_OVER){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 100, 100);
		}
	}

}
