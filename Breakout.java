/*
 * File: Breakout.java
 * -------------------
 * This file will eventually implement the game of Breakout.
 */
import java.awt.Color;

public class Breakout{
	////////////////////////////////////////////////////////
	/////////////////////	CONSTANTS	////////////////////

	 /** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	 /** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Brick colors */
	private static final Color[] BRICK_COLORS = {StdDraw.RED, StdDraw.GREEN, StdDraw.ORANGE, StdDraw.CYAN, StdDraw.MAGENTA};

	/** 2D array of Bricks */
	private static Brick[][] bricks = new Brick[10][10];

	 /** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	 /** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	 /** Separation between bricks */
	private static final int BRICK_SEP = 4;

	 /** Width of a brick */
	private static final int BRICK_WIDTH =
	 (WIDTH - (NBRICKS_PER_ROW+1) * BRICK_SEP) / NBRICKS_PER_ROW;

	 /** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	 /** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Ball to be used in the game */
	private static Ball ball;
	private static final Color BALL_COLOR = StdDraw.BLACK;

	/** Paddle to be used in the game. */
	private static Paddle paddle;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	 /** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;
	private static final Color PADDLE_COLOR = StdDraw.DARK_GRAY;

	 /** Number of turns */
	private static final int NTURNS = 3;
	private static int roundNumber = 0;
	
	////////////////////////////////////////////////////////

	public static void run() 
	{
		setup();
		play();
	}

	public static void setup()
	{
		//Establish the application size and adjust the axes scales
		StdDraw.setCanvasSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		StdDraw.setXscale(0, APPLICATION_WIDTH);
		StdDraw.setYscale(0, APPLICATION_HEIGHT);
		StdDraw.setFont();
		StdDraw.enableDoubleBuffering();
		//Draw the paddle
		paddle = new Paddle(APPLICATION_WIDTH/2, PADDLE_Y_OFFSET + PADDLE_HEIGHT/2, PADDLE_COLOR, PADDLE_WIDTH/2, PADDLE_HEIGHT/2);
		//Draw the ball
		ball = new Ball(APPLICATION_WIDTH/2, APPLICATION_HEIGHT/2, BALL_COLOR);
		//Draw the bricks
		drawOriginalBricks();
		StdDraw.show();
	}

	public static void play()
	{
		while (!isLevelBeaten() && roundNumber < NTURNS)
		{
			StdDraw.clear();
			resetBallAndPaddle();
			drawAllBricks();
			StdDraw.show();
			waitForStart();
			while(isBallInPlay())
			{
				StdDraw.clear();
				drawAllBricks();
				drawPaddle();
				drawBall();
				checkForBallBrickCollision();
				StdDraw.show();
				StdDraw.pause(30);
			}
			roundNumber++;
		}
		StdDraw.clear();
		if (isLevelBeaten())
		{
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(APPLICATION_WIDTH/2, APPLICATION_HEIGHT/2, "Congratulations! You're a winner!");
		}
		else
		{
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(APPLICATION_WIDTH/2, APPLICATION_HEIGHT/2, "Game over!");
		}
		StdDraw.show(); 	
	}

	public static void drawPaddle()
	{
		paddle.changeX(StdDraw.mouseX());
	}

	public static boolean isBallInPlay()
	{
		return ball.getY() >= PADDLE_Y_OFFSET;
	}
	public static void drawBall()
	{
		//check to see if there is a wall collision
		if (ball.getX() >= APPLICATION_WIDTH || ball.getX() < 0)
			ball.flipVX();
		if (ball.getY() >= APPLICATION_HEIGHT || ball.getY() < 0)
			ball.flipVY();
		//check to see if there is a paddle collision
		if (Math.abs(ball.getY() - (PADDLE_Y_OFFSET + PADDLE_HEIGHT/2)) < 5 && (ball.getX() < paddle.getX()+PADDLE_WIDTH/2 && ball.getX() > paddle.getX()-PADDLE_WIDTH/2))
			ball.flipVY();
		ball.updateX();
		ball.updateY();
		ball.updateBall();
	}

	public static void resetBallAndPaddle()
	{
		ball = new Ball(APPLICATION_WIDTH/2, APPLICATION_HEIGHT/2, BALL_COLOR);
		paddle = new Paddle(APPLICATION_WIDTH/2, PADDLE_Y_OFFSET + PADDLE_HEIGHT/2, PADDLE_COLOR, PADDLE_WIDTH/2, PADDLE_HEIGHT/2);
	}

	public static void waitForStart()
	{
		while (!mouseClicked())
		{}
	}

	/** To be used at the beginning of the game in setup */
	public static void drawOriginalBricks()
	{
		int colorIndex = -1;
		for (int r = 0; r < bricks.length; r++)
		{
			if (r % 2 == 0)
				colorIndex++;
			for (int c = 0; c < bricks[r].length; c++)
			{
				bricks[r][c] = new Brick(c*(BRICK_WIDTH) + (c+1)*BRICK_SEP + BRICK_WIDTH/2, HEIGHT - (BRICK_Y_OFFSET + BRICK_HEIGHT/2) - (r*BRICK_SEP) - (r*BRICK_HEIGHT), BRICK_COLORS[colorIndex], BRICK_WIDTH/2, BRICK_HEIGHT/2);
			}
		}
	}

	public static void drawAllBricks()
	{
		for (int r = 0; r < bricks.length; r++)
		{
			for (int c = 0; c < bricks[r].length; c++)
			{
				bricks[r][c].drawBrick();
			}
		}
	}

	public static boolean isLevelBeaten()
	{
		for (int r = 0; r < bricks.length; r++)
		{
			for (int c = 0; c < bricks[r].length; c++)
			{
				if(!bricks[r][c].isBrickBroken())
					return false;
			}
		} 
		return true;
	}

	public static void checkForBallBrickCollision()
	{
		double ballX = ball.getX();
		double ballY = ball.getY();
		for (int r = 0; r < bricks.length; r++)
		{
			for (int c = 0; c < bricks[r].length; c++)
			{
				if (!bricks[r][c].isBrickBroken() && bricks[r][c].isInside(ballX, ballY))
				{
					bricks[r][c].breakBrick();
					ball.flipVY();
				}
			}
		}
	}

	public static boolean mouseClicked()
	{
		if (StdDraw.mousePressed())
		{
			while (StdDraw.mousePressed())
			{}
			return true;
		}
		return false;
	}

	public static void main(String[] args)
	{
		run();
	}
}