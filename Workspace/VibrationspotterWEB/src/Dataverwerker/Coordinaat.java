package Dataverwerker;


public class Coordinaat {
	private double x;
	private double y;
	
	public Coordinaat(){
		
	}
	
	public Coordinaat(double x,double y){
		this.x=x;
		this.y=y;
	}
	
	public void print(){
		System.out.println("x: "+x);
		System.out.println("y: "+y);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	
	
	

}
