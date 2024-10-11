public class Main {
    public static void main(String[] args) {
        long[] c = new long[10];
        long n = 3;
        int k = 0;
        while (n <= 21) {
            c[k] = n;
            n += 2; 
            k += 1;
        }
        double[] x = new double[10];
        for (int y = 0; y < 10; y++) {
            x[y] = ((double)(Math.random() * 16) - 2);
        }
        double[][] a = new double[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (c[i] == 5) {
                    double dr = 1./3;
                    double num1 = Math.pow(0.5 * ((x[j] - 1) / 2), 2);
                    double num2 = Math.pow(num1, dr);
                    a[i][j] = num2;
                }
                else if (c[i] >= 9 && c[i] <= 21) {
                    a[i][j] = Math.sin(Math.tan(Math.pow(x[j] / (x[j] + 2), x[j])));
                }
                else {
                    double tem = (x[j]+6)/16;
                    a[i][j] = (2./3)/(Math.pow((2. / 3) / (Math.pow((tem / (1 - tem)), 3)), 3));   
                }
                }
            } 
        for (int i1 = 0; i1 < 10; i1++){
            for(int j1 = 0; j1 < 10; j1++) {   
                double p = a[i1][j1];
                System.out.printf("%.4f",p);
                System.out.print(" ");
                }
                System.out.println();
            }
        }
        }