package edu.nyu.pqs.connectfour;

import edu.nyu.pqs.connectfour.grid.DropEffect;
import edu.nyu.pqs.connectfour.player.ConnectFourPlayer;
import edu.nyu.pqs.connectfour.player.ConnectFourPlayerFactory;
import edu.nyu.pqs.connectfour.grid.ConnectFourGrid;
import edu.nyu.pqs.connectfour.grid.ConnectFourGrid.Drop;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import static edu.nyu.pqs.connectfour.ConnectFourConfigs.*;

/**
 * This is the model for the observer-listener design pattern. User can register/deregister
 * listeners to this model.
 * Evoked by the View, the model will call all listeners to perform actions like
 * start game, end game, etc.
 * The model mainly contains a list of listeners, list of players, game grid,
 * and game mode (single or double). The model can decide which player to play/drop
 * in a turn, update grid, and fire corresponding event according to the drop result.
 */
public class ConnectFourModel {
  
  private static ConnectFourModel model = new ConnectFourModel();
  private List<ConnectFourListener> listeners = new LinkedList<ConnectFourListener>();
  private boolean singleMode = false;
  private int turn = 0;
  private ConnectFourPlayer[] players = new ConnectFourPlayer[nPlayers];
  private ConnectFourGrid grid;
  
  /**
   * singleton method
   * @return this.model
   */
  public static ConnectFourModel getModel() {
    return model;
  }
  
  /**
   * Add the listener to the listeners list, if it hasn't been registered.
   * @param listener an implementation of Listener to be registered
   */
  public void registerListener(ConnectFourListener listener) {
    if (listener == null) {
      throw new NullPointerException("Cannot register Null listener");
    }
    if (!listeners.contains(listener)) {
      listeners.add(listener);
    }
  }
  
  /**
   * Remove the listener from the listeners list.
   * @param listener an implementation of Listener to be removed
   */
  public void deregisterListener(ConnectFourListener listener) {
    if (listener == null) {
      throw new NullPointerException("Cannot register Null listener");
    }
    listeners.remove(listener);
  }
  
  /**
   * Get all listeners of the model.
   * @return the list of listeners
   */
  public List<ConnectFourListener> getListeners() {
    return listeners;
  }
  
  /**
   * @return the current Grid of the game
   */
  public ConnectFourGrid getGrid() {
    return grid;
  }
  
  /**
   * Start the game, and notify all listeners to start the game
   */
  public void startGame() {
    players[0] = ConnectFourPlayerFactory.getPlayer(false, 1);
    players[1] = ConnectFourPlayerFactory.getPlayer(singleMode, 2);
    grid = new ConnectFourGrid();
    turn = 0;
    fireGameStartedEvent();
  }
  
  /**
   * Set Model's mode to be single or double player
   * @param isSingle if the game is single mode or not
   */
  public void setSingleMode(boolean isSingle) {
    singleMode = isSingle;
  }
  
  /**
   * Perform dropping at this column for manual player;
   * auto player may select other column later.
   * By selecting the drop button at this column:
   *    For single mode: first apply drop for manual player1, if the drop doesn't
   *        result in winning the game, or drops at a full column, then automatically
   *        perform the opponent's drop;
   *    For double mode: let this turn's player to drop. If this drop isn't at a full
   *        column, change turn to its opponent.
   * @param column the column # of the drop JButton on the View
   */
  public void drop(int column) {
    if (column >= nColumns || column < 0) {
      throw new IllegalArgumentException("column number should be [0, nColumns-1]");
    }
    if (singleMode) {
      turn = 0;
      Drop drop1 = players[0].drop(column, grid);
      applyDropEffect(drop1);
      if (turn > 0 && drop1.getEffect() != DropEffect.WIN) {
        applyDropEffect(players[1].drop(column, grid));
      }
    } else {
      Drop drop = players[turn].drop(column, grid);
      applyDropEffect(drop);
    }
  }
  
  private void applyDropEffect(Drop drop) {
    if (drop.getEffect() == DropEffect.COL_FULL) {
      fireColFullEvent(drop.getColumn());
      return;
    }
    fireUpdateGridEvent(drop.getRow(), drop.getColumn(), drop.getColor());
    turn = (turn + 1) % nPlayers;
  
    if (drop.getEffect() == DropEffect.DRAW) {
      fireDrawGameEvent();
    } else if (drop.getEffect() == DropEffect.WIN) {
      fireWinGameEvent(drop.getColor());
    }
  }
  
  private void fireGameStartedEvent() {
    for (ConnectFourListener listener : listeners) {
      listener.gameStarted();
    }
  }
  
  private void fireColFullEvent(int column) {
    for (ConnectFourListener listener : listeners) {
      listener.colFull(column);
    }
  }
  
  private void fireDrawGameEvent() {
    for (ConnectFourListener listener : listeners) {
      listener.draw();
      listener.gameover();
    }
  }
  
  private void fireWinGameEvent(Color color) {
    for (ConnectFourListener listener : listeners) {
      listener.win(color);
      listener.gameover();
    }
  }
  
  private void fireUpdateGridEvent(int row, int column, Color color) {
    for (ConnectFourListener listener : listeners) {
      listener.updateGrid(row, column, color);
    }
  }
}
