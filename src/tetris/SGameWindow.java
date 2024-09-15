package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Calendar;

@SuppressWarnings("serial")
public class SGameWindow extends BaseWindow {
	public static final String[] GAME_TYPE_NAME = {"ENDLESS","TIME_ATTACK","SCORE_ATTACK","HALF-TA","HALF-SA"};
	
	public SGameWindow() {
		super();

		CommonField.GAME_LEVEL = CommonField.LEVEL;
		CommonField.GAME_SCORE = 0;
		CommonField.GAME_LINE = 0;
	}
	
	@Override
	public void paint(Graphics g) {
		run(g);
		if (CommonField.isPaused) {
			pause(g);
		}
	}

	private void run(Graphics g) {
		Dimension size = this.getSize();
		Image back = this.createImage(size.width, size.height);
		Graphics buffer = back.getGraphics();

		buffer.setColor(Color.BLACK);
		buffer.fillRect(0, 0, size.width, size.height);

		buffer.setColor(Color.LIGHT_GRAY);
		buffer.fillRect(CommonField.X0 + CommonField.CELL_SIZE * 6 - 2,
				(int) (CommonField.Y0 + (CommonField.CELL_SIZE - 24) * 1.5),
				(CommonField.X_CELLS + 2) * CommonField.CELL_SIZE + 2,
				(CommonField.Y_CELLS + 1) * CommonField.CELL_SIZE);

		buffer.setFont(new Font(Font.SERIF, Font.BOLD, CommonField.CELL_SIZE));
		buffer.drawString("HOLD", (int) (CommonField.X0 + CommonField.CELL_SIZE * 1.5),
				CommonField.Y0 + CommonField.CELL_SIZE);

		buffer.drawString("NEXT", (int) (CommonField.X0 + CommonField.CELL_SIZE * (CommonField.X_CELLS + 11.5)),
				CommonField.Y0 + CommonField.CELL_SIZE);

		buffer.setFont(new Font(Font.SERIF, Font.PLAIN, CommonField.CELL_SIZE - 5));
		buffer.drawString("SCORE", (int) (CommonField.X0 + CommonField.CELL_SIZE * (CommonField.X_CELLS + 10)),
				CommonField.Y0 + CommonField.CELL_SIZE * 10);
		buffer.drawString(String.format("%09d", CommonField.GAME_SCORE),
				(int) (CommonField.X0 + CommonField.CELL_SIZE * (CommonField.X_CELLS + 11)),
				(int) (CommonField.Y0 + CommonField.CELL_SIZE * 11));
		buffer.drawString("LEVEL", (int) (CommonField.X0 + CommonField.CELL_SIZE * (CommonField.X_CELLS + 10)),
				(int) (CommonField.Y0 + CommonField.CELL_SIZE * 12.5));
		buffer.drawString(String.format("%3d", CommonField.GAME_LEVEL),
				(int) (CommonField.X0 + CommonField.CELL_SIZE * (CommonField.X_CELLS + 11)),
				(int) (CommonField.Y0 + CommonField.CELL_SIZE * 13.5));
		buffer.drawString("LINE", (int) (CommonField.X0 + CommonField.CELL_SIZE * (CommonField.X_CELLS + 10)),
				CommonField.Y0 + CommonField.CELL_SIZE * 15);
		buffer.drawString(String.format("%3d", CommonField.GAME_LINE),
				(int) (CommonField.X0 + CommonField.CELL_SIZE * (CommonField.X_CELLS + 11)),
				(int) (CommonField.Y0 + CommonField.CELL_SIZE * 16));
		buffer.drawString("TIME", (int) (CommonField.X0 + CommonField.CELL_SIZE * (CommonField.X_CELLS + 10)),
				(int) (CommonField.Y0 + CommonField.CELL_SIZE * 17.5));
		buffer.drawString(String.format("%02d:%02d.%02d", CommonField.GAME_TIME.get(Calendar.MINUTE),
				CommonField.GAME_TIME.get(Calendar.SECOND), CommonField.GAME_TIME.get(Calendar.MILLISECOND) / 100),
				(int) (CommonField.X0 + CommonField.CELL_SIZE * (CommonField.X_CELLS + 11)),
				(int) (CommonField.Y0 + CommonField.CELL_SIZE * 18.5));

		buffer.setFont(new Font(Font.SERIF, Font.BOLD, CommonField.CELL_SIZE * 2));
		buffer.drawString("TETRIS", (int) (CommonField.X0 + CommonField.CELL_SIZE * (CommonField.X_CELLS + 9.5)),
				CommonField.Y0 + CommonField.CELL_SIZE * 7);

		if (CommonField.nextBlocks.size() > 0) {
			int[][] nB = new Block(CommonField.nextBlocks.get(0)).getBlock();
			buffer.setColor(Block.BLOCK_COLOR[CommonField.nextBlocks.get(0)]);
			for (int y = 0; y < nB.length; y++) {
				for (int x = 0; x < nB[0].length; x++) {
					if (nB[y][x] != 0) {
						buffer.fillRect(
								(int) (CommonField.X0 + CommonField.CELL_SIZE * (CommonField.X_CELLS + x + 11.5)),
								CommonField.Y0 + CommonField.CELL_SIZE * (y + 2), CommonField.CELL_SIZE - 2,
								CommonField.CELL_SIZE - 2);
					}
				}
			}
		}

		if (CommonField.holdBlock != null) {
			int[][] hB = CommonField.holdBlock.getBlock();
			buffer.setColor(Block.BLOCK_COLOR[CommonField.holdBlock.getSort()]);
			for (int y = 0; y < hB.length; y++) {
				for (int x = 0; x < hB[0].length; x++) {
					if (hB[y][x] != 0) {
						buffer.fillRect(
								(int) (CommonField.X0 + CommonField.CELL_SIZE * (CommonField.X_CELLS + x - 8.5)),
								CommonField.Y0 + CommonField.CELL_SIZE * (y + 2), CommonField.CELL_SIZE - 2,
								CommonField.CELL_SIZE - 2);
					}
				}
			}
		}

		for (int y = 0; y < CommonField.field.length; y++) {
			for (int x = 0; x < CommonField.field[0].length; x++) {
				switch (CommonField.field[y][x]) {
				case Block.VOID:
					buffer.setColor(Block.VOID_COLOR);
					break;
				case Block.EDGE_WALL:
					buffer.setColor(Block.WALL_COLOR);
					break;
				case Block.BLOKEN_BLOCK:
					buffer.setColor(Block.BLOKEN_COLOR);
					break;
				default:
					if (CommonField.field[y][x] <= Block.MAX_BLOCK_TYPES) {
						buffer.setColor(Block.BLOCK_COLOR[CommonField.field[y][x]]);
					} else {
						buffer.setColor(Block.BLOCK_COLOR[CommonField.field[y][x] - Block.MAX_BLOCK_TYPES]);
					}
					break;
				}
				buffer.fillRect(CommonField.X0 + CommonField.CELL_SIZE * (x + 6),
						(int) (CommonField.Y0 + (CommonField.CELL_SIZE - 24) * 1.5) + y * CommonField.CELL_SIZE,
						CommonField.CELL_SIZE - 2, CommonField.CELL_SIZE - 2);
			}
		}

		this.showFPS(buffer, size);

		g.drawImage(back, 0, 0, this);
	}
	
	private void pause(Graphics g) {
		Dimension size = this.getSize();

		g.setColor(new Color(0, 0, 0, 128));
		g.fillRect(0, 0, size.width, size.height);

		g.setColor(new Color(120, 250, 90, 200));
		g.fillRect(CommonField.CELL_SIZE * 3, CommonField.CELL_SIZE * 3, size.width - CommonField.CELL_SIZE * 6,
				size.height - CommonField.CELL_SIZE * 6);

		g.setFont(new Font(Font.SERIF, Font.BOLD, CommonField.CELL_SIZE * 2));
		if (CommonField.pauseSelection == 0) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.DARK_GRAY);
		}
		g.drawString("RESUME", (int) (size.width / 2.0 - CommonField.CELL_SIZE * 4.5), CommonField.CELL_SIZE * 6);
		if (CommonField.pauseSelection == 1) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.DARK_GRAY);
		}
		g.drawString("RETRY", (int) (size.width / 2.0 - CommonField.CELL_SIZE * 3.7),
				((CommonField.CELL_SIZE * 6) + (size.height - CommonField.CELL_SIZE * 4)) / 2);
		if (CommonField.pauseSelection == 2) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.DARK_GRAY);
		}
		g.drawString("RETIRE", (int) (size.width / 2.0 - CommonField.CELL_SIZE * 4),
				size.height - CommonField.CELL_SIZE * 4);

	};

}
