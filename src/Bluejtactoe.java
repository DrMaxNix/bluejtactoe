import java.util.ArrayList;
import java.util.List;

/*
	All you need to play a nice round of Tic-tac-toe in less than 430 lines!
	
	@author DrMaxNix
	@version 1.0.2
*/
public class Bluejtactoe {
	private boolean gameRunning;
	private char currentPlayer;
	private List<List<Character>> field = new ArrayList<>();
	
	
	
	
	
	///////////////////////////
	// C O N T R U C T O R S //
	///////////////////////////
	
	/*
		Constructor with default values (field-size = 3)
	*/
	public Bluejtactoe(){
		//reset game-start
		gameRunning = false;
		
		//reset current player
		currentPlayer = 'x';
		
		//start with empty field
		field = emptyField(3);
		
		//print field for first time
		updateField();
	}
	/*
		Constructor for custom field-size (I always wanted to play a round of 9x9 tic-tac-toe ╰(*°▽°*)╯ ................)
	*/
	public Bluejtactoe(int NEWFIELDSIZE){
		//reset game-start
		gameRunning = false;
		
		//reset current player
		currentPlayer = 'x';
		
		
		// FIELDSIZE //
		//check if fieldsize is in range
		if(!(NEWFIELDSIZE >= 2 && NEWFIELDSIZE <= 9)){
			System.out.println("Field-size must be between 2 and 9");
			System.exit(0);
		}
		
		//start with empty field
		field = emptyField(NEWFIELDSIZE);
		
		
		// PRINT FIELD FOR FIRST TIME //
		updateField();
	}
	
	
	
	
	
	///////////////
	// I N P U T //
	///////////////
	
	/*
		Handle and process user input
	*/
	public void playerInput(String COORDS){
		//get one coord and check if it is valid
		int coordX = coordStringGet('x', COORDS);
		
		//check if input was valid
		if(coordX >= 0){
			//get other coord
			int coordY = coordStringGet('y', COORDS);
			
			//check if that cell is still empty
			char thatCellCurrentState = getCell(coordX, coordY);
			if(thatCellCurrentState == ' '){
				//save current player
				char thatPlayer = currentPlayer;
				
				//swap current player for next round
				currentPlayer = (currentPlayer == 'o' ? 'x' : 'o');
				
				//set cell
				setCell(coordX, coordY, thatPlayer);
				
			} else {
				System.out.println("Player " + thatCellCurrentState + " has already put their marker into that cell..");
			}
		}
	}
	
	
	
	
	
	///////////////////////////////////
	// F I E L D   &   D I S P L A Y //
	///////////////////////////////////
	
	/*
		Generate and return empty field with FIELDSIZE
	*/
	private static List<List<Character>> emptyField(int FIELDSIZE){
		//generate new field
		List<List<Character>> field = new ArrayList<>();
		
		//fill field with spaces
		for(int x = 0; x < FIELDSIZE; x++){
			//start with empty row
			List<Character> row = new ArrayList<>();
			
			//add this row's columns
			for(int y = 0; y < FIELDSIZE; y++){
				row.add(' ');
			}
			
			//add this row to row list
			field.add(row);
		}
		
		//return filled field
		return field;
	}
	
	/*
		Print new field on screen
	*/
	private void updateField(){
		//get field size
		int fieldSize = field.size();
		
		//clear output
		for(int q = 0; q < 128; q++){
			System.out.println();
		}
		
		
		// PRINT HEADER //
		if(!gameRunning){
			System.out.println("BlueJ-tac-toe v1.0.2 by DrMaxNix (www.drmaxnix.de)");
		}
		
		System.out.print("┌");
		for(int x = 0; x < fieldSize; x++){
			System.out.print((x > 0 ? "┬" : "") + "-" + (char)(x + 65) + "-");
		}
		System.out.println("┐");
		
		
		// PRINT ROWS //
		for(int y = 0; y < fieldSize; y++){
			//maybe print spacer row
			if(y > 0){
				System.out.print("├");
				for(int x = 0; x < fieldSize; x++){
					System.out.print((x > 0 ? "┼" : "") + "---");
				}
				System.out.println("┤");
			}
			
			//print row
			System.out.print(y + 1);
			for(int x = 0; x < fieldSize; x++){
				//get this field's state
				char thatFieldState = field.get(x).get(y);
				
				//print cell
				System.out.print((x > 0 ? "│" : "") + " " + thatFieldState + " ");
			}
			System.out.println(y + 1);
		}
		
		
		// PRINT FOOTER //
		System.out.print("└");
		for(int x = 0; x < fieldSize; x++){
			System.out.print((x > 0 ? "┴" : "") + "-" + (char)(x + 65) + "-");
		}
		System.out.println("┘");
		
		
		// CHECK FOR A WINNER //
		winnerCheck();
		
		
		// PRINT USAGE MESSAGE //
		if(!gameRunning){
			//make game running
			gameRunning = true;
			
			System.out.println("To make a move use the playerInput()-method. E.g. playerInput(\"A3\")");
			System.out.println("It takes one parameter: a string of the char and the number making up the");
			System.out.println("coordinates for your desired cell (order and case doesn't matter).");
			System.out.println();
		}
		
		//print next player
		System.out.println("Player " + currentPlayer + ", please make your move!");
	}
	
