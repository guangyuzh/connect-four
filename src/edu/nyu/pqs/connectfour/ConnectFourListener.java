package edu.nyu.pqs.connectfour;

import java.awt.Color;

/**
 * This is an interface for the observer-listener design pattern.
 * All the implementations will follow this API, and will be called simultaneously
 * by the Model
 */
public interface ConnectFourListener {
  
  /**
   * Start the game, and may initialize some fields also
   */
  public void gameStarted();
  
  /**
   * Update the Grid for the input spot and color
   * @param row the dropped row
   * @param column the dropped column
   * @param color the dropped color
   */
  public void updateGrid(int row, int column, Color color);
  
  /**
   * Notify this column is full
   * @param column the column in the grid which is full
   */
  public void colFull(int column);
  
  /**
   * Notify the game is draw
   */
  public void draw();
  
  /**
   * Notify the winner of the game
   * @param color the winner's color
   */
  public void win(Color color);
  
  /**
   * End the game.
   */
  public void gameover();
}
