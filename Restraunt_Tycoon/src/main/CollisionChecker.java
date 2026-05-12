package main;
import entity.Entity;

public class CollisionChecker {
    Gamepanel gp;
    public static String contactStall = "";

    public CollisionChecker(Gamepanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // Calculate the edges of the entity's solid area in world coordinates
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
 
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;
 
        int stallLeftWorldX = entity.worldX + entity.solidArea.x;
        int stallRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int stallTopWorldY = entity.worldY + entity.solidArea.y;
        int stallBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
 
        int stallLeftCol = stallLeftWorldX / gp.stallTileSize;
        int stallRightCol = stallRightWorldX / gp.stallTileSize;
        int stallTopRow = stallTopWorldY / gp.stallTileSize;
        int stallBottomRow = stallBottomWorldY / gp.stallTileSize;
        int tileNum1, tileNum2;
 
        // Check collision with stall layer first
        switch (entity.direction) {
            case "up" -> {
                stallTopRow = (stallTopWorldY - entity.speed) / gp.stallTileSize;
                tileNum1 = gp.tileM.stallTileNum[stallLeftCol][stallTopRow];
                tileNum2 = gp.tileM.stallTileNum[stallRightCol][stallTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                    checkStall(entity);
                }
            }
            case "down" -> {
                stallBottomRow = (stallBottomWorldY + entity.speed) / gp.stallTileSize;
                tileNum1 = gp.tileM.stallTileNum[stallLeftCol][stallBottomRow];
                tileNum2 = gp.tileM.stallTileNum[stallRightCol][stallBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                    checkStall(entity);
                }
            }
            case "left" -> {
                stallLeftCol = (stallLeftWorldX - entity.speed) / gp.stallTileSize;
                tileNum1 = gp.tileM.stallTileNum[stallLeftCol][stallTopRow];
                tileNum2 = gp.tileM.stallTileNum[stallLeftCol][stallBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                    checkStall(entity);
                }
            }
            case "right" -> {
                stallRightCol = (stallRightWorldX + entity.speed) / gp.stallTileSize;
                tileNum1 = gp.tileM.stallTileNum[stallRightCol][stallTopRow];
                tileNum2 = gp.tileM.stallTileNum[stallRightCol][stallBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                    checkStall(entity);
                }
            }
        }
 
        // Check tile layer
        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
            }
        }
    }

    public void checkStall(Entity entity) {
        if (entity.worldX < 1050 && entity.worldY < 750) {
            contactStall = "Blue";
        } else if (entity.worldX > 1950 && entity.worldY > 650) {
            contactStall = "Red";
        } else if (entity.worldX < 1050 && entity.worldY > 2200) {
            contactStall = "Green";
        }
    }
}