	/*
		Set cell on field
	*/
	private void setCell(int X, int Y, char CHAR){
		//set cell
		field.get(X).set(Y, CHAR);
		
		//update displayed field
		updateField();
	}
	/*
		Get cell on field
	*/
	private char getCell(int X, int Y){
		//get cell
		char cell = field.get(X).get(Y);
		
		//return
		return cell;
	}
	
	
	
	
	
	///////////////////////////////////
	// W I N N E R   C H E C K I N G //
	///////////////////////////////////
	
	/*
		Check for and handle a win
	*/
	private void winnerCheck(){
		//get field size
		int fieldSize = field.size();
		
		// CHECK ROWS AND COLUMNS //
		for(int q = 0; q < 2; q++){
			//for each row/column
			for(int w = 0; w < fieldSize; w++){
				//start positive
				boolean isWin = true;
				
				//get first marker (rest of row/column will be compared with that one)
				char compareMarker = field.get(w).get(w);
				
				//check this row/column's markers
				for(int e = 0; e < fieldSize; e++){
					//get this marker
					char thatMarker;
					if(q == 0){
						thatMarker = field.get(w).get(e);
					} else {
						thatMarker = field.get(e).get(w);
					}
					
					//compare
					if(thatMarker != compareMarker || thatMarker == ' '){
						//this marker rules this row/column out
						isWin = false;
						e = fieldSize;
					}
				}
				
				//handle win
				if(isWin){
					//print win message
					System.out.println();
					System.out.println("---------------------------------");
					System.out.println("Player " + compareMarker + " won!");
					System.out.println("---------------------------------");
					
					//exit
					System.exit(0);
				}
			}
		}
		
		
		// CHECK DIAGONALS //
		for(int q = 0; q < 2; q++){ //q=0:/\< q=1:/\>
			//get first marker (rest of diagonal will be compared with that one)
			char compareMarker = field.get(0).get(q * (fieldSize - 1));
			
			//start positive
			boolean isWin = true;
			
			//check this row/column's markers
			for(int e = 0; e < fieldSize; e++){
				//get this marker
				char thatMarker;
				if(q == 0){
					thatMarker = field.get(e).get(e);
				} else {
					thatMarker = field.get(e).get((fieldSize - (e + 1)));
				}
				
				//compare
				if(thatMarker != compareMarker || thatMarker == ' '){
					//this marker rules this diagonal out
					isWin = false;
					e = fieldSize;
				}
			}
			
			//handle win
			if(isWin){
				//print win message
				System.out.println();
				System.out.println("---------------------------------");
				System.out.println("Player " + compareMarker + " won!");
				System.out.println("---------------------------------");
				
				//exit
				System.exit(0);
			}
		}
		
		
		// CHECK FOR A TIE //
		for(int q = 0; q < 2; q++){
			//start positive
			boolean isTie = true;
			
			//check all cells if there's a marker inside
			for(int x = 0; x < fieldSize; x++){
				for(int y = 0; y < fieldSize; y++){
					if(getCell(x, y) == ' '){
						isTie = false;
					}
				}
			}
			
			//handle tie
			if(isTie){
				//print tie message
				System.out.println();
				System.out.println("-------------------------------");
				System.out.println("Tie! Nobody won! (Or did both?)");
				System.out.println("-------------------------------");
				
				//exit
				System.exit(0);
			}
		}
	}
	
	
	
	
	
	
	///////////////////////////////////////
	// C O O R D S   C O N V E R S I O N //
	///////////////////////////////////////
	
	/*
		Get X OR Y coord as int from USER INPUT (string)
	*/
	private int coordStringGet(char XORY, String USERINPUT){
		// CHECK STRING-LENGTH //
		if(USERINPUT.length() != 2){
			System.out.println("Invalid input '" + USERINPUT + "'");
			return -1;
		}
		
		
		// GET INDIVIDUAL CHARS //
		char char0 = USERINPUT.charAt(0);
		char char1 = USERINPUT.charAt(1);
		
		
		// GET COORDS SORTED //
		//get y-coord (number)
		char coordY = ((char0 >= 48 && char0 <= 57) ? char0 : char1);
		
		//get x-coord (char, the one that isn't the number)
		char coordX = ((char0 >= 48 && char0 <= 57) ? char1 : char0);
		
		//maybe convert x-coord (char) to right case
		if((coordX >= 97 && coordX <= 122)){ //if lowercase..
			//make uppercase
			coordX -= 32;
		}
		
		
		// CHECK IF THE CHARS ARE VALID //
		//get field size
		int fieldSize = field.size();
		
		//y-coord (number)
		if(!(coordY > 48 && coordY <= (48 + fieldSize))){
			System.out.println("Invalid input '" + USERINPUT + "'");
			return -1;
		}
		
		//x-coord (char)
		if(!(coordX >= 65 && coordX <= (65 + (fieldSize - 1)))){
			System.out.println("Invalid input '" + USERINPUT + "'");
			return -1;
		}
		
		
		// CONVERT FROM CHAR TO INT //
		coordX -= 65;
		coordY -= 49;
		
		//return
		if(XORY == 'x'){
			return coordX;
		} else {
			return coordY;
		}
	}
}
