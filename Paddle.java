import java.awt.Color;

public class Paddle
{
	private double x;
	private double y;
	private Color color;
	private double paddleWidth;
	private double paddleHeight;

	public Paddle(double x, double y, Color c, double halfWidth, double halfHeight)
	{
		this.x = x;
		this.y = y;
		this.color = c;
		this.paddleWidth = 2*halfWidth;
		this.paddleHeight = 2*halfHeight;
		StdDraw.setPenColor(color);
		StdDraw.filledRectangle(x, y, halfWidth, halfHeight);
	}

	public double getX()
	{return x;}

	public void changeX(double newX)
	{
		x = newX;
		StdDraw.setPenColor(color);
		StdDraw.filledRectangle(x, y, paddleWidth/2, paddleHeight/2);
	}
}