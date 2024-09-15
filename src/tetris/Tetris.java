package tetris;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Tetris extends JFrame {

	public static void main(String[] args) {
		Tetris t = new Tetris();
		t.setDefaultCloseOperation(EXIT_ON_CLOSE);
		t.setTitle("TETRIS");
		t.setSize(CommonField.X0 * 2 + CommonField.CELL_SIZE * (CommonField.X_CELLS + 18),
				CommonField.Y0 + CommonField.CELL_SIZE * (CommonField.Y_CELLS + 3) + CommonField.X0);
		t.setLocationRelativeTo(null);
		t.setResizable(false);
		t.addKeyListener(new KeySystem());
		t.setVisible(true);

		Thread gameThread = new Thread(new MainSystem(t));
		gameThread.start();
	}

	private Tetris() {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream("bin/tetris/tetris.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			CommonField.CELL_SIZE = Integer.parseInt(p.getProperty("CELL_SIZE","24"));
			//タテ、ヨコは変更不可にする
			CommonField.X_CELLS = 10;//Integer.parseInt(p.getProperty("X_CELLS","10"));
			CommonField.Y_CELLS = 20;//Integer.parseInt(p.getProperty("Y_CELLS","20"));
			CommonField.FPS = Integer.parseInt(p.getProperty("FPS","60"));
			CommonField.LEVEL = Integer.parseInt(p.getProperty("LEVEL","1"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			
			System.gc();
			System.exit(0);
		}

		try {
			if (CommonField.LEVEL < 1 || CommonField.LEVEL > 20) {
				throw new IllegalArgumentException("レベルは1~20の範囲で指定してください。");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			CommonField.LEVEL = 1;
		}

		CommonField.field = new int[CommonField.Y_CELLS + 1][CommonField.X_CELLS + 2];
		CommonField.window = 0;
		CommonField.gameMode = 0;
	}

}
