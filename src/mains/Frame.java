package mains;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.util.Objects;

public class Frame extends JFrame{
    ImageIcon icon;
    Panel gamePanel;
    Frame() {
        icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/essentials/logo_rounded.png")));

        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Java Game");
        gamePanel = new Panel();
        this.getContentPane().add(gamePanel);
        gamePanel.setThread();
        gamePanel.setupGame();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

