package edu.nyu.pqs.connectfour.player;

import edu.nyu.pqs.connectfour.grid.ConnectFourGrid;
import edu.nyu.pqs.connectfour.grid.ConnectFourGrid.Drop;
import edu.nyu.pqs.connectfour.grid.DropEffect;
import org.junit.Before;
import org.junit.Test;

import static edu.nyu.pqs.connectfour.ConnectFourConfigs.player1Color;
import static edu.nyu.pqs.connectfour.ConnectFourConfigs.player2Color;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DualConnectFourPlayerTest {
  private ConnectFourPlayer manualPlayer;
  private ConnectFourPlayer autoPlayer;
  private ConnectFourGrid grid = new ConnectFourGrid();
  
  public DualConnectFourPlayerTest() {
    manualPlayer = ConnectFourPlayerFactory.getPlayer(false, 1);
    autoPlayer = ConnectFourPlayerFactory.getPlayer(true, 2);
  }
  
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
  public void testGetPlayerColor() throws Exception {
    assertEquals(manualPlayer.getPlayerColor(), player1Color);
    assertEquals(autoPlayer.getPlayerColor(), player2Color);
  }
  
  @Test
  public void testDrop_success() throws Exception {
    Drop drop = manualPlayer.drop(5, grid);
    assertEquals(drop.getEffect(), DropEffect.SUCCESS);
    assertEquals(drop.getRow(), 4);
    assertEquals(drop.getColumn(), 5);
    assertEquals(drop.getColor(), manualPlayer.getPlayerColor());
  }
  
  @Test
  public void testDrop_colFull() throws Exception {
    grid.dropResult(3, player1Color);
    grid.dropResult(4, player2Color);
    grid.dropResult(3, player1Color);
    grid.dropResult(3, player2Color);
    Drop drop = manualPlayer.drop(3, grid);
    assertEquals(drop.getEffect(), DropEffect.COL_FULL);
    assertEquals(drop.getColumn(), 3);
    assertEquals(drop.getRow(), -1);
    assertEquals(drop.getColor(), manualPlayer.getPlayerColor());
  }
  
  @Test
  public void testDrop_win() {
    grid.dropResult(1, player1Color);
    grid.dropResult(5, player2Color);
    Drop drop = manualPlayer.drop(0, grid);
    assertEquals(drop.getEffect(), DropEffect.WIN);
    assertEquals(drop.getColumn(), 0);
    assertEquals(drop.getRow(), 5);
    assertEquals(drop.getColor(), manualPlayer.getPlayerColor());
  }
  
  @Test
  public void testDrop_autoWin() {
    grid.dropResult(2, player1Color);
    grid.dropResult(1, player2Color);
    grid.dropResult(2, player1Color);
    Drop drop = autoPlayer.drop(-1, grid);
    assertEquals(drop.getEffect(), DropEffect.WIN);
    assertEquals(drop.getColumn(), 2);
    assertEquals(drop.getRow(), 2);
    assertEquals(drop.getColor(), autoPlayer.getPlayerColor());
  }
  
  @Test
  public void testEquals() {
    assertFalse(manualPlayer.equals(autoPlayer));
    assertTrue(manualPlayer.equals(manualPlayer));
    assertFalse(manualPlayer.equals(null));
  }
 
}