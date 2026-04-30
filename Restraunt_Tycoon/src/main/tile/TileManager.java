package main.tile;
// Graphics rendering for drawing on the game panel
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.Gamepanel;

public class TileManager {
    Gamepanel gp;
    Tile[] tile;
    int mapTileNum[][];
    int mapObjectNum[][]; // Layer for objects/stalls drawn on top

    public TileManager(Gamepanel gp) {
        this.gp = gp;
        tile = new Tile[17]; // 17 types of tiles
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow]; // 20 x 15 tiles
        mapObjectNum = new int[gp.maxScreenCol][gp.maxScreenRow]; // Object layer initialized to 0 (empty)
        getTileImage(); // Load the tile images
        loadMap("/res/maps/map1.txt"); // Load the map layout from a text file
        loadObjectMap("/res/maps/map1_objects.txt"); // Load the object/stall layout from a text file
    }

    private BufferedImage loadImage(String fileName) {
        try (InputStream is = getClass().getResourceAsStream("/tiles/" + fileName)) {
            if (is != null) {
                return ImageIO.read(is);
            }
        } catch (IOException ignored) {
        }

        try {
            return ImageIO.read(new File("res/tiles/" + fileName));
        } catch (IOException e) {
            System.err.println("Failed to load tile image '" + fileName + "': " + e.getMessage());
            return null;
        }
    }

    private void getTileImage() {
        // Load tile images and set collision properties
        tile[0] = new Tile();
        tile[0].image = loadImage("Grass.png"); // res/tiles/Grass.png

        tile[1] = new Tile();
        tile[1].image = loadImage("Rock.png"); // res/tiles/Rock.png

        tile[2] = new Tile();
        tile[2].image = loadImage("Floor.png"); // res/tiles/Floor.png

        tile[3] = new Tile();
        tile[3].image = loadImage("Bush.png"); // res/tiles/Bush.png

        tile[4] = new Tile();
        tile[4].image = loadImage("Concrete_Path.png"); // res/tiles/Concrete_Path.png

        tile[5] = new Tile();
        tile[5].image = loadImage("Road.png"); // res/tiles/Road.png

        tile[6] = new Tile();
        tile[6].image = loadImage("Rough_Path.png"); // res/tiles/Rough_Path.png

        tile[7] = new Tile();
        tile[7].image = loadImage("Tree.png"); // res/tiles/Tree.png
        tile[7].collision = true;

        tile[8] = new Tile();
        tile[8].image = loadImage("RedStall.png"); // res/tiles/RedStall.png
        tile[8].collision = true;

        tile[9] = new Tile();
        tile[9].image = loadImage("BlueStall.png"); // res/tiles/BlueStall.png
        tile[9].collision = true;

        tile[10] = new Tile();
        tile[10].image = loadImage("GreenStall.png"); // res/tiles/GreenStall.png
        tile[10].collision = true;
    }

    private void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br;
            if (is != null) {
                br = new BufferedReader(new InputStreamReader(is));
            } else {
                // Fallback to file system reading
                br = new BufferedReader(new java.io.FileReader(filePath.substring(1))); // Remove leading "/"
            }
            int col = 0;
            int row = 0;
            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();
                if (line == null) 
                    break; // End of file
                while (col < gp.maxScreenCol) {
                    String numbers[] = line.split(" ");
                    if (col < numbers.length) {
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[col][row] = num;
                    }
                    col++;
                }
                if (col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Error reading map file '" + filePath + "': " + e.getMessage());
        }
    }

    private void loadObjectMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br;
            if (is != null) {
                br = new BufferedReader(new InputStreamReader(is));
            } else {
                // Fallback to file system reading
                br = new BufferedReader(new java.io.FileReader(filePath.substring(1))); // Remove leading "/"
            }
            int col = 0;
            int row = 0;
            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();
                if (line == null) 
                    break; // End of file
                while (col < gp.maxScreenCol) {
                    String numbers[] = line.split(" ");
                    if (col < numbers.length) {
                        int num = Integer.parseInt(numbers[col]);
                        mapObjectNum[col][row] = num;
                    }
                    col++;
                }
                if (col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (IOException e) {
            // Object map file is optional - if it doesn't exist, all values stay at 0 (empty)
            System.out.println("Object map file '" + filePath + "' not found. Using empty object layer.");
        }
    }

    public void draw(Graphics2D g2) {
        // Draw background tiles first
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;
            
            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }

        // Draw objects/stalls on top of the background
        col = 0;
        row = 0;
        x = 0;
        y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int objectNum = mapObjectNum[col][row];
            if (objectNum != 0) { // 0 means no object
                g2.drawImage(tile[objectNum].image, x, y, gp.tileSize, gp.tileSize, null);
            }
            col++;
            x += gp.tileSize;
            
            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
