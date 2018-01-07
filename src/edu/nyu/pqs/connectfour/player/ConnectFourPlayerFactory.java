package edu.nyu.pqs.connectfour.player;

public class ConnectFourPlayerFactory {
  
  /**
   * Build a newly instantiated player with defined status.
   * @param isAuto if this player is manual or auto (for single mode opponent)
   * @param player number of this player, can only be int 1 or 2
   * @return a newly instantiated player
   */
  public static ConnectFourPlayer getPlayer(boolean isAuto, int player) {
    if (player != 1 && player != 2) {
      throw new IllegalArgumentException("Can only accept player number as 1 or 2");
    }
    return new DualConnectFourPlayer.Builder(player).setMode(isAuto).build();
  }
}
