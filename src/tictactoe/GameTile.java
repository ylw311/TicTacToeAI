/*Linda Wang
March 4
GameTile
Class that allow to set, check, and get owner*/
package tictactoe;
/**Class for Tic-Tac-Toe tile
	*@author Linda
	*/


public class GameTile{
	/**Stores a String representing the owner "X" or "O" of a GameTile
	*/
	protected String owner;
	/**Constructs a GameTile, sets owner to null by default
		*/	
	public GameTile (){
		owner = " ";
	}
	/**This will return who owns this particular GameTile
		*@return String "X" if player 1 owns tile, "O" if player 2 owns tile, empty string if unowned
	
		*/
	public String getOwner(){
		if (owner.equals("X")) {
			return "X";
		} else if (owner.equals("O")) {
			return "O";
		} else {
			return " ";
		}
	}
	/**This will assign a new owner to the game tile
		*@param player A String indicating which player will own the tile ("X" or "O")
		*/
	public void setOwner(String player){
		if (player.equals("X")) {
			owner = "X";
		} else if (player.equals("O")) {
			owner = "O";
		}
	}
	/**This will determine whether any player owns a particular tile
		*@return boolean true if a player owns the tile, false otherwise
		*/
	public boolean owned(){
		if (owner.equals("X") || owner.equals("O")){
			return true;
		} else {
			return false;
		}

	}
}
	