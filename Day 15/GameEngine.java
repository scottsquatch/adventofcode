import java.util.Map;
import java.util.TreeMap;

public class GameEngine
{
    private GameMap map;
    private boolean gameOver;
    private int lastFullRoudCompleted;

    public GameEngine(GameMap map)
    {
        this.map = map;
        gameOver = false;
        lastFullRoudCompleted = -1;
    }

    public void playGame(boolean printMapAfterRound)
    {
        while (!gameOver)
        {
            lastFullRoudCompleted++;
            playRound();
            if (printMapAfterRound)
            {
                System.out.println("After round " + lastFullRoudCompleted + ":");
                System.out.println(toString());
                printUnits();
            }
        }
    }

    public void playRound()
    {
        // First we get the turn order
        TreeMap<Location, Unit> turnOrder = new TreeMap<Location, Unit>();
        for (Map.Entry<Location, Unit> entry : map.getUnits().entrySet())
        {
            turnOrder.put(entry.getKey(), entry.getValue());
        }

        while(!gameOver && !turnOrder.isEmpty())
        {
            Map.Entry<Location, Unit> entry = turnOrder.pollFirstEntry();

            // It is possible that the unit was killed. Need to check first
            if (map.get(entry.getKey()) != CellType.EMPTY)
            {
                playTurn(entry.getKey(), entry.getValue());
            }
        }
    }

    private void playTurn(Location loc, Unit unit)
    {
        // Determine if there are any enemies
        if (!unit.scanfieldForEnemies(map))
        {
            gameOver = true;
        }
        else
        {
            // First we try to attack
            Location attackLoc = unit.getUnitToAttack(loc, map);
            if (attackLoc != null)
            {
                // Perform attack
                Unit defendingUnit = map.getUnits().get(attackLoc);

                defendingUnit.hp -= unit.ap;

                if (defendingUnit.isDead())
                {
                    map.remove(attackLoc);
                }
            }
            else
            {
                // Try to move
                Location to = unit.getMove(loc, map);

                if (to != null)
                {
                    map.move(loc, to);
                    attackLoc = unit.getUnitToAttack(to, map);

                    if (attackLoc != null)
                    {
                        // Perform attack
                        Unit defendingUnit = map.getUnits().get(attackLoc);

                        defendingUnit.hp -= unit.ap;

                        if (defendingUnit.isDead())
                        {
                            map.remove(attackLoc);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString()
    {
        return map.toString();
    }

    public void printUnits()
    {
        map.printUnitTable();
    }

    public int getSumOfAllUnitsHealth()
    {
        int sum = 0;
        for (Unit u : map.getUnits().values())
        {
            sum += u.hp;
        }

        return sum;
    }

    public int getFullRounds()
    {
        return lastFullRoudCompleted;
    }

    public String getWinningTeamName()
    {
        return map.getUnits().firstEntry().getValue().getTeamName(); 
    }

    public int getNumUnits(char unitSymbol)
    {
        int num = 0;
        for (Unit u : map.getUnits().values())
        {
            if (u.getSymbol() == unitSymbol)
            {
                num++;
            }
        }

        return num;
    }
}