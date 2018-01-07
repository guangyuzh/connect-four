package edu.nyu.pqs.connectfour.grid;

/**
 * The effect of a drop can be one of:
 *    1. SUCCESS: successful/normal drop
 *    2. WIN: drop to win/connect four
 *    3. DRAW: all spots are occupied on the grid, and the game is draw
 *    4. COL_FULL: all rows in the selected column have already been occupied/full
 */
public enum DropEffect {
  SUCCESS,
  COL_FULL,
  WIN,
  DRAW
}
