package bship.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


import bship.ai.AIOpponent;
import bship.logic.BattleShip;
import bship.logic.Carrier;
import bship.logic.CellState;
import bship.logic.Coords;
import bship.logic.Cruiser;
import bship.logic.Destroyer;
import bship.logic.Ship;
import bship.logic.Submarine;

public class TestAiOpponent
{
	@Test
	public void TestGeneratePlaceShips()
	{
		AIOpponent aiOpponent = new AIOpponent();
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
		
		aiOpponent.PlaceShips(ships);
		
		CellState cells[][] = aiOpponent.getGameMap().getMap();
		
		for(int i = 0; i < cells.length; i++)
			{
				for(int j = 0; j < cells[i].length; j++)
					if(cells[i][j].getShip() == null)
						System.out.print("null  ");
					else
						System.out.print(cells[i][j].getShip().getName() + "  ");
				System.out.println();
			}
		
		
		ArrayList<Coords> aroundCoords = new ArrayList<Coords>();
		for(Ship ship: ships)
		{
			aroundCoords = Coords.getSurroundingCoords(ship.getCoords());
			for(Coords coords: aroundCoords)
			{
				if(aiOpponent.getGameMap().areCoordsInMapRange(coords))
					if(aiOpponent.getGameMap().getCellState(coords).hasShip())
						fail();
			}
		}
	}
}
