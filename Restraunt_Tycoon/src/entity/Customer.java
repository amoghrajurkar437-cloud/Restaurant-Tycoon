package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Gamepanel;

public class Customer extends Entity {
    public  int screenX; // X position of the customer on the screen, which can be used for rendering the customer
    public  int screenY; // Y position of the customer on the screen, which can be used for rendering the customer
    public Customer(Gamepanel gp, int x, int y) {
        super(gp);
        direction = "down";
        speed = 5;
        screenX = x;
        screenY = y;

        getCustomerImage();
    }

    private void getCustomerImage() {
        try {
            up1 = ImageIO.read(new File("res/player/up1.png"));
            up2 = ImageIO.read(new File("res/player/up2.png"));
            down1 = ImageIO.read(new File("res/player/down1.png"));
            down2 = ImageIO.read(new File("res/player/down2.png"));
            left1 = ImageIO.read(new File("res/player/left1.png"));
            left2 = ImageIO.read(new File("res/player/left2.png"));
            right1 = ImageIO.read(new File("res/player/right1.png"));
            right2 = ImageIO.read(new File("res/player/right2.png"));
            upStill = ImageIO.read(new File("res/player/up_still.png"));
            downStill = ImageIO.read(new File("res/player/down_still.png"));
            leftStill = ImageIO.read(new File("res/player/left_still.png"));
            rightStill = ImageIO.read(new File("res/player/right_still.png"));
        } catch (IOException e) {
            System.out.println("Error loading customer images");
        }
    }

    public void update() {
        // Logic to update the customer's position and behavior goes here
        isMoving = false;
    }
        public void draw(Graphics2D g2) {
            BufferedImage image = null; // Variable to hold the current image to be drawn based on the player's direction and animation state

            if (!isMoving) {
                // Use still images when the player is not moving
                switch (direction) {
                    case "up" -> image = upStill;
                    case "down" -> image = downStill;
                    case "left" -> image = leftStill;
                    case "right" -> image = rightStill;
                }
            } else {
                // Use walking animation when the player is moving
                switch (direction) {
                    case "up" -> {
                        if (SpriteNum == 1) {
                            image = up1;
                        } else {
                            image = up2;
                        }
                    }
                    case "down" -> {
                    if (SpriteNum == 1) {
                            image = down1;
                        } else {
                            image = down2;
                        }
                    }
                    case "left" -> {
                        if (SpriteNum == 1) {
                            image = left1;
                        } else {
                            image = left2;
                        }
                    }
                    case "right" -> {
                        if (SpriteNum == 1) {
                            image = right1;
                        } else {
                            image = right2;
                        }
                    }
                }
            }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // Draw the player image at the current position with the specified tile size

    }
}
