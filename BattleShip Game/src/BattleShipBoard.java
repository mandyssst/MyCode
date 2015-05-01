public class BattleShipBoard{
	private int row =3;
	private int col =3;
	private String[][] board = new String[row][col];
	public int scores = 1;
	
	//Constructor. the default game board is 3 X 3.
	public BattleShipBoard(){
		
	}
	
	public BattleShipBoard(int m,int n){
		row = m;
		col = n;
		board = new String[row][col];
	}
	
	//Print Method
	public void print(){
		String str = "|\t";
		for(int i =0; i< row;i++){
			for(int j=0; j< col; j++){
				str += board[i][j] + "\t";
			}
			System.out.println(str + "|");
            str = "|\t";;
		}
	}
	
	//determine whether the game should be terminated.
	public boolean isTerminated(){
		for(int i = 0; i< row;i++){
			for(int j = 0; j< col;j++){
				if("S".equals(board[i][j]))
					return false;
			}
		}	
		return true;
	}		
	
	//Place ships and mines
	public int numOfShips(){
		if(row * col <= 16){
			System.out.println("1 ship and 1 mine are hidden!");
			return 1;
		}
		else if(row * col >16 && row * col <= 36){
			System.out.println("2 ships and 2 mines are hidden!");
			return 2;
		}
		else if(row * col > 36){
			System.out.println("3 ships and 3 mines are hidden!");
			return 3;
		}
		else return 0;
	}
		
	
	public void placeShip(){
		int centerrow = 0;
		int centercol = 0;
		boolean pass = false;
		while(! pass){
			centerrow = (int) Math.floor(Math.random() * row);
			centercol = (int) Math.floor(Math.random() * col);// position center
			if(board[centerrow][centercol]== null){	
				int expand = (int) Math.floor(Math.random() * 2);
				if(expand == 0){// horizontal ship
					int tail = centercol -1;
					int head = centercol +1;
					if(tail >=0 && head < col && board[centerrow][head] == null && board[centerrow][tail] == null){
						board[centerrow][tail] = "S";
						board[centerrow][head] = "S";
						pass = true;
					}
				}
				if(expand == 1){// vertical ship
					int head = centerrow +1;
					int tail = centerrow -1;
					if(tail >=0 && head < row && board[head][centercol] == null && board[tail][centercol] == null){
						board[tail][centercol] = "S";
						board[head][centercol] = "S";
						pass = true;
					}
				}
			}
		}
		board[centerrow][centercol] = "S";
	}
	
	public void placeMine(){
		int minerow = 0;
		int minecol = 0;
		boolean pass = false;
		while(! pass){
			minerow = (int) Math.floor(Math.random() * row);
			minecol = (int) Math.floor(Math.random() * col);
			if(board[minerow][minecol] == null){
				pass = true;}
			}
		board[minerow][minecol] = "M";
	}
	
	//Hit
	public void hit(int m, int n){
		if(board[m][n] == "S"){//hit a ship
			scores ++;
			board[m][n] = "H";
			System.out.println("Congratulation!You hit a ship!");
			System.out.println("Your current sorce: " + scores);
		}
		
		else if(board[m][n] == "M"){//hit a mine
			scores +=2;
			board[m][n] = "D";
			System.out.println("Oops, You hit a mine! Mine exploded!");
			if(m >1 && board[m-1][n] == "S"){
				System.out.println("but you're very Close");
			}
			else if(n>1 && board[m][n-1] == "S"){
				System.out.println("but you're very Close");
			}
			else if(m+1 < row && board[m+1][n] == "S"){
				System.out.println("but you're very Close");
			}
			else if(n+1 < col && board[m][n+1] == "S"){
				System.out.println("but you're very Close");
			}
			else if(m >2 && board[m-2][n] == "S"){
				System.out.println("but you're close");
			}
			else if(n>2 && board[m][n-2] == "S"){
				System.out.println("but you're Close");
			}
			else if(m+2 < row && board[m+2][n] == "S"){
				System.out.println("but you're Close");
			}
			else if(n+2 < col && board[m][n+2] == "S"){
				System.out.println("but you're Close");
			}	
		    System.out.println("Your current sorce: " + scores);
		}
		
		else if(board[m][n] == "H"){//redundant strike
			scores +=2;
			System.out.println("Redundant strike");
			System.out.println("Your current sorce: " + scores);
		}
		
		else if(board[m][n] == "D"){//hit a mine with redundant strike
			scores +=3;
			System.out.println("Oops, You hit a mine that you've hitted!");
			if(m >1 && board[m-1][n] == "S"){
				System.out.println("but you're very Close");
			}
			else if(n>1 && board[m][n-1] == "S"){
				System.out.println("but you're very Close");
			}
			else if(m+1 < row && board[m+1][n] == "S"){
				System.out.println("but you're very Close");
			}
			else if(n+1 < col && board[m][n+1] == "S"){
				System.out.println("but you're very Close");
			}
			else if(m >2 && board[m-2][n] == "S"){
				System.out.println("but you're close");
			}
			else if(n>2 && board[m][n-2] == "S"){
				System.out.println("but you're Close");
			}
			else if(m+2 < row && board[m+2][n] == "S"){
				System.out.println("but you're Close");
			}
			else if(n+2 < col && board[m][n+2] == "S"){
				System.out.println("but you're Close");
			}	
		    System.out.println("Your current sorce: " + scores);
		}
		
		else if(board[m][n] == null){//A miss
			scores ++;
			board[m][n] = "H";
			if(m >1 && board[m-1][n] == "S"){
				System.out.println("A Miss, but Very Close");
			}
			else if(n>1 && board[m][n-1] == "S"){
				System.out.println("A Miss, but Very Close");
			}
			else if(m+1 < row && board[m+1][n] == "S"){
				System.out.println("A Miss, but Very Close");
			}
			else if(n+1 < col && board[m][n+1] == "S"){
				System.out.println("A Miss, but Very Close");
			}
			else if(m >2 && board[m-2][n] == "S"){
				System.out.println("A Miss, but Close");
			}
			else if(n>2 && board[m][n-2] == "S"){
				System.out.println("A Miss, but Close");
			}
			else if(m+2 < row && board[m+2][n] == "S"){
				System.out.println("A Miss, but Close");
			}
			else if(n+2 < col && board[m][n+2] == "S"){
				System.out.println("A Miss, but Close");
			}	
			else System.out.println("A Miss");
			System.out.println("Your current sorce: " + scores);		
			}
		}
	
}