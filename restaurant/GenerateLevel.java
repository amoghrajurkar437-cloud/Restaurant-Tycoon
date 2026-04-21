package restaurant;
import java.io.File;
import java.util.*;

public class GenerateLevel {
    private int level;
    int[] map1 = new int[18 * 14 + 1]; // Array to hold the map data for level 1
    int[] map2 = new int[18 * 14 + 1]; // Array to hold the map data for level 2
    int[] map3 = new int[18 * 14 + 1]; // Array to hold the map data for level 3

    // Constructor to initialize the level
    public GenerateLevel(int level) {
        this.level = level;
    }

    // Getter for level
    public int getLevel() {
        return level;
    }

    // Setter for level
    public void setLevel(int level) {
        this.level = level;
    }

    // Checks counter and resets it if its to the max size of the map arrays
    public int checkCounter(int c) {
        if (c >= 253) {
            return 0; // Reset the counter to 0 if it reaches the maximum size of the map arrays
        }
        else {
            return c; // Return the current value of the counter
        }
    }

    // Method to process the level file and print its contents
    public void ProcessFile(File file) {
        int counter = 0; // Counter to keep track of the current position in the map arrays
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (char c : line.toCharArray()) {
                    // Store the character in the appropriate map array based on the level
                    switch (level) {
                        case 1 -> {
                            // Convert the character to its numeric value and store it in map1
                            map1[counter] = Character.getNumericValue(c);
                            counter++; // Increment the counter to move to the next position in the map1 array
                            counter = checkCounter(counter);
                            break;
                        }
                        case 2 -> {
                            // Convert the character to its numeric value and store it in map2
                            map2[counter] = Character.getNumericValue(c);
                            counter++; // Increment the counter to move to the next position in the map2 array
                            counter = checkCounter(counter);
                            break;
                        }
                        case 3 -> {
                            // Convert the character to its numeric value and store it in map3
                            map3[counter] = Character.getNumericValue(c);
                            counter++; // Increment the counter to move to the next position in the map3 array
                            counter = checkCounter(counter);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Print an error message if there is an issue reading the file
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    public void loadMap(Gamepanel gamePanel, int[] map1, int[] map2, int[] map3) {
        // This method can be used to load the map data into the game panel
        // Implementation will depend on how you want to represent the map in the game
        switch (getLevel()) {
            // Load map1 into the game panel
            case 1 -> {
                if (map1 != null) {
                    for (int i = 0; i < map1.length; i++) {
                        // Code to load map1 into the game panel based on the values in the map1 array
                        switch (map1[i]) {
                            case 0 -> {
                                gamePanel.paintComponent(gamePanel.getGraphics(), map1, map2, map3, getLevel(), gamePanel.tileSize, "#000000"); // Example of how to call the paintComponent method with the map data
                                break;
                            }
                            case 1 -> {
                                gamePanel.paintComponent(gamePanel.getGraphics(), map1, map2, map3, getLevel(), gamePanel.tileSize, "#FFFFFF"); // Example of how to call the paintComponent method with the map data
                                break;
                            }
                            case 2 -> {
                                gamePanel.paintComponent(gamePanel.getGraphics(), map1, map2, map3, getLevel(), gamePanel.tileSize, "#808080"); // Example of how to call the paintComponent method with the map data
                                break;
                            }
                        }
                    }
                }
                break;
            }
            case 2 -> {
                // Load map2 into the game panel
                if (map2 != null) {
                    // Code to load map2 into the game panel
                }
                break;
            }
            case 3 -> {
                // Load map3 into the game panel
                if (map3 != null) {
                    // Code to load map3 into the game panel
                }
                break;
            }
        }
    }
}
