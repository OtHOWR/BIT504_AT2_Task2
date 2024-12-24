import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

 //GameMain contains the main method and handles the game's logic, user interactions and UI updates.

public class GameMain extends JPanel implements MouseListener{
	// Constants for row and column game dimesions, and title
	public static final int ROWS = 3;     
	public static final int COLS = 3;  
	public static final String TITLE = "Tic Tac Toe";

	//constants for dimensions used for drawing
	public static final int CELL_SIZE = 100;
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
	public static final int CELL_PADDING = CELL_SIZE / 6;    
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;    
	public static final int SYMBOL_STROKE_WIDTH = 8;
	
	// Declare game object variables
	private Board board;
	private GameState currentState; 
	private Player currentPlayer; 
	private JLabel statusBar;       
	

	// Initialise the game board and set up UI components
	public GameMain() {   
		// Add required event listener to 'this'       
 		addMouseListener(this); //line I added

		// Setup the status bar (JLabel) to display status message       
		statusBar = new JLabel("         ");       
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));       
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));       
		statusBar.setOpaque(true);       
		statusBar.setBackground(Color.LIGHT_GRAY);  
		
		// Layout of the panel is in border layout
		setLayout(new BorderLayout());       
		add(statusBar, BorderLayout.SOUTH);
		// Account for statusBar height in overall height
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));
		
		// Create a new instance of the Board class
		board = new Board();
		
		// Call the method to initialise the game board
		initGame();
	}
	
	public static void main(String[] args) {
		    // Run GUI code in Event Dispatch thread for thread safety.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
				//create a main window to contain the panel
				JFrame frame = new JFrame(TITLE);
				
				//Create the new GameMain panel and add it to the frame
						
				GameMain gameMain = new GameMain();
				frame.add(gameMain);
				
				// Set the default close operation of the frame to exit_on_close
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // default close operation set
				
				frame.pack();             
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
	         }
		 });
	}
	/** Custom painting codes on this JPanel */
	public void paintComponent(Graphics g) {
		// Fill background and set color to white
		super.paintComponent(g);
		setBackground(Color.WHITE);
		// Ask the game board to paint itself
		board.paint(g);
		
		// Set status bar message
		if (currentState == GameState.PLAYING) {          
			statusBar.setForeground(Color.BLACK);          
			if (currentPlayer == Player.CROSS) {   
				// GUI BRANCH
				//TODO: use the status bar to display the message "X"'s Turn  GUI

				
			} else {    
				 // GUI BRANCH
				//TODO: use the status bar to display the message "O"'s Turn

				
			}       
			} else if (currentState == GameState.DRAW) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("It's a Draw! Click to play again.");       
			} else if (currentState == GameState.CROSS_WON) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("'X' Won! Click to play again.");       
			} else if (currentState == GameState.NOUGHT_WON) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("'O' Won! Click to play again.");       
			}
		}
	
	  /** Initialise the game-board contents and the current status of GameState and Player) */
		public void initGame() {
			for (int row = 0; row < ROWS; ++row) {          
				for (int col = 0; col < COLS; ++col) {  
					// All cells empty
					board.cells[row][col].content = Player.EMPTY;           
				}
			}
			 currentState = GameState.PLAYING;
			 currentPlayer = Player.CROSS;
		}
		
		/**After each turn check to see if the current player hasWon by putting their symbol in that position, 
		 * If they have the GameState is set to won for that player
		 * If no winner then isDraw is called to see if deadlock, if not GameState stays as PLAYING
		 * Updates the game state after a player's move
		 */
		public void updateGame(Player thePlayer, int row, int col) {
			// Check for win after play
			if(board.hasWon(thePlayer, row, col)) {
				// Check which player has won and update the currentstate to the appropriate gamestate for the winner
				switch(thePlayer){
					case CROSS: {
						currentState = GameState.CROSS_WON;
						break;
					}
					case NOUGHT: {
						currentState = GameState.NOUGHT_WON;
						break;
					}
				}
			
				
			} else if (board.isDraw ()) {	
				// Set the currentstate to the draw gamestate
				currentState = GameState.DRAW;
			}
			// Otherwise no change to current state of playing
		}
		
		/** Event handler for the mouse click on the JPanel. If selected cell is valid and Empty then current player is added to cell content.
		 *  UpdateGame is called which will call the methods to check for winner or Draw. if none then GameState remains playing.
		 *  If win or Draw then call is made to method that resets the game board.  Finally a call is made to refresh the canvas so that new symbol appears*/
	
	public void mouseClicked(MouseEvent e) {  
	    // Get the coordinates of where the click event happened            
		int mouseX = e.getX();             
		int mouseY = e.getY();             
		// Get the row and column clicked             
		int rowSelected = mouseY / CELL_SIZE;             
		int colSelected = mouseX / CELL_SIZE;               			
		if (currentState == GameState.PLAYING) {                
			if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS && board.cells[rowSelected][colSelected].content == Player.EMPTY) {
				// Move  
				board.cells[rowSelected][colSelected].content = currentPlayer; 
				// Update currentState                  
				updateGame(currentPlayer, rowSelected, colSelected); 
				// Switch player
				if (currentPlayer == Player.CROSS) {
					currentPlayer =  Player.NOUGHT;
				}
				else {
					currentPlayer = Player.CROSS;
				}
			}             
		} else {        
			// game over and restart              
			initGame();            
		}   
		// GUI BRANCH
		//TODO: redraw the graphics on the UI          
           
	}
	@Override
	public void mousePressed(MouseEvent e) {
		//  Auto-generated, event not used
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//  Auto-generated, event not used
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// Auto-generated,event not used
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// Auto-generated, event not used
	}
}
