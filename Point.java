package othello;


public class Point {
	private int x;
	private int y;
	//-1: null; 1: black; 0: white
	private int color = -1;

	public static final int DIAMETER=30;//radius of the point
	
	
	public Point() { }
	public Point(int xIn, int yIn, int colorIn) {
		this.color = colorIn;
		this.x = xIn;
		this.y = yIn;

	}
	
	public void setX(int xIn) {
		this.x = xIn;
	}
	
	public void setY(int yIn) {
		this.y = yIn;
	}
	public void setColor(int colorIn) {
		this.color = colorIn;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getColor() {
		return this.color;
	}
}
