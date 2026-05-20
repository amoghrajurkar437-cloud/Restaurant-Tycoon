package main;
import entity.Player;
import java.awt.*;
import javax.swing.*;
import main.tile.TileManager;

public class Gamepanel extends JPanel implements Runnable {
    // Screen tile settings
    final int orignalTileSize = 16;
    public final int tileSize = orignalTileSize * 4;
    public final int stallTileSize = tileSize * 4;

    // 20 x 15 tile screen
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 15;

    public final int screenWidth  = tileSize * maxScreenCol; // 1280 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 960 pixels

    int boostBarWidth = 220;
    int boostBarHeight = 28;
    int boostBarMargin = 20;

    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);
    public OrderBoard orderBoard = new OrderBoard(this);
    public Inventory inventory = new Inventory();
    public RestockPanel restockPanel = new RestockPanel(inventory);

    // World settings
    public final int maxWorldCol = 60;
    public final int maxWorldRow = 45;
    public final int worldWidth  = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    public String gameState = "WORLD";
    public final String WORLD_STATE = "WORLD";
    public final String STALL_STATE = "STALL";

    // Tracks which stall the player is currently inside
    public String currentStallType = "";

    // One-shot consumed flags so held keys don't repeat actions
    private boolean toggleConsumed = false;
    private boolean fulfillConsumed = false;
    private boolean enterConsumed = false;
    private boolean backspaceConsumed = false;
    private boolean upConsumed = false;
    private boolean downConsumed = false;
    private int lastDigitConsumed = -1;

    public Gamepanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(62, 194, 83));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        player.update();

        if (gameState.equals(STALL_STATE)) {
            if (currentStallType.equals("Green")) {
                updateRestockPanel();
            } else {
                updateOrderBoard();
            }
        }
    }

    private void updateOrderBoard() {
        // toggle order board visibility
        if (keyH.toggleOrdersPressed && !toggleConsumed) {
            orderBoard.visible = !orderBoard.visible;
            toggleConsumed = true;
        }
        if (!keyH.toggleOrdersPressed) toggleConsumed = false;

        // fulfill next item on customer 1
        if (keyH.fulfillPressed && !fulfillConsumed) {
            orderBoard.fulfillFirst();
            fulfillConsumed = true;
        }
        if (!keyH.fulfillPressed) fulfillConsumed = false;
    }

    private void updateRestockPanel() {
        // toggle restock panel visibility
        if (keyH.toggleOrdersPressed && !toggleConsumed) {
            restockPanel.visible = !restockPanel.visible;
            toggleConsumed = true;
        }
        if (!keyH.toggleOrdersPressed) toggleConsumed = false;

        if (!restockPanel.visible) return;

        // Keep KeyHandler typingMode in sync with the panel
        keyH.typingMode = restockPanel.typingMode;

        // Arrow keys navigate the list, WASD moves the player
        if (!restockPanel.typingMode) {
            if (keyH.upArrow && !upConsumed) {
                restockPanel.moveSelection(-1);
                upConsumed = true;
            }
            if (!keyH.upArrow) upConsumed = false;

            if (keyH.downArrow && !downConsumed) {
                restockPanel.moveSelection(1);
                downConsumed = true;
            }
            if (!keyH.downArrow) downConsumed = false;
        }

        // Enter — either enter typing mode or confirm transfer
        if (keyH.enterPressed && !enterConsumed) {
            if (!restockPanel.typingMode) {
                restockPanel.selectCurrentItem();
                keyH.typingMode = true;
            } else {
                restockPanel.confirmTransfer();
                keyH.typingMode = false;
            }
            enterConsumed = true;
        }
        if (!keyH.enterPressed) enterConsumed = false;

        // Number keys — append digit to typed quantity
        if (restockPanel.typingMode && keyH.lastDigit != -1 && keyH.lastDigit != lastDigitConsumed) {
            restockPanel.appendDigit((char) ('0' + keyH.lastDigit));
            lastDigitConsumed = keyH.lastDigit;
        }
        if (keyH.lastDigit == -1) lastDigitConsumed = -1;

        // Backspace — delete last typed digit
        if (keyH.backspacePressed && !backspaceConsumed) {
            restockPanel.deleteDigit();
            backspaceConsumed = true;
        }
        if (!keyH.backspacePressed) backspaceConsumed = false;
    }

    private void drawBoostBar(Graphics2D g2) {
        int x = screenWidth - boostBarWidth - boostBarMargin;
        int y = boostBarMargin;
        float ratio = player.getBoostChargeRatio();
        int innerWidth = boostBarWidth - 4;
        int fillWidth = Math.max(0, Math.min(innerWidth, (int)(innerWidth * ratio)));

        g2.setColor(new Color(0, 0, 0, 170));
        g2.fillRoundRect(x, y, boostBarWidth, boostBarHeight, 14, 14);

        g2.setColor(new Color(30, 30, 30));
        g2.fillRoundRect(x + 2, y + 2, innerWidth, boostBarHeight - 4, 12, 12);

        g2.setColor(new Color(0, 120, 255));
        g2.fillRoundRect(x + 2, y + 2, fillWidth, boostBarHeight - 4, 12, 12);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, boostBarWidth, boostBarHeight, 14, 14);

        g2.setFont(new Font("Arial", Font.BOLD, 16));
        String label = "SHIFT = SPRINT";
        FontMetrics fm = g2.getFontMetrics();
        int textX = x + (boostBarWidth - fm.stringWidth(label)) / 2;
        int textY = y + ((boostBarHeight - fm.getHeight()) / 2) + fm.getAscent();
        g2.drawString(label, textX, textY);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState.equals(WORLD_STATE)) {
            tileM.draw(g2);
        } else if (gameState.equals(STALL_STATE)) {
            tileM.drawStallInterior(g2);
        }

        // Player drawn before panels so the overlay renders on top of it
        player.draw(g2);
        drawBoostBar(g2);

        if (gameState.equals(STALL_STATE)) {
            if (currentStallType.equals("Green")) {
                restockPanel.draw(g2);
            } else {
                orderBoard.draw(g2);
            }
        }
        g2.dispose();
    }
}