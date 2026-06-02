package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import main.Gamepanel;

// Car entity with movement, spawning, InPath and outPath like Customer.
// Drawn at 4x tile size with no animation.
public class Car extends Entity {

    private BufferedImage image;
    public int carSize = 4; // Size is 4x tile size
    public Random rand = new Random();
    public int InPath = rand.nextInt(3) + 1;
    public boolean place_order = false;
    public boolean isServed = false;
    public int outPath = InPath;
    public boolean leftMap = false;

    public Car(Gamepanel gp, int worldX, int worldY) {
        super(gp);
        this.worldX = worldX;
        this.worldY = worldY;
        direction = "left";
        speed = 5;
        solidArea = new java.awt.Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = gp.tileSize * carSize - 16;
        solidArea.height = gp.tileSize * carSize - 16;
        loadImage();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(new File("res/tiles/Red_truck.png"));
        } catch (IOException e) {
            System.out.println("Failed to load car image: " + e.getMessage());
            image = null;
        }
    }

    public void InPath() {
        if (worldX > gp.tileSize * 34) {
            direction = "left";
            update();
        }
    }

    public void outPath() {
        if (worldX > 0) {
            direction = "left";
            update();
        } else {
            leftMap = true;
        }
    }

    public void update() {
        isMoving = false;
        collisionOn = false;
        gp.cChecker.checkEntityCollision(this, gp.cars); // Avoid other cars

        // World boundaries for cars
        if (direction.equals("up") && worldY <= 0) {
            collisionOn = true;
        }
        if (direction.equals("down") && worldY + gp.tileSize * carSize >= gp.worldHeight) {
            collisionOn = true;
        }
        if (direction.equals("left") && worldX <= 0) {
            collisionOn = true;
        }
        if (direction.equals("right") && worldX + gp.tileSize * carSize >= gp.worldWidth) {
            collisionOn = true;
        }

        if (collisionOn == false) {
            isMoving = true;
            switch (direction) {
                case "up" -> {
                    worldY -= speed; // Update worldX to reflect the customer's movement in the world
                    isMoving = true;
                    break;
                }
                case "down" -> {
                    worldY += speed; // Update worldY to reflect the customer's movement in the world
                    isMoving = true;
                    break;
                }
                case "left" -> {
                    worldX -= speed; // Update worldX to reflect the customer's movement in the world
                    isMoving = true;
                    break;
                }
                case "right" -> {
                    worldX += speed; // Update worldX to reflect the customer's movement in the world
                    isMoving = true;
                    break;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (image == null) {
            return;
        }
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        int size = gp.tileSize * carSize;
        g2.drawImage(image, screenX, screenY, size, size, null);
    }
}
