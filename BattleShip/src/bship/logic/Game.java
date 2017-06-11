package bship.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import bship.gui.CurrGameData;
import bship.gui.GameGui;
import bship.network.data.GameResultData.GameResult;

/**
 * Implements all the logic of a BattleShip game. Is used by the Gui and the AIOpponent classes.
*/
public class Game
{
	protected boolean allyTurn;
	protected GameMap map;
	protected GameMap opponentMap;
	private Opponent opponent;
	private int aliveShips;
	private ArrayList<Ship> opponentShips;
	private GameGui gui;
	final HashMap<String, GameResult> shipNameToGameResult = new HashMap<String, GameResult>();
	final HashMap<GameResult, Ship> gameResultToShip = new HashMap<GameResult, Ship>();	
	final int numShips = 6;
	
	/**  
	 * Constructor of game class for the Gui interface. It is also used for testing but is called from the subclass on the Tests package.
     * @param allyMap The map of the ally player to start with. It is a GameMap class type.
	 * @param opponent An intermediate class responsible for handling all the communication with the opponent player.
	 * @param gui The Graphical User Interface operating this Game class. It commands the game class based on input from user. The Game class also informs the Gui when the opponent of the changes in game that are not under this gui's control, i. e., when the opponent informs the results of a play or when he shoots this player.
	 */ 
	public Game(GameMap allyMap, Opponent opponent, GameGui gui)
	{
		this.aliveShips = numShips;
		this.allyTurn = CurrGameData.allyHasInitTurn;
		this.map = allyMap;
		opponentShips = new ArrayList<Ship>();
		this.opponentMap = new DefaultMap(true);
		this.opponent = opponent;
		this.gui = gui;
		FillShipNameToResultMap();
		InitializeOpponentGameMap();
	}
	
	private void FillShipNameToResultMap() 
	{
		shipNameToGameResult.put("Carrier", GameResult.SINK_CARRIER);
		shipNameToGameResult.put("BattleShip", GameResult.SINK_BATTLESHIP);
		shipNameToGameResult.put("Cruiser", GameResult.SINK_CRUISER);
		shipNameToGameResult.put("Submarine", GameResult.SINK_SUBMARINE);
		shipNameToGameResult.put("Destroyer", GameResult.SINK_DESTROYER);
	}
	
	private void InitializeOpponentGameMap()
	{
		opponentMap.fill(new OpponentCellState(null, false));
	}
	
	/**  
	 * Getter for the OpponentShips ArrayList that contains all the Ships of the enemy opponent as they are revealed (when fully destroyed).
     * @return Returns the ArrayList of enemy ships.
	 */ 
	public ArrayList<Ship> getOpponentShips()
	{
		return opponentShips;
	}
	
	/**  
	 * Getter for the ally game map.
     * @return The current ally game map.
	 */ 
	public GameMap getAllyMap()
	{
		return map;
	}
	
	/**  
	 * Setter for the ally game map.
     * @param allyGameMap The new GameMap class to set the ally map equal to.
	 */ 
	public void setAllyMap(GameMap allyGameMap)
	{
		this.map = allyGameMap;
		opponentMap.refreshObserver(gui);
		opponentMap.notifyObserver();
	}

	/**  
	 * Getter for the opponent game map.
     * @return The current opponent game map.
	 */ 
	public GameMap getOpponentMap()
	{
		return opponentMap;
	}
	
	/**  
	 * Setter for the opponent game map. Also refreshes the GameMap's observer to the current Gui (of this game) to ensure it monitors all the changes in the map to be able to draw accordingly.
     * @param opponentGameMap The new Opponent class to set the current opponent of this game equal to.
	 */ 
	public void setOpponentMap(GameMap opponentGameMap)
	{
		this.opponentMap = opponentGameMap;
		if(gui != null)
		{
			opponentMap.refreshObserver(gui);
			opponentMap.notifyObserver();
		}
	}
	
	/**  
	 * Getter for the Opponent of this Game class. Opponent is an intermediate class responsible for handling all the communication with the opponent player.
     * @return The current opponent of this Game class.
	 */ 
	public Opponent getOpponent() 
	{
		return opponent;
	}
	
	/**  
	 * Returns the cell state (class CellState) respecting to some coords in the ally game map.
	 * @param coords The coords of the cell to retrieve.
     * @return The current cell state (class CellState) respecting to the coords received as parameter.
	 */ 
	public CellState getAllyCellState(Coords coords)
	{
		return map.getCellState(coords);
	}
	
	/**  
	 * Sets the cell state (class CellState) respecting to some coords in the ally game map.
	 * @param coords The coords of the cell to be set.
	 * @param state The new state to set on the given coords.
	 */ 
	public void setAllyCellState(Coords coords, CellState state)
	{
		map.setCellState(coords, state);
	}
	
	/**  
	 * Returns the cell state (class CellState) respecting to some coords in the opponent game map.
	 * @param coords The coords of the cell to retrieve.
     * @return The current cell state (class CellState) respecting to the coords received as parameter.
	 */ 
	public CellState getOpponentCellState(Coords coords)
	{
		return opponentMap.getCellState(coords);
	}
	
	/**  
	 * Sets the cell state (class CellState) respecting to some coords in the opponent game map.
	 * @param coords The coords of the cell to be set.
	 * @param state The state of the cell to be set.
	 */ 
	public void setOpponentCellState(Coords coords, CellState state)
	{
		opponentMap.setCellState(coords, state);
	}
	
	/**  
	 * Tells if the game has ended.
     * @return A boolean value indicating wether the game has ended or not.
	 */ 
	public boolean isEndOfGame()
	{
		return aliveShips == 0;
	}
	
	private void getCellStatesOfCoords(boolean isAlly, ArrayList<Coords> coords, ArrayList<CellState> states)
	{
		states.clear();
		
		if(isAlly)
		{
			for (Coords coord : coords)
			{
				states.add(getAllyCellState(coord));
			}
		}
		else
		{
			for (Coords coord : coords)
			{
				states.add(getOpponentCellState(coord));
			}
		}
	
	}
	
	/**  
	 * Returns and ArrayList of Coords that represent the coords around a ship. If the ship has size 2, it will have 10 surrounding coords.
     * @param ship The ship of which to get the surrounding coords.
     * @return An ArrayList containing the surrounding coords of a Ship.
	 */ 
	public ArrayList<Coords> getSurroundingCoordsOfShip(Ship ship) 
	{
		ArrayList<Coords> shipCoords = ship.getCoords();
		ArrayList<Coords> surroundingCoords = Coords.getSurroundingCoords(shipCoords);
	
		for(int i = 0; i < surroundingCoords.size(); i++)
		{
			if(!map.areCoordsInMapRange(surroundingCoords.get(i)))
			{
				surroundingCoords.remove(i);
				i--;
			}		
		}
		
		return surroundingCoords;
	}
	
	/**  
	 * This method is called by GUI or the AI Opponent to shoot the opponent and informs Opponent class of the shot. It is called when the user clicks on the opponent map to shoot it or when the AI Opponent class makes a shot. Receives the coords (in board coords notation) respecting to the cell where the ally shot. It performs validation so that it doesn't inform the opponent if the cell is already discovered.
     * @param coords The coords where the ally shot.
     * @throws IOException thrown the info cannot be send.
	 */
	public void shootOpponent(Coords coords) throws IOException
	{
		if(!allyTurn)
			return;
		
		if (getOpponentCellState(coords).isDiscovered())
			return;

		opponent.shoot(coords);
	}
	
	/**  
	 * This method is called by Opponent class to shoot the ally. Receives the coords (in board coords notation) respecting to the cell where the ally shot. The GUI is notified of the shot through Observer. It is observing the GameMap and is notified when it changes.
     * @param shootCoords The coords where the opponent shot. (and where to shoot the ally)
	 */ 
	public void shootAlly(Coords shootCoords)
	{
		if(allyTurn)
			return;
		
		CellState state = getAllyCellState(shootCoords);
		if (state.isDiscovered())
			return;
		
		//Set cell as discovered
		state.setDiscovered(true);
		
		Ship ship = state.getShip();
		if (ship == null)
			return;
		ship.shoot();
		
		if(!ship.isDestroyed())
			return;
		aliveShips--;
		
		updateCellsOfSankShip(ship, true, ship.getSize() - 1);	
	}

	private void updateCellsOfSankShip(Ship ship, boolean isAlly, int lowerBound) 
	{
		ArrayList<Coords> coordsArray = new ArrayList<Coords>();
		coordsArray.addAll(ship.getCoords());
		coordsArray.addAll(getSurroundingCoordsOfShip(ship));
		ArrayList<CellState> statesArray = new ArrayList<CellState>();
		getCellStatesOfCoords(isAlly, coordsArray, statesArray);
		setCellStatesAsDiscovered(statesArray, lowerBound);
	}
	
	/**  
	 * This method informs the results of the last shot in the ally map, i. e., if the shot landed on water, ship (and eventually revealed it). It should be called after calling shootAlly method. The result is returned on a GameResult Data type.
     * @param shootCoords The coords where the opponent shot and of which to retrieve the result.
	 * @return The result of the last play in the form of a GameResult data type. The possible values are WATER, HIT, SINK_CARRIER, SINK_BATTLESHIP, SINK_DESTROYER, SINK_SUBMARINE or SINK_CRUISER. The symbolic values are self-explanatory.
	 */ 
	public GameResult getPlayEffects(Coords shootCoords)
	{
		CellState cell = getAllyCellState(shootCoords);
		if (!cell.hasShip())
		{
			allyTurn = true;
			return GameResult.WATER;
		}
	
		allyTurn = false;
		Ship ship = cell.getShip();
		if(ship.isDestroyed())
			return shipNameToGameResult.get(ship.getName());
		else
			return GameResult.HIT;
	}
	
	
	private void setCellStatesAsDiscovered(ArrayList<CellState> statesArray, int lowerBound)
	{
		for (int i = lowerBound; i < statesArray.size(); i++)
				statesArray.get(i).setDiscovered(true);
	}
	
	/**  
	 * This method is called when the opponent informs the results of the last shot. It should be called by the Opponent class when the ally makes a shot (by calling shootOpponent method of this Game class) and when the Game class of the opponent has computed the results of the shot and sent them to this Opponent class. Which in turn will inform this Game class of the results of the shot. It performs the necessary updates to the opponent map. The gui (if applicable) if informed by Observer. It is observing the GameMap class.
     * @param lastShootCoords The coords where the ally shot and of which to handle the result received from the opponent.
	 * @param result The result (computed by the opponent) of the last shot by the ally.
	 */ 
	public void handleResultData(Coords lastShootCoords, GameResult result)
	{
		CellState cell;
		
		if(result == GameResult.WATER)
		{
			cell = new OpponentCellState(null, false, true); 
			allyTurn = false;
		}
		else if(result == GameResult.HIT)
		{
			cell = new OpponentCellState(null, true, true);
			allyTurn = true;
		} 
		else
		{
			cell = handleOpponentSankShip(lastShootCoords, result);
			allyTurn = true;
		} 
			
		setOpponentCellState(lastShootCoords, cell);
	}

	/**  
	 * This method is an auxiliar method to the handleResultData method. And is called when the last shot of the ally has resulted in the full destruction of an enemy ship. It should reveal the entire ship and the surround cells. It is also called by the unit tests package, for unit testing purposes (testing only this method instead of the whole handleResultData logic at once).
     * @param lastShootCoords The coords where the ally shot and of which to handle the result received from the opponent.
	 * @param result The result (computed by the opponent) of the last shot by the ally.
	 * @return the new cell containing the destroyed ship.
	 */ 
	public CellState handleOpponentSankShip(Coords lastShootCoords, GameResult result) 
	{
		Ship destroyedShip;
		switch(result)
		{
		case SINK_CARRIER:
			destroyedShip = new Carrier();
			break;
		case SINK_BATTLESHIP:
			destroyedShip = new BattleShip();
			break;
		case SINK_CRUISER:
			destroyedShip = new Cruiser();
			break;
		case SINK_DESTROYER:
			destroyedShip = new Destroyer();
			break;
		case SINK_SUBMARINE:
			destroyedShip = new Submarine();
			break;
		default:
			destroyedShip = null;
			break;
		}
		
		CellState cell = new OpponentCellState(destroyedShip, true, true);
		destroyedShip.Destroy();
		
		destroyedShip.addCoord(lastShootCoords);
		discoverCoordsOfSankShip(destroyedShip);
		Collections.sort(destroyedShip.getCoords());

		updateCellsOfSankShip(destroyedShip, false, 0);
		opponentShips.add(destroyedShip);
		setShipOfAllCells(destroyedShip);
		
		return cell;
	}

	private void setShipOfAllCells(Ship ship)
	{
		for (Coords coord : ship.getCoords())
		{
			getOpponentCellState(coord).setShip(ship);
		}
	}

	/**  
	 * Tries to find the sank ship coords. It tries all 4 possible directions, to find out which one is correct, ad find out the given ship coords.
     * @param ship the ship that it's trying to discover the coords
     */
	private void discoverCoordsOfSankShip(Ship ship)
	{
		Coords coords = ship.getCoords().get(0);
		
		//Go try catch to get the direction of the ship
		//Try left coord
		tryOpponentShipHeadingDirection(ship, coords, "horizontal", -1, 0);
		//Try right coord
		tryOpponentShipHeadingDirection(ship, coords, "horizontal", 1, 0);
		
		//If the ship has been marked as horizontal, it is not needed to search on the y axis
		if (ship.getDirection() == "horizontal")
			return;
		
		//Try bottom coord
		tryOpponentShipHeadingDirection(ship, coords, "vertical", 0, -1);
		//Try top coord
		tryOpponentShipHeadingDirection(ship, coords, "vertical", 0, 1);
	}
	/**  
	 * Tries to find the ship coords given a certain direction and some init coords. The method will increment x and y by xInc and yInc respectively, and verifie if that is a valid coord, that is if is not of board range and if it has the ship we are looking for.
     * @param ship the ship that it's trying to discover the coords
     * @param initCoords the coords where start the discover.
     * @param direction the direction of the ship, vertical or horizontal.
     * @param xInc the increment in X at each iteration of the discover.
     * @param yInc the increment in Y at each iteration of the discover.
     */
	private void tryOpponentShipHeadingDirection(Ship ship, Coords initCoords, String direction, int xInc, int yInc)
	{
		Coords coords = new Coords(initCoords); 
		
		coords.incrementX(xInc);
		coords.incrementY(yInc);
		if(!opponentMap.areCoordsInMapRange(coords))
			return;
		
		while (getOpponentCellState(coords).hasShip())
		{
			ship.setDirection(direction);
			ship.addCoord(new Coords(coords));
			coords.incrementX(xInc);
			coords.incrementY(yInc);
			if(!opponentMap.areCoordsInMapRange(coords))
				return;
		}
	}
	/**  
	 * Calls gui and return an image of the ally map, currently displayed in the screen.
     * @return the ally map image as an Object
     */
	public Object getAllyMapImage()
	{
		return gui.getAllyMapImage();
	}
	/**  
	 * Responsable for informing the gui that the game has over and the ally loses.
	 * @param winnerGameMap the gameMap of the ally player, that was defeated.
     */
	public void declareDefeat(Object winnerGameMap)
	{
		gui.declareGameDefeat(winnerGameMap);
	}
	/**  
	 * Responsable for informing the gui that the game has over and the ally wins.
     */
	public void declareVictory()
	{
		gui.declareGameVictory();
	}
	/**  
	 * Responsable for informing the gui that the opponent quit the game.
     */ 
	public void opponentQuit() 
	{
		gui.opponentQuit();
	}
}