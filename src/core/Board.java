package core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Board {
	 
    private final Scoreboard scoreboard;
    private final Objective objective;
    private static Team team;
 
    /**
     * Create a new score board for the player
     * and set a personal team to change his nameTag
     * @param player
     * @param prefix
     * @param suffix
     */
	public Board(Player player, String prefix, String suffix) {
    	this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective(player.getName(), "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(this.scoreboard);
        if (this.scoreboard.getTeam(player.getName()) == null) {
        	this.scoreboard.registerNewTeam(player.getName());
        }
        team = this.scoreboard.getTeam(player.getName());
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
        team.addEntry(player.getName());
    }
 
    /**
     * Add team for every player
     * @param player
     * @param newPlayer
     */
	public static void addTeam(GamePlayer player, GamePlayer newPlayer, String prefix) {
        if (player.getBoard().scoreboard.getTeam(newPlayer.getPlayer().getName()) == null) {
        	player.getBoard().scoreboard.registerNewTeam(newPlayer.getPlayer().getName());
        }
        team = player.getBoard().scoreboard.getTeam(newPlayer.getPlayer().getName());
        team.setPrefix(prefix);
        team.setSuffix("§r");
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
        team.addEntry(newPlayer.getPlayer().getName());
    }
    
    /**
     * Get score board
     * @return
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
 
    /**
     * Get score board name
     * @return
     */
    public String getTitle() {
        return objective.getDisplayName();
    }
 
    /**
     * Set score board name
     * @param name
     */
    public void setTitle(String name) {
        this.objective.setDisplayName(name);
    }
 
    /**
     * Set lines
     * @param row
     * @param text
     */
    public void set(int row, String text) {
        if(text.length() > 32) { text = text.substring(0, 32);}
 
        for(String entry : this.scoreboard.getEntries()) {
            if(this.objective.getScore(entry).getScore() == row) {
                this.scoreboard.resetScores(entry);
                break;
            }
        }
        this.objective.getScore(text).setScore(row);
    }
 
    /**
     * Remove line
     * @param row
     */
    public void remove(int row) {
        for(String entry : this.scoreboard.getEntries()) {
            if(this.objective.getScore(entry).getScore() == row) {
                this.scoreboard.resetScores(entry);
                break;
            }
        }
    }
}
