package edu.nyu.pqs.connectfour.player;

import edu.nyu.pqs.connectfour.grid.ConnectFourGrid;
import edu.nyu.pqs.connectfour.grid.ConnectFourGrid.Drop;

import java.awt.Color;

import static edu.nyu.pqs.connectfour.ConnectFourConfigs.*;

/**
 * Auto-manual dualism of ConnectFourPlayer, which implements the Player interface.
 * A manual player can drop at a specific column on the Grid;
 * A auto player can automatically select the optimal drop for next step.
 * The player will update the grid, and reply the drop result to the Model.
 * For building a new player, should not directed accessed, but instead call
 * the ConnectFourPlayerFactory.
 */
public class DualConnectFourPlayer implements ConnectFourPlayer {
  private final Color playerColor;
  private final boolean isAuto;
  
  private DualConnectFourPlayer(Builder builder) {
    if (builder == null) {
      throw new NullPointerException("Cannot accept null Builder.");
    }
    this.playerColor = builder.playerColor;
    this.isAuto = builder.isAuto;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Drop drop(int column, ConnectFourGrid grid) {
    if (isAuto) {
      return grid.autoDrop(playerColor);
    } else {
      return grid.dropResult(column, playerColor);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Color getPlayerColor() {
    if (playerColor == null) {
      throw new NullPointerException("The playerColor hasn't been initialized yet.");
    }
    return playerColor;
  }
  
  /**
   * Builder for instantiating a new Player. Should not directed accessed,
   * but instead call the ConnectFourPlayerFactory.
   */
  public static class Builder {
    private Color playerColor;
    private boolean isAuto;
  
    /**
     * Should not directed accessed, but instead call the ConnectFourPlayerFactory
     * @param player number of the player, can only be int 1 or 2
     */
    public Builder(int player) {
      if (player != 1 && player != 2) {
        throw new IllegalArgumentException("Can only accept player number as 1 or 2");
      }
      playerColor = player == 1 ? player1Color : player2Color;
    }
  
    /**
     * Should not directed accessed, but instead call the ConnectFourPlayerFactory
     * @param isAuto if this is an auto player
     * @return
     */
    public Builder setMode(boolean isAuto) {
      this.isAuto = isAuto;
      return this;
    }
  
    /**
     * Should not directed accessed, but instead call the ConnectFourPlayerFactory
     * @return Builder for Player
     */
    public DualConnectFourPlayer build() {
      return new DualConnectFourPlayer(this);
    }
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof DualConnectFourPlayer)) {
      return false;
    }
    DualConnectFourPlayer player = (DualConnectFourPlayer) obj;
    return player.isAuto == this.isAuto
        && player.playerColor == this.playerColor;
  }
  
  @Override
  public int hashCode() {
    return playerColor.hashCode() + (isAuto ? 1 : -1) * 3;
  }
}
