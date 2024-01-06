package Matrices;
import java.io.*;
import java.util.Arrays;

public class Matrix {
    public double calculate_det(double[][] matrix)
    {
        int level = matrix.length;

        if (level < 2)
        {
            throw new ArithmeticException();
        }

        else if (level == 2)
        {
            return (matrix[0][0] * matrix[1][1]) - (matrix[0][1]*matrix[1][0]);
        }

        else
        {
            double result = 0;
            for(int i=0; i<level; i++)
            {
                double[][] leveled_down_matrix = new double[level-1][level-1];
                int k = 0;
                for(int j=0; j<level; j++)
                {
                    if(i != j)
                    {
                        for(int l=1; l<level; l++)
                        {
                            leveled_down_matrix[k][l-1] = matrix[j][l];
                        }
                        k++;
                    }

                }
                result += Math.pow(-1, i) * matrix[i][0]*calculate_det(leveled_down_matrix);
            }
            return result;
        }
    }

    public double[][][] create_LU_matrices(double[][] matrix) {
        if (calculate_det(matrix) != 0) {
            boolean non_zero_diagonal = true;
            int level = matrix.length;
            for (int i = 0; i < level; i++) {
                if (matrix[i][i] == 0) {
                    non_zero_diagonal = false;
                    break;
                } else {
                    continue;
                }
            }

            if (non_zero_diagonal) {
                double[][] l = new double[level][level];
                double[][] u = new double[level][level];

                for (int i = 0; i < level; i++) {
                    for (int j = 0; j < level; j++) {
                        if (i > j) {
                            u[i][j] = 0;
                        } else if (i == j) {
                            u[i][j] = 1;
                            l[i][j] = matrix[i][j];
                        }
                    }
                    l[i][0] = matrix[i][0];
                }

                for (int i = 1; i < level; i++) {
                    u[0][i] = matrix[0][i] / l[0][0];
                }

                for (int j = 1; j < level; j++) {
                    for (int i = 1; i < j; i++) {
                        double sum = 0;
                        for (int k = 0; k < i; k++) {
                            sum += l[i][k] * u[k][j];
                        }

                        u[i][j] = (1 / l[i][i]) * (matrix[i][j] - sum);
                    }

                    for (int i = j; i < level; i++) {
                        double sum = 0;
                        for (int k = 0; k < i; k++) {
                            sum += l[i][k] * u[k][j];
                        }
                        l[i][j] = matrix[i][j] - sum;
                    }
                }

                double[][][] lu = new double[2][level][level];

                for (int i = 0; i < level; i++) {
                    for (int j = 0; j < level; j++) {
                        lu[0][i][j] = l[i][j];
                        lu[1][i][j] = u[i][j];
                    }
                }
                return lu;
            }

            else
            {
                throw new ArithmeticException("Nie można rozłożyć macierzy mającej 0 na przekątnej");
            }
        }

        else
        {
            throw new ArithmeticException("Nie można rozłożyć macierzy osobliwej!");
        }
    }

    public void record_LU_to_file(double[][][] lu)
    {
        try {
            FileWriter fw = new FileWriter("Powstale_macierze.txt");

            fw.write("Macierz L:" + '\n');
            for(int i=0; i<lu[0].length; i++)
            {
                StringBuilder line = new StringBuilder("|");
                for(int j=0; j<lu[0][i].length; j++)
                {
                    line.append(lu[0][i][j]).append(" ");
                }
                line.append("|").append('\n');
                fw.write(line.toString());
            }

            fw.write("Macierz U:" + '\n');
            for(int i=0; i<lu[1].length; i++)
            {
                StringBuilder line = new StringBuilder("|");
                for(int j=0; j<lu[1][i].length; j++)
                {
                    line.append(lu[1][i][j]).append(" ");
                }
                line.append("|").append('\n');
                fw.write(line.toString());
            }

            fw.close();
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double[][] load_matrix_from_file(String file_directory)
    {
        try {
            BufferedReader fr = new BufferedReader(new FileReader(file_directory));
            String line = fr.readLine();
            String[] check_length = line.split(" ");
            double[][] matrix = new double[check_length.length][check_length.length];
            int j = 0;
            while (line != null)
            {
                System.out.println(line);
                String[] line_array = line.split(" ");

                for(int i=0; i < matrix.length; i++)
                {
                    matrix[j][i] = Double.parseDouble(line_array[i]);
                }
                j++;
                line = fr.readLine();
            }
            fr.close();
            return matrix;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
