package restaurant; 
import java.awt.*;
import javax.swing.*;

public class Gamepanel extends JPanel {
    // Screen tile settings
    final int orignalTileSize = 16;
    public final int tileSize = orignalTileSize * 3;

    // 18 x 14 tiles set up
    public final int maxScreenCol = 18;
    public final int maxScreenRow = 14;

    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // Constructor that allows us to set up the game panel
    public Gamepanel(){
        this.setPreferredSize(new Dimension(1200, 800)); // Set the preferred size of the panel
        this.setBackground(Color.white); // Set the background color of the panel to white
        this.setDoubleBuffered( true); // Improve rendering performance by enabling double buffering
    }

    public void paintComponent(Graphics g, int[] map1, int[] map2, int[] map3, int level, int tileSize, String color) {
        super.paintComponent(g);

        for (int row = 0; row < maxScreenRow; row++) {
            for (int col = 0; col < maxScreenCol; col++) {
                int x = col * tileSize; // Calculate the x-coordinate for the current tile
                int y = row * tileSize; // Calculate the y-coordinate for the current tile

                g.setColor(Color.decode(color)); // Set the color for drawing the tile
                g.fillRect(x, y, tileSize, tileSize); // Draw the tile
            }
        }
    }
}