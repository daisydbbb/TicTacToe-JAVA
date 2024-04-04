package tictactoe;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TicTacToeModel implements TicTacToe {
  // add your implementation here
  private final Player[][] board;
  private Player nextPlayer;

  public TicTacToeModel() {
    this.board = new Player[3][3];
    this.nextPlayer = Player.X; // start with player X
  }


  @Override
  public String toString() {
    // Using Java stream API to save code:
    return Arrays.stream(getBoard()).map(
            row -> " " + Arrays.stream(row).map(
                p -> p == null ? " " : p.toString()).collect(Collectors.joining(" | ")))
        .collect(Collectors.joining("\n-----------\n"));
    // This is the equivalent code as above, but using iteration, and still using
    // the helpful built-in String.join method.
    /**********
     List<String> rows = new ArrayList<>();
     for(tictactoe.Player[] row : getBoard()) {
     List<String> rowStrings = new ArrayList<>();
     for(tictactoe.Player p : row) {
     if(p == null) {
     rowStrings.add(" ");
     } else {
     rowStrings.add(p.toString());
     }
     }
     rows.add(" " + String.join(" | ", rowStrings));
     }
     return String.join("\n-----------\n", rows);
     ************/
  }

  @Override
  public void move(int r, int c) throws IllegalArgumentException, IllegalStateException {
    // position already occupied
    if (getMarkAt(r, c) != null) {
      throw new IllegalArgumentException("The position is invalid!");
    }
    if (isGameOver()) {
      throw new IllegalStateException("The game is over!");
    }
    board[r][c] = nextPlayer;
    // switch turn
    nextPlayer = nextPlayer == Player.X ? Player.O: Player.X;
  }

  @Override
  public Player getTurn() {
    return isGameOver() ? null: nextPlayer;
  }

  @Override
  public boolean isGameOver() {
    return HorizontalWin() != null || VerticalWin()!= null || DiagonalWin()!= null || isBoardFull();
  }

  private boolean checkEqual(Player a, Player b, Player c) {
    return a != null && a.equals(b) && a.equals(c);
  }

  public Player HorizontalWin() {
    for (int i = 0; i< 3; i++) {
      if (checkEqual(board[i][0], board[i][1], board[i][2])) {
        return board[i][0];
      }
    }
    return null;
  }

  public Player VerticalWin() {
    for (int i = 0; i< 3; i++) {
      if (checkEqual(board[0][i], board[1][i], board[2][i] )) {
        return board[0][i];
      }
    }
    return null;
  }

  public Player DiagonalWin(){
    if (checkEqual(board[0][0], board[1][1], board[2][2])
            || checkEqual(board[0][2], board[1][1], board[2][0])) {
      return board[1][1];
    }
    return null;
  }

  public boolean isBoardFull() {
    for (int i=0; i< 3; i++) {
      for (int j=0; j<3; j++) {
        if (board[i][j] == null) {
          return false;
        }
      }
    }
    return true;
  }


  @Override
  public Player getWinner() {
    if (!isGameOver()) {
      return null;
    }
    if (HorizontalWin() != null) return HorizontalWin();
    if (VerticalWin() != null) return VerticalWin();
    if (DiagonalWin() != null) return DiagonalWin();
    return null;

  }

  @Override
  public Player[][] getBoard() {
    Player[][] boardCopy = new Player[3][3];
    for (int i=0; i< 3; i++) {
      System.arraycopy(board[i], 0, boardCopy[i], 0, 3);
//      for (int j =0; j<3; j++) {
//        boardCopy[i][j] = board[i][j];
//      }
    }
    return boardCopy;
  }

  @Override
  public Player getMarkAt(int r, int c) throws IllegalArgumentException {
    if (r < 0 || r > 2 || c < 0 || c > 2) {
      throw new IllegalArgumentException("Out of board boundary!");
    }
    return board[r][c];
  }
}
