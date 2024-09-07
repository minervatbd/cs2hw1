/* Mina Georgoudiou
 * Dr. Steinberg
 * COP3503 Fall 2024
 * Programming Assignment 1
 */

package games;

import java.util.Random;

// our good ole game class
public class Game 
{

	final static int HEIGHT = 8;
	final static int WIDTH = 8;

	// 8x8 int array representing the board
	public int[][] board = new int[HEIGHT][WIDTH];
	
	// char array for possible player 2 moves
	public char[] moves = {'d', 'r', 'b'};
	
	// reference to random class object for later
	public Random random;
	
	// constructor
	public Game(Random random) 
	{
		this.random = random;
	}

	// returns a random move as a char from the move array
	public char selectPlayerTwoMove() 
	{
		return moves[random.nextInt(0,3)];
	}

	// plays the game function
	public int play() 
	{

		// reset board helper
		resetBoard();

		// important variable for turn order
		int numMoves = 0;
		
		// position trackers
		int coords[] = {0,0};

		// loop runs until game becomes inactive at position 7,7
		while (gameActive(coords)) 
		{
			// default move is invalid, but it wont ever remain like this.
			char currentMove = 'a';

			// player one's turn
			if (numMoves % 2 == 0) 
			{
				// player one move generation: i came up with the formula after graphing out all of the areas that you can potentially get locked into an unwinnable situation.
				// the only places where it wouldnt happen were spots in odd rows/columns that were both greater than one.

				// default to diagonal down
				currentMove = 'd';
				
				// if the row is odd, you need to move right
				if (coords[1] % 2 == 1)
					currentMove = 'r';
				
				// if the row is even, you need to move down
				else if (coords[0] % 2 == 1)
					currentMove = 'b';
			}

			// player two's turn
			else
			{
				// initial select
				currentMove = selectPlayerTwoMove();
				// loop to make sure its actually valid
				while (!moveValid(currentMove, coords))
					currentMove = selectPlayerTwoMove();
			}

			makeMove(currentMove, coords);

			numMoves++;
		}

		// only returns 1 if number of moves is odd, which would mean player 1 won
		return numMoves % 2;
	}

	// helper that sets the board to all 0s except the starting point.
	private void resetBoard()
	{
		// nested for loop to just set everything to 0 except the initial space
		for (int x = 0; x < WIDTH; x++)
		{
			for (int y = 0; y < HEIGHT; y++) 
			{
				if (y == 0 && x == 0)
					board[x][y] = 1;

				else
					board[x][y] = 0;
			}
		}
	}

	// helper function for applying moves, moves should be checked for validity BEFORE this point
	private int[] makeMove(char move, int[] coords)
	{
		// removes old position from board
		board[coords[0]][coords[1]] = 0;

		if (move == 'd' || move == 'r')
			coords[0]++;
	
		if (move == 'd' || move == 'b')
			coords[1]++;

		// sets new position in the board
		board[coords[0]][coords[1]] = 1;

		return coords;
	}

	// helper function for checking if the current position is the endgame position
	private boolean gameActive(int[] coords)
	{
		if (coords[0] == WIDTH - 1 && coords[1] == HEIGHT - 1)
			return false;
		
		return true;
	}

	// helper function for checking the validity of player two's randomly generated moves. pretty self explanatory, if a move exceeds width/height, return false
	private boolean moveValid(char move, int[] coords)
	{
		if (move == 'a')
			return false;
		
		if ((move == 'r' || move == 'd') && coords[0] == WIDTH - 1)
			return false;
		
		else if ((move == 'b' || move == 'd') && coords[1] == HEIGHT - 1)
			return false;

		return true;
	}
}