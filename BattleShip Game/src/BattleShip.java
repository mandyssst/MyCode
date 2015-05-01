//Group members: Ruiqi Liu. Shitong Song.
import java.util.Scanner;

public class BattleShip{
	public static void main(String[] args){
		String gameOn = "y";
		while(gameOn == "y"){
			//Greetings
			Scanner in = new Scanner(System.in);
			System.out.println("Welcome to the Battleship Game!");
			System.out.println("Do you want to enter Test Mode? Enter 'y' for yes,'n' for no.");
			String testMode = in.nextLine();
			System.out.println("Do you want to enter verbose mode? Enter 'y' for yes, 'n' for no.");
			String verboseMode = in.nextLine();
			
			//Determine the board size
			System.out.println("What board size do you want to play? Please enter two Integers between 3 to 10 (including)");
			BattleShipBoard game = new BattleShipBoard();
			int row =3;
			int col =3;
			boolean pass = false;
			int maxTry = 3;
			while(! pass && maxTry > 0){
				maxTry--;
				row = in.nextInt();
				col = in.nextInt();
				if(col >=3 && row >= 3 && col <=10 && row <=10){
					game = new BattleShipBoard(row,col);
					pass = true;
				}
				else{
					System.out.println("Integers must be between 3 to 10 (including), try again!");
				}
			}
			if(pass == false){
				System.out.println("Maximum Number of Tries Reached! The game will be initialed with a 3*3 game board.");
			}
			
			//Automatically place ships and mines
			int number = game.numOfShips();
			for(int i=0; i < number;i++){
				game.placeShip();
				game.placeMine();
			}
			
			if(testMode.equals("y") || verboseMode.equals("y")){
				game.print();
			}
			System.out.println("The upper left coordinates in the game are (1,1), so enter moves between (1, 1) and" + " (" + row + "," + col +")" );
			
			//Game starts
			while(!game.isTerminated()){
				System.out.println("Please enter two integers to represent the cordinates you hope to hit:");
				pass = false;
				int m = 0;
				int n = 0;
				while(!pass){
					m = in.nextInt();
					n = in.nextInt();
					if(m >= 1 && n >= 1 && m <= row && m <= col)
						pass = true;
					else
						System.out.println("Enter moves between (1, 1) and" + " (" + row + "," + col +")" );
				}
				game.hit(m-1, n-1);
				if(verboseMode.equals("y")){
					game.print();
				}
				System.out.println();
			}
			
			System.out.println("Game Over.Your total score is: " + game.scores);
			System.out.println("Do you want to try again? Enter 'y' for yes,'n' for no.");
			gameOn = in.next();
		}
	}
}