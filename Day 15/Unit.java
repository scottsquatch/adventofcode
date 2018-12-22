import java.util.TreeMap;

public abstract class Unit
{
	public int hp;
	public int ap;


	public abstract char getSymbol();
	public abstract boolean isEnemy(Unit other);
	public abstract String getTeamName();

	public boolean isDead()
	{
		return hp < 1;
	}

	public Location getMove(Location currentLocation, GameMap map)
	{
		Location to = null;

		ShortestPathFinder pathFinder = new ShortestPathFinder(currentLocation, map);

		// Find shortest path to the nearest enemy
		Location[] path = null;
		TreeMap<Location, Unit> units = map.getUnits();
		for (Location l : units.keySet())
		{
			Unit otherUnit = units.get(l);

			if (isEnemy(otherUnit) 
				&& pathFinder.hasPathTo(l))
			{
				Location[] shortestPathToL = pathFinder.getShortestPath(l);

				if (path == null || shortestPathToL.length < path.length)
				{
					path = shortestPathToL;
				}
			}
		}

		if (path != null 
			&& path.length > 1)
		{
			to = path[1];
		}

		return to;
	}

	public Location getUnitToAttack(Location currentLocation, GameMap map)
	{
		TreeMap<Location, Unit> units = map.getUnits();
		Location attackLoc = null;
		int lowestHitPoints = Integer.MAX_VALUE;
		for (Location loc : currentLocation.getAdjacentLocations())
		{
			Unit other = units.get(loc);

			if (other != null
				&& isEnemy(other)
				&& other.hp < lowestHitPoints)
			{
				lowestHitPoints = other.hp;
				attackLoc = loc;
			}
		}

		return attackLoc;
	}

	public boolean scanfieldForEnemies(GameMap map)
	{
		for (Unit otherUnit : map.getUnits().values())
		{
			if (isEnemy(otherUnit))
			{
				return true;
			}
		}

		// If we get here then we did not find a match
		return false;
	}

	@Override
	public String toString()
	{
		return getSymbol() + " " + hp;
	}
}

class Goblin extends Unit
{
	public Goblin()
	{
		hp = 200;
		ap = 3;
	}

	public char getSymbol()
	{
		return CellType.GOBLIN.getSymbol();
	}

	public boolean isEnemy(Unit other)
	{
		return other.getSymbol() == CellType.ELF.getSymbol();
	}

	@Override
	public String getTeamName()
	{
		return "Goblins";
	}
}

class Elf extends Unit
{
	public Elf()
	{
		hp = 200;
		ap = 3;
	}

	public char getSymbol()
	{
		return CellType.ELF.getSymbol();
	}

	public boolean isEnemy(Unit other)
	{
		return other.getSymbol() == CellType.GOBLIN.getSymbol();
	}

	@Override
	public String getTeamName()
	{
		return "Elves";
	}
}
