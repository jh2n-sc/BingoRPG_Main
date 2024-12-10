package mains;

import bingo.BingoMain;
import objects.*;
import json.*;
import entity.*;

import java.io.*;
import java.util.Random;

public class AssetInitializer {
    Panel gamePanel;
    ObjectPopulator objectPopulator;
    public AssetInitializer(Panel gamePanel) {
        this.gamePanel = gamePanel;
        this.objectPopulator = new ObjectPopulator(gamePanel, "/config/object_config/properties.json");
        objectPopulator.repopulate();
        getCard();
        insPattern();
    }

    public void setNPC() {
        gamePanel.npc[0] = new NPC_OldGuy(gamePanel);
        gamePanel.npc[0].entityPosX = 24 * Panel.TILE_SIZE;
        gamePanel.npc[0].entityPosY = 29 * Panel.TILE_SIZE;
        gamePanel.npc[3] = new NPC_OldGuy(gamePanel);
        gamePanel.npc[3].entityPosX = 31 * Panel.TILE_SIZE;
        gamePanel.npc[3].entityPosY = 32 * Panel.TILE_SIZE;
        gamePanel.npc[4] = new NPC_OldGuy(gamePanel);
        gamePanel.npc[4].entityPosX = 50 * Panel.TILE_SIZE;
        gamePanel.npc[4].entityPosY = 18 * Panel.TILE_SIZE;
        gamePanel.npc[5] = new NPC_OldGuy(gamePanel);
        gamePanel.npc[5].entityPosX = 58 * Panel.TILE_SIZE;
        gamePanel.npc[5].entityPosY = 38 * Panel.TILE_SIZE;
        gamePanel.npc[6] = new NPC_OldGuy(gamePanel);
        gamePanel.npc[6].entityPosX = 50 * Panel.TILE_SIZE;
        gamePanel.npc[6].entityPosY = 54 * Panel.TILE_SIZE;
        gamePanel.npc[1] = new NPC_Boss(gamePanel);
        gamePanel.npc[1].entityPosX = 35 * Panel.TILE_SIZE;
        gamePanel.npc[1].entityPosY = 27 * Panel.TILE_SIZE;

    }

    public void getCard() {
        int[][] card = new int[5][5];
        String sp;
        String[] line;
        try {

            InputStream st = getClass().getResourceAsStream("/config/player/player.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(st));

            for (int i = 0; i < 5; i++) {
                sp = reader.readLine();

                line = sp.split(" ");

                for (int j = 0; j < 5; j++) {
                    card[i][j] = Integer.parseInt(line[j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        gamePanel.player.bingoCard =  card;
    }

    public void insPattern() {
        int numPattern = 1;
        int[][][] patt;
        String sp;
        String[] spl;
        Random rand = new Random();
        int selection = 0;

        try {
            InputStream st = getClass().getResourceAsStream("/config/player/pattern.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(st));
            numPattern = Integer.parseInt(reader.readLine());
            selection = rand.nextInt(numPattern);
            reader.readLine();
            patt = new int[numPattern][5][5];


            for (int i = 0; i < numPattern; i++) {
                for (int j = 0; j < 5; j++) {
                    spl = reader.readLine().split(" ");
                    for (int k = 0; k < 5; k++) {
                        patt[i][j][k] = Integer.parseInt(spl[k]);
                    }
                }
                reader.readLine();
            }

            for (int i = 0; i < 5; i++) {
                for(int j = 0; j < 5; j++) {
                    if ( patt[selection][j][i] == 0) {
                        BingoMain.PATTERN[i][j] = false;
                    }
                    else {
                        BingoMain.PATTERN[i][j] = true;
                    }
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
 }
