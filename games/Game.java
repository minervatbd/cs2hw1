/* Mina Georgoudiou
 * Dr. Steinberg
 * COP3503 Fall 2024
 * Programming Assignment 1
 */

package games;

import java.util.Random;

// our good ole game class
public class Game {

	final static int HEIGHT = 8;
	final static int WIDTH = 8;

	// 8x8 int array representing the board
	public int[][] board = new int[HEIGHT][WIDTH];
	
	// char array for possible player 2 moves
	public char[] moves = {'d', 'r', 'b'};
	
	// reference to random class object for later
	public Random random;
	
	// constructor
	public Game(Random random) {
		this.random = random;
	}

	// returns a random move as a char from the move array
	public char selectPlayerTwoMove() {
		return moves[random.nextInt(0,3)];
	}

	public int play() {

		// reset board helper
		resetBoard();

		// important variable for turn order
		int numMoves = 0;
		
		// position trackers
		int coords[] = {0,0};

		while (gameActive(coords)) {

			char currentMove = 'a';

			// player one's turn
			if (numMoves % 2 == 0) {
				
				// need to generate player one's move first

				// default to diagonal down
				currentMove = 'd';
				
				if (coords[1] % 2 == 1)
					currentMove = 'r';
				
				else if (coords[0] % 2 == 1)
					currentMove = 'b';
			}

			// player two's turn
			else {
				currentMove = selectPlayerTwoMove();
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
	private void resetBoard() {

		for (int x = 0; x < WIDTH; x++) {

			for (int y = 0; y < HEIGHT; y++) {

				if (y == 0 && x == 0)
					board[x][y] = 1;

				else
					board[x][y] = 0;
			}
		}
	}

	// helper function for applying moves, moves should be checked for validity BEFORE this point
	private int[] makeMove(char move, int[] coords) {
		board[coords[0]][coords[1]] = 0;

		if (move == 'd' || move == 'r')
			coords[0]++;
	
		if (move == 'd' || move == 'b')
			coords[1]++;

		board[coords[0]][coords[1]] = 1;

		return coords;
	}

	private boolean gameActive(int[] coords) {

		if (coords[0] == WIDTH - 1 && coords[1] == HEIGHT - 1)
			return false;
		
		return true;
	}

	private boolean moveValid(char move, int[] coords) {

		if (move == 'a')
			return false;
		
		if ((move == 'r' || move == 'd') && coords[0] == WIDTH - 1)
			return false;
		
		else if ((move == 'b' || move == 'd') && coords[1] == HEIGHT - 1)
			return false;

		return true;
	}

	private boolean compare(int[] coords, int target) {

		int comp = coords[0]*10 + coords[1];

		if (comp == target)
			return true;
		
		return false;
	}

}