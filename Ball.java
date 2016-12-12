import java.awt.Color;

public class Ball
{
	private int radius;
	private double ballX, ballY, vx, vy;
	private Color ballColor;

	public Ball(double startX, double startY, Color color)
	{
		radius = 6;
		ballX = startX;
		ballY = startY;
		vx = 5.2;
		vy = 6.1;
		ballColor = color;
		updateBall();
	}

	public void updateBall()
	{
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledCircle(ballX, ballY, radius);
	}

	public double getY()
	{return ballY;}

	public double getX()
	{return ballX;}

	public void updateX()
	{ballX += vx;}

	public void updateY()
	{ballY += vy;}

	public void updateX(double newX)
	{ballX = newX;}

	public void updateY(double newY)
	{ballY = newY;}

	public void flipVX()
	{vx *= -1;}

	public void flipVY()
	{vy *= -1;}

}