package entity;
import main.Gamepanel;
import main.KeyHandler;
import java.awt.*;

public class Player extends Entity {
    Gamepanel gp; // Reference to the Gamepanel, which can be used to access game-related properties and methods
    KeyHandler keyH; // Reference to the KeyHandler, which can be used to check the state of key presses
    // Boost variables
    public boolean boostActive = false; // Indicates whether the boost is currently active
    public int boostTimer = 0; // Timer to track the duration of the boost
    public boolean boostReloading = false; // Indicates whether the boost is currently reloading
    public int reloadTimer = 0; // Timer to track the duration of the boost reload

    public Player(Gamepanel gp, KeyHandler keyH) {
        this.gp = gp; // Initialize the Gamepanel reference
        this.keyH = keyH; // Initialize the KeyHandler reference
        setDefaultValues(); // Set the default values for the player's position and speed
    }

    private void setDefaultValues() {
        x = 100; // Set the default X position of the player
        y = 100; // Set the default Y position of the player
        speed = 5; // Set the default speed of the player
    }

    public void update() {
        // Update game state logic
        // Player postion 
        if (keyH.upPressed == true) {
            y -= speed; // Move player up
        }
        if (keyH.downPressed == true) {
            y += speed; // Move player down
        }
        if (keyH.leftPressed == true) {
            x -= speed; // Move player left
        }
        if (keyH.rightPressed == true) {
            x += speed; // Move player right
        }
        // if boost key is pressed and boost is not active or reloading, activate boost
        if (keyH.boostPressed == true && !boostActive && !boostReloading) {
            boostActive = true;
            boostTimer = 30; // .5 second at 60 FPS
        }
        
        // Handle boost timer and reloading logic
        if (boostActive) {
            boostTimer--;
            if (boostTimer <= 0) {
                boostActive = false;
                boostReloading = true;
                reloadTimer = 60; // 1 second reload time at 60 FPS
            }
        }
        
        // Handle boost reloading timer
        if (boostReloading) {
            reloadTimer--;
            if (reloadTimer <= 0) {
                boostReloading = false;
            }
        }
        
        // Set player speed based on boost status
        if (boostActive) {
            speed = 9; // Increased speed when boost is active
        } else {
            speed = 5;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.black); // Set the drawing color to black
        g2.fillRect(x, y, gp.tileSize, gp.tileSize); // Fill the background with black
    }
}
