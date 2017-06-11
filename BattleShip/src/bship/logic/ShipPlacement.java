package bship.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


public abstract class ShipPlacement 
{
	private GameMap map;
	private HashMap<String, Ship> shipsByName = new HashMap<String, Ship>();
	private HashMap<String, Boolean> shipIsAlreadyPlaced = new HashMap<String, Boolean>();
	
	/**  
	 * Constructor of ShipPlacement class given the game map.
     * @param map The map where the ships will be placed.
	 */ 
	public ShipPlacement(GameMap map)
	{
		this.map = map;
		fillShipsHashMaps();
	}

	/**  
	 * Getter of the map where the ships are being placed.
     * @return map The map where the ships are being placed.
	 */ 
	public GameMap getMap()
	{
		return map;
	}
	
	/**  
	 * Getter of the ship, given the shipName.
     * @return ship The ship with that name.
	 */ 
	private Ship getShipByName(String shipName)	{
		return shipsByName.get(shipName);
	}
	
	public String getShipDirection(String shipName)
	{
		return getShipByName(shipName).getDirection();
	}

	public Coords getShipInitCoords(String shipName)
	{
		return getShipByName(shipName).getInitCoords();
	}

	private void fillShipsHashMaps() 
	{
		ArrayList<String> shipsNames  = new ArrayList<String>();
		shipsNames.add("BattleShip");
		shipsNames.add("Carrier");
		shipsNames.add("Cruiser1");
		shipsNames.add("Cruiser2");
		shipsNames.add("Destroyer");
		shipsNames.add("Submarine");
		
		shipsByName.put(shipsNames.get(0), new BattleShip());
		shipsByName.put(shipsNames.get(1), new Carrier());
		shipsByName.put(shipsNames.get(2), new Cruiser());
		shipsByName.put(shipsNames.get(3), new Cruiser());
		shipsByName.put(shipsNames.get(4), new Destroyer());
		shipsByName.put(shipsNames.get(5),new Submarine());
		
		for (String shipName : shipsNames)
		{
			shipIsAlreadyPlaced.put(shipName, false);
		}		
	}
	
	/**  
	 * Checks if a ship is already placed.
	 * @param shipName The name of the ship to check.
     * @return true if ship already placed. False otherwise.
	 */ 
	public boolean isShipsAlreadyPlaced(String shipName)
	{
		return shipIsAlreadyPlaced.get(shipName);
	}

	/**  
	 * Checks if a ship drop is valid. Verifies if all coords of the ship and the surrounding coords of were it's trying to place the ship are empty. 
	 * @param ship The ship that's trying to place.
     * @return true if valid. False otherwise.
	 */ 
	public boolean isShipDropValid(Ship ship)
	{
		ArrayList<Coords> shipCoords = ship.getCoords();
		if(!map.areListOfCoordsInMapRange(shipCoords))
			return false;
		
		ArrayList<Coords> coordsArray = new ArrayList<Coords>();
		coordsArray = Coords.getSurroundingCoords(shipCoords);
		coordsArray.addAll(shipCoords);
		for(Coords currCoords: coordsArray)
		{
			if(!map.areCoordsInMapRange(currCoords))
				continue;
			
			if(map.getCellState(currCoords) != null)
				if(map.getCellState(currCoords).hasShip())
					return false;
		}
		
		return true;
	}
	
	/**  
	 * Pick up the ship with that name.
	 * @param shipName The ship name that's trying to pick.
	 */ 
	public void pickUpShip(String shipName)
	{
		if (shipIsAlreadyPlaced.get(shipName))
		{
			Ship ship = shipsByName.get(shipName);
			for(Coords currCoords: ship.getCoords())
			{
				map.setCellState(currCoords, new AllyCellState(null));
			}
			ship.clearCoords();
			shipIsAlreadyPlaced.put(shipName, false);
		}
	}
	
	/**  
	 * Pick up the given ship.
	 * @param ship The ship that's trying to pick.
	 */ 
	public void pickUpShip(Ship ship)
	{
		String shipName = getShipName(ship);
		pickUpShip(shipName);
	}
	
	/**  
	 * Tries to drop ship. Verifies if all coords of the ship and the surrounding coords of were it's trying to place the ship are empty. 
	 * @param initCoord The init coords of the ship that it's trying to drop.
	 * @param shipName The name of the ship that it's trying to drop.
	 * @param direction The direction of the ship that it's trying to drop.
     * @return true if dropped. False otherwise.
	 */ 
	public boolean dropShip(Coords initCoord, String shipName, String direction)
	{
		Ship ship = shipsByName.get(shipName);
		ship.setDirection(direction);
		ship.fillCoordsByInitCoord(initCoord);
		
		return dropShip(ship);
	}
	
	/**  
	 * Tries to drop ship. Verifies if all coords of the ship and the surrounding coords of were it's trying to place the ship are empty. 
	 * @param ship The ship that it's trying to drop.
     * @return true if dropped. False otherwise.
	 */ 
	public boolean dropShip(Ship ship)
	{
		String shipName = getShipName(ship);
		if (shipName == null)
			throw new IllegalArgumentException();
		
		if(!isShipDropValid(ship))
		{
			ship.clearCoords();
			return false;
		}
		
		ArrayList<Coords> shipCoords = ship.getCoords();
		for(Coords currCoords: shipCoords)
		{
			map.setCellState(currCoords, new AllyCellState(ship));
		}
		Collections.sort(ship.getCoords());
		
		shipIsAlreadyPlaced.put(shipName, true);
		return true;
	}
	
	public String getShipName(Ship ship)
	{
		String shipName = null;
		
		Iterator<Entry<String, Ship>> it = shipsByName.entrySet().iterator();
		
		while (it.hasNext())
		{
			Entry<String, Ship> pair = it.next();
			if (pair.getValue() == ship)
				shipName = (String) pair.getKey();
		}
		
		return shipName;
	}
	
	public boolean verifyAllShipsArePlaced() 
	{
		Iterator<Entry<String, Boolean>> it = shipIsAlreadyPlaced.entrySet().iterator();
		
		while (it.hasNext())
		{
			Entry<String, Boolean> pair = it.next();
			if (pair.getValue().equals(false))
				return false;
		}

		return true;
	}
	
	public ArrayList<Ship> getPlacedShips()
	{
		ArrayList<Ship> ships = new ArrayList<Ship>();
		Iterator<Entry<String, Boolean>> it = shipIsAlreadyPlaced.entrySet().iterator();
		
		while (it.hasNext())
		{
			Entry<String, Boolean> pair = it.next();
			if((Boolean)pair.getValue() == true)
				ships.add(shipsByName.get(pair.getKey()));
		}
		
		return ships;
	}
	
	public ArrayList<Ship> getShipsToPlace()
	{
		ArrayList<Ship> ships = new ArrayList<Ship>();
		Iterator<Entry<String, Ship>> it = shipsByName.entrySet().iterator();
		
		while (it.hasNext())
		{
			Entry<String, Ship> pair = it.next();
			ships.add((Ship) pair.getValue());
		}
		
		return ships;
	}
}
