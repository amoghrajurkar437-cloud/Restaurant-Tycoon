package main;
import java.util.ArrayList;
import java.util.Random;

public class OrderList {
    // Each entry is { itemName, quantity }
    public ArrayList<String[]> items = new ArrayList<>();

    public OrderList(int level, String stall) {
        Random num = new Random();
        switch (level) {
            case 1 -> {
                int orderCount = num.nextInt(2, 4); // 2 or 3 items per customer
                for (int i = 0; i < orderCount; i++) {
                    if (stall.equals("Red")) {
                        int choice = num.nextInt(2);
                        // Type of food, burger or fries at random
                        String food = choice == 0 ? "Burger" : "Fries";
                        // If food is a burger, the quantity can be 1 to 2, otherwise, if it is Fries, the quantity can be 2 to 4
                        int qty = food.equals("Burger") ? num.nextInt(1, 3) : num.nextInt(2, 4);
                        // Adds the item and number of that item as a pain
                        items.add(new String[]{food, String.valueOf(qty)});
                    }
                    if (stall.equals("Blue")) {
                        int choice = num.nextInt(2);
                        // Type of food, milk shakes or ice cream at random
                        String food = choice == 0 ? "MilkShake" : "IceCream";
                        // If food is a milk shakes, the quantity can be 1 to 2, otherwise, if it is ice cream, the quantity can be 2 to 5
                        int qty = food.equals("MilkShake") ? num.nextInt(1, 3) : num.nextInt(2, 5);
                        // Adds the item and number of that item as a pain
                        items.add(new String[]{food, String.valueOf(qty)});
                    }
                }
            }
            case 2 -> System.out.println("level 2 food possibilities");
            case 3 -> System.out.println("level 3 food possibilities");
        }
    }

    // Reduce the first item's quantity by 1, remove it if it hits 0. Returns true if order is now empty.
    public boolean fulfillNext() {
        if (items.isEmpty()) return true;
        String[] first = items.get(0);
        int qty = Integer.parseInt(first[1]) - 1;
        if (qty <= 0) {
            items.remove(0);
        } else {
            first[1] = String.valueOf(qty);
        }
        return items.isEmpty();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}