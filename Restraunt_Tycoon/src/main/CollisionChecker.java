package main;
import entity.Entity;

public class CollisionChecker {
    Gamepanel gp;

    public CollisionChecker(Gamepanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // Calculate the edges of the entity's solid area in world coordinates
        int entityLeftWorldX = entity.worldX + entity.solidArea.x; // Calculate the left edge of the entity's solid area in world coordinates
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width; // Calculate the right edge of the entity's solid area in world coordinates
        int entityTopWorldY = entity.worldY + entity.solidArea.y; // Calculate the top edge of the entity's solid area in world coordinates
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height; // Calculate the bottom edge of the entity's solid area in world coordinates

        int entityLeftCol = entityLeftWorldX / gp.tileSize; // Determine which tile column the left edge of the solid area is in
        int entityRightCol = entityRightWorldX / gp.tileSize; // Determine which tile column the right edge of the solid area is in
        int entityTopRow = entityTopWorldY / gp.tileSize; // Determine which tile row the top edge of the solid area is in
        int entityBottomRow = entityBottomWorldY / gp.tileSize; // Determine which tile row the bottom edge of the solid area is in

        // Stalls are 4x4 tiles, so we need to check the stall layer separately
        int stallLeftWorldX = entity.worldX + entity.solidArea.x; // Calculate the left edge of the entity's solid area in world coordinates (same as tile layer)
        int stallRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width; // Calculate the right edge of the entity's solid area in world coordinates (same as tile layer)
        int stallTopWorldY = entity.worldY + entity.solidArea.y; // Calculate the top edge of the entity's solid area in world coordinates (same as tile layer)
        int stallBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height; // Calculate the bottom edge of the entity's solid area in world coordinates (same as tile layer)

        int stallLeftCol = stallLeftWorldX / gp.stallTileSize; // Determine which stall column the left edge of the solid area is in
        int stallRightCol = stallRightWorldX / gp.stallTileSize; // Determine which stall column the right edge of the solid area is in
        int stallTopRow = stallTopWorldY / gp.stallTileSize; // Determine which stall row the top edge of the solid area is in
        int stallBottomRow = stallBottomWorldY / gp.stallTileSize; // Determine which stall row the bottom edge of the solid area is in
        int tileNum1, tileNum2; // Variables to hold the tile numbers for collision checking

        // Check collision with stall layer first since it's on top of the tile layer
        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (stallTopWorldY - entity.speed) / gp.stallTileSize; // Calculate the new top row if the entity moves up
                tileNum1 = gp.tileM.stallTileNum[stallLeftCol][stallTopRow]; // Get the tile number for the left edge of the solid area 
                tileNum2 = gp.tileM.stallTileNum[stallRightCol][stallTopRow]; // Get the tile number for the right edge of the solid area
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true; // Set collision flag if either tile has collision properties
                    checkStall(entity);
                }
            }
            case "down" -> {
                entityBottomRow = (stallBottomWorldY + entity.speed) / gp.stallTileSize; // Calculate the new bottom row if the entity moves down
                tileNum1 = gp.tileM.stallTileNum[stallLeftCol][stallBottomRow]; // Get the tile number for the left edge of the solid area 
                tileNum2 = gp.tileM.stallTileNum[stallRightCol][stallBottomRow]; // Get the tile number for the right edge of the solid area
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true; // Set collision flag if either tile has collision properties
                    checkStall(entity);
                }
            }
            case "left" -> {
                entityLeftCol = (stallLeftWorldX - entity.speed) / gp.stallTileSize; // Calculate the new left column if the entity moves left
                tileNum1 = gp.tileM.stallTileNum[stallLeftCol][stallTopRow]; // Get the tile number for the left edge of the solid area
                tileNum2 = gp.tileM.stallTileNum[stallLeftCol][stallBottomRow]; // Get the tile number for the right edge of the solid area
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true; // Set collision flag if either tile has collision properties
                    checkStall(entity);
                }
            }
            case "right" -> {
                entityRightCol = (stallRightWorldX + entity.speed) / gp.stallTileSize; // Calculate the new right column if the entity moves right
                tileNum1 = gp.tileM.stallTileNum[stallRightCol][stallTopRow]; // Get the tile number for the left edge of the solid area
                tileNum2 = gp.tileM.stallTileNum[stallRightCol][stallBottomRow]; // Get the tile number for the right edge of the solid area
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true; // Set collision flag if either tile has collision properties
                    checkStall(entity);
                }
            }
        }

        // If no collision with stall layer, check tile layer
        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize; // Calculate the new top row if the entity moves up
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; // Get the tile number for the left edge of the solid area 
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; // Get the tile number for the right edge of the solid area
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true; // Set collision flag if either tile has collision properties
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize; // Calculate the new bottom row if the entity moves down
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; // Get the tile number for the left edge of the solid area 
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; // Get the tile number for the right edge of the solid area
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true; // Set collision flag if either tile has collision properties
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize; // Calculate the new left column if the entity moves left
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow]; // Get the tile number for the left edge of the solid area
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; // Get the tile number for the right edge of the solid area
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true; // Set collision flag if either tile has collision properties
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize; // Calculate the new right column if the entity moves right
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; // Get the tile number for the left edge of the solid area
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; // Get the tile number for the right edge of the solid area
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true; // Set collision flag if either tile has collision properties
                }
            }
        }
    }

    public void checkStall(Entity entity) {
        System.out.println("Checking stall collision for entity at worldX: " + entity.worldX + ", worldY: " + entity.worldY);
        System.out.println("Entity direction: " + entity.direction);
    }

}