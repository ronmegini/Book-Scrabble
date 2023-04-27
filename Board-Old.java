package test;

import javax.swing.text.Position;
import test.Tile;
import java.util.ArrayList;

public class BoardOld {

    private static Board boardInstance = null;
    Tile[][] boardArray = new Tile[15][15];
    int[][] score = new int[15][15];

    private Board() {
        for (int i = 0; i < 15; i += 1) {
            for (int j = 0; j < 15; j += 1) {
                boardArray[i][j] = null;
            }
        }

        // Scores:
        // Center
        score[7][7] = 5;

        // Setting Red
        score[0][0] = (4);
        score[0][7] = (4);
        score[0][14] = (4);
        score[7][0] = (4);
        score[7][14] = (4);
        score[14][0] = (4);
        score[14][7] = (4);
        score[14][14] = (4);

        // Setting Yellow
        score[1][1] = (3);
        score[2][2] = (3);
        score[3][3] = (3);
        score[4][4] = (3);
        score[1][13] = (3);
        score[2][12] = (3);
        score[3][11] = (3);
        score[4][10] = (3);
        score[13][13] = (3);
        score[12][12] = (3);
        score[11][11] = (3);
        score[10][10] = (3);
        score[10][4] = (3);
        score[11][3] = (3);
        score[12][2] = (3);
        score[13][1] = (3);

        // Setting Blue
        score[1][5] = (2);
        score[1][9] = (2);
        score[5][1] = (2);
        score[5][5] = (2);
        score[5][9] = (2);
        score[5][13] = (2);
        score[9][1] = (2);
        score[9][5] = (2);
        score[9][9] = (2);
        score[9][13] = (2);
        score[13][5] = (2);
        score[13][9] = (2);

        // Setting LightBlue
        score[0][3] = (1);
        score[0][11] = (1);
        score[2][6] = (1);
        score[2][8] = (1);
        score[3][0] = (1);
        score[3][7] = (1);
        score[3][14] = (1);
        score[6][2] = (1);
        score[6][6] = (1);
        score[6][8] = (1);
        score[6][12] = (1);
        score[7][3] = (1);
        score[7][11] = (1);
        score[8][2] = (1);
        score[8][6] = (1);
        score[8][8] = (1);
        score[8][12] = (1);
        score[11][0] = (1);
        score[11][7] = (1);
        score[11][14] = (1);
        score[12][6] = (1);
        score[12][8] = (1);
        score[14][3] = (1);
        score[14][11] = (1);

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

    public static boolean isBetween(int value, int min, int max) {
        return ((value >= min) && (value <= max));
    }

    public boolean isInBorders(Word checkedWord) {
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
        if (isBetween(position, 0, 14) && isBetween(start, 0, 14) && isBetween(end, 0, 14)) {
            return true;
        }
        return false;

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

    public boolean isLegalHafifa(Word checkeWord) {
        int row = checkeWord.getRow();
        int col = checkedWord.getCol();
        boolean hafifaFound = false;
        for (int i = 0; i < checkedWord.tiles.length; i += 1) {
            if (boardArray[row][col] != null) {
                hafifaFound = true;
                if (boardArray[row][col] != checkeWord.tiles[i]) {
                    return false;
                }
            }
        }
        if (hafifaFound) {
            return true;
        } else {
            return false;
        }

    }

    public boolean boardLegal(Word checkedWord) {
        if (this.boardArray[7][7] == null) {
            return isInBorders(checkedWord) && isFirstWord(checkedWord);
        }

        return ((isInBorders(checkedWord)) && (isLegalHafifa(checkedWord)));

    }

    public boolean dictionaryLegal(Word checkedWord) {
        return true;
    }

    public Word MakeWord(int row, int col, int length, boolean vertical, Tile t) {
        int copyiedCol = col;
        int copyiedRow = row;
        Tile[] temp = new Tile[length];
        for (int i = 0; i < length; i++) {
            if (boardArray[copyiedRow][copyiedCol] == null) {
                temp[i] = t;
            } else {
                temp[i] = boardArray[copyiedRow][copyiedCol];
            }
            if (vertical)
                copyiedRow++;
            else
                copyiedCol++;
        }
        return new Word(temp, row, col, vertical);
    }

    public ArrayList<Word> getWords(Word checkedWord) {
        ArrayList<Word> returnedWordList = new ArrayList<>();
        int row = checkedWord.getRow();
        int col = checkedWord.getCol();
        int start = 0;
        int end = 0;
        returnedWordList.add(checkedWord);
        if (boardArray[7][7] == null && isFirstWord(checkedWord))
            return returnedWordList;
        for (int i = 0; i < checkedWord.tiles.length; i += 1) {
            if (checkedWord.isVertical()) {
                if (checkedWord.tiles[i] == null && boardArray[row][col] != null) {
                    row += 1;
                    continue;
                }
                if (col != 0) {
                    if (boardArray[row][col - 1] != null) {
                        col -= 1;
                        while (boardArray[row][col] != null) {
                            start += 1;
                            col -= 1;
                        }
                    }
                    col = checkedWord.getCol();
                }
                if (col != 14) {
                    if (boardArray[row][col + 1] != null) {
                        col += 1;
                        while (boardArray[row][col] != null) {
                            end += 1;
                            col += 1;
                        }
                    }
                    col = checkedWord.getCol();
                }
                if (start != 0 && end == 0)
                    returnedWordList.add(
                            MakeWord(row, checkedWord.getCol() - start, start + 1, false, checkedWord.tiles[i]));
                if (start == 0 && end != 0)
                    returnedWordList.add(MakeWord(row, checkedWord.getCol(), end + 1, false, checkedWord.tiles[i]));
                if (start != 0 && end != 0)
                    returnedWordList.add(MakeWord(row, checkedWord.getCol() - start, start + end + 1, false,
                            checkedWord.tiles[i]));
                row += 1;
                col = checkedWord.getCol();
            } else {
                if (checkedWord.tiles[i] == null && boardArray[row][col] != null) {
                    col += 1;
                    continue;
                }
                if (col != 0) {
                    if (boardArray[row - 1][col] != null) {
                        row -= 1;
                        while (boardArray[row][col] != null) {
                            row -= 1;
                            start += 1;
                        }
                    }
                    row = checkedWord.getRow();
                }
                if (row != 14) {
                    if (boardArray[row + 1][col] != null) {
                        row += 1;
                        while (boardArray[row][col] != null) {
                            row += 1;
                            end += 1;
                        }
                    }
                    row = checkedWord.getRow();
                }
                if (start == 0 && end != 0)
                    returnedWordList.add(MakeWord(checkedWord.getRow(), col, end + 1, true, checkedWord.tiles[i]));
                if (start != 0 && end == 0)
                    returnedWordList
                            .add(MakeWord(checkedWord.getRow() - start, col, start + 1, true, checkedWord.tiles[i]));
                if (start != 0 && end != 0)
                    returnedWordList.add(MakeWord(checkedWord.getRow() - start, col, start + end + 1, true,
                            checkedWord.tiles[i]));
                row = checkedWord.getRow();
                col += 1;
            }
            start = 0;
            end = 0;
        }
        return returnedWordList;
    }

    public int getScore(Word checkedWord) {
        // Green=0, LightBlue=1, Blue=2, Yellow=3, Red=4, Star=5
        boolean doubleScore = false;
        boolean trippleScore = false;
        int col = checkedWord.getCol();
        int row = checkedWord.getRow();
        int tileScore = 0;
        int sum = 0;
        for (int i = 0; i < checkedWord.tiles.length; i++) {
            if (checkedWord.tiles[i] == null && boardArray[row][col] != null) {
                tileScore = boardArray[row][col].getScore();
            } else {
                if (checkedWord.tiles[i] != null)
                    tileScore = checkedWord.tiles[i].getScore();
            }
            switch (score[row][col]) {
                case 5: {
                    if (boardArray[row][col] == null) {
                        doubleScore = true;
                    }
                    sum += tileScore;
                    break;
                }
                case 4: {
                    trippleScore = true;
                    sum += tileScore;
                    break;
                }
                case 3: {
                    doubleScore = true;
                    sum += tileScore;
                    break;
                }
                case 2: {
                    sum += (3 * tileScore);
                    break;
                }
                case 1: {
                    sum += (2 * tileScore);
                    break;
                }
                default: {
                    sum += tileScore;
                    break;
                }
            }
            if (checkedWord.isVertical())
                row++;
            else
                col++;
        }
        if (trippleScore)
            return sum * 3;
        else if (doubleScore)
            return sum * 2;
        else
            return sum;
    }

    public int tryPlaceWord(Word checkedWord) {
        ArrayList<Word> wordsList = getWords(checkedWord);
        int Wordscore = 0;
        int Row = checkedWord.getRow();
        int Col = checkedWord.getCol();
        if (!boardLegal(checkedWord))
            return 0;
        if (getWords(checkedWord).size() == 1) {
            Word debug = wordsList.get(0);
            Wordscore = getScore(debug);
        } else {
            for (Word word : wordsList)
                Wordscore += getScore(word);
        }
        for (int i = 0; i < checkedWord.tiles.length; i++) {
            if (checkedWord.tiles[i] != null)
                boardArray[Row][Col] = checkedWord.tiles[i];
            if (checkedWord.isVertical())
                Row++;
            else
                Col++;
        }
        return Wordscore;

    }
}
