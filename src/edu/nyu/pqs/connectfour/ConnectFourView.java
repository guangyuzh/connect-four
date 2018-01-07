package edu.nyu.pqs.connectfour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.border.LineBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import java.util.Random;

import static edu.nyu.pqs.connectfour.ConnectFourConfigs.*;

/**
 * A listener of the game, will register to and be evoked by the Model.
 * This View builds a GUI for the game, and will update according to the Model.
 */
public class ConnectFourView implements ConnectFourListener {
  
  private ConnectFourModel model;
  private boolean isSingleMode = false;
  private static Random rand = new Random();
  private final int id = rand.nextInt();
  
  private JFrame frame = new JFrame();
  private JPanel panel = new JPanel(new BorderLayout());
  private JPanel modeSubPanel = new JPanel(new FlowLayout());
  private JPanel gridDropPanel = new JPanel(new BorderLayout());
  private JTextArea gameLogs = new JTextArea(1, 10);
  private JScrollPane logScrollPane = new JScrollPane(gameLogs);
  private JPanel dropSubPanel = new JPanel(new GridLayout(1, nColumns));
  private JPanel gridSubPanel = new JPanel(new GridLayout(nRows, nColumns, 5, 5));
  private JButton start = new JButton("New Game");
  private JRadioButton doubleButton = new JRadioButton("Two players");
  private JRadioButton singleButton = new JRadioButton("Single player vs. computer");
  private JButton[] drops;
  private JLabel[][] grids;
  
  ConnectFourView(ConnectFourModel model) {
    this.model = model;
    model.registerListener(this);
    initView();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void gameStarted() {
    singleButton.setEnabled(false);
    doubleButton.setEnabled(false);
    for (int c = 0; c < drops.length; c++) {
      drops[c].setEnabled(true);
    }
    for (int r = 0; r < grids.length; r++) {
      for (int c = 0; c < grids[0].length; c++) {
        grids[r][c].setBackground(Color.WHITE);
      }
    }
    String mode = isSingleMode ? "Single" : "Double";
    gameLogs.setText(mode + " mode game starts...\n");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void updateGrid(int row, int column, Color color) {
    grids[row][column].setBackground(color);
    int player = color == player1Color ? 1 : 2;
    gameLogs.append(String.format("player %d drops at (%d, %d).\n", player, row, column));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void colFull(int column) {
    gameLogs.append(String.format("Column %d is full. Drop at other columns.\n", column));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void draw() {
    gameLogs.append("\nThis is a draw game.\n");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void win(Color color) {
    int player = color == player1Color ? 1 : 2;
    gameLogs.append(String.format("\nplayer %d wins!\n", player));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void gameover() {
    gameLogs.append("...Game ends. Select game mode to start a new game.");
    singleButton.setEnabled(true);
    doubleButton.setEnabled(true);
    for (int c = 0; c < drops.length; c++) {
      drops[c].setEnabled(false);
    }
  }
    
  private void initView() {
    initModePanel();
    panel.add(modeSubPanel, BorderLayout.NORTH);
    initGridPanel();
    gridDropPanel.add(gridSubPanel, BorderLayout.CENTER);
    initDropPanel();
    gridDropPanel.add(dropSubPanel, BorderLayout.SOUTH);
    panel.add(gridDropPanel, BorderLayout.CENTER);
    initGameLogs();
    panel.add(logScrollPane, SpringLayout.EAST);
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(panel, BorderLayout.CENTER);
    frame.setSize(850, 700);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
  
  private void initGameLogs() {
    gameLogs.setLineWrap(true);
    gameLogs.setWrapStyleWord(true);
    gameLogs.setMargin(new Insets(5, 5, 5, 5));
    gameLogs.setEditable(true);
    gameLogs.append("Welcome to Connect Four game! Please select the game mode.\n");
    logScrollPane.createVerticalScrollBar();
  }
  
  private void initGridPanel() {
    grids = new JLabel[nRows][nColumns];
    for (int r = 0; r < grids.length; r++) {
      for (int c = 0; c < grids[0].length; c++) {
        grids[r][c] = new JLabel();
        grids[r][c].setBorder(new LineBorder(Color.DARK_GRAY));
        grids[r][c].setOpaque(true);
        grids[r][c].setBackground(Color.WHITE);
        gridSubPanel.add(grids[r][c], BorderLayout.CENTER);
      }
    }
  }
  
  private void initModePanel() {
    singleButton.setSelected(false);
    singleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.setSingleMode(true);
        isSingleMode = true;
      }
    });
    doubleButton.setSelected(true);
    doubleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.setSingleMode(false);
        isSingleMode = false;
      }
    });
    ButtonGroup group = new ButtonGroup();
    group.add(singleButton);
    group.add(doubleButton);
    start.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.startGame();
      }
    });
    modeSubPanel.add(start);
    modeSubPanel.add(singleButton);
    modeSubPanel.add(doubleButton);
  }
  
  private void initDropPanel() {
    drops = new JButton[nColumns];
    for (int c = 0; c < drops.length; c++) {
      drops[c] = new JButton("Drop");
      drops[c].setActionCommand(Integer.toString(c));
      drops[c].setBackground(Color.YELLOW);
      drops[c].setOpaque(true);
      drops[c].setEnabled(false);
      drops[c].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          int column = Integer.parseInt(e.getActionCommand());
          model.drop(column);
        }
      });
      dropSubPanel.add(drops[c], BorderLayout.CENTER);
    }
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof ConnectFourView)) {
      return false;
    }
    ConnectFourView view = (ConnectFourView) obj;
    return this.id == view.id;
  }
  
  @Override
  public int hashCode() {
    return id + 1023 * (isSingleMode ? 1 : 0);
  }
  
}
