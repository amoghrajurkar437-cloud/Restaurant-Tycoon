package restaurant;
import java.awt.*;
import javax.swing.*;

public class Gamepanel extends JPanel {
    final int orignalTileSize = 16;
    public final int tileSize = orignalTileSize * 3;
    public final int maxScreenCol = 18;
    public final int maxScreenRow = 14;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    public Gamepanel(){
        this.setPreferredSize(new Dimension(1200, 800));
        this.setBackground(Color.black);
        this.setDoubleBuffered( true);
    }
}