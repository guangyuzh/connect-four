package edu.nyu.pqs.connectfour.player;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ConnectFourPlayerFactoryTest {
  
  public ConnectFourPlayerFactoryTest() {
  }
  
  @Test
  public void testGetPlayer() {
    ConnectFourPlayer player1 = new
        DualConnectFourPlayer.Builder(1).setMode(false).build();
    ConnectFourPlayer player2 = ConnectFourPlayerFactory.getPlayer(false, 1);
    assertTrue(player1.equals(player2));
  }
  
  @Test
  public void testGetPlayer_wrongPlayerNum() {
    try {
      ConnectFourPlayerFactory.getPlayer(false, 3);
    } catch (IllegalArgumentException e) {
      return;
    }
    fail();
  }
  
}