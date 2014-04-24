package pt.cmov.bomberman.model;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.DONUT)
public class GameLevel {
	
	private static GameLevel INSTANCE = new GameLevel();
	
	private GameBoard board;
	private int duration;
	private String player_name;
	private String level_name;
	private int explosion_timeout;
	private int explosion_range;
	private int explosion_duration;
	private int enemy_speed;
	private int robot_score;
	private int opponent_score;

	private GameLevel() {

	}

	public void setBoard(GameBoard board) {
		this.board = board;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}

	public void setExplosion_timeout(int explosion_timeout) {
		this.explosion_timeout = explosion_timeout;
	}

	public void setExplosion_range(int explosion_range) {
		this.explosion_range = explosion_range;
	}

	public void setExplosion_duration(int explosion_duration) {
		this.explosion_duration = explosion_duration;
	}

	public void setEnemy_speed(int enemy_speed) {
		this.enemy_speed = enemy_speed;
	}

	public void setRobot_score(int robot_score) {
		this.robot_score = robot_score;
	}

	public void setOpponent_score(int opponent_score) {
		this.opponent_score = opponent_score;
	}

	public void draw(Canvas canvas) {
		if (board != null)
			board.draw(canvas);
	}

	public int getDuration() {
		return duration;
	}

	public String getLevel_name() {
		return level_name;
	}

	public int getExplosion_timeout() {
		return explosion_timeout;
	}

	public int getExplosion_range() {
		return explosion_range;
	}

	public int getExplosion_duration() {
		return explosion_duration;
	}

	public int getEnemy_speed() {
		return enemy_speed;
	}

	public int getRobot_score() {
		return robot_score;
	}

	public int getOpponent_score() {
		return opponent_score;
	}

	public GameBoard getBoard() {
		return board;
	}

    public synchronized int isJoinable() {
    	Player p = board.newPlayer();
    	if (p != null)
    		return p.getPlayer_number();
    	else
    		return -1;
    }
	public static GameLevel getInstance() {
        return INSTANCE;
    }
	public void initClient(String ip, int port) {
		// TODO Implement
	}

	public static GameLevel getInstance() {
		return INSTANCE;
	}

	/**
	 * @return the player_name
	 */
	public String getPlayer_name() {
		return player_name;
	}

	/**
	 * @param player_name the player_name to set
	 */
	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}
}
