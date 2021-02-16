package comparator;

import java.util.Comparator;

import core.GamePlayer;

public class PlayerComparator implements Comparator<GamePlayer> {
	
	/**
	 * Compare player point 
	 * used to sort the player list and make the leader board
	 * @param a
	 * @param b
	 */
    public int compare(GamePlayer a, GamePlayer b) {
        int comparator = Integer.valueOf(b.getScore()).compareTo(a.getScore());
        
        return comparator == 0 ? Integer.valueOf(b.getScore()).compareTo(a.getScore()) : comparator;
    }
}