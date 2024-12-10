package bingo;

public class BingoMarker implements Cloneable {
    public static final boolean[][] FALSE_CARD = {
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false}
    };
    public static final int BINGO_SIZE = 5;

    private boolean[][] markBoard;
    protected boolean[][] pattern;
    private boolean win;
    private int numberOfWinMark;
    private int numberOfCurrentMark;


    BingoMarker(boolean[][] pattern) {
        this.pattern = deepCopyCard(pattern);
        markBoard = deepCopyCard(FALSE_CARD);
        win = false;
        initNumberOfWinMark();
        initGetCurrentMark();
    }

    /**Used for deep cloning the object, also a requirement for deep cloning
     * a subclass.
     *
     * it overrides the "Cloneable" interface and makes another of its own,
     * "Cloneable" interface only shallow copies, which might conflict with
     * the program.
     */
    @Override
    public BingoMarker clone() {
        try {
            BingoMarker cloned = (BingoMarker) super.clone(); // this shallow copies all fields.
            cloned.markBoard = deepCopyCard(this.markBoard); // this (deep) copies the "markBoard" field of this obj.
            cloned.pattern = deepCopyCard(this.pattern); // this ---- ------ --- "pattern" ----.
            return cloned;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean changePattern(boolean[][] pattern) {
        boolean[][] newPattern = deepCopyCard(pattern);
        if(newPattern == null) {
            System.err.println("Warning(BingoMarker.changePattern()): Pattern is not compatible!");
            return false;
        }
        this.pattern = newPattern;
        return true;
    }

    public boolean getWin() {
        initGetCurrentMark();
        if (numberOfWinMark != numberOfCurrentMark) {
            return false;
        }
        win = true;
        return win;
    }
    public void markCardBool(boolean[][] card) {
        markBoard = deepCopyCard(card);
    }

    public int getNumberOfWinMark() {
        initNumberOfWinMark();
        return numberOfWinMark;
    }

    public int getNumberOfCurrentMark() {
        initGetCurrentMark();
        return numberOfCurrentMark;
    }

    public boolean[][] getMarkBoard() {
        return deepCopyCard(markBoard);
    }
    public boolean[][] getPattern() {
        return deepCopyCard(pattern);
    }

    public boolean markCard(int x, int y) {
        if (x < 0 || x >= BINGO_SIZE || y < 0 || y >= BINGO_SIZE) {
            System.err.println("Warning(BingoMarker.markCard()): Coordinates given is invalid");
            return false;
        }

        this.markBoard[x][y] = true;
        return true;
    }


    /** Used for deep copying reference types (array)
     *  using this.pattern = pattern for example will
     *  also copy the address of the memory and therefore,
     *  anything changes made to that variable outside
     *  the object will change the field in itself
     **/
    public static int[][] deepCopyCard(int[][] card) {
        if (!checkCardSizeValid(card)) {
            System.err.println("Warning(BingoMarker.deepCopyCard()): Card size is invalid!");
            return null;
        }
        int[][] deepCopy = new int[BINGO_SIZE][BINGO_SIZE];

        for (int i = 0; i < BINGO_SIZE; i++) {
            System.arraycopy(card[i], 0, deepCopy[i], 0, BINGO_SIZE);
        }
        return deepCopy;
    }
    public static boolean[][] deepCopyCard(boolean[][] card) {
        if (!checkCardSizeValid(card)) {
            System.err.println("Warning(BingoMarker.deepCopyCard()): Card size is invalid!");
            return null;
        }
        boolean[][] deepCopy = new boolean[BINGO_SIZE][BINGO_SIZE];

        for (int i = 0; i < BINGO_SIZE; i++) {
            System.arraycopy(card[i], 0, deepCopy[i], 0, BINGO_SIZE);
        }
        return deepCopy;
    }

    public static boolean checkCardSizeValid(boolean[][] card) {
        if (card.length != BINGO_SIZE) {
            return false;
        }
        for (boolean[] arr : card) {
            if (arr.length != BINGO_SIZE) {
                return false;
            }
        }
        return true;
    }
    public static boolean checkCardSizeValid(int[][] card) {
        if (card.length != BINGO_SIZE) {
            return false;
        }
        for (int[] arr : card) {
            if (arr.length != BINGO_SIZE) {
                return false;
            }
        }
        return true;
    }

    private void initGetCurrentMark() {
        numberOfCurrentMark = 0;
        for (int i = 0; i < BINGO_SIZE; i++) {
            for (int j = 0; j < BINGO_SIZE; j++) {
                if (pattern[i][j] && markBoard[j][i]) {
                    numberOfCurrentMark++;
                }
            }
        }
    }
    private void initNumberOfWinMark() {
        numberOfWinMark = 0;
        for (int i = 0; i < BINGO_SIZE; i++) {
            for (int j = 0; j < BINGO_SIZE; j++) {
                if (pattern[i][j]) {
                    numberOfWinMark++;
                }
            }
        }
    }
}
