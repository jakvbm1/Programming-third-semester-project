package Window;

import javax.swing.*;
import java.awt.*;
import java.util.jar.JarEntry;

public class ResultFrame extends JFrame{
    private ResultPanel panel;
    private double[][][] lu;
    private JButton terminateButton;

    public ResultFrame(double[][][] lu)
    {
        this.lu = lu;
        this.panel = new ResultPanel(lu);
        setSize(300, 100 + 36*(3+(lu[0].length*2)));
        getContentPane().add(panel);
    }

}
