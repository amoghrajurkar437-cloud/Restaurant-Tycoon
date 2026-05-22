package main;
import java.awt.*;
import java.util.ArrayList;

public class OrderBoard {
    // ArrayList where each element is a customer's full OrderList (their items + quantities).
    // It is final, but customers can still be added/removed from it
    private final ArrayList<OrderList> customers = new ArrayList<>();

    // Layout constants
    private static final int PANEL_X = 10;
    private static final int PANEL_Y = 10;
    private static final int ROW_WIDTH = 200;
    private static final int ROW_HEIGHT = 22;
    private static final int HEADER_HEIGHT = 28;
    private static final int PADDING = 10;
    private static final int CORNER_ARC = 12;

    public boolean visible = true;

    public OrderBoard(Gamepanel gp) {
    }

    // Called when entering a stall — generate a fresh set of customers.
    // Green is a restock stall so it gets no customer orders.
    public void loadForStall(String stall) {
        customers.clear();
        if (stall.equals("Green")) return;
        customers.add(new OrderList(1, stall));
        customers.add(new OrderList(1, stall));
    }

    // Press 2 — give the next item on customer 1's order, remove them if done
    public void fulfillFirst() {
        if (customers.isEmpty()) return;
        boolean done = customers.get(0).giveFood();
        if (done) customers.remove(0);
    }

    public void draw(Graphics2D g2) {
        if (!visible || customers.isEmpty()) return;

        int rows = customers.size();
        int panelWidth = rows * ROW_WIDTH + PADDING * 2;

        // Find max rows needed so background height fits the tallest column
        int maxItems = 0;
        for (OrderList o : customers) 
            maxItems = Math.max(maxItems, o.items.size());
        int panelHeight = HEADER_HEIGHT + maxItems * ROW_HEIGHT + PADDING * 2;

        // Semi-transparent background
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(PANEL_X, PANEL_Y, panelWidth, panelHeight, CORNER_ARC, CORNER_ARC);
        // Border
        g2.setColor(new Color(255, 255, 255, 60));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(PANEL_X, PANEL_Y, panelWidth, panelHeight, CORNER_ARC, CORNER_ARC);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        for (int i = 0; i < rows; i++) {
            OrderList order = customers.get(i);
            int rowX = PANEL_X + PADDING + i * ROW_WIDTH;

            // Divider line between columns
            if (i > 0) {
                g2.setColor(new Color(255, 255, 255, 40));
                g2.setStroke(new BasicStroke(1f));
                g2.drawLine(rowX - PADDING / 2, PANEL_Y + 6, rowX - PADDING / 2, PANEL_Y + panelHeight - 6);
            }

            // Header
            g2.setFont(new Font("Arial", Font.BOLD, 13));
            g2.setColor(new Color(255, 220, 80));
            g2.drawString("Customer " + (i + 1) + ":", rowX, PANEL_Y + PADDING + 14);

            // Items and quantities on the planet
            g2.setFont(new Font("Arial", Font.PLAIN, 13));
            g2.setColor(Color.WHITE);
            for (int j = 0; j < order.items.size(); j++) {
                String[] item = order.items.get(j);
                String line = item[0] + " x" + item[1];
                int textY = PANEL_Y + PADDING + HEADER_HEIGHT + j * ROW_HEIGHT;
                g2.drawString(line, rowX, textY);
            }
        }
    }
}