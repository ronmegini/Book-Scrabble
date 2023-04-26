package test;

import test.Tile;

public class Board {

    Tile[][] boardArray = new Tile[15][15];

    private static Board boardInstance = null;

    private Board() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                boardArray[i][j] = null;
            }
        }
    }

    public static Board getBoard() {
        if (boardInstance == null) {
            boardInstance = new Board();
        }
        return boardInstance;

    }

    public Tile[][] getTiles() {
        return this.boardArray;
    }

    public static void main(String[] args) {
        Board myBoard = Board.getBoard();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.println(myBoard[i][j]);
            }
        }

    }

}
