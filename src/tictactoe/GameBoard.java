/*Linda Wang
March 4
GameBoard
Class that puts piece on tile, draws, and checks wins*/
package tictactoe;

/**
 * Class for Tic Tac Toe gameboard
 * 
 * @author Linda
 */
public class GameBoard {
	/**
	 * An array of 9 GameTiles representing the TicTacToe gameboard
	 */
	GameTile[] board;

	/**
	 * Constructs an empty gameboard of 9 GameTiles and fills it with unowned tiles
	 */
	public GameBoard() {
		board = new GameTile[9];
		for (int i = 0; i < 9; i++)
			board[i] = new GameTile();
	}

	/**
	 * This will allow a player to claim a GameTile on the TicTacToe board
	 * 
	 * @return A boolean true if the player successfully played on a tile, false if
	 *         that tile is already owned or the index is out of bounds
	 * @param player A String indicating which player is to own the tile ("X" or
	 *               "O")
	 * @param tile   An integer representing the tile the player wishes to claim
	 */
	public boolean play(String player, int tile) {
		//index is tile-1
		//display error if tile index is out of bounds or if it's owned
		if ((tile > 9) || (tile < 1) || (board[tile-1].owned() == true)) {
			return false;
		} else {
			board[tile-1].setOwner(player);
			return true;
		}

	}

	/**
	 * This will check to see if there are three tiles in a row belonging to a
	 * player
	 * 
	 * @return A boolean true if the player has three tiles in a row, false
	 *         otherwise
	 * @param player A String indicating which player to check for a win ("X" or
	 *               "O")
	 */
	public boolean checkWin(String player) {
		byte count = 1;
		int i = 0;
		int j = 0;
		int p = 0;

		// check rows
		while (count <= 3) {
			// check horizontal rows
			if ((player.equals(board[i].getOwner()) && player.equals(board[i + 1].getOwner())
					&& player.equals(board[i + 2].getOwner()))) {
				return true;
			}
			// check vertical rows
			if ((player.equals(board[j].getOwner()) && player.equals(board[j + 3].getOwner())
					&& player.equals(board[j + 6].getOwner()))) {
				return true;
			}
			i += 3;
			j++;
			count++;
		}

		// check diagonal rows
		if ((player.equals(board[p].getOwner()) && player.equals(board[p + 4].getOwner())
				&& player.equals(board[p + 8].getOwner()))) {
			return true;
		}
		p = 2;
		if ((player.equals(board[p].getOwner()) && player.equals(board[p + 2].getOwner())
				&& player.equals(board[p + 4].getOwner()))) {
			return true;
		}

		// if none of above matches, X did not win yet
		return false;

	}

	/**
	 * This will draw the current gameboard on the screen
	 */
	public void drawBoard() {
		int j = 0;
		for (int i = 1; i <= 3; i++) {
			System.out.println("--------------------");
			System.out.println("|  " + board[j].getOwner() + "  |  " + board[j+1].getOwner() + "  |  " + board[j+2].getOwner() + "  |");
			System.out.println("--------------------");
			j+=3;
		}

	}
}// end class