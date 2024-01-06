package Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorPanel extends JPanel {
    private String message;
    private JFrame frame;

    public ErrorPanel(JFrame frame, String message)
    {
        this.frame = frame;
        this.message = message;
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        JLabel label = new JLabel(message);
        label.setFont(new Font("Comic sans MS", Font.PLAIN, 24));
        label.setForeground(Color.DARK_GRAY);
        JButton exit_button = new JButton("OK");
        exit_button.addActionListener(e -> frame.dispose());
        add(label);
        add(exit_button);

    }
}
