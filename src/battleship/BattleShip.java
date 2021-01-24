package battleship;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import java.awt.Color;
import javafx.geometry.Insets;
import java.util.Random;
import java.util.*;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author gstev, Brian Rodriguez
 */
public class BattleShip extends Application{
    
    private static final int MAXSHIPS = 14;
    private static final int GRIDSIZE  = 16;
    private GridPane pnlPlayer = new GridPane();
    private GridPane pnlControl = new GridPane();
    private Label[][] lblPlayer = new Label[GRIDSIZE][GRIDSIZE];
    private Label[][] lblRevealed = new Label[GRIDSIZE][GRIDSIZE];
    private Image[] imgShips = new Image[16];
    private Ship[] shipInfo = new Ship[8];
    private ArrayList<Ship> shipList = new ArrayList<Ship>();
    private char[][] ocean = new char[GRIDSIZE][GRIDSIZE];
    private int missTally = 0;
    
    final Label missCount = new Label();
    final Button Show = new Button("SHOW SHIPS");
    final Button Reset = new Button("RESET");
    
    Image imgHit = new Image("file:Images\\batt103.gif");
    Image imgMiss = new Image("file:Images\\batt102.gif");
    Image imgShip = new Image("file:Images\\batt100.gif");
    
    @Override
    public void start(Stage primaryStage) {
                
        BorderPane root = new BorderPane();
                
        Scene scene = new Scene(root, 300, 400);
        
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.show();
        this.initOcean();
        this.createPlayerPanel();
        this.createPlayerControl();
        createShips();
        root.setCenter(pnlPlayer);
	root.setTop(pnlControl);
        placeShips();
    }
    private void createPlayerControl()
    {
	pnlControl.setStyle("-fx-background-color:#EEEEEE;");
        pnlControl.setHgap(3.5);
	Show.setOnMouseClicked((MouseEvent me) -> {
                   revealShips();
               });
	Reset.setOnMouseClicked((MouseEvent me) -> {
                   ResetShips();
               });
       
       pnlControl.add(Reset,1,1);
       pnlControl.add(Show,2,1);
       pnlControl.add(missCount,3,1);
       missCount.setText("Misses: 0");
    }
    private void createPlayerPanel()
    {
       pnlPlayer.setStyle("-fx-background-color:#0000FF;");
       
       for(int row = 0; row < GRIDSIZE; row++)
       {
           for(int col = 0; col < GRIDSIZE; col++)
           {
               final int a = row;
               final int b = col;
               lblPlayer[row][col] = new Label();
               lblPlayer[row][col].setGraphic(new ImageView(imgShip));
               lblPlayer[row][col].setMaxSize(16.0, 16.0);
               lblPlayer[row][col].setStyle("-fx-border-width:1;-fx-border-color:black;");
               lblPlayer[row][col].setOnMouseClicked((MouseEvent me) -> {
                   Update(a,b);
               });
               pnlPlayer.add(lblPlayer[row][col], col, row);
            }
       }
    }
    public void ResetShips(){
        shipList.clear();
                
        this.initOcean();
        missCount.setText("Misses: 0");
        
        createPlayerPanel();
        createShips();
        placeShips();
        missTally = 0;
    }
    private void Update(int row, int col)
    {
        switch (ocean[row][col])
        {
            case 'O': ocean[row][col] = 'X';
                lblPlayer[row][col].setGraphic(new ImageView(imgMiss));
                missTally++;
                missCount.setText("Misses: " + Integer.toString(missTally));
                break;
            case 'F': case 'M': case 'C': case 'B': ocean[row][col] = 'H';
                lblPlayer[row][col].setGraphic(new ImageView(imgHit));
                break;
            default: break;
        }
        checkSunk();
    }
    private void checkSunk(){

	for(Ship si: shipList){
		int hits = 0;
		
		for(int i = 0; i < si.length(); i++){
			if(si.Direction == 'V' && ocean[si.getRow()+i][si.getCol()] == 'H'){
				hits++;
			}
			else if(si.Direction == 'H' && ocean[si.getRow()][si.getCol()+i] == 'H'){
				hits++;
			}
		}
		
		if(hits == si.length()){
                        si.sunk = true;
			if(si.Direction == 'V'){
				for(int i = si.getRow(), j = 0; i < si.getRow() + si.length(); i++, j++)
				{                                                          
					lblPlayer[i][si.getCol()].setGraphic(si.getShipImages()[j]);
				}
			}
			else if(si.Direction == 'H'){
				for(int i = si.getCol(), j = 0; i < si.getCol() + si.length(); i++, j++)
				    {                                                          
					lblPlayer[si.getRow()][i].setGraphic(si.getShipImages()[j]);
				    }
			}
		}
	}
}
    private void createShips()
    {
        this.loadShipImages();
        this.createShipInfo();
    }
    private void loadShipImages()
    {
        for(int i = 0; i < 10 ; i++)
        {
            imgShips[i] = new Image("file:Images\\batt" + (i + 1) + ".gif");
        }
	
	for(int i = 0; i < 6 ; i++)
	{
		imgShips[i+10] = new Image("file:Images\\batt20" + (i + 1) + ".gif");
	}
    }
    private void createShipInfo()
    {
        shipInfo[0] = new Minesweep('H',imgShips);
        shipInfo[1] = new Minesweep('V',imgShips);
        shipInfo[2] = new Frigate('H',imgShips);
        shipInfo[3] = new Frigate('V',imgShips);
        shipInfo[4] = new Cruiser('H',imgShips);
        shipInfo[5] = new Cruiser('V',imgShips);
        shipInfo[6] = new Bship('H',imgShips);
        shipInfo[7] = new Bship('V',imgShips);  
    }
    private void initOcean()
    {
        for(int row = 0; row < 16; row++)
        {
            for(int col = 0; col < 16; col++)
            {
                    ocean[row][col] = 'O';
            }
        }
    }
    private void revealShips()
    {
        for(Ship si: shipList)
        {
            si.sunk = false;
            if(si.Direction == 'V'){
				for(int i = si.getRow(), j = 0; i < si.getRow() + si.length(); i++, j++)
				{                                                          
					lblPlayer[i][si.getCol()].setGraphic(si.getShipImages()[j]);
				}
			}
			else if(si.Direction == 'H'){
				for(int i = si.getCol(), j = 0; i < si.getCol() + si.length(); i++, j++)
				    {                                                          
					lblPlayer[si.getRow()][i].setGraphic(si.getShipImages()[j]);
				    }
			}
        }
    }
    private void placeShips()
    {
       // Create a Random object to select ships
        Random r = new Random();

        // Create random objects to place the ship at a row and a column
        Random randCol = new Random();
        Random randRow = new Random();

        //Place the ships, typically there are 14
        for(int ships = 0; ships < MAXSHIPS; ships++)
        {
                //Get a random ship
                Ship si = new Ship(shipInfo[r.nextInt(8)]);

                int row = randRow.nextInt(16);
                int col = randCol.nextInt(16);
                int direction = checkDirection(si, row, col);
                while(direction == 0) // 0 direction says that we can not place the ship
                {
                        row = randRow.nextInt(16);
                        col = randCol.nextInt(16);
                        direction = checkDirection(si, row, col);
                }
                
                // got a clear path, let put the ship on the ocean
                if(si.Direction == 'H')  // place horizontal
                {
                        si.setRow(row);
                        if(direction == 1)
                        {
                            si.setCol(col);
                            for(int i = col, j = 0; i < col + si.length(); i++, j++)
                            {                                                          
                                String name = si.getName();
                                ocean[row][i] = name.charAt(0);
                            }
                        }
                        else
                        {   
                            si.setCol(col+1);
                            for(int i = col + si.length(), j = 0 ; i > col; i--, j++)
                            {
                                String name = si.getName();
                                ocean[row][i] = name.charAt(0);
                            }
                        }
                }
                else // Must be vertical direction
                {
                        si.setCol(col);
                        if(direction == 1) // place pieces in positive direction
                        {
                            si.setRow(row);
                            for(int i = row, j = 0; i < row + si.length(); i++, j++)
                            {
                                String name = si.getName();
                                ocean[i][col] = name.charAt(0);
                            }
                        }
                        else
                        {
                            si.setRow(row+1);
                            for(int i = row + si.length(), j = 0; i > row; i--, j++)
                            {
                                String name = si.getName();
                                ocean[i][col] = name.charAt(0);
                            }
                        }
                }
                
                shipList.add(si);
        } 
    }
    private int checkDirection(Ship si, int row, int col)
    {
        if(si.Direction == 'H')
            return checkHorizontal(si, row, col);
        else
            return checkVertical(si, row, col);
    }
    int checkHorizontal(Ship si,int row, int col)
    {
            boolean clearPath = true;

            int len = si.length();

            for(int i = col; i < (col + si.length()); i++)
            {
                    if(i >= GRIDSIZE) //This would put us outside the ocean
                    {
                            clearPath = false;
                            break;
                    }
                    if(ocean[row][i] != 'O') // Ship already exists in this spot
                    {
                            clearPath = false;
                            break;
                    }
            }
            if(clearPath == true) // ok to move in the positive direction
                    return 1; 

            //Next Chec the negative direction
            for(int i = col; i > (col - si.length()); i--)
            {
                    if(i < 0) //This would put us outside the ocean
                    {
                            clearPath = false;
                            break;
                    }
                    if(ocean[row][i] != 'O') // Ship already exists in this spot
                    {
                            clearPath = false;
                            break;
                    }			

            }
            if(clearPath == true) //Ok to move in negative direction
                    return -1;
            else
                    return 0;   // No place to move			

    }
	
    int checkVertical(Ship si,int row, int col)
    {
            boolean clearPath = true;
            int len = si.length();

            for(int i = row; i < (row + si.length()); i++)
            {
                if(i >= GRIDSIZE) //This would put us outside the ocean
                {
                        clearPath = false;
                        break;
                }
                if(ocean[i][col] != 'O') // Ship already exists in this spot
                {
                        clearPath = false;
                        break;
                }
            }
            if(clearPath == true) // ok to move in the positive direction
                    return 1; 

            //Next Check the negative direction
            for(int i = row; i > (row - si.length() ); i--)
            {
                if(i < 0) //This would put us outside the ocean
                {
                        clearPath = false;
                        break;
                }
                if(ocean[i][col] != 'O') // Ship already exists in this spot
                {
                        clearPath = false;
                        break;
                }			

            }
            if(clearPath == true) //Ok to move in negative direction
                return -1;
            else
                return 0;   // No place to move			

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
