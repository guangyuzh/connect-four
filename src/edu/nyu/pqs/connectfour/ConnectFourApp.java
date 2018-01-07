package edu.nyu.pqs.connectfour;

/**
 * Run the Connect Four game App
 */
public class ConnectFourApp {
  
  private void go() {
    ConnectFourModel model = ConnectFourModel.getModel();
    new ConnectFourView(model);
    new ConnectFourLogger(model);
  }

  public static void main(String[] args) {
    new ConnectFourApp().go();
  }

}
