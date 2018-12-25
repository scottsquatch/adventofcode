import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;

public class BaseConstructionSimulator
{
    CharMap2D map;
    public BaseConstructionSimulator(String[] lines)
    {
        map = new CharMap2D(lines);
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

    public int getNum(LumberGridType type)
    {
        return map.getNum(type.getSymbol());
    }

    @Override 
    public String toString()
    {
        return map.toString();
    }
}