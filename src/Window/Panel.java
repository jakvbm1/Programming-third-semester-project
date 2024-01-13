package Window;

import Matrices.Matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class Panel extends JPanel {

    private double[][] matrix;
    private Matrix matrix_instance;

    private Frame parent;
    public Panel(Frame parent)
    {
        setBackground(new Color(243, 229, 171));
        this.parent = parent;
        matrix_instance = new Matrix();

        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        String message = "Wybierz sposób wprowadzenia macierzy";
        Font font = new Font("Comic sans MS",Font.BOLD, 30);
        JLabel label = new JLabel(message);
        label.setFont(font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);

        Dimension bs = new Dimension(300, 100);

        JButton loadbut = new JButton("Wczytaj z pliku");
        loadbut.addActionListener(new LoadAction());
        loadbut.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadbut.setAlignmentY(Component.CENTER_ALIGNMENT);
        loadbut.setPreferredSize(bs);

        JButton addbut = new JButton("Wprowadź ręcznie");
        addbut.addActionListener(new InsertAction());
        addbut.setAlignmentX(Component.CENTER_ALIGNMENT);
        addbut.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        addbut.setPreferredSize(bs);

        add(loadbut);
        add(addbut);

    }
    private void handle_matrix()
    {
        try
        {
            double[][][] lu_matrices = matrix_instance.create_LU_matrices(matrix);
            matrix_instance.record_LU_to_file(lu_matrices);
            ResultFrame resFrame = new ResultFrame(lu_matrices);
            resFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            resFrame.setVisible(true);
        }
        catch (Exception e)
        {
            ErrorFrame bmframe = new ErrorFrame("Niewłaściwa macierz", "Nie można rozłożyć podanej macierzy");
            bmframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            bmframe.setVisible(true);
        }
    }

    private class LoadAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            parent.dispose();
        JFrame frame = new JFrame("Ścieżka do Macierzy");
        frame.setSize(300, 150);
        JTextField text = new JTextField();
            JButton ok = getjButton(text, frame);
            JPanel panel = new JPanel(new GridLayout(2, 1));
       panel.add(text);
       panel.add(ok);

       frame.getContentPane().add(panel);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);
        }

        private JButton getjButton(JTextField text, JFrame frame) {
            JButton ok = new JButton("OK");


            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String user_input = text.getText();
                    try
                    {
                    matrix = matrix_instance.load_matrix_from_file(user_input);
                    handle_matrix();
                    System.out.println(user_input);
                    frame.dispose();
                    }
                    catch(Exception ex)
                    {
                        frame.dispose();
                        ErrorFrame errorFr = new ErrorFrame("Błąd wczytywania z pliku", "Nie udało się odczytać macierzy z pliku");
                        errorFr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        errorFr.setVisible(true);

                    }
                }
            });
            return ok;
        }

    }

    private class InsertAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            parent.dispose();
            JFrame frame = new JFrame("Podaj wymiar twojej macierzy");
            frame.setSize(300, 150);
            JTextField text = new JTextField();
            JButton ok = getjButton(text, frame);
            JPanel panel = new JPanel(new GridLayout(2, 1));
            panel.add(text);
            panel.add(ok);

            frame.getContentPane().add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }

        private JButton getjButton(JTextField text, JFrame frame) {
            JButton ok = new JButton("OK");


            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String user_input = text.getText();
                    frame.dispose();
                    try {
                        int dimension = Integer.parseInt(user_input);
                        JFrame matrix_frame = new JFrame();
                        matrix_frame.setSize(200 + 10 * dimension, 200 + 10 * dimension);
                        JPanel panel = new JPanel(new GridLayout(dimension + 1, dimension));
                        JTextField[][] matrix_elements = new JTextField[dimension][dimension];
                        JButton contButton = getjButton(dimension, matrix_elements, matrix_frame);

                        for (int i = 0; i < dimension; i++) {
                            for (int j = 0; j < dimension; j++) {
                                matrix_elements[i][j] = new JTextField(5);
                                panel.add(matrix_elements[i][j]);
                            }
                        }
                        panel.add(contButton);
                        matrix_frame.getContentPane().add(panel);
                        matrix_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        matrix_frame.setVisible(true);
                    }

                    catch (Exception ex)
                    {
                        ErrorFrame errorFr = new ErrorFrame("Błąd Argumentu", "Podano niewłaściwy argument");
                        errorFr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        errorFr.setVisible(true);
                    }

                }

                private JButton getjButton(int dimension, JTextField[][] matrix_elements, JFrame frame) {
                    JButton contButton = new JButton("OK");

                    contButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            matrix = new double[dimension][dimension];
                            for(int i = 0; i< dimension; i++)
                            {
                                for (int j = 0; j< dimension; j++)
                                {
                                    String element = matrix_elements[i][j].getText();
                                    if(!element.isEmpty())
                                    {
                                        matrix[i][j] = Double.parseDouble(element);
                                    }

                                    else
                                    {
                                        matrix[i][j] = 0;
                                    }

                                }
                            }
                            handle_matrix();
                            frame.dispose();
                        }
                    });
                    return contButton;
                }
            });
            return ok;
    }

}}
