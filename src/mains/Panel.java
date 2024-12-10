package mains;

import entity.Player;
import objects.*;
import tile.TileHandler;
import entity.*;
import javax.swing.JPanel;
import java.awt.*;
import bingo.*;

public class Panel extends JPanel implements Runnable {
    public static final int OLD_TILE_SIZE = 16;
    public static final int SCALE = 3;
    public static final int TILE_SIZE = OLD_TILE_SIZE * SCALE;

    public final int NUMBER_COL = 16;
    public final int NUMBER_ROW = 12;

    public final int SCREEN_WIDTH = NUMBER_COL * TILE_SIZE;
    public final int SCREEN_HEIGHT = NUMBER_ROW * TILE_SIZE;

    public final int MAX_WORLD_COL_SIZE = 75;
    public final int MAX_WORLD_ROW_SIZE = 75;
    public final int WORLD_WIDTH = SCREEN_WIDTH * TILE_SIZE;
    public final int WORLD_HEIGHT = SCREEN_HEIGHT * TILE_SIZE;
    public boolean finished = false;

    public final int FPS = 60;
    public final int TPS = 60;
    public int gameMode = 1;
    public boolean bingo = false;
    public boolean winner = false;
    public boolean over = false;
    public boolean cheated = false;
    int seconds = 1;
    long passed = 0;
    long tmp = 0;
    Thread thread;

    public BingoMain bingoMain = new BingoMain(this);

    public ParentObject[] object;

    // TO DO: fix this
    public KeyboardListener keyboardListener = new KeyboardListener();
    public  TileHandler tileHandler = new TileHandler(this);
    public Player player = new Player(this, this.keyboardListener);
    public CollisionDetector collisionDetector = new CollisionDetector(this);
    public AssetInitializer assetInitializer = new AssetInitializer(this);
    public UI ui = new UI(this);
    public Entity[] npc = new Entity[10];
    Panel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(keyboardListener);
        this.setDoubleBuffered(true);
        this.setBackground(Color.BLACK);
    }

    @Override
    public void run() {

        /* there are different calculations for game 'tick' and frames, this is to ensure
         * that the game will run the same regardless of FPS
        **/
        double intervalT = 1_000_000_000.0 / TPS;
        double intervalF = 1_000_000_000.0 / FPS;
        double accumulatorT = 0;
        double accumulatorF = 0;
        long prevTime = System.nanoTime();
        long currTime;

        while (thread != null && thread.isAlive()) {
            currTime = System.nanoTime();
            accumulatorT += (currTime - prevTime) / intervalT;
            accumulatorF += (currTime - prevTime) / intervalF;
            prevTime = currTime;

            if (accumulatorT >= 1) {
                update();
                accumulatorT = 0;
            }
            if (accumulatorF >= 1) {
                repaint();
                accumulatorF = 0;
            }
        }
    }

    public void setupGame() {
        assetInitializer.setNPC();
    }

    public void setThread() {
        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g) {

        // hardware issues fixed.
       Toolkit.getDefaultToolkit().sync();
        // DEBUG
        long drawStart = System.nanoTime();

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        tileHandler.drawTile(g2d);

        for (ParentObject parentObject : object) {
            if (parentObject == null) {
                continue;
            }
            parentObject.render(g2d, this);
        }
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].render(g2d);
            }
        }
        player.render(g2d);
        if (keyboardListener.isDebugPressed()) {
            long drawEnd = System.nanoTime();
            this.passed += +drawEnd - drawStart;


            if(seconds >= TPS) {
                tmp = this.passed / 60;
                seconds = 1;
                this.passed = 0;
            }
            System.out.println("DrawTime: " + tmp);
            g2d.setColor(Color.WHITE);
            g2d.setFont(ui.arial_40);
            g2d.drawString("DrawTime: " + tmp, 50, 200);
            seconds++;

        }
        ui.draw(g2d);
        if (bingo && gameMode == 0) {
            ui.renderCard(g2d);
            ui.renderOppCard(g2d);
            ui.printOppProg(g2d);
            ui.printCurrNumber(g2d);
            ui.renderPattern(g2d);
        }
        g2d.dispose();
    }

    public void update() {

        if (keyboardListener.isPausedPressed()) {
            gameMode = 0;

        }
        else{
            gameMode = 1;
        }
        if (gameMode == 0 && !bingo) {
            ui.setPaused(true);
        } else if (gameMode == 1 && !bingo) {
            ui.setPaused(false);
            player.updatePos();
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].updatePos();
                }
            }
        }
        else if(bingo){
            gameMode = 0;
            ui.listenKeyboardSlot();
            bingoMain.tick(ui.slotCol, ui.slotRow);
            if(bingoMain.checkWin() == 1) {
                System.out.println("winn");
                bingo = false;
                ui.isWin = 1;
                gameMode = 1;
                over = true;
                finished = true;
                winner = true;
            }
            else if (bingoMain.checkWin() == 2) {
                System.out.println("lose");
                System.out.println("Game Over");
                bingo = false;
                ui.isWin = 2;
                gameMode = 1;
                over = true;
                finished = true;
                winner = false;
            }
        } else {
            bingo = false;
            gameMode = 1;
        }

    }
    public void bingoBoss() {
        gameMode = 2;
        bingoMain.setupGame();
        bingo = true;
        winner = false;
        over = false;
    }
}
