package bship.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


import bship.ai.AIOpponent;
import bship.ai.AIShipPlacer;
import bship.logic.BattleShip;
import bship.logic.Carrier;
import bship.logic.CellState;
import bship.logic.Coords;
import bship.logic.Cruiser;
import bship.logic.DefaultMap;
import bship.logic.Destroyer;
import bship.logic.GameMap;
import bship.logic.MultiplayerShipPlacement;
import bship.logic.Ship;
import bship.logic.ShipPlacement;
import bship.logic.Submarine;

public class TestAiShipPlacer
{
	@Test
	public void TestPlaceShipsInMap()
	{
		GameMap map = new DefaultMap(false);
		ShipPlacement shipPlacement = new MultiplayerShipPlacement(map);
		Ship destroyer = new Destroyer();
		Ship battleShip = new BattleShip();
		Ship carrier = new Carrier();
		Ship submarine = new Submarine();
		Ship cruiser = new Cruiser();
		
		ArrayList<Ship> ships = new ArrayList<Ship>();
		ships.add(destroyer);
		ships.add(battleShip);
		ships.add(carrier);
		ships.add(submarine);
		ships.add(cruiser);
		
		AIShipPlacer.placeShipsInMap(shipPlacement);
		
		ArrayList<Coords> aroundCoords = new ArrayList<Coords>();
		for(Ship ship: ships)
		{
			aroundCoords = Coords.getSurroundingCoords(ship.getCoords());
			for(Coords coords: aroundCoords)
			{
				if(map.areCoordsInMapRange(coords))
					if(map.getCellState(coords).hasShip())
						fail();
			}
		}
	}
}
