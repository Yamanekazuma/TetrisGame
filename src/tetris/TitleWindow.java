package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

@SuppressWarnings("serial")
public class TitleWindow extends BaseWindow {
	public TitleWindow() {
		super();
	}

	@Override
	public void paint(Graphics g) {
		Dimension size = this.getSize();
		Image back = this.createImage(size.width, size.height);
		Graphics buffer = back.getGraphics();

		buffer.setColor(Color.BLACK);
		buffer.fillRect(0, 0, size.width, size.height);

		buffer.setColor(Color.LIGHT_GRAY);
		buffer.setFont(new Font(Font.SERIF, Font.BOLD, CommonField.CELL_SIZE * 5));
		buffer.drawString("TETRIS", (int) (size.width / 2 - 9.7 * CommonField.CELL_SIZE),
				CommonField.Y0 + CommonField.CELL_SIZE * 4);

		buffer.setColor(Color.GRAY);
		buffer.setFont(new Font(Font.SERIF, Font.BOLD, (int) (CommonField.CELL_SIZE * 1.5)));
		buffer.drawString("1-PLAYER", (int) (size.width / 2 - 4.5 * CommonField.CELL_SIZE),
				(int) (size.height / 2 - (CommonField.CELL_SIZE - 2) * 2));// Y0+CELL_SIZE*8);
		buffer.drawString("2-PLAYER", (int) (size.width / 2 - 4.5 * CommonField.CELL_SIZE),
				(int) (size.height / 2 + (CommonField.CELL_SIZE - 2) * 4));// Y0+CELL_SIZE*14);

		buffer.setFont(new Font(Font.SERIF, Font.BOLD, (int) (CommonField.CELL_SIZE * 1.1)));
		if (CommonField.gameMode == CommonField.GAME_MARATHON) {
			buffer.setColor(Color.LIGHT_GRAY);
		} else {
			buffer.setColor(Color.DARK_GRAY);
		}
		buffer.drawString(SGameWindow.GAME_TYPE_NAME[CommonField.gameType], (int) (size.width / 2 - 3.5 * CommonField.CELL_SIZE), (int) (size.height / 2));// Y0+CELL_SIZE*10);
		
		if(CommonField.gameType != CommonField.TGAME_ENDLESS){
			buffer.fillPolygon(Triangle.create((int) (size.width / 2 - 6 * CommonField.CELL_SIZE), (int) (size.height / 2),
					(int) (CommonField.CELL_SIZE * 1.0), (int) (CommonField.CELL_SIZE * 0.8), Triangle.WEST));
		}
		if(CommonField.gameType != CommonField.TGAME_HALF_SA){
		buffer.fillPolygon(
				Triangle.create((int) (size.width / 2 + 6.5 * CommonField.CELL_SIZE), (int) (size.height / 2),
						(int) (CommonField.CELL_SIZE * 1.0), (int) (CommonField.CELL_SIZE * 0.8), Triangle.EAST));
		}
		if (CommonField.gameMode == CommonField.GAME_VS_COM) {
			buffer.setColor(Color.LIGHT_GRAY);
		} else {
			buffer.setColor(Color.DARK_GRAY);
		}
		buffer.drawString("VS COM", (int) (size.width / 2 - 3.5 * CommonField.CELL_SIZE),
				(int) (size.height / 2 + (CommonField.CELL_SIZE - 2) * 2));// Y0+CELL_SIZE*12);
		if (CommonField.gameMode == CommonField.GAME_VS_PLAYER) {
			buffer.setColor(Color.LIGHT_GRAY);
		} else {
			buffer.setColor(Color.DARK_GRAY);
		}
		buffer.drawString("VS PLAYER", (int) (size.width / 2 - 3.5 * CommonField.CELL_SIZE),
				(int) (size.height / 2 + (CommonField.CELL_SIZE - 2) * 6));// Y0+CELL_SIZE*16);

		buffer.setColor(Color.GRAY);
		buffer.setFont(new Font(Font.SERIF, Font.PLAIN, (int) (CommonField.CELL_SIZE * 0.8)));
		buffer.drawString("Copyright AISHO 2018 All rights reserved",
				(int) (size.width / 2 - 7 * CommonField.CELL_SIZE), (int) (size.height - 55));

		this.showFPS(buffer, size);

		g.drawImage(back, 0, 0, this);
	}

}
