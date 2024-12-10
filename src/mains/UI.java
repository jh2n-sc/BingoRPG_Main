package mains;

import bingo.BingoMain;

import java.awt.*;

public class UI {
    Panel gp;
    public Font arial_40;
    boolean debug;
    String msg;
    boolean isMsgOn;
    int counterMsg;
    boolean isPaused;
    public int slotCol = 2;
    public int slotRow = 4;
    public boolean clicked = false;

    int gameOverCounter = 0;
    int isWin = 0;

    public UI(Panel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 20);
        debug = false;
        isMsgOn = false;
        isPaused = false;
        msg = "";
        counterMsg = 0;

    }

    public void draw(Graphics2D g2d) {
        g2d.setFont(arial_40);
        g2d.setColor(Color.WHITE);
        if(gp.keyboardListener.isDebugPressed()) {
            debug =!debug;
            g2d.drawString("Pos(X, Y):" + gp.player.entityPosX / Panel.TILE_SIZE +", " +gp.player.entityPosY / Panel.TILE_SIZE, 50, 100);
            g2d.drawString("Speed: " + gp.player.variableEntitySpeed, 50, 125);
            gp.collisionDetector.checkTile(gp.player);
            g2d.drawString("Collision: " + gp.player.isCollisionOn, 50, 150);
            g2d.drawString("Direction: " + gp.player.direction, 50, 175);
        }
        if (isMsgOn && counterMsg < 120) {
            g2d.setFont(g2d.getFont().deriveFont(20f));
            counterMsg++;
            g2d.drawString(msg, 50, 250);
            }
        if (counterMsg >= 120) {
            isMsgOn = false;
            counterMsg = 0;
        }
        if(gp.gameMode == 0) {
            g2d.setFont(g2d.getFont().deriveFont(50f));
            g2d.drawString("Paused", gp.SCREEN_WIDTH -200, 100);
        }
        if (gameOverCounter >= 300) {
            gp.over = false;

        }
        if(gp.over && gameOverCounter <= 300) {
            if (isWin == 1) {
                win(g2d);
                showMessage("You've become a Millionaire!!!");
                gp.player.coins = 1_000_000;
            }
            else if (isWin == 2) {
                lose(g2d);
            }
            gameOverCounter++;
        }
        else {
            gameOverCounter = 0;
        }
        g2d.setFont(arial_40);
        g2d.drawString("Coins: " + gp.player.coins + " Luck: " + (double)gp.player.luck/5 * 100 + "%", 50, 50);
    }

    public void showMessage(String msg) {        this.msg = msg;
        isMsgOn = true;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void setSubWindow(int x, int y, int width, int height, Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 220));
        g2d.fillRoundRect(x, y, width, height, 10, 10);

        g2d.setColor(new Color(255, 255, 255));
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 10, 10);
    }

    public void renderCard(Graphics2D g2d) {
        int frameX = Panel.TILE_SIZE * 9;
        int frameY = Panel.TILE_SIZE;
        int frameWidth = Panel.TILE_SIZE * 6;
        int frameHeight = Panel.TILE_SIZE * 6;

        setSubWindow(frameX, frameY, frameWidth, frameHeight, g2d);

        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        for (int i = 0; i < gp.player.bingoCard.length; i++) {
            for (int j = 0; j < gp.player.bingoCard[i].length; j++) {
                if (gp.player.markCard[j][i] == false) {
                    g2d.setColor(new Color(255, 0, 0, 255));
                } else {
                    g2d.setColor(new Color(0, 255, 0, 255));
                }
                g2d.drawString(String.valueOf(gp.player.bingoCard[j][i]), slotXstart + 15 + (Panel.TILE_SIZE * i),
                    slotYstart + 30 + (Panel.TILE_SIZE * j));


            }
        }
        int slotX = slotXstart;
        int slotY = slotYstart;

        // Cursor
        int cursorX = slotXstart + (Panel.TILE_SIZE * slotCol);
        int cursorY = slotYstart + (Panel.TILE_SIZE * slotRow);
        int cursorHeight = Panel.TILE_SIZE;
        int cursorWidth = Panel.TILE_SIZE;
        // Draw Cursor
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    }
    public void renderOppCard(Graphics2D g2d) {
        int frameX = Panel.TILE_SIZE;
        int frameY = Panel.TILE_SIZE;
        int frameWidth = Panel.TILE_SIZE * 6;
        int frameHeight = Panel.TILE_SIZE * 6;

        setSubWindow(frameX, frameY - 20, frameWidth, frameHeight + 20, g2d);
        g2d.setColor(new Color(255, 255, 255));
        g2d.drawString("Opponent: ", frameX + 15, frameY + 10);
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        for (int i = 0; i < gp.bingoMain.getOppCard().length; i++) {
            for (int j = 0; j < gp.bingoMain.getOppCard()[i].length; j++) {
                if (!gp.bingoMain.getMarkOppCard()[i][j]) {
                    g2d.setColor(new Color(255, 0, 0, 255));
                } else {
                    g2d.setColor(new Color(0, 255, 0, 255));
                }
                g2d.drawString(String.valueOf(gp.bingoMain.getOppCard()[i][j]), slotXstart + 15 + (Panel.TILE_SIZE * i),
                        slotYstart + 30 + (Panel.TILE_SIZE * j));


            }
        }
        int slotX = slotXstart;
        int slotY = slotYstart;

        // Cursor
        // Draw Cursor
    }
    public void listenKeyboardSlot() {
        if (gp.keyboardListener.isAPressed() && gp.keyboardListener.isKeyboardPressed() && !clicked) {
            if (slotCol - 1 >= 0) {
                slotCol--;

                //else {

                //}///
            }
            else {
                if (slotRow - 1 >= 0) {
                    slotRow--;
                    slotCol = 4;
                }
            }
            clicked = true;
        }
        else if (gp.keyboardListener.isDPressed() && gp.keyboardListener.isKeyboardPressed() && !clicked) {
            if (slotCol + 1 <= 4) {
                slotCol++;
            }
            else {
                if (slotRow + 1 <= 4) {
                    slotRow++;
                    slotCol = 0;
                }
            }
            clicked = true;
        }
        else if (gp.keyboardListener.isWPressed() && gp.keyboardListener.isKeyboardPressed() && !clicked) {
            if (slotRow - 1 >= 0) {
                slotRow--;

                //else {

                //}///
            }
            else {
            }
            clicked = true;
        }
        else if (gp.keyboardListener.isSPressed() && gp.keyboardListener.isKeyboardPressed() && !clicked) {
            if (slotRow + 1 <= 4) {
                slotRow++;

                //else {

                //}///
            }
            else {
            }
            clicked = true;
        }
        else if (gp.keyboardListener.isSpacerPressed() && gp.keyboardListener.isKeyboardPressed() && !clicked) {
            gp.player.markCard[slotRow][slotCol] = !gp.player.markCard[slotRow][slotCol];
            clicked = true;
        }
        if (!gp.keyboardListener.isKeyboardPressed()){
            clicked = false;
        }
    }
    public void printOppProg(Graphics2D g2d) {
        int num = gp.bingoMain.getOppProg();

        setSubWindow(Panel.TILE_SIZE, Panel.TILE_SIZE * 8, Panel.TILE_SIZE * 7, Panel.TILE_SIZE * 2, g2d);
        g2d.setColor(Color.WHITE);
        g2d.setFont(arial_40.deriveFont(22f));
        g2d.drawString("Boss Progress: " + gp.bingoMain.getOppProg() + "/" +  gp.bingoMain.getAllProg(), Panel.TILE_SIZE + 20, Panel.TILE_SIZE * 9);
    }
    public void printCurrNumber(Graphics2D g2d) {
        int num = gp.bingoMain.currChip;

        setSubWindow(Panel.TILE_SIZE * 16 / 2 - Panel.TILE_SIZE + 15, Panel.TILE_SIZE * 12 / 2 - Panel.TILE_SIZE, Panel.TILE_SIZE * 1 + 20, Panel.TILE_SIZE * 1 + 20, g2d);
        g2d.setFont(arial_40.deriveFont(30f));
        g2d.setColor(Color.WHITE);
        g2d.drawString(String.valueOf(num), Panel.TILE_SIZE * 16 / 2 - Panel.TILE_SIZE + 35, Panel.TILE_SIZE * 12 / 2);
    }
    public void win(Graphics2D g2d) {
        setSubWindow(Panel.TILE_SIZE, Panel.TILE_SIZE * 2, Panel.TILE_SIZE * 3 + 10, Panel.TILE_SIZE * 2, g2d);
        g2d.setFont(arial_40.deriveFont(30f));
        g2d.setColor(Color.WHITE);
        g2d.drawString("You win!", Panel.TILE_SIZE + 20, Panel.TILE_SIZE * 3 + 10 );
        isMsgOn = true;
        if(counterMsg >= 120) {
            isMsgOn = false;
            counterMsg = 0;
            return;
        }
        counterMsg++;
    }
    public void lose(Graphics2D g2d) {
        setSubWindow(Panel.TILE_SIZE, Panel.TILE_SIZE * 2, Panel.TILE_SIZE * 3 + 20, Panel.TILE_SIZE * 2, g2d);
        g2d.setFont(arial_40.deriveFont(30f));
        g2d.setColor(Color.WHITE);
        g2d.drawString("You lose!", Panel.TILE_SIZE + 20, Panel.TILE_SIZE * 3 + 10 );
        isMsgOn = true;
        if(counterMsg >= 120) {
            isMsgOn = false;
            counterMsg = 0;
            return;
        }
        counterMsg++;

    }
    public void renderPattern(Graphics2D g2d) {
        int frameWidth = (Panel.OLD_TILE_SIZE * 2)* 6;
        int frameHeight =( Panel.OLD_TILE_SIZE * 2) * 6;
        int frameX = Panel.TILE_SIZE * 16 - frameWidth - Panel.TILE_SIZE;
        int frameY = Panel.TILE_SIZE * 12 - frameHeight - Panel.TILE_SIZE / 2;

        setSubWindow(frameX, frameY - 20, frameWidth, frameHeight + 20, g2d);
        g2d.setFont(arial_40.deriveFont(20f));
        g2d.setColor(new Color(255, 255, 255));
        g2d.drawString("Pattern: ", frameX + 15, frameY + 10);
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 40;
        g2d.setFont(arial_40.deriveFont(30f));
        for (int i = 0; i < gp.bingoMain.getOppCard().length; i++) {
            for (int j = 0; j < gp.bingoMain.getOppCard()[i].length; j++) {
                if (!BingoMain.PATTERN[i][j]) {
                    g2d.setColor(new Color(255, 0, 0, 255));
                } else {
                    g2d.setColor(new Color(0, 255, 0, 255));
                }
                g2d.drawString("*", slotXstart + (Panel.OLD_TILE_SIZE * 2 * i),
                        slotYstart + ((Panel.OLD_TILE_SIZE * 2) *j));


            }
        }
        int slotX = slotXstart;
        int slotY = slotYstart;

        // Cursor
        // Draw Cursor
    }

}
