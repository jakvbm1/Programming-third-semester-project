package Matrices;
import java.io.*;
import java.util.Arrays;

public class Matrix {


    public double[][][] create_LU_matrices(double[][] matrix) {

            int level = matrix.length;

            if (matrix[0][0] != 0) {
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
                        if(l[i][i] != 0)
                        {
                            u[i][j] = (1 / l[i][i]) * (matrix[i][j] - sum);
                        }

                        else
                        {
                            throw new ArithmeticException("Nie można rozłożyć macierzy");
                        }

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
                throw new ArithmeticException("Nie można rozłożyć macierzy");
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
                if(line_array.length != matrix.length)
                {
                    throw new RuntimeException("Podana macierz nie jest kwadratowa!");
                }
                for(int i=0; i < matrix.length; i++)
                {
                    matrix[j][i] = Double.parseDouble(line_array[i]);
                }
                j++;
                line = fr.readLine();
            }
            fr.close();
            if(j == matrix.length)
            {
                return matrix;
            }

            else
            {
                throw new RuntimeException("Podana macierz nie jest kwadratowa");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
