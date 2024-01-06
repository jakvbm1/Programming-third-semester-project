package Window;

import javax.swing.*;
import java.awt.*;

public class ErrorFrame extends JFrame {
    private ErrorPanel panel;
    private static final int height = 200, width = 400;

    private String error_name;
    private String error_message;
    public ErrorFrame(String error_name, String error_message)
    {
        panel = new ErrorPanel(this, error_message);
        this.error_name = error_name;
        setTitle(error_name);
        setSize(width, height);
        Container cont = getContentPane();
        cont.add(panel);
    }
}
