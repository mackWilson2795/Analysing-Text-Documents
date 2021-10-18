package cpen221.mp1.crypanalysis;
import java.lang.Math;
public class DFT {
    public static ComplexNumber[] dft(ComplexNumber[] inSignal) {

        ComplexNumber[] transformed = new ComplexNumber[inSignal.length];
        int N = inSignal.length;
        double N1 = (double) inSignal.length;

        for (int j = 0; j < N; j++){

            transformed[j] = new ComplexNumber();

            for (int k = 0; k < N; k++){

                ComplexNumber coeffs = new ComplexNumber((int)Math.cos((Math.PI/N)*2*k*j),(int)-Math.sin((Math.PI/N)*2*k*j));
                ComplexNumber copy = new ComplexNumber(inSignal[k]);

                copy.multiply(coeffs);

                transformed[j].add(copy);

            }

        }

        return transformed;
    }

    public static ComplexNumber[] dft(int[] inSignal) {
        ComplexNumber[] transformed = new ComplexNumber[inSignal.length];
        int N = inSignal.length;

        for (int j = 0; j < N; j++){

            transformed[j] = new ComplexNumber();

            for (int k = 0; k < N; k++){

                ComplexNumber coeffs = new ComplexNumber((int)Math.cos((Math.PI/N)*2*k*j),(int)-Math.sin((Math.PI/N)*2*k*j));
                ComplexNumber copy = new ComplexNumber(inSignal[k], 0);

                copy.multiply(coeffs);

                transformed[j].add(copy);

            }

        }

        return transformed;
    }

}
