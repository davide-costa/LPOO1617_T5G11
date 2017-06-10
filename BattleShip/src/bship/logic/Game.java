package bship.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import bship.gui.CurrGameData;
import bship.gui.GameGui;
import bship.network.data.GameResultData.GameResult;

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
		this.aliveShips = 1;//TODO: Tirar isto
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
	
	public ArrayList<Ship> getOpponentShips()
	{
		return opponentShips;
	}
	
	public GameMap getAllyMap()
	{
		return map;
	}
	
	public void setAllyMap(GameMap allyGameMap)
	{
		this.map = allyGameMap;
		opponentMap.refreshObserver(gui);
		opponentMap.notifyObserver();
	}

	public GameMap getOpponentMap()
	{
		return opponentMap;
	}
	
	public void setOpponentMap(GameMap opponentGameMap)
	{
		this.opponentMap = opponentGameMap;
		opponentMap.refreshObserver(gui);
		opponentMap.notifyObserver();
	}
	
	public Opponent getOpponent() 
	{
		return opponent;
	}
	
	public CellState getAllyCellState(Coords coords)
	{
		return map.getCellState(coords);
	}
	
	public void setAllyCellState(Coords coords, CellState state)
	{
		map.setCellState(coords, state);
	}
	
	public CellState getOpponentCellState(Coords coords)
	{
		return opponentMap.getCellState(coords);
	}
	
	public void setOpponentCellState(Coords coords, CellState state)
	{
		opponentMap.setCellState(coords, state);
	}
	
	public boolean isEndOfGame()
	{
		return aliveShips == 0;
	}
	
	public void getCellStatesOfCoords(boolean isAlly, ArrayList<Coords> coords, ArrayList<CellState> states)
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
	
	//this method is called by GUI to shoot the opponent and informs Opponent of the shot
	public void shootOpponent(Coords coords) throws IOException
	{
		if(!allyTurn)
			return;
		
		if (getOpponentCellState(coords).isDiscovered())
			return;

		opponent.shoot(coords);
	}
	
	//this method is called by Opponent class when the opponent shoots this player and informs the effects to Opponent class. The GUI is notified by observing that the map changed
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
	
	
	public void setCellStatesAsDiscovered(ArrayList<CellState> statesArray, int lowerBound)
	{
		for (int i = lowerBound; i < statesArray.size(); i++)
				statesArray.get(i).setDiscovered(true);
	}
	
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

	public Object getAllyMapImage()
	{
		return gui.getAllyMapImage();
	}

	public void declareDefeat(Object winnerGameMap)
	{
		gui.declareGameDefeat(winnerGameMap);
	}
	
	public void declareVictory()
	{
		gui.declareGameVictory();
	}

	public void opponentQuit() 
	{
		gui.opponentQuit();
	}
}