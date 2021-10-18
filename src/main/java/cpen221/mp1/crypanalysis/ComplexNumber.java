package cpen221.mp1.crypanalysis;

public class ComplexNumber {
    private double imaginary;
    private double real;


    public ComplexNumber(double real, double imaginary) {

        this.real = real;
        this.imaginary = imaginary;

    }

    public ComplexNumber() {

        imaginary = 0;
        real = 0;

    }

    public ComplexNumber(ComplexNumber seed) {
        imaginary = seed.im();
        real = seed.re();
    }

    public void add(ComplexNumber other) {

        imaginary += other.im();
        real += other.re();

    }

    public void multiply(ComplexNumber other) {
        double temp = real;
        real = (real*other.re()) - (imaginary*other.im());
        imaginary = (temp*other.im()) + (imaginary*other.re());
    }

    public String toString() {

        return real + " " + imaginary + "i";
    }

    public double re() {
        // TODO: obtain the real valued part
        return real;
    }

    public double im() {
        // TODO: obtain the imaginary part
        return imaginary;
    }

    @Override
    public boolean equals(Object other) {
        // TODO: implement equality if two ComplexNumbers
        // have the same real and imaginary parts
        if (!(other instanceof ComplexNumber)){
            return false;
        }
        ComplexNumber a = (ComplexNumber) other;
        return (imaginary == a.im())&&(real == a.re());
    }

    @Override
    public int hashCode() {
        return 5; //TODO: Possibly change
    }
}
