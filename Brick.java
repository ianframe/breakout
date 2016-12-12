import java.awt.Color;

public class Brick
{
	private double x;
	private double y;
	private Color color;
	private boolean isBroken;
	private double halfBrickWidth;
	private double halfBrickHeight;

	public Brick(double x, double y, Color c, double halfWidth, double halfHeight)
	{
		this.x = x;
		this.y = y;
		this.color = c;
		this.isBroken = false;
		this.halfBrickWidth = halfWidth;
		this.halfBrickHeight = halfHeight;
		StdDraw.setPenColor(color);
		StdDraw.filledRectangle(x, y, halfBrickWidth, halfBrickHeight);
	}

	public void breakBrick()
	{
		isBroken = true;
	}

	public boolean isBrickBroken()
	{
		return isBroken;
	}

	public void drawBrick()
	{
		if (!isBroken)
		{
			StdDraw.setPenColor(color);
			StdDraw.filledRectangle(x, y, halfBrickWidth, halfBrickHeight);
		}
	}

	public boolean isInside(double objX, double objY)
	{
		if ((objX > x - halfBrickWidth) && (objX < x + halfBrickWidth))
			if ((objY < y + halfBrickHeight) && (objY > y - halfBrickHeight))
				return true;
		return false;
	}

}