package bingo;

import entity.Player;
import mains.Panel;

import java.util.ArrayList;
import java.util.Random;

public class BingoMain {

    Panel gp;
    Random rand = new Random();
    BingoPlayer mainPlayer;
    BingoPlayer oppPlayer;
    public int currChip = 0;
    public int counter = 0;
    public ArrayList<Integer> chips = new ArrayList<Integer>();
    public BingoMain(Panel gp) {
        this.gp = gp;
    }

    public static boolean[][] PATTERN = {
        {true, true, true, true, true},
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false}
    };
    public void setupGame() {
        BingoPlayer.setPattern(PATTERN);
        BingoPlayer.setStartingMoney(1);
        BingoPlayer.setPricePerCard(1);

        double priceMoney = 10;
        Person mainPerson = new Person(0, "Juan");
        Person oppPerson = new Person(1, "Aaron");
        mainPerson.setMoney(gp.player.coins);
        BingoCard newCard = new BingoCard(PATTERN, gp.player.bingoCard);
        oppPerson.setMoney(5);

        mainPlayer = new BingoPlayer(mainPerson);
        oppPlayer = new BingoPlayer(oppPerson);
        oppPlayer.addCard();
        BingoCard card = new BingoCard(PATTERN, gp.player.bingoCard);
        mainPlayer.addCard(card);

    }


    // one game tick to progress the game
    public void tick(int x, int y) {
        boolean[][] reverse = new boolean[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                reverse[j][i] = gp.player.markCard[i][j];
            }
        }
        if (counter >= 120) {
            counter = 0;
            int random = rand.nextInt(5) + 1;

            // for luck gameplay
            /*if (gp.player.luck >= random) {
                for (int i = 0; i < gp.player.bingoCard.length; i++) {
                    for (int j = 0; j < gp.player.bingoCard[i].length; j++) {
                        if(chips.contains(gp.player.bingoCard[i][j])) {
                            continue;
                        }
                        else {
                            currChip = gp.player.bingoCard[i][j];
                            chips.add(currChip);
                            BingoPlayer.setRecentNumber(currChip);
                            System.out.println(currChip);
                            mainPlayer.markCardBool(reverse);
                            oppPlayer.markAllCards();

                            return;
                        }
                    }
                }
            }
            else if (gp.player.luck - 5 <= 0) {
                BingoCard[] card = oppPlayer.getCards();
                int[][] bin = card[0].getCard();
                for (int i = 0; i < gp.player.bingoCard.length; i++) {
                    for (int j = 0; j < gp.player.bingoCard[i].length; j++) {
                        if(chips.contains(bin[i][j])) {
                            continue;
                        }
                        else {
                            currChip = bin[i][j];
                            chips.add(currChip);
                            BingoPlayer.setRecentNumber(currChip);
                            System.out.println(currChip);
                            mainPlayer.markCardBool(reverse);
                            oppPlayer.markAllCards();

                            return;
                        }
                    }
                }
            }*/
            do {
                currChip = rand.nextInt(75) + 1;
            } while(chips.contains(currChip));
            chips.add(currChip);
            BingoPlayer.setRecentNumber(currChip);
            mainPlayer.markCardBool(gp.player.markCard);
            oppPlayer.markAllCards();

        }
        else {
            counter++;
        }

    }

    public int checkWin() {
        if (mainPlayer.getWin()) {
            for(int i = 0; i < gp.player.bingoCard.length; i++) {
                for (int j = 0; j < gp.player.bingoCard[i].length; j++) {
                    if(!chips.contains(gp.player.bingoCard[j][i]) && PATTERN[j][i]) {
                        gp.cheated = true;
                        gp.ui.showMessage("You cheated!");
                        return 2;
                    }
                }
            }
            return 1;
        }
        if (oppPlayer.getWin()) {
            return 2;
        }
        return 0;
    }
    public int getOppProg() {
        BingoCard[] bingo = oppPlayer.getCards();
        assert bingo.length > 0;
        return bingo[0].getNumberOfCurrentMark();
    }
    public int getAllProg() {
        BingoCard[] bingo = oppPlayer.getCards();
        assert bingo.length > 0;
        return bingo[0].getNumberOfWinMark();
    }
    public boolean[][] getMarkOppCard() {
        BingoCard[] bingo = oppPlayer.getCards();
        assert bingo.length > 0;
        return bingo[0].getMarkBoard();
    }
    public int[][] getOppCard() {
        BingoCard[] bingo = oppPlayer.getCards();
        assert bingo.length > 0;
        return bingo[0].getCard();
    }
}
