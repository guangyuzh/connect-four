package edu.nyu.pqs.connectfour;

import edu.nyu.pqs.connectfour.grid.ConnectFourGrid;
import org.junit.Test;

import java.awt.Color;

import static edu.nyu.pqs.connectfour.ConnectFourConfigs.player1Color;
import static edu.nyu.pqs.connectfour.ConnectFourConfigs.player2Color;
import static org.junit.Assert.*;

public class ConnectFourModelTest {
  
  private ConnectFourModel model = new ConnectFourModel();
  private ConnectFourView view = new ConnectFourView(model);
  private ConnectFourLogger logger = new ConnectFourLogger(model);
  
  public ConnectFourModelTest() {
    model.registerListener(view);
    model.registerListener(logger);
  }
  
  @Test
  public void testRegisterListener() {
    assertTrue(model.getListeners().contains(view));
    assertTrue(model.getListeners().contains(logger));
  }
  
  @Test
  public void testRegisterListener_null() {
    try {
      model.registerListener(null);
    } catch (NullPointerException e) {
      return;
    }
    fail();
  }
  
  @Test
  public void testDeregisterListener() {
    model.deregisterListener(view);
    assertFalse(model.getListeners().contains(view));
  }
  
  @Test
  public void testStartGame() {
    model.startGame();
    // Will notice on the console/GUI
  }
  
  @Test
  public void testDrop_singleMode() {
    model.setSingleMode(true);
    model.startGame();
    model.drop(3);
    model.drop(4);
    ConnectFourGrid grid = model.getGrid();
    int cnt = 0;
    for (Color[] gs : grid.getColors()) {
      for (Color g : gs) {
        if (g == player2Color) {
          cnt ++;
        }
      }
    }
    // Made automatic drops after each player's drop
    assertEquals(cnt, 2);
  }
  
  @Test
  public void testDrop_singleMode_colFul() {
    model.setSingleMode(true);
    model.startGame();
    model.drop(3);
    model.drop(3);
    model.drop(3);
    model.drop(3);
    model.drop(3);
    model.drop(3);
    ConnectFourGrid grid = model.getGrid();
    int cnt = 0;
    for (Color[] gs : grid.getColors()) {
      for (Color g : gs) {
        if (g == player2Color) {
          cnt ++;
        }
      }
    }
    // player has some retries, due to column full
    assertTrue(cnt < 6);
  }
  
  @Test
  public void testDrop_double() {
    model.startGame();
    model.setSingleMode(false);
    model.drop(3);
    model.drop(4);
    model.drop(3);
    model.drop(4);
    ConnectFourGrid grid = model.getGrid();
    int cnt1 = 0;
    int cnt2 = 0;
    for (Color[] gs : grid.getColors()) {
      for (Color g : gs) {
        if (g == player1Color) {
          cnt1 ++;
        }
        if (g == player2Color) {
          cnt2 ++;
        }
      }
    }
    // Players manually drop in turns.
    assertEquals(cnt1, 2);
    assertEquals(cnt2, 2);
  }
  
}