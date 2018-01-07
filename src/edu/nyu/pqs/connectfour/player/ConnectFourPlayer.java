package edu.nyu.pqs.connectfour.player;

import edu.nyu.pqs.connectfour.grid.ConnectFourGrid;
import edu.nyu.pqs.connectfour.grid.ConnectFourGrid.Drop;

import java.awt.*;

/**
 * Interface for the game player.
 * It's generic for both mode (manual or auto player)
 */
public interface ConnectFourPlayer {
  
  /**
   * get Player's color
   * @return player's color
   */
  public Color getPlayerColor();
  
  /**
   * Drops at the select column on the current grid.
   * It's called by the Model, and will call drop related methods in
   * the Grid class to real decide the dropping results.
   * @param column the selected column by the manual player;
   *               will be ignored by the auto player in the single mode
   * @param grid current game Grid containing all the dropping info/status
   * @return Drop result. It will be passed from the Grid methods to the Model.
   */
  public Drop drop(int column, ConnectFourGrid grid);
  
}
