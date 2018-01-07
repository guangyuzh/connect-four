package edu.nyu.pqs.connectfour;

import java.awt.Color;
import java.util.Random;
import java.util.logging.Logger;

import static edu.nyu.pqs.connectfour.ConnectFourConfigs.*;

/**
 * A listener of the game, will register to and be evoked by the Model
 * Will log game info (start, moves, wins, etc.) to the console.
 */
public class ConnectFourLogger implements ConnectFourListener {
  
  private static Random rand = new Random();
  private final int id = rand.nextInt();
  
  private static final Logger LOGGER = Logger.getLogger(ConnectFourListener.class.getName());
  
  public ConnectFourLogger(ConnectFourModel model) {
    model.registerListener(this);
  }
  
  public Logger getLogger() {
    return LOGGER;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void gameStarted() {
    LOGGER.info("Game started!\n");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void updateGrid(int row, int column, Color color) {
    int player = color == player1Color ? 1 : 2;
    LOGGER.info(String.format("player %d drops at (%d, %d).", player, row, column));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void colFull(int column) {
    LOGGER.info(String.format("Column %d is full. Drop at other columns", column));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void draw() {
    LOGGER.info("This is a draw game.");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void win(Color color) {
    int player = color == player1Color ? 1 : 2;
    LOGGER.info(String.format("player %d wins!", player));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void gameover() {
    LOGGER.info("Game ends. select game mode to start new game.");
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof ConnectFourLogger)) {
      return false;
    }
    ConnectFourLogger view = (ConnectFourLogger) obj;
    return this.id == view.id;
  }
  
  @Override
  public int hashCode() {
    return id;
  }
  
}
