package pt.cmov.bomberman.presenter.view;

import pt.cmov.bomberman.R;
import pt.cmov.bomberman.model.GameLevel;
import pt.cmov.bomberman.net.server.Server;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	private static final String TAG = MainThread.class.getSimpleName();
	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	private boolean running;
	private Object mPauseLock;
	private boolean mPaused;

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
		this.mPauseLock = new Object();
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Object getmPauseLock() {
		return mPauseLock;
	}

	public void setmPauseLock(Object mPauseLock) {
		this.mPauseLock = mPauseLock;
	}

	public boolean ismPaused() {
		return mPaused;
	}

	public void setmPaused(boolean mPaused) {
		this.mPaused = mPaused;
	}

	@SuppressLint("WrongCall")
	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		while (running) {
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();

				synchronized (surfaceHolder) {
					if (GameLevel.getInstance().isGameOver()) {
						running = false;
						canvas.drawColor(Color.BLACK);
						Bitmap gameover = BitmapFactory.decodeResource(gamePanel.getResources(), R.drawable.gameover);
						canvas.drawBitmap(gameover, 0, 0, null);
						Server.getInstance().broadcastGameOver(GameLevel.getInstance().getGameWinner(), GameLevel.getInstance().getWinnerScore());
						// TODO Show this to the user
						String gameWinner = GameLevel.getInstance().getGameWinner();
						int winnerScore = GameLevel.getInstance().getWinnerScore();
						if (winnerScore != -1) {
							Log.d("LevelFileParser", "*** Game Over. Winner: " + gameWinner + " (scored " + winnerScore + " points).");
						} else {
							Log.d("LevelFileParser", "*** Game Over. No winner!");
						}
					} else {
						this.gamePanel.onDraw(canvas);
					}
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			synchronized (mPauseLock) {
				while (mPaused) {
					try {
						mPauseLock.wait();
					} catch (InterruptedException e) {
					}
				}
			}

		}
	}
}
