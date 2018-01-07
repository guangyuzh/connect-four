package edu.nyu.pqs.connectfour.grid;

import java.awt.Color;
import java.util.Random;

import static edu.nyu.pqs.connectfour.ConnectFourConfigs.*;

/**
 * This class represents the game grid, containing colors of each drop,
 * and the spots occupied in each column. Methods are provided to
 * inform the effect (success, win, column already fully occupied, draw)
 * for a player/color after dropping at the specified column. It can also
 * decided the next optimal draw if called by the single-player mode.
 * The information will be shared among Model and Player.
 */
public class ConnectFourGrid {
  
  private static final int nConnect = 4;
  // colors to represent players' drops
  // 0: dropped by player1; 1: dropped by player2
  private Color[][] colors = new Color[nRows][nColumns];
  private int[] rowOccupied = new int[nColumns];
  private int totOccupiedGrids = 0;
  private static final int totGrids = nRows * nColumns;
  
  /**
   * Constructor
   * initialized no rows are occupied in all columns
   */
  public ConnectFourGrid() {
    for (int r = 0; r < rowOccupied.length; r++) {
      rowOccupied[r] = nRows;
    }
  }
  
  /**
   * get Grid's spot-wise color
   * @return all colors for each spot on the Grid
   */
  public Color[][] getColors() {
    return colors;
  }
  
  /**
   * Automatically select the next optimal drop in this order:
   *     1. has a drop for this player to win
   *        (its color connect four)
   *     2. if not, has a drop to prevent the opponent to win
   *        (other's color connect four)
   *     3. if not, has a drop to approach winning
   *        (its color connect three)
   *     4. if not, has a drop to prevent the opponent approaching winning
   *        (other's color connect three)
   *     5. if not, randomly choose a non-full column.
   * This method is only for single mode.
   * @param playColor the color of the player who makes this drop
   * @return the automatically selected drop, (including effects, etc.)
   */
  public Drop autoDrop(Color playColor) {
    int coloum = tryDrop(playColor, nConnect);
    Color opponent = playColor == player1Color ? player2Color : player1Color;
    if (coloum == -1) {
      coloum = tryDrop(opponent, nConnect);
    }
    if (coloum == -1) {
      coloum = tryDrop(playColor, nConnect - 1);
    }
    if (coloum == -1) {
      coloum = tryDrop(opponent, nConnect - 1);
    }
    if (coloum == -1) {
      while (true) {
        Random rand = new Random();
        coloum = rand.nextInt(nColumns);
        if (rowOccupied[coloum] > 0) {
          break;
        }
      }
    }
    return dropResult(coloum, playColor);
  }
  
  /**
   * Decides the drop effect and row for this player to drop at the specified column.
   * The effect can be one of:
   *    1. successful/normal drop
   *    2. drop to win/connect four
   *    3. all spots are occupied on the grid, and the game is draw
   *    4. all rows in the selected column have already been occupied/full (return row = -1)
   * @param column the selected column
   * @param playerColor the player/dropper's color
   * @return Drop type containing information; row will be -1 if all rows are occupied
   *         in this column
   */
  public Drop dropResult(int column, Color playerColor) {
    Drop result = new Drop(-1, column, playerColor);
    if (rowOccupied[column] > 0) {
      rowOccupied[column] --;
      result.row = rowOccupied[column];
      colors[result.row][column] = playerColor;
      totOccupiedGrids ++;
      Drop lastDrop = new Drop(result.row, column, playerColor);
      if (willHaveConnect(lastDrop, nConnect)) {
        result.effect = DropEffect.WIN;
      } else if (totOccupiedGrids == totGrids) {
        result.effect = DropEffect.DRAW;
      } else {
        result.effect = DropEffect.SUCCESS;
      }
    } else {
      result.effect = DropEffect.COL_FULL;
    }
    return result;
  }
  
  private int tryDrop(Color color, int length) {
    for (int col = 0; col < nColumns; col++) {
      int row = rowOccupied[col] - 1;
      if (row >= 0) {
        Drop trial = new Drop(row, col, color);
        if (willHaveConnect(trial, length)) {
          return col;
        }
      }
    }
    return -1;
  }
  
  private boolean willHaveConnect(Drop drop, int length) {
    return verticalConnect(drop) >= length
        || horizontalConnect(drop) >= length
        || diagonal1Connect(drop) >= length
        || diagonal2Connect(drop) >= length;
  }
  
  private int verticalConnect(Drop drop) {
    int cnt = 1;
    int row = drop.getRow() - 1;
    while (row >= 0 && colors[row][drop.column] == drop.color) {
      cnt ++;
      row --;
    }
    row = drop.getRow() + 1;
    while (row < nRows && colors[row][drop.column] == drop.color) {
      cnt ++;
      row ++;
    }
    return cnt;
  }
  
  private int horizontalConnect(Drop drop) {
    int cnt = 1;
    int col = drop.getColumn() - 1;
    while (col >= 0 && colors[drop.getRow()][col] == drop.color) {
      cnt ++;
      col --;
    }
    col = drop.getColumn() + 1;
    while (col < nColumns && colors[drop.getRow()][col] == drop.color) {
      cnt ++;
      col ++;
    }
    return cnt;
  }
  
  private int diagonal1Connect(Drop drop) {
    int cnt = 1;
    int col = drop.getColumn() - 1;
    int row = drop.getRow() - 1;
    while (col >= 0 && row >= 0
        && colors[row][col] == drop.color) {
      cnt ++;
      col --;
      row --;
    }
    col = drop.getColumn() + 1;
    row = drop.getRow() + 1;
    while (col < nColumns && row < nRows
        && colors[row][col] == drop.color) {
      cnt ++;
      col ++;
      row ++;
    }
    return cnt;
  }
  
  private int diagonal2Connect(Drop drop) {
    int cnt = 1;
    int col = drop.getColumn() + 1;
    int row = drop.getRow() - 1;
    while (col < nColumns && row >= 0
        && colors[row][col] == drop.color) {
      cnt ++;
      col ++;
      row --;
    }
    col = drop.getColumn() - 1;
    row = drop.getRow() + 1;
    while (col >= 0 && row < nRows
        && colors[row][col] == drop.color) {
      cnt ++;
      col --;
      row ++;
    }
    return cnt;
  }
  
  /**
   * This class informs results after making a drop. It will be decided and
   * returned by other methods in the Grid class, to inform model the dropping
   * results.
   */
  public class Drop {
    private int column;
    private int row;
    private Color color;
    private DropEffect effect;
  
    /**
     * Constructor
     * @param row the dropped row, decided by the lowest non-occupied row in the grid
     * @param column the dropped column, either decided by the manual player, or selected
     *               automatically by methods in the Grid class.
     * @param color the dropper/player's color, can either be player1Color or player2Color
     */
    Drop(int row, int column, Color color) {
      if (color != player1Color && color != player2Color) {
        throw new IllegalArgumentException("Unknown color.");
      }
      this.row = row;
      this.column = column;
      this.color = color;
    }
  
    /**
     * get Drop's color
     * @return  player/dropper's color
     */
    public Color getColor() {
      return color;
    }
  
    /**
     * get Drop's row
     * @return dropped row
     */
    public int getRow() {
      return row;
    }
  
    /**
     * get Drop's column
     * @return dropped column
     */
    public int getColumn() {
      return column;
    }
  
    /**
     * get Drop's effect
     * @return dropped effect
     */
    public DropEffect getEffect() {
      return effect;
    }
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof ConnectFourGrid)) {
      return false;
    }
    ConnectFourGrid gd = (ConnectFourGrid) obj;
    return colors == gd.colors
        && rowOccupied == gd.rowOccupied
        && totOccupiedGrids == gd.totOccupiedGrids;
  }
  
  @Override
  public int hashCode() {
    int hash = 13;
    for (Color[] cs : colors) {
      for (Color c : cs) {
        if (c != null) {
          hash += (c == player1Color ? 53 : 79);
        }
      }
    }
    for (int r : rowOccupied) {
      hash += 29 * r;
    }
    return hash + 1023 * totOccupiedGrids;
  }
}
