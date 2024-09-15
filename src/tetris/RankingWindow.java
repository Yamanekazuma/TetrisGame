package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("serial")
public class RankingWindow extends BaseWindow {
	public static final int INPUT_NAME = 0;
	public static final int CHECK_NAME = 1;
	public static final int RANKING_VIEW = 2;

	@Override
	public void paint(Graphics g) {
		Dimension size = this.getSize();
		Image back = this.createImage(size.width,size.height);
		Graphics buffer = back.getGraphics();
		
		buffer.setColor(Color.BLACK);
		buffer.fillRect(0, 0, size.width, size.height);
		
		buffer.setColor(Color.lightGray);
		buffer.setFont(new Font(Font.SERIF, Font.BOLD, CommonField.CELL_SIZE + 3));
		
		buffer.drawString("RANK",(int)(CommonField.CELL_SIZE * 1), size.height / 10);
		buffer.drawString("NAME",(int)(CommonField.CELL_SIZE * 5.3), size.height / 10);
		buffer.drawString("SCORE",(int)(CommonField.CELL_SIZE * 10), size.height / 10);
		buffer.drawString("LEVEL",(int)(CommonField.CELL_SIZE * 15), size.height / 10);
		buffer.drawString("LINE",(int)(CommonField.CELL_SIZE * 20), size.height / 10);
		buffer.drawString("TIME",(int)(CommonField.CELL_SIZE * 24), size.height / 10);
		
		if(CommonField.ranking != null){
			buffer.setColor(Color.WHITE);
			buffer.setFont(new Font(Font.SERIF,Font.PLAIN,CommonField.CELL_SIZE+1));
			for(int y=0;y < CommonField.ranking.length;y++){
				for(int x=0;x<CommonField.ranking[0].length;x++){
					switch(x){
					case RankingSystem.RANK:
						buffer.drawString(String.format("%2s", String.valueOf(CommonField.ranking[y][x])), (int)(CommonField.CELL_SIZE * 2.1), (int) (size.height / 6 + y * CommonField.CELL_SIZE * 1.8));
						break;
					case RankingSystem.NAME:
						buffer.drawString(String.format("%-10s", String.valueOf(CommonField.ranking[y][x])), (int)(CommonField.CELL_SIZE * 5.0), (int) (size.height / 6 + y * CommonField.CELL_SIZE * 1.8));
						break;
					case RankingSystem.SCORE:
						buffer.drawString(String.format("%09d", Integer.parseInt(String.valueOf(CommonField.ranking[y][x]))), (int)(CommonField.CELL_SIZE * 9.8), (int) (size.height / 6 + y * CommonField.CELL_SIZE * 1.8));
						break;
					case RankingSystem.LEVEL:
						buffer.drawString(String.format("%2s", String.valueOf(CommonField.ranking[y][x])), (int)(CommonField.CELL_SIZE * 16.5), (int) (size.height / 6 + y * CommonField.CELL_SIZE * 1.8));
						break;
					case RankingSystem.LINE:
						buffer.drawString(String.format("%3s", String.valueOf(CommonField.ranking[y][x])), (int)(CommonField.CELL_SIZE * 20.7), (int) (size.height / 6 + y * CommonField.CELL_SIZE * 1.8));
						break;
					case RankingSystem.TIME:
						buffer.drawString((String) CommonField.ranking[y][x], (int)(CommonField.CELL_SIZE * 23.4), (int) (size.height / 6 + y * CommonField.CELL_SIZE * 1.8));
						break;
					}
				}
			}
		}
		
		
		buffer.setFont(new Font(Font.SERIF,Font.PLAIN,CommonField.CELL_SIZE));
		buffer.setColor(Color.LIGHT_GRAY);

		switch(CommonField.INPUT_STATUS){
		case INPUT_NAME:
			buffer.drawString("Please your name.", (int) (size.width / 2.0 - CommonField.CELL_SIZE * 3.5), size.height-CommonField.CELL_SIZE);
			break;
		case CHECK_NAME:
			buffer.drawString("Is it Okay?", (int) (size.width / 2.0 - CommonField.CELL_SIZE * 1.5), size.height-CommonField.CELL_SIZE);
			break;
		case RANKING_VIEW:
			buffer.drawString("Press \"Enter Key\" to Go Title.", (int) (size.width / 2.0 - CommonField.CELL_SIZE * 6), size.height-CommonField.CELL_SIZE);
			break;
		}
		
		this.showFPS(buffer, size);
		
		g.drawImage(back, 0, 0, this);
	}

}
