package tetris;

public class FrameRate {
	private long prev;
	private int count;
	private float frameRate;
	private final int updateSpan;

	public FrameRate(int updateSpan) {
		this.updateSpan = updateSpan;
		prev = System.currentTimeMillis();
	}

	public void calcFrameRate() {
		long now = System.currentTimeMillis();
		count++;
		if (now - prev >= updateSpan) {
			frameRate = (float) (count * 1000) / (float) (now - prev);
			prev = now;
			count = 0;
		}
	}

	public float getFrameRate() {
		return frameRate;
	}
}