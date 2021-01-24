package battleship;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author gstev, Brian Rodriguez
 */
public class Ship {
    protected String shipName;
    protected int[] shipPieces;
    protected int[] sunkPieces;
    protected Image[] shipImages;
    protected Label[] lblShip;
    protected boolean sunk;
    protected int row;
    protected int col;
    char Direction;

    Ship(String name, int[] pieces,char Direction,Image[] images) {
	this.Direction = Direction;
	shipName = name;
	shipPieces = pieces;
        shipImages = images;
        lblShip = new Label[pieces.length];
        sunk = false;
    }
    Ship(Ship other) {
	this.Direction = other.Direction;
	this.shipName = other.shipName;
	this.shipPieces = other.shipPieces;
	this.sunkPieces = other.sunkPieces;
	this.shipImages = other.shipImages;
	this.lblShip = other.lblShip;
	this.sunk = other.sunk;
    }
	public String getName() {
	    return this.shipName;
	}
	public void setShipPieces(int[] pieces) {
	    shipPieces = pieces;
	}
	public int[] getShipPieces() {
	    return shipPieces;
	}
	public int getDirection() {
	    return this.Direction;
	}
	public int length() {
	    return shipPieces.length;
	}
	public void setRow(int row){
	    this.row = row;
	}
	public void setCol(int col){
	    this.col = col;
	}
	public int getRow(){
	    return row;
	}
	public int getCol(){
	    return col;
	}
	public Label[] getShipImages() {
	    for(int i = 0; i < lblShip.length; i++) {
	        lblShip[i] = new Label();
	        if(!sunk) {
	            lblShip[i].setGraphic(new ImageView(shipImages[shipPieces[i]]));
	        } else lblShip[i].setGraphic(new ImageView(shipImages[sunkPieces[i]+10]));
	    }
	    return lblShip;
	}
}

class Frigate extends Ship {
	
	Frigate(char Direction,Image[] images) {
		super("Frigate",new int[]{0,4},Direction,images);
		sunkPieces = new int[]{0,2};
                if(Direction == 'V') {
                    this.shipPieces = new int[]{5,9};
		    sunkPieces = new int[]{3,5};
                    for(int i = 0; i < lblShip.length; i++) {
                        lblShip[i] = new Label();
                        lblShip[i].setGraphic(new ImageView(shipImages[shipPieces[i]]));
                    }
                }
            }
        }

class Minesweep extends Ship {
        
	Minesweep(char Direction,Image[] images) {
		super("Minesweep",new int[]{0,1,4},Direction,images);
		sunkPieces = new int[]{0,1,2};
                if(Direction == 'V') {
                    this.shipPieces = new int[]{5,6,9};
		    sunkPieces = new int[]{3,4,5};
                    for(int i = 0; i < lblShip.length; i++) {
                        lblShip[i] = new Label();
                        lblShip[i].setGraphic(new ImageView(shipImages[shipPieces[i]])); 
                    }
                }
            }
        }

class Cruiser extends Ship {
        
	Cruiser(char Direction,Image[] images) {
		super("Cruiser",new int[]{0,1,2,4},Direction,images);
		sunkPieces = new int[]{0,1,1,2};
                if(Direction == 'V') {
                    this.shipPieces = new int[]{5,6,7,9};
		    sunkPieces = new int[]{3,4,4,5};
                    for(int i = 0; i < lblShip.length; i++) {
                        lblShip[i] = new Label();
                        lblShip[i].setGraphic(new ImageView(shipImages[shipPieces[i]]));
                    }
                }
            }
        }

class Bship extends Ship {
        
	Bship(char Direction,Image[] images) {
		super("Battleship",new int[]{0,1,2,3,4},Direction,images);
		sunkPieces = new int[]{0,1,1,1,2};
                if(Direction == 'V'){
                    this.shipPieces = new int[]{5,6,7,8,9};
		    sunkPieces = new int[]{3,4,4,4,5};
                    for(int i = 0; i < lblShip.length; i++) {
                        lblShip[i] = new Label();
                        lblShip[i].setGraphic(new ImageView(shipImages[shipPieces[i]]));
                    }
                }
            }
        }
