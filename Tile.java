package test;

import java.util.Arrays;

public class Tile {
    final char letter;
    final int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public static class Bag {
        int[] tilesAmount;
        Tile[] tiles = new Tile[26];
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int[] scores = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10 };
        int[] tilesMaximumAmount = new int[] { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1,
                2, 1 };
        private static Bag bagInstance = null;

        private Bag() {
            this.tilesAmount = new int[] { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2,
                    1 };
            for (int i = 0; i <= 25; i++) {
                this.tiles[i] = new Tile(alphabet[i], scores[i]);
            }
        }

        public static Bag getBag() {
            if (bagInstance == null) {
                bagInstance = new Bag();
            }
            return bagInstance;

        }

        public Tile getRand() {
            if (size() == 0) {
                return null;
            } else {
                while (true) {
                    int rnd = new Random().nextInt(this.tilesAmount.length);
                    if (tilesAmount[rnd] != 0) {
                        this.tilesAmount[rnd] -= 1;
                        return this.tiles[rnd];
                    }

                }
            }
        }

        public Tile getTile(char searchChar) {
            for (int i = 0; i < this.tiles.length; i++) {
                if (this.tiles[i].letter == searchChar) {
                    if (tilesAmount[i] != 0) {
                        this.tilesAmount[i] -= 1;
                        return this.tiles[i];
                    }
                }
            }
            return null;
        }

        public void put(Tile returnedTile) {
            for (int i = 0; i < this.alphabet.length; i++) {
                if (this.alphabet[i] == returnedTile.letter) {
                    if (this.tilesAmount[i] < this.tilesMaximumAmount[i]) {
                        this.tilesAmount[i] += 1;
                    }
                }
            }

        }

        public int size() {
            int totalAmount = 0;
            for (int i = 0; i < this.tilesAmount.length; i += 1) {
                totalAmount += tilesAmount[0];
            }
            return totalAmount;
        }

        public int[] getQuantities() {
            int[] copiedArray = Arrays.copyOf(this.tilesAmount, this.tilesAmount.length);
            return copiedArray;
        }

    }

    /*
     * public static void main(String[] args) {
     * Bag myBag = Tile.Bag.getBag();
     * 
     * int[] quantities = myBag.getQuantities();
     * for (int i = 0; i < quantities.length; i++) {
     * System.out.println(quantities[i]);
     * }
     * }
     */
}