package edu.nyu.pqs.connectfour.grid;

import edu.nyu.pqs.connectfour.grid.ConnectFourGrid.Drop;
import org.junit.Before;
import org.junit.Test;

import static edu.nyu.pqs.connectfour.ConnectFourConfigs.nColumns;
import static edu.nyu.pqs.connectfour.ConnectFourConfigs.player1Color;
import static edu.nyu.pqs.connectfour.ConnectFourConfigs.player2Color;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ConnectFourGridTest {
  
  private ConnectFourGrid grid = new ConnectFourGrid();
  
  @Before
  public void setupGrid() {
    grid.dropResult(3, player1Color);
    grid.dropResult(4, player2Color);
    grid.dropResult(3, player1Color);
    grid.dropResult(3, player2Color);
    grid.dropResult(2, player1Color);
    grid.dropResult(4, player2Color);
    grid.dropResult(4, player1Color);
    grid.dropResult(5, player2Color);
  }
  
  @Test
  public void testAutoDrop() {
    grid.dropResult(1, player1Color);
    
    // Prevent opponent to win
    Drop drop  = grid.autoDrop(player2Color);
    assertEquals(drop.getColumn(), 0);
    assertEquals(drop.getRow(), 5);
    assertEquals(drop.getEffect(), DropEffect.SUCCESS);
    assertEquals(drop.getColor(), player2Color);
    
    // Try to connect three
    grid.dropResult(2, player1Color);
    drop = grid.autoDrop(player2Color);
    assertEquals(drop.getColumn(), 6);
    assertEquals(drop.getRow(), 5);
    assertEquals(drop.getEffect(), DropEffect.SUCCESS);
    assertEquals(drop.getColor(), player2Color);
    
    // Prevent opponent to connect three
    grid.dropResult(5, player1Color);
    drop = grid.autoDrop(player2Color);
    assertEquals(drop.getColumn(), 1);
    assertEquals(drop.getRow(), 4);
    assertEquals(drop.getEffect(), DropEffect.SUCCESS);
    assertEquals(drop.getColor(), player2Color);
    
    // Will avoid full column(s)
    grid.dropResult(3, player1Color);
    grid.dropResult(3, player2Color);
    grid.dropResult(3, player1Color);
    drop = grid.autoDrop(player2Color);
    assertNotEquals(drop.getColumn(), 3);
    assertEquals(drop.getEffect(), DropEffect.SUCCESS);
    assertEquals(drop.getColor(), player2Color);
  }
  
  @Test
  public void testAutoDrop_random() {
    grid = new ConnectFourGrid();
    grid.dropResult(1, player1Color);
    Drop drop = grid.autoDrop(player2Color);
    assertTrue(drop.getColumn() >= 0);
    assertTrue(drop.getColumn() < nColumns);
    assertEquals(drop.getEffect(), DropEffect.SUCCESS);
    assertEquals(drop.getColor(), player2Color);
  }
  
  @Test
  public void testDropResult_colFull() {
    grid.dropResult(3, player1Color);
    grid.dropResult(4, player2Color);
    grid.dropResult(3, player1Color);
    grid.dropResult(3, player2Color);
    Drop drop = grid.dropResult(3, player1Color);
    assertEquals(drop.getEffect(), DropEffect.COL_FULL);
    assertEquals(drop.getColumn(), 3);
    assertEquals(drop.getRow(), -1);
    assertEquals(drop.getColor(), drop.getColor());
  }
  
  @Test
  public void testEquals() {
    ConnectFourGrid grid1 = new ConnectFourGrid();
    assertFalse(grid.equals(grid1));
    assertFalse(grid.equals(null));
    ConnectFourGrid grid2 = grid;
    assertTrue(grid.equals(grid2));
  }
  
}