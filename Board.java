package test;

import java.util.ArrayList;

public class Board {
    private static Board board_instance = null;
    Tile[][] board;
    String[][] bonusSquares;
    boolean firstWordPlacedOnBoard;

    private Board() {
        String[][] bonusSquaresCopy = {
                { "Red", null, null, "LightBlue", null, null, null, "Red", null, null, null, "LightBlue", null, null,
                        "Red" },
                { null, "Yellow", null, null, null, "Blue", null, null, null, "Blue", null, null, null, "Yellow",
                        null },
                { null, null, "Yellow", null, null, null, "LightBlue", null, "LightBlue", null, null, null, "Yellow",
                        null, null },
                { "LightBlue", null, null, "Yellow", null, null, null, "LightBlue", null, null, null, "Yellow", null,
                        null, "LigthBlue" },
                { null, null, null, null, "Yellow", null, null, null, null, null, "Yellow", null, null, null, null },
                { null, "Blue", null, null, null, "Blue", null, null, null, "Blue", null, null, null, "Blue", null },
                { null, null, "LightBlue", null, null, null, "LightBlue", null, "LightBlue", null, null, null,
                        "LightBlue", null, null },
                { "Red", null, null, "LightBlue", null, null, null, "Star", null, null, null, "LightBlue", null, null,
                        "Red" },
                { null, null, "LightBlue", null, null, null, "LightBlue", null, "LightBlue", null, null, null,
                        "LightBlue", null, null },
                { null, "Blue", null, null, null, "Blue", null, null, null, "Blue", null, null, null, "Blue", null },
                { null, null, null, null, "Yellow", null, null, null, null, null, "Yellow", null, null, null, null },
                { "LightBlue", null, null, "Yellow", null, null, null, "LightBlue", null, null, null, "Yellow", null,
                        null, "LigthBlue" },
                { null, null, "Yellow", null, null, null, "LightBlue", null, "LightBlue", null, null, null, "Yellow",
                        null, null },
                { null, "Yellow", null, null, null, "Blue", null, null, null, "Blue", null, null, null, "Yellow",
                        null },
                { "Red", null, null, "LightBlue", null, null, null, "Red", null, null, null, "LightBlue", null, null,
                        "Red" },
        };
        board = new Tile[15][15];
        bonusSquares = new String[15][15];

        // Initiate board and bonuses
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = null;
                bonusSquares[i][j] = bonusSquaresCopy[i][j];
            }
        }

        // First word already been placed
        firstWordPlacedOnBoard = false;
    }

    public static Board getBoard() {
        if (board_instance == null) {
            board_instance = new Board();
        }

        return board_instance;
    }

    public Tile[][] getTiles() {
        return board.clone();
    }

    public boolean boardLegal(Word word) {
        if (!isWordFitInBoard(word)) {
            return false;
        }

        if (isFirstWord(word)) {
            return true;
        } else {

            if (!isWordLeanOnTile(word)) {
                return false;
            }

            if (isWordSwapTile(word)) {
                return false;
            }
            return true;
        }

    }

    private boolean isWordFitInBoard(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        int wordLength = word.getTiles().length;
        int lastLetterPos = word.isVertical() ? row + wordLength - 1 : col + wordLength - 1;

        // Checks if the word fits within the bounding of the board
        if (row < 0 || col < 0 || lastLetterPos >= board.length) {
            return false;
        }

        // Checking if first word is placed in the center of the board
        if (!firstWordPlacedOnBoard) {
            int boardCenter = board.length / 2;
            int firstLetterPos = word.isVertical() ? row : col;
            int axisIndex = word.isVertical() ? col : row;

            return axisIndex == boardCenter && firstLetterPos <= boardCenter && boardCenter <= lastLetterPos;
        }

        return true;
    }

    public static boolean isBetween(int value, int min, int max) {
        return ((value >= min) && (value <= max));
    }

    public boolean isFirstWord(Word checkedWord) {
        int position, start, end;
        if (checkedWord.isVertical()) {
            position = checkedWord.col;
            start = checkedWord.row;
            end = checkedWord.row + checkedWord.tiles.length;
        } else {
            position = checkedWord.row;
            start = checkedWord.col;
            end = checkedWord.col + checkedWord.tiles.length;
        }
        if ((position == 7) && isBetween(start, 0, 7) && isBetween(end, 7, 14)) {
            return true;
        }
        return false;
    }

    private boolean isWordSwapTile(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        boolean isVertical = word.isVertical();
        Tile[] wordTiles = word.getTiles();

        int emptyTileCount = 0;
        int sameTileCount = 0;

        for (int i = 0; i < wordTiles.length; i++) {
            Tile currentBoardTile = isVertical ? board[row + i][col] : board[row][col + i];
            Tile currentWordTile = wordTiles[i];

            if (currentBoardTile == null && currentWordTile == null) {
                emptyTileCount++;
            } else if (currentBoardTile != null && currentWordTile != null && currentBoardTile == currentWordTile) {
                sameTileCount++;
            }
        }

        return emptyTileCount > 0 || sameTileCount > 0;
    }

    private boolean isWordLeanOnTile(Word word) {
        if (!firstWordPlacedOnBoard) {// First word does not need to be leaned on tile (need to be leaned on "Star")
            return true;
        }

        if (isWordOverLapTile(word) || isWordAdjacentToTile(word)) {
            return true;
        }

        return false;
    }

    private boolean isWordOverLapTile(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        boolean isVertical = word.isVertical();
        int wordLength = word.getTiles().length;
        boolean isOverlapping = false;

        // Check if any tile on the board intersects with the new word
        for (int i = 0; i < wordLength; i++) {
            if (isVertical) {
                if (row + i >= board.length) {
                    break;
                }
                if (board[row + i][col] != null) {
                    isOverlapping = true;
                    break;
                }
            } else {
                if (col + i >= board[0].length) {
                    break;
                }
                if (board[row][col + i] != null) {
                    isOverlapping = true;
                    break;
                }
            }
        }

        return isOverlapping;
    }

    private boolean isWordAdjacentToTile(Word word) {// Returns true if there is any tile near to word so it is not
                                                     // "floating" in board

        int row = word.getRow();
        int col = word.getCol();
        int wordLength = word.getTiles().length;
        boolean isVertical = word.isVertical();

        // Checking ending tiles

        // Getting the tile position that above the word if vertical or the tile
        // position that is left to the word if horizontal
        int tileRow = isVertical ? row - 1 : row;
        int tileCol = isVertical ? col : col - 1;

        // Checking if the above tile or the left tile within the board bounding
        // And if it is not null returns true becuase the word adjacent to a tile
        boolean isAdjacent = isVertical ? tileRow > 0 && board[tileRow][tileCol] != null
                : tileCol > 0 && board[tileRow][tileCol] != null;
        if (isAdjacent) {
            return true;
        }

        // Getting the tile position that under the word if vertical or the tile
        // position that is right to the word if horizontal
        tileRow = isVertical ? row + wordLength : row;
        tileCol = isVertical ? col + wordLength : col;

        // Checking if the under tile or the right tile within the board bounding
        // And if it is not null returns true becuase the word adjacent to a tile
        isAdjacent = isVertical ? tileRow < board.length && board[tileRow][tileCol] != null
                : tileCol < board.length && board[tileRow][tileCol] != null;
        if (isAdjacent) {
            return true;
        }

        // Checking the whole word nearby tiles
        isAdjacent = isVertical ? tileLeftToWord(word) || tileRightToWord(word)
                : tileUnderTheWord(word) || tileAboveTheWord(word);
        return isAdjacent;
    }

    private boolean tileUnderTheWord(Word word) {
        if (word.getRow() - 1 < 0) {
            return false;
        }

        int row = word.getRow() - 1;
        int col = word.getCol();
        int wordLength = word.getTiles().length;

        for (int i = 0; i < wordLength; i++) {
            if (board[row][col + i] != null) {
                return true;
            }
        }

        return false;
    }

    private boolean tileAboveTheWord(Word word) {
        if (word.getRow() + 1 <= board.length) {
            return false;
        }

        int row = word.getRow() + 1;
        int col = word.getCol();
        int wordLength = word.getTiles().length;

        for (int i = 0; i < wordLength; i++) {
            if (board[row][col + i] != null) {
                return true;
            }
        }

        return false;
    }

    private boolean tileLeftToWord(Word word) {
        if (word.getCol() - 1 < 0) {
            return false;
        }

        int row = word.getRow();
        int col = word.getCol() - 1;
        int wordLength = word.getTiles().length;

        for (int i = 0; i < wordLength; i++) {
            if (board[row + i][col] != null) {
                return true;
            }
        }

        return false;
    }

    private boolean tileRightToWord(Word word) {
        if (word.getCol() + 1 <= board.length) {
            return false;
        }

        int row = word.getRow();
        int col = word.getCol() + 1;
        int wordLength = word.getTiles().length;

        for (int i = 0; i < wordLength; i++) {
            if (board[row + i][col] != null) {
                return true;
            }
        }

        return false;
    }

    public boolean dictionaryLegal(Word word) {
        return true;
    }

    public ArrayList<Word> getWords(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        boolean isVertical = word.isVertical();
        Tile[] wordTiles = word.getTiles();
        ArrayList<Word> newWords = new ArrayList<Word>();

        // Placing the word for checking other word creation
        for (int i = 0; i < wordTiles.length; i++) {
            Tile currentBoardTile = isVertical ? board[row + i][col] : board[row][col + i];

            if (currentBoardTile == null) {
                board[isVertical ? row + i : row][isVertical ? col : col + i] = wordTiles[i];
            }
        }

        // Vertical Word - finding the longest vertical new created word
        // Horizontal Word - finding the longest horizontal new created word
        int startWordIndex = isVertical ? findTopTileRow(row, col) : findLeftTileCol(row, col);
        int endWordIndex = isVertical ? findBottomTileRow(row, col) : findRightTileCol(row, col);

        // Create the new word found
        Word newWord = isVertical ? createLongestVertical(startWordIndex, endWordIndex, col)
                : createLongestHorizontal(startWordIndex, endWordIndex, row);

        newWords.add(newWord);

        // Vertical Word - finding horizontal word from each Tile of the new vertical
        // word
        // Horizontal Word - finding vertical word from each Tile of the new Horizontal
        // word
        for (int i = 0; i < wordTiles.length; i++) {
            if (wordTiles[i] == null) {
                continue;
            }

            int startSubWordIndex = isVertical ? findLeftTileCol(row + i, col) : findTopTileRow(row, col + i);
            int endSubWordIndex = isVertical ? findRightTileCol(row + i, col) : findBottomTileRow(row, col + i);

            if (startSubWordIndex != endSubWordIndex) {// Create the new word found
                newWord = isVertical ? createLongestHorizontal(startSubWordIndex, endSubWordIndex, row + i)
                        : createLongestVertical(startSubWordIndex, endSubWordIndex, col + i);
                newWords.add(newWord);
            }
        }

        // Taking the word out of the board
        for (int i = 0; i < wordTiles.length; i++) {
            if (wordTiles[i] != null) {
                board[isVertical ? row + i : row][isVertical ? col : col + i] = null;
            }
        }

        return newWords;
    }

    private int findRightTileCol(int row, int col) {
        int currentCol = col;
        while (currentCol < board.length && board[row][currentCol] != null) {
            currentCol++;
        }
        return currentCol - 1;
    }

    private int findLeftTileCol(int row, int col) {
        int currentCol = col;
        while (currentCol >= 0 && board[row][currentCol] != null) {
            currentCol--;
        }
        return currentCol + 1;
    }

    private int findBottomTileRow(int row, int col) {
        int currentRow = row;
        while (currentRow < board.length && board[currentRow][col] != null) {
            currentRow++;
        }
        return currentRow - 1;
    }

    private int findTopTileRow(int startingRow, int column) {
        int currentRow = startingRow;

        while (currentRow > 0 && board[currentRow - 1][column] != null) {
            currentRow--;
        }

        return currentRow;
    }

    private Word createLongestVertical(int startWordRow, int endWordRow, int col) {// Create the new word from the new
                                                                                   // tiles in the board
        Tile[] newWordTiles = new Tile[endWordRow - startWordRow + 1];

        for (int i = 0; i < newWordTiles.length; i++) {
            newWordTiles[i] = board[startWordRow + i][col];
        }

        return new Word(newWordTiles, startWordRow, col, true);
    }

    private Word createLongestHorizontal(int startWordCol, int endWordCol, int row) {// Create the new word from the new
                                                                                     // tiles in the board
        Tile[] newWordTiles = new Tile[endWordCol - startWordCol + 1];

        for (int i = 0; i < newWordTiles.length; i++) {
            newWordTiles[i] = board[row][startWordCol + i];
        }

        return new Word(newWordTiles, row, startWordCol, false);
    }

    public int getScore(Word word) {
        int multiplier = 1;
        int score = 0;

        int row = word.getRow();
        int col = word.getCol();
        boolean isVertical = word.isVertical();
        Tile[] tiles = word.getTiles();

        for (int i = 0; i < tiles.length; i++) {
            int letterScore = tiles[i].score;
            String bonusSquare = isVertical ? bonusSquares[row + i][col] : bonusSquares[row][col + i];

            if (bonusSquare == null) {
                score += letterScore;
            } else {
                switch (bonusSquare) {
                    case "Star":
                        if (!firstWordPlacedOnBoard) {
                            multiplier = 2;
                        }
                        score += letterScore;
                        break;
                    case "Yellow":
                        multiplier = 2;
                        score += letterScore;
                        break;
                    case "Red":
                        multiplier = 3;
                        score += letterScore;
                        break;
                    case "Blue":
                        score += 3 * letterScore;
                        break;
                    case "LightBlue":
                        score += 2 * letterScore;
                        break;
                    default:
                        score += letterScore;
                        break;
                }
            }
        }

        return score * multiplier;
    }

    public int tryPlaceWord(Word word) {
        if (!boardLegal(word)) {
            return 0;
        }

        ArrayList<Word> wordsCreated = getWords(word);

        for (Word w : wordsCreated) {
            if (!dictionaryLegal(w)) {
                return 0;
            }
        }

        int row = word.getRow();
        int col = word.getCol();
        boolean isVertical = word.isVertical();
        Tile[] wordTiles = word.getTiles();

        for (int i = 0; i < wordTiles.length; i++) {
            Tile currentBoardTile = isVertical ? board[row + i][col] : board[row][col + i];

            if (currentBoardTile == null) {
                board[isVertical ? row + i : row][isVertical ? col : col + i] = wordTiles[i];
            }
        }

        int totalScore = 0;

        for (Word w : wordsCreated) {
            totalScore += getScore(w);
        }

        firstWordPlacedOnBoard = true;

        return totalScore;
    }
}