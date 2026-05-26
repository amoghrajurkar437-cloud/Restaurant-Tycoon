package main;

public class Inventory {

    public static final String[] INVENTORY = {"Raw Meat", "Buns", "Ice", "Potatoes", "Milk", "Milk Shake Flavors", "Burgers", "Fries", "Milk Shakes", "Ice Creams"};

    // Max the player can carry per item
    public static final int PLAYER_CAP = 100;

    // Player starts with 0 of everything
    public static int[] playerItems = new int[INVENTORY.length];
    public int playerMoney = 1000;

    // Green stall starts with 1000 of everything
    public static int[] stallItems = new int[INVENTORY.length];

    public Inventory() {
        for (int i = 0; i < INVENTORY.length; i++) {
            stallItems[i] = 1000;
        }
    }

    // Caps at player limit and stall stock. Returns how many were actually taken.
    public static int giveToPlayer(int itemIndex, int amount) {
        int inStall = stallItems[itemIndex];
        int playerHas = playerItems[itemIndex];
        int canCarry = PLAYER_CAP - playerHas;

        // Take as many as possible within both limits
        int actual = Math.min(amount, Math.min(inStall, canCarry));
        if (actual <= 0) {
            return 0;
        }

        stallItems[itemIndex] -= actual;
        playerItems[itemIndex] += actual;

        printPlayerInventory();
        return actual;
    }

    public static boolean takeFromPlayer(int itemIndex, int amount) {
        int playerHas = playerItems[itemIndex];
        if (playerHas < amount) {
            return false;
        }
        playerItems[itemIndex] -= amount;
        printPlayerInventory();
        return true;
    }

    private static void printPlayerInventory() {
        for (int i = 0; i < INVENTORY.length; i++) {
            System.out.println(INVENTORY[i] + ": " + playerItems[i]);
        }
    }
}
