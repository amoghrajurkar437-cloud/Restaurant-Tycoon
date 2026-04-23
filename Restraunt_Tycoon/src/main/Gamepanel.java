package main;
import entity.Player;
import java.awt.*;
import javax.swing.*;

public class Gamepanel extends JPanel implements Runnable {
    // Screen tile settings
    final int orignalTileSize = 16;
    public final int tileSize = orignalTileSize * 4;

    // 20 x 15 tiles set up
    final int maxScreenCol = 20;
    final int maxScreenRow = 15;

    final int screenWidth = tileSize * maxScreenCol; // 1280 pixels
    final int screenHeight = tileSize * maxScreenRow; // 960 pixels

    int FPS = 60; // Frames per second for the game loop

    KeyHandler keyH = new KeyHandler(); // Key handler for handling keyboard input
    Thread gameThread; // Thread to run the game loop
    Player player = new Player(this, keyH); // Create a player instance and pass the Gamepanel and KeyHandler references

    // Constructor that allows us to set up the game panel
    public Gamepanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Set the preferred size of the panel
        this.setBackground(Color.white); // Set the background color of the panel to white
        this.setDoubleBuffered( true); // Improve rendering performance by enabling double buffering
        this.addKeyListener(keyH);
        this.setFocusable(true); // Make the panel focusable to receive keyboard input
    }

    public void startGameThread() {
        gameThread = new Thread(this); // Create a new thread for the game loop
        gameThread.start(); // Start the game thread
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // 0.1666666 seconds per frame
        double delta = 0; // Variable to track the time difference for frame updates
        long lastTime = System.nanoTime();
        long currentTime;
        // FPS check
        //long timer = 0; 
        //int drawCount = 0;

        // Game loop logic
        while (gameThread != null) {
            // Calculate the time difference and update the game state at the specified FPS
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            // FPS check
            //long timer = 0; 
            //int drawCount = 0;
            //timer += currentTime - lastTime;
            lastTime = currentTime;

            // If enough time has passed (delta >= 1), update the game state and repaint the screen
            if (delta >= 1) {
                update(); // Update game state
                repaint(); // Request a repaint to update the screen
                delta--;
                // drawCount++; FPS check - Increment the draw count for FPS calculation
            }

            // FPS check - Print the actual FPS every second for debugging purposes
            /*if (timer >= 1000000000) {
                // Print the actual FPS every second for debugging purposes
                System.out.println("FPS: " + drawCount);
                drawCount = 0; // Reset the draw count for the next second
                timer = 0; // Reset the timer for the next second
            }*/
        }
    }

    public void update() {
        player.update(); // Update the player's state, including movement and boost logic
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to ensure proper painting
        Graphics2D g2 = (Graphics2D) g; // Cast Graphics to Graphics2D for better control over rendering
        // Draw game elements here using g2
        player.draw(g2); // Draw the player on the screen
        g2.dispose(); // Dispose of the graphics context to free up resources
    }
}