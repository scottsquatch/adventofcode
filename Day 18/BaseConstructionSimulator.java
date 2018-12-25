import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.Hashtable;
import java.util.Map;
import java.util.HashSet;;

public class BaseConstructionSimulator
{
    private CharMap2D map;
    private long oscillationThreshold;
    private Hashtable<Long, Integer> oscillationTable;

    public BaseConstructionSimulator(String[] lines)
    {
        map = new CharMap2D(lines);
        oscillationThreshold = 1000;
        oscillationTable = new Hashtable<Long, Integer>();
    }

    public void passMinute()
    {
        CharMap2D nextMap = new CharMap2D(map);
        // Create a clone of the map, as we don't want changes to affect the result unitl the next minute
        for (int y = 0; y < map.getMaxY(); y++)
        {
            for (int x = 0; x < map.getMaxX(); x++)
            {
                char c = map.get(x, y);

                char cPrime = SimulationRule.getSimulationRule(c).getNextCharacter(map.getAdj(x, y, 3), c);

                nextMap.set(x, y, cPrime);
            }
        }

        map = nextMap;
    }

    public void passMinute(int numThreads)
    {
        int intervalSize = (int)Math.ceil(map.getMaxX() / (double) numThreads);

        // System.out.println("Interval size: " + intervalSize);

        CharMap2D newMap = new CharMap2D(map);

        // We will split the rows for parallism
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int startx = 0;
        int endx = newMap.getMaxX() - 1;
        for (int i = 0; i < numThreads; i++)
        {
            int starty = i * intervalSize;
            int endy = starty + intervalSize - 1;
            executor.execute(() ->
            {
                BaseConstructionSimulatorThread t = new BaseConstructionSimulatorThread(startx, starty, endx, endy, map, newMap);
                t.start();
                try
                {
                    t.join();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            });
        }

        // now wait for all threads to complete
        try
        {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);

            while (!executor.isTerminated()) { }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        map = newMap;
    }

    public int getResourceScore(long minutes, int numThreads)
    {
        if (minutes < oscillationThreshold)
        {
            // run normal simulation
            for (long i = 0; i < minutes; i++) { passMinute(numThreads); }

            return getResourceScore();
        }
        else 
        {
            // Determine if we need to get the oscillation table
            if (oscillationTable.isEmpty())
            {
                Hashtable<Integer, Long> resoureceScoreTable = new Hashtable<Integer, Long>();
                for (long l = 1; l <= minutes; l++)
                {
                    passMinute(numThreads);
                    // only check every so ofter
                    if (l % oscillationThreshold == 0)
                    {
                        int key = getResourceScore();
                        System.out.println(l + ": " + key);
                        long val = l / oscillationThreshold;
                        if (!resoureceScoreTable.containsKey(key))
                        {
                            resoureceScoreTable.put(key, l / oscillationThreshold);
                        }
                        else
                        {
                            // We found an oscillation, populate osciallationTable
                            for (int score : resoureceScoreTable.keySet())
                            {
                                oscillationTable.put(resoureceScoreTable.get(score) / oscillationThreshold, score);
                            }

                            break;
                        }
                    }
                }
            }
            
            long key = (minutes / oscillationThreshold) % oscillationTable.size();

            return oscillationTable.get(key);
        }
    }

    public int getNum(LumberGridType type)
    {
        return map.getNum(type.getSymbol());
    }

    public int getResourceScore()
    {
        int numTreeSquares = getNum(LumberGridType.TREES);
        int numLumberyards = getNum(LumberGridType.LUMBERYARD);
        
        return numTreeSquares * numLumberyards;
    }

    @Override 
    public String toString()
    {
        return map.toString();
    }
}