/*Linda Wang
March 13, 2021
ComputerPlayer
computer moves at random and learns from mistakes -- final version -- edited and shorted*/
package tictactoe;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class ComputerPlayer {
	public static void main(String args[]) throws Exception {

		
		/*Notes
		 * How the data was populated:
		 * using a while loop and changed some variables to make auto-runs
		 * (ie: player would use index = r.nextInt((9 - 1) + 1) + 1;)
		 */
		
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in), 1);
		Random r = new Random();
	
		// 2D array that stores past combinations that lead to losts for comparison
		String filename = "LostCombos";
		int[][] pastCombo = readFile(filename);

		GameBoard game = new GameBoard();// makes a blank gameboard
		String player1;
		String player2 = "Computer";
		int turn = 0;
		int index;
		boolean errorFlag;
		boolean gameOver = false;
		boolean lost = false;
		boolean forfeit = false;

		// current moves
		int moves[] = new int[9];

		ArrayList<Integer> temp1 = new ArrayList<Integer>();
		ArrayList<Integer> temp2 = new ArrayList<Integer>();
		ArrayList<Integer> temp3 = new ArrayList<Integer>();
		ArrayList<Integer> temp4 = new ArrayList<Integer>();
		ArrayList<Integer> tempSortedNoDuplicate = new ArrayList<Integer>();

		// start of game prep
		System.out.print("Please Enter Your Name: ");
		player1 = keyboard.readLine();		
		System.out.println(player1 + ", you are X's");
		System.out.println(player2 + " is the O's");
		System.out.println("Press ENTER to continue");
		keyboard.readLine();
		System.out.println();
		System.out.println();
		game.drawBoard();
		System.out.println();
		System.out.println();
		while (gameOver == false) {
			errorFlag = false;
			if (turn % 2 == 0) {// if it is player 1's turn
				do {
					System.out.print(player1 + " enter your choice (1-9): ");					
					index = Integer.parseInt(keyboard.readLine());// Assumes a valid number is entered
					errorFlag = game.play("X", index);
					if (errorFlag == false)
						System.out.println("That square is already taken or invalid.  Try again");
				} while (errorFlag == false);// makes sure player enters a square not yet used

				System.out.println();
				System.out.println();
				game.drawBoard();
				System.out.println();
				System.out.println();
				if (game.checkWin("X")) {
					System.out.println(player1 + ", YOU WIN!!");
					gameOver = true;
					lost = true;
				}
			} else {// player 2s turn
				System.out.print(player2 + " enter your choice (1-9): ");
				do {

					// temporary index: still needs to be checked for combinations
					index = r.nextInt((9 - 1) + 1) + 1;

					// first computer turn, 2nd turn in general
					if ((turn == 1) && (pastCombo.length != 0)) {
						// add all computer turn 1
						for (int i = 0; i < pastCombo.length; i++) {
							temp1.add(pastCombo[i][1]);
						}
						temp1.add(moves[0]);

						// change to a no duplicate array and sort it
						tempSortedNoDuplicate = sortNoDuplicate(temp1);

						// if possible, generate an index that haven't been used before to max
						// probability
						// if not possible, take the random index as it is
						if (tempSortedNoDuplicate.size() < 9) {
							index = generateNonUsedIndex(tempSortedNoDuplicate);
						}

						// second computer turn, 4th turn in general
					} else if (turn == 3) {
						// find a definite losing combo: all previous is same, while c3 is 0
						for (int i = 0; i < pastCombo.length; i++) {
							if ((pastCombo[i][0] == moves[0]) && (pastCombo[i][1] == moves[1])
									&& (pastCombo[i][2] == moves[2]) && (pastCombo[i][5] == 0)) {
								temp2.add(pastCombo[i][3]);
							}
						}

						// if there there are still possibilities of repeat
						// avoid a potential losing combination
						if (temp2.size() != 0) {
							// add already owned index in (avoid infinite loop)
							temp2 = addPreviousSteps(temp2, moves, 2);
							// change to a no duplicate array and sort it
							tempSortedNoDuplicate = sortNoDuplicate(temp2);

							// find a random combo that hasn't yet resulted in a definite loss
							index = generateNonUsedIndex(tempSortedNoDuplicate);
						}
						// **it is not possible to forfeit in computer turn 2

						// third computer turn, 6th turn in general
					} else if (turn == 5) {
						// find a definite losing combo:
						// all previous is same, while next computer step is 0
						for (int i = 0; i < pastCombo.length; i++) {
							if ((pastCombo[i][0] == moves[0]) && (pastCombo[i][1] == moves[1])
									&& (pastCombo[i][2] == moves[2]) && (pastCombo[i][3] == moves[3])
									&& (pastCombo[i][4] == moves[4]) && (pastCombo[i][7] == 0)) {
								temp3.add(pastCombo[i][5]);
							}
						}

						// if there is a path that leads to a definite lose, avoid it
						if (temp3.size() != 0) {
							temp3 = addPreviousSteps(temp3, moves, 4);
							tempSortedNoDuplicate = sortNoDuplicate(temp3);

							// if there is a possible path that doesn't lead to defeat, find it
							if (tempSortedNoDuplicate.size() < 9) {

								// find a random combo that hasn't yet resulted in a definite loss
								index = generateNonUsedIndex(tempSortedNoDuplicate);

								// else if all paths lead to lose, forfeit
							} else {
								gameOver = true;
								forfeit = true;
								break;
							}

						}

						// fourth computer turn, 8th turn in general
					} else if (turn == 7) {
						for (int i = 0; i < pastCombo.length; i++) {
							// if previous is all the same, store the combo that lead to lost
							if ((pastCombo[i][0] == moves[0]) && (pastCombo[i][1] == moves[1])
									&& (pastCombo[i][2] == moves[2]) && (pastCombo[i][3] == moves[3])
									&& (pastCombo[i][4] == moves[4]) && (pastCombo[i][5] == moves[5])
									&& (pastCombo[i][6] == moves[6])) {
								temp4.add(pastCombo[i][7]);
							}
						}

						// if size is two:
						// meaning last 2 slots are all loses, forfeit
						if (temp4.size() >= 2) {
							// do not save as lost combo if forfeit(lost combo already exists)
							gameOver = true;
							forfeit = true;
							break;

						} else if (temp4.size() != 0) {
							temp4 = addPreviousSteps(temp4, moves, 6);
							index = generateNonUsedIndex(temp4);
						}
					}

					errorFlag = game.play("O", index);
				} while (errorFlag == false);// makes sure computer does not repeat a step when picking randomly

				// don't display computer choice forfeit
				if (forfeit == true) {
					break;
				}

				// display computer's choice
				System.out.println(index);

				System.out.println();
				System.out.println();
				game.drawBoard();
				System.out.println();
				System.out.println();
				if (game.checkWin("O")) {
					System.out.println(player2 + ", YOU WIN!!");
					gameOver = true;
				}
			}

			storeCurrentIndex(moves, turn, index);

			turn++;
			if (turn == 9 && gameOver == false) {
				gameOver = true;
				System.out.println("DRAW");
			}
		} // end while

		if (lost == true) {
			writeFile(filename, moves);
		}

		if (forfeit == true) {
			System.out.println(player2 + ": I lost.");			
		}		
		
		keyboard.close();

	}// end main

	public static int generateNonUsedIndex(ArrayList<Integer> temp) {
		// find a random combo that hasn't yet resulted in a definite loss
		Random r = new Random();
		int index = 0;
		boolean loop = true;
		boolean contains;

		while (loop == true) {
			index = r.nextInt((9 - 1) + 1) + 1;

			// check if the index generated was taken, if not, stop looping
			contains = temp.contains(index);

			if (contains == false) {
				loop = false;
				break;
			}
		}

		return index;
	}
	
	//stores current index into the "current moves array" named moves
	public static int[] storeCurrentIndex(int moves[], int turn, int index) {
		moves[turn] = index;
		return moves;
	}

	//adds previous moves (all user and computer) to the temp arraylist
	public static ArrayList<Integer> addPreviousSteps(ArrayList<Integer> temp, int moves[], int num) {
		for (int i = 0; i <= num; i++) {
			temp.add(moves[i]);
		}
		return temp;
	}
	
	//make sure the arraylist does not have duplicates, and is sorted
	public static ArrayList<Integer> sortNoDuplicate(ArrayList<Integer> list) {
		// change to a no duplicate array and sort it
		Set<Integer> hashSet = new LinkedHashSet<Integer>(list);
		ArrayList<Integer> tempSortedNoDuplicate = new ArrayList<Integer>(hashSet);
		Collections.sort(tempSortedNoDuplicate);

		return tempSortedNoDuplicate;
	}

	// below all deals with file handling - function is as name suggests
	public static void writeFile(String filename, int[] data) throws IOException {
		// objects to write to a file
		File file = new File(filename);
		// true means append to the end of file instead of covering previous
		FileWriter fw = new FileWriter(file, true);
		PrintWriter write = new PrintWriter(fw);

		// if lost, write current moves into file: separated by space
		for (int i = 0; i < data.length; i++) {
			write.print(data[i] + " ");
		}

		// finish writing to file
		write.println();
		write.close();
	}

	public static int[][] readFile(String filename) throws IOException {
		// value initialized, each element = 0
		int[][] array = new int[getFileLength(filename)][];

		File file = new File(filename);
		Scanner read = new Scanner(file);

		for (int i = 0; i < array.length; i++) {
			// String array is seperated by space
			String[] temp = read.nextLine().split(" ");

			// Create a double 1D array with length of 9 (in this case)
			int[] tempNums = new int[temp.length];

			// cast string array content to integer and store in a 1D array
			for (int j = 0; j < temp.length; j++) {
				tempNums[j] = Integer.parseInt(temp[j]);
			}
			// Assign the row to 2D array
			array[i] = tempNums;
		}

		read.close();

		return array;
	}

	public static int getFileLength(String filename) throws IOException {
		int length = 0;

		// objects to read a file
		File file = new File(filename);
		Scanner read = new Scanner(file);

		while (read.hasNextLine()) {
			// reads the data, but doesn't store it
			read.nextLine();
			length++;
		}

		read.close();

		return length;
	}

}// end class