package bingo;

import java.util.Random;
import java.util.Formatter;



public class BingoCard extends BingoMarker {
    public static final int LOWEST_NUM = 1;
    public static final int HIGHEST_NUM = 75;
    public static final int[][] ZERO_CARD = {
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}
    };
    public static final int CHEAT_CODE = 0;

    private int[][] card;

    BingoCard(boolean[][] pattern) {
        super(pattern);
        card = randomCardGenerator();
    }

    BingoCard(boolean[][] pattern, int[][] card) {
        super(pattern);
        this.card = deepCopyCard(card);
    }

    // Clone
    @Override
    public BingoCard clone() {
        BingoCard cloned = (BingoCard) super.clone(); // this shallow copies all fields.
        // this (deep) copies the "card" field of this obj.
        cloned.card = deepCopyCard(this.card);
        return cloned;
    }

    // Accessors
    public int[][] getCard() {
        return deepCopyCard(this.card);
    }

    public boolean markCard(int recentChip) {
        if (LOWEST_NUM > recentChip || HIGHEST_NUM < recentChip) {
            System.err.println("Warning(BingoCard.markCard()): recentChip is an Invalid Number!");
            return false;
        }
        for (int i = 0; i < BINGO_SIZE; i++) {
            for (int j = 0; j < BINGO_SIZE; j++) {
                if (card[i][j] == recentChip || card[i][j] == CHEAT_CODE) {
                    super.markCard(i, j);
                }
            }
        }
        return true;
    }
    public void markManually(int x, int y) {
        super.markCard(x, y);
    }
    public void markWithArray(boolean[][] array) {
        for (int i = 0; i < BINGO_SIZE; i++) {
            for (int j = 0; j < BINGO_SIZE; j++) {
                if (array[j][i] && super.pattern[j][i]) {
                    super.markCard(i, j);
                }
            }
        }
    }

    public int[][] randomCardGenerator() {
        int[][] newCard = deepCopyCard(ZERO_CARD);
        for (int i = 0; i < 5; i++) {
            assert newCard != null : "newCard is null";
            newCard[i] = bingoColumnGenerator(i);
        }
        newCard[2][2] = CHEAT_CODE;
        return newCard;
    }


    /**
     *  Creates random number inclusively
     * */
    public static int randomNumber(int fr, int to) {
        Random rand = new Random();
        return rand.nextInt(to - fr + 1) + fr;
    }

    public static boolean duplicateNumber(int[] array, int num) {
        if (array == null) {
            System.err.println("Warning(BingoCard.duplicateNumber()): array is null!");
            return true;
        }
        for (int j : array) {
            if (j == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * bingoColumnGenerator(int column)
     * <p>
     * column refers to the column which to generate from:
     * 0 = B,
     * 1 = I,
     * 2 = N,
     * 3 = G,
     * 4 = 0,
     */
    private int[] bingoColumnGenerator(int numColumn) {
        if (numColumn < 0 || numColumn > 4) {
            System.err.println("Warning(BingoCard.bingoColumnGenerator()): column is out of bounds!");
            return null;
        }

        // Calculates the required range of numbers based on the column
        int maxNum = numColumn * 15 + 15;
        int minNum = numColumn * 15 + 1;

        // Creates a new single dimension array for duplicate checking
        int[] column = new int[BINGO_SIZE];


        System.arraycopy(ZERO_CARD[0], 0, column, 0, BINGO_SIZE);
        for (int i = 0; i < BINGO_SIZE; i++) {
            int num;
            // the loop keeps on going if the number existed in the array before copying it.
            do {
                // generates random number
                num = randomNumber(minNum, maxNum);
            } while (duplicateNumber(column, num));

            // saves the number into the array
            column[i] = num;
        }
        return column;
    }

    /** Checks whether the card is within the range
     * */
    public boolean cardIsWithinRange(int[][] card) {
        for (int column = 0; column < card.length; column++) {
            int minNum = (column * 15) + 1;
            int maxNum = (column * 15) + 15;

            for (int row = 0; row < card.length; row++) {
                // skips "free" cell
                if (row == 2 && column == 2) {
                    continue;
                }
                if (card[column][row] > maxNum || card[column][row] < minNum) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter newFormatter = new Formatter(sb);
        for (int i = 0; i < BINGO_SIZE; i++) {
            for (int j = 0; j < BINGO_SIZE; j++) {
                newFormatter.format("%2d ", card[j][i]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
