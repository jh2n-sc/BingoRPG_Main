package bingo;

import java.util.ArrayList;

public class BingoPlayer extends Person {
    private static int recentNumber;
    private static int prevNumber;
    private static boolean[][] pattern;
    private static double startingMoney;
    private static double pricePerCard;

    private ArrayList<BingoCard> myCards;
    private boolean win;
    private int numCards;

    // Constructors
    BingoPlayer(Person person) {
        super(person);
        myCards = new ArrayList<>();
        win = false;
        numCards = 0;
        if (super.getMoney() == 0) {
            super.setMoney(startingMoney);
        }
    }
    BingoPlayer(Person person, int numCards) {
        super(person);
        myCards = new ArrayList<>();
        if (calculateMoney(numCards) < 0) {
            System.err.println("Warning(Player): Insufficient money!");
            this.numCards = 0;
        }
        if(!initAllCards()) {
            System.err.println("Warning(Player): Pattern is not initialized!");
        }
        if (super.getMoney() == 0) {
            super.setMoney(startingMoney);
        }
        win = false;
    }
    public void markGuiCard(int row, int column) {
        assert !myCards.isEmpty();
        BingoCard myCard = myCards.get(0);

        myCard.markManually(row, column);
    }

    // Public Methods
    public boolean markAllCards() {
        if(recentNumber == prevNumber) {
            System.err.println("Warning(Player.markAllCards()): Recent cards are already marked!");
            return false;
        }
        for(BingoCard card : myCards) {
            card.markCard(recentNumber);
        }
        return true;
    }

    public void markCardBool(boolean[][] markBoard) {
        assert !myCards.isEmpty();
        BingoCard myCard = myCards.get(0);
       /*boolean[][] reverse = new boolean[5][5];
        for (int i = 0; i < myCards.size(); i++) {
            for (int j = 0; j < myCards.size(); j++) {
                reverse[j][i] = markBoard[i][j];
                System.out.print(markBoard[j][i]);
            }
            System.out.println();
        }*/
        myCard.markWithArray(markBoard);
    }
    public boolean addCard() {
        double newMoney = calculateMoney(numCards + 1);
        if(newMoney < 0) {
            return false;
        }
        BingoCard newCloned = new BingoCard(BingoPlayer.pattern);
        myCards.add(newCloned);
        numCards++;
        super.setMoney(newMoney);
        return true;
    }
    public boolean addCard(BingoCard card) {
        double newMoney = calculateMoney(numCards + 1);
        if(newMoney < 0) {
            return false;
        }
        BingoCard newCloned = card.clone();
        newCloned.changePattern(BingoPlayer.pattern);
        myCards.add(newCloned);
        numCards++;
        super.setMoney(newMoney);
        return true;
    }
    public double calculateMoney(int numCards) {
        return super.getMoney() - pricePerCard * numCards;
    }

    // Setters
    public static boolean setPattern(boolean[][] pattern) {
        boolean[][] newPattern = BingoMarker.deepCopyCard(pattern);
        if (newPattern == null) {
            System.err.println("Warning(Player.setPattern()): Pattern is not compatible!");
            return false;
        }
        BingoPlayer.pattern = newPattern;
        return true;
    }

    // Accessors
    public boolean getWin() {
        for(BingoCard card : myCards) {
            if (card.getWin()){
                win = true;
            }
        }
        return win;
    }
    public int getNumCards() {
        return numCards;
    }
    public BingoCard[] getCards() {
        BingoCard[] newCards = new BingoCard[myCards.size()];
        for(int i = 0; i < myCards.size(); i++) {
            BingoCard tmp = myCards.get(i);
            newCards[i] = tmp.clone();
        }
        return newCards;
    }

    // Static Mutators
    public static boolean setRecentNumber(int recentNumber) {
        if(recentNumber < BingoCard.LOWEST_NUM || recentNumber > BingoCard.HIGHEST_NUM) {
            System.err.println("Warning(Player.setRecentNumber()): Recent number out of bounds!");
            return false;
        }
        BingoPlayer.prevNumber = BingoPlayer.recentNumber;
        BingoPlayer.recentNumber = recentNumber;
        return true;
    }
    public static boolean setStartingMoney(double startingMoney) {
        if(startingMoney < 0) {
            System.err.println("Warning(Player.setStartingMoney()): Forbidden value!");
            return false;
        }
        BingoPlayer.startingMoney = startingMoney;
        return true;
    }
    public static boolean setPricePerCard(double pricePerCard) {
        if(pricePerCard < 0 || (pricePerCard > BingoPlayer.startingMoney)) {
            System.err.println("Warning(Player.setPricePerCard()): Forbidden value!");
            return false;
        }
        BingoPlayer.pricePerCard = pricePerCard;
        return true;
    }

    public static int getRecentNumber() {
        return BingoPlayer.recentNumber;
    }
    public static double getStartingMoney() {
        return BingoPlayer.startingMoney;
    }
    public static double getPricePerCard() {
        return BingoPlayer.pricePerCard;
    }

    // Private method
    private boolean initAllCards() {
        if (pattern == null) {
            return false;
        }
        addCard(new BingoCard(pattern));
        return true;
    }

    // Overridden clone
    @Override
    public BingoPlayer clone() {
        Person newPerson = super.clone();
        BingoPlayer newBingoPlayer = new BingoPlayer(newPerson);

        ArrayList<BingoCard> newCards = new ArrayList<>();
        for (BingoCard card : myCards) {
            newCards.add(card.clone());
        }
        newBingoPlayer.myCards = newCards;
        return newBingoPlayer;
    }


}
