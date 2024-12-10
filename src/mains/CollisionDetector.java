package mains;

import entity.Entity;

import java.awt.*;

public class CollisionDetector {
    Panel gamePanel;
    int entityBoxLeftX;
    int entityBoxRightX;

    int entityBoxTopY;
    int entityBoxBottomY;

    int entityLeftColumn;
    int entityRightColumn;
    int entityTopRow;
    int entityBottomRow;

    int tileNumber1, tileNumber2;

    public CollisionDetector(Panel gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void checkTile(Entity entity) {
        initializeEntityVariables(entity);

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityBoxTopY - entity.entitySpeed * 2) / gamePanel.TILE_SIZE;
                tileNumber1 = gamePanel.tileHandler.worldMap[entityLeftColumn][entityTopRow];
                tileNumber2 = gamePanel.tileHandler.worldMap[entityRightColumn][entityTopRow];
                if (gamePanel.tileHandler.tileList.get(tileNumber1).collisionSolid ||
                    gamePanel.tileHandler.tileList.get(tileNumber2).collisionSolid) {
                    entity.isCollisionOn = true;
                    entity.cantGoDirection = "up";
                }
                break;
            case "down":
                entityBottomRow = (entityBoxBottomY - entity.entitySpeed + entity.entitySpeed * 2) / gamePanel.TILE_SIZE;
                tileNumber1 = gamePanel.tileHandler.worldMap[entityLeftColumn][entityBottomRow];
                tileNumber2 = gamePanel.tileHandler.worldMap[entityRightColumn][entityBottomRow];
                if (gamePanel.tileHandler.tileList.get(tileNumber1).collisionSolid ||
                    gamePanel.tileHandler.tileList.get(tileNumber2).collisionSolid) {
                    entity.isCollisionOn = true;
                    entity.cantGoDirection = "down";
                }
                break;
            case "left":
                entityLeftColumn = (entityBoxLeftX - entity.entitySpeed * 2) / gamePanel.TILE_SIZE;
                tileNumber1 = gamePanel.tileHandler.worldMap[entityLeftColumn][entityTopRow];
                tileNumber2 = gamePanel.tileHandler.worldMap[entityLeftColumn][entityBottomRow];
                if (gamePanel.tileHandler.tileList.get(tileNumber1).collisionSolid ||
                    gamePanel.tileHandler.tileList.get(tileNumber2).collisionSolid) {
                    entity.isCollisionOn = true;
                    entity.cantGoDirection = "left";
                }
                break;
            case "right":
                entityRightColumn = (entityBoxRightX - entity.entitySpeed + entity.entitySpeed * 2) / gamePanel.TILE_SIZE;
                tileNumber1 = gamePanel.tileHandler.worldMap[entityRightColumn][entityTopRow];
                tileNumber2 = gamePanel.tileHandler.worldMap[entityRightColumn][entityBottomRow];
                if (gamePanel.tileHandler.tileList.get(tileNumber1).collisionSolid ||
                    gamePanel.tileHandler.tileList.get(tileNumber2).collisionSolid) {
                    entity.isCollisionOn = true;
                    entity.cantGoDirection = "right";
                }
                break;

        }
    }

    public void initializeEntityVariables(Entity entity) {

        // this is done for reusability

        // entity boxes are the points of the hitbox of an entity
        /**
         *  basically the entity box is the sides of the rectangle, this is combined to
         *  calculate for the outer corners of it.
         *
         *
         *  all the lines are the entity left box
         *  entity columns are what the actual position of the entity's hit boxes in the map
         *  entityBox* are the actual position, it is multiplied by pixel, the entity---row/column is the actual
         *  x and y position of it. it is divided by TILE_SIZE.
         *   +-----+
         *   |     |
         *   +-----+
         * */
        this.entityBoxLeftX = entity.entityPosX + entity.collisionBox.x;
        this.entityBoxRightX = entity.entityPosX + entity.collisionBox.x + entity.collisionBox.width;

        this.entityBoxTopY = entity.entityPosY + entity.collisionBox.y;
        this.entityBoxBottomY = entity.entityPosY + entity.collisionBox.y + entity.collisionBox.height;

        this.entityLeftColumn = entityBoxLeftX / gamePanel.TILE_SIZE;
        this.entityRightColumn = entityBoxRightX / gamePanel.TILE_SIZE;
        this.entityTopRow = entityBoxTopY / gamePanel.TILE_SIZE;
        this.entityBottomRow = entityBoxBottomY / gamePanel.TILE_SIZE;
    }

    public String checkForCollision(Entity entity, String miniDirection) {
        initializeEntityVariables(entity);

        // failsafe is for tolerance. to fix clipping
        /** miniDirection is used for checking whether the entity can or cannot go at a certain direction
         *  for example to check whether you can go left use checkForCollision(<your entity>, "left");
         *  it is then checked if you can go left. the result is also a string. maybe i should use boolean for this
         *  instead...
         * */
        int failSafe = 2;
        switch (miniDirection) {

            case "up":
                entityTopRow = (entityBoxTopY - entity.entitySpeed) / gamePanel.TILE_SIZE;
                tileNumber1 = gamePanel.tileHandler.worldMap[entityLeftColumn][entityTopRow];
                tileNumber2 = gamePanel.tileHandler.worldMap[entityRightColumn][entityTopRow];
                if (gamePanel.tileHandler.tileList.get(tileNumber1).collisionSolid ||
                    gamePanel.tileHandler.tileList.get(tileNumber2).collisionSolid) {
                    return "up";
                }
                break;
            case "down":
                if (entity.entitySpeed > 6) {

                }
                entityBottomRow = (entityBoxBottomY - entity.entitySpeed + (entity.variableEntitySpeed * failSafe)) / gamePanel.TILE_SIZE;
                tileNumber1 = gamePanel.tileHandler.worldMap[entityLeftColumn][entityBottomRow];
                tileNumber2 = gamePanel.tileHandler.worldMap[entityRightColumn][entityBottomRow];
                if (gamePanel.tileHandler.tileList.get(tileNumber1).collisionSolid ||
                    gamePanel.tileHandler.tileList.get(tileNumber2).collisionSolid) {
                    return "down";
                }
                break;
            case "left":
                entityLeftColumn = (entityBoxLeftX - entity.entitySpeed) / gamePanel.TILE_SIZE;
                tileNumber1 = gamePanel.tileHandler.worldMap[entityLeftColumn][entityTopRow];
                tileNumber2 = gamePanel.tileHandler.worldMap[entityLeftColumn][entityBottomRow];
                if (gamePanel.tileHandler.tileList.get(tileNumber1).collisionSolid ||
                    gamePanel.tileHandler.tileList.get(tileNumber2).collisionSolid) {
                    return "left";
                }
                break;
            case "right":
                if (entity.entitySpeed > 6) {

                }
                entityRightColumn = (entityBoxRightX - entity.entitySpeed + (entity.variableEntitySpeed * failSafe)) / gamePanel.TILE_SIZE;
                tileNumber1 = gamePanel.tileHandler.worldMap[entityRightColumn][entityTopRow];
                tileNumber2 = gamePanel.tileHandler.worldMap[entityRightColumn][entityBottomRow];
                if (gamePanel.tileHandler.tileList.get(tileNumber1).collisionSolid ||
                    gamePanel.tileHandler.tileList.get(tileNumber2).collisionSolid) {
                    return "right";
                }
                break;

        }
        return "none";
    }

    public int isCollidingWithObj(Entity entity, boolean player) {
        int index = -1;

        for (int i = 0; i < gamePanel.object.length; i++) {
            if (gamePanel.object[i] == null) {
                continue;
            }

            // creates a new "rectangle" hitbox to check whether an entity is colliding with an object

            Rectangle entRect = new Rectangle(entity.collisionBox);
            Rectangle objRect = new Rectangle(gamePanel.object[i].collisionBox);


            // calculates where the entity is on the map, and then adding the collisions box to it
            // for checking
            entRect.x = entity.entityPosX + entity.collisionBox.x;
            entRect.y = entity.entityPosY + entity.collisionBox.y;

            // the same with the entity
            objRect.x = gamePanel.object[i].objectX + gamePanel.object[i].collisionBox.x;
            objRect.y = gamePanel.object[i].objectY + gamePanel.object[i].collisionBox.y;


            // on switch, it checks the direction for when the entity collides, by adding its speed to it
            // and checking if it is colliding beforehand, this is to make sure that it's checked to
            // avoid clipping
            switch (gamePanel.player.direction) {
                case "up":
                    entRect.y -= entity.entitySpeed;
                    if (entRect.intersects(objRect)) {
                        index = i;
                    }
                    break;
                case "down":
                    entRect.y += entity.entitySpeed;
                    if (entRect.intersects(objRect)) {
                        index = i;
                    }
                    break;
                case "left":
                    entRect.x -= entity.entitySpeed;
                    if (entRect.intersects(objRect)) {
                        index = i;
                    }
                    break;
                case "right":
                    entRect.x += entity.entitySpeed;
                    if (entRect.intersects(objRect)) {
                        index = i;
                    }
                    break;
            }

            // player interacts with an object, so the index of the objects is returned to player
            // otherwise the index is irrelevant, for now at least.
            if (!player) {
                index = -1;
            }
        }
        return index;
    }

    public int checkEntityCollision(Entity entity, Entity[] target) {
        int index = -1;

        for (int i = 0; i < target.length; i++) {
            if (target[i] == null) {
                continue;
            }
            Rectangle entRect = new Rectangle(entity.collisionBox);
            Rectangle objRect = new Rectangle(target[i].collisionBox);


            // calculates where the entity is on the map, and then adding the collisions box to it
            // for checking
            entRect.x = entity.entityPosX + entity.collisionBox.x;
            entRect.y = entity.entityPosY + entity.collisionBox.y;

            // the same with the entity
            objRect.x = target[i].entityPosX + target[i].collisionBox.x;
            objRect.y = target[i].entityPosY + target[i].collisionBox.y;


            // on switch, it checks the direction for when the entity collides, by adding its speed to it
            // and checking if it is colliding beforehand, this is to make sure that it's checked to
            // avoid clipping
            switch (target[i].direction) {
                case "up":
                    entRect.y -= entity.entitySpeed;
                    if (entRect.intersects(objRect)) {
                        index = i;
                    }
                    break;
                case "down":
                    entRect.y += entity.entitySpeed;
                    if (entRect.intersects(objRect)) {
                        index = i;
                    }
                    break;
                case "left":
                    entRect.x -= entity.entitySpeed;
                    if (entRect.intersects(objRect)) {
                        index = i;
                    }
                    break;
                case "right":
                    entRect.x += entity.entitySpeed;
                    if (entRect.intersects(objRect)) {
                        index = i;
                    }
                    break;
            }
        }

        // creates a new "rectangle" hitbox to check whether an entity is colliding with an object



        // player interacts with an object, so the index of the objects is returned to player
        // otherwise the index is irrelevant, for now at least.
        return index;
    }
}
