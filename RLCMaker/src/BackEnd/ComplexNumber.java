package BackEnd;

public class ComplexNumber {
	private double real;
	private double imaginary;
	public double getReal() {
		return real;
	}
	public void setReal(double real) {
		this.real = real;
	}
	public double getImaginary() {
		return imaginary;
	}
	public void setImaginary(double imaginary) {
		this.imaginary = imaginary;
	}
	
	public ComplexNumber(double a) {
		this.real = a;
		this.imaginary = 0;
	}
	public ComplexNumber(double a, double b) {
		this(a);
		this.imaginary = b;
	}
	public ComplexNumber() {
		this(0,0);
	}
	
	
	public ComplexNumber add(ComplexNumber a) {
		this.real += a.real;
		this.imaginary += a.imaginary;
		return this;
	}
	
	public ComplexNumber sub(ComplexNumber a) {
		this.real -= a.real;
		this.imaginary -= a.imaginary;
		return this;
	}
	
	public ComplexNumber mul(ComplexNumber a) {
		ComplexNumber b = new ComplexNumber(0);
		b.real = (this.real*a.real) - (this.imaginary*a.imaginary);
		b.imaginary = (this.real*a.imaginary) + (this.imaginary*a.real);
		return b;
	}
	
	public ComplexNumber div(ComplexNumber a) {
		ComplexNumber b = new ComplexNumber(0);
		b.real = (this.real*a.real +this.imaginary*a.imaginary)/(a.real*a.real + a.imaginary*a.imaginary);
		b.imaginary = (this.imaginary*a.real - this.real*a.imaginary)/(a.real*a.real + a.imaginary*a.imaginary);
		return b;
	}
	
	public double module() {
		return Math.sqrt(this.real*this.real + this.imaginary*this.imaginary);
	}
	

	public double trigonometricAngle() {
		return Math.atan(imaginary/real);
	}
	
	public boolean equals(double x) {
		if (this.imaginary ==0 && this.real==x ) return true;
		else return false;
	}
	
	public ComplexNumber mul(double x) {
		ComplexNumber a = new ComplexNumber(x);
		ComplexNumber b = new ComplexNumber(0);
		b = this.mul(a);
		return b;
	}
	
	public void setValue(double x) {
		this.real = x;
		this.imaginary = 0;
	}
	
	public void setValue(ComplexNumber a) {
		this.real = a.real;
		this.imaginary = a.imaginary;
	}
}

