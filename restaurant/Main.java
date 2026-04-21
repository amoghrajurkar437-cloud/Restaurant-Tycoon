package restaurant; // Package organize classes in Java, must be named the after the folder name
import java.io.File;
import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame window = new JFrame(); // Create a new JFrame window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
        window.setResizable(false); // Prevent the window from being resized
        window.setLocationRelativeTo(null); // Center the window on the screen
        window.setVisible(true); // Make the window visible
        window.setSize(1200, 800); // Set the size of the window
        window.setTitle("Restaurant"); // Set the title of the window
        Gamepanel gamePanel = new Gamepanel(); // Create an instance of the GamePanel class
        window.add(gamePanel); // Add the GamePanel to the window
        window.pack(); // Adjust the window size to fit the preferred size of its components

        // File data
        File level1 = new File("restaurant/level_map_data/level1.txt");
        File level2 = new File("restaurant/level_map_data/level2.txt");
        File level3 = new File("restaurant/level_map_data/level3.txt");

        // Create instances of the GenerateLevel class for each level
        GenerateLevel generateLevel = new GenerateLevel(1); // Create an instance of the GenerateLevel class with level 1
        GenerateLevel generateLeve2 = new GenerateLevel(2); // Create an instance of the GenerateLevel class with level 2
        GenerateLevel generateLeve3 = new GenerateLevel(3); // Create an instance of the GenerateLevel class with level 3  

        // Process the level files to populate the map arrays
        generateLevel.ProcessFile(level1); // Process the first level file
        generateLeve2.ProcessFile(level2); // Process the second level file
        generateLeve3.ProcessFile(level3); // Process the third level file

        // Load the map data into the game panel
        if (true) {
            generateLevel.loadMap(gamePanel, generateLevel.map1, generateLevel.map2, generateLevel.map3);
        }
    }
}

