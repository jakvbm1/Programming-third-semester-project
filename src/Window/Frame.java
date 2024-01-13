package Window;
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private static final int width = 600;
    private static final int height = 400;
    private Panel panel;

    public Frame()
    {
        setSize(width, height);
        setTitle("Rozklad LU");
        setResizable(false);
        this.panel = new Panel(this);
        Container cont = getContentPane();
        cont.add(panel);
    }

}

