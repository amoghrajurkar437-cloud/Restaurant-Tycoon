package main;

public class Inventory {
    public static final String[] ITEM_NAMES = {
        "Raw Meat", "Buns", "Ice", "Potatoes", "Milk", "Milk Shake Flavors"
    };

    // Max the player can carry per item (money is index -1, handled separately)
    public static final int PLAYER_CAP = 100;

    // Player starts with 0 of everything
    public int[] playerItems = new int[ITEM_NAMES.length];
    public int playerMoney   = 0;

    // Green stall starts with 1000 of everything
    public int[] stallItems  = new int[ITEM_NAMES.length];

    public Inventory() {
        for (int i = 0; i < ITEM_NAMES.length; i++) {
            stallItems[i] = 1000;
        }
    }

    // Transfer 'amount' of item at index from stall to player.
    // Caps at player limit and stall stock. Returns how many were actually taken.
    public int transferToPlayer(int itemIndex, int amount) {
        int inStall     = stallItems[itemIndex];
        int playerHas   = playerItems[itemIndex];
        int canCarry    = PLAYER_CAP - playerHas;

        // Take as many as possible within both limits
        int actual = Math.min(amount, Math.min(inStall, canCarry));
        if (actual <= 0) return 0;

        stallItems[itemIndex]  -= actual;
        playerItems[itemIndex] += actual;

        printPlayerInventory();
        return actual;
    }

    private void printPlayerInventory() {
        System.out.println("--- Player Inventory ---");
        for (int i = 0; i < ITEM_NAMES.length; i++) {
            System.out.println("  " + ITEM_NAMES[i] + ": " + playerItems[i]);
        }
        System.out.println("  Money: " + playerMoney);
        System.out.println("------------------------");
    }
}