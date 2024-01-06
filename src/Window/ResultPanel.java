package Window;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class ResultPanel extends JPanel {
    private double[][][] lu;

    public ResultPanel(double[][][] lu)
    {
        this.lu = lu;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font font = new Font("Comic sans MS",Font.BOLD, 30);
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(font);
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds("Macierz L", context);
        double x = (getWidth() - bounds.getWidth()) /2;

        g2.drawString("Macierz L", (int)x, 30);
        int a = 1;
        double x2 = x;
        for(int i = 0; i<lu[0].length; i++)
        {
            StringBuilder line = new StringBuilder();
            for(int j=0; j<lu[0].length; j++)
            {
                line.append(lu[0][i][j]).append(" ");
            }
            if (i == 0)
            {
                Rectangle2D bounds2 = font.getStringBounds(line.toString(), context);
                x2 = (getWidth() - bounds2.getWidth()) /2;
            }
            g2.drawString(line.toString(), (int)x2, 30+(30*a));
            a++;
        }
        a++;
        g2.drawString("Macierz U", (int)x, 30+(30*a));
        a++;
        for(int i = 0; i<lu[1].length; i++)
        {
            StringBuilder line = new StringBuilder();
            for(int j=0; j<lu[1].length; j++)
            {
                line.append(lu[1][i][j]).append(" ");
            }
            g2.drawString(line.toString(), (int)x2, 30+(30*a));
            a++;
        }
    }
}
