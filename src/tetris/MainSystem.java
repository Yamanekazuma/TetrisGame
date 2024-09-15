package tetris;

public class MainSystem implements Runnable {
	private Tetris t;
	private KeySystem key;
	private SubSystem sub;

	public MainSystem(Tetris frame) {
		this.t = frame;
		key = (KeySystem) t.getKeyListeners()[0];
		CommonField.fr = new FrameRate(500);
	}

	@Override
	public void run() {
		double addMillis = 1000.0 / CommonField.FPS;
		double nextTime = System.currentTimeMillis() + addMillis;
		int preWindow = -1;
		SubSystem buffer = null;

		while (true) {
			if (preWindow != CommonField.window) {
				changeWindow();
			}
			preWindow = CommonField.window;

			if((buffer=sub.system())!=null){
				sub = buffer;
			}

			CommonField.fr.calcFrameRate();

			t.repaint();

			try {
				long res = (long) nextTime - System.currentTimeMillis();
				if (res < 0)
					res = 0;
				Thread.sleep(res);
				nextTime += addMillis;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void changeWindow() {
		BaseWindow window = null;

		switch (CommonField.window) {
		case (BaseWindow.TITLE_WINDOW):
			sub = new TitleSystem(key);
			window = new TitleWindow();
			break;
		case (BaseWindow.SGAME_WINDOW):
			int level;
			int line;
			switch(CommonField.gameType){
			case CommonField.TGAME_TIME_ATTACK:
				level = 1;
				line = 100;
				break;
			case CommonField.TGAME_SCORE_ATTACK:
				level = CommonField.LEVEL;
				line = 100;
				break;
			case CommonField.TGAME_HALF_TA:
				level = 1;
				line = 50;
				break;
			case CommonField.TGAME_HALF_SA:
				level = CommonField.LEVEL;
				line = 50;
				break;
			default:
				level = CommonField.LEVEL;
				line = Integer.MAX_VALUE;
			}
			sub = new GameSystem(key, level,line);
			window = new SGameWindow();
			break;
		case (BaseWindow.VSCOM_WINDOW):
			window = new TitleWindow();
			break;
		case (BaseWindow.VS2PL_WINDOW):
			window = new TitleWindow();
			break;
		case (BaseWindow.RANKING_WINDOW):
			window = new RankingWindow();
			break;
		case (BaseWindow.REGISTER_WINDOW):
			window = new RankingWindow();
			break;
		}
		if (window != null) {
			t.getContentPane().removeAll();
			t.getContentPane().add(window);
			t.validate();
			t.repaint();
		}
	}
}
