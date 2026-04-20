package restaurant;
import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setSize(1200, 800);
        Gamepanel gamePanel = new Gamepanel();
        window.add(gamePanel);
        window.pack();
        System.out.print("hello world");
    }
}

