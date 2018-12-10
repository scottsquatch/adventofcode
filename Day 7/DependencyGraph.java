import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;

public class DependencyGraph
{
  private Hashtable<Character, List<Character> > mDependenciesToSteps;
  private Hashtable<Character, Set<Character> > mStepsToDependencies;

  public DependencyGraph()
  {
    mDependenciesToSteps = new Hashtable<Character, List<Character> >();
    mStepsToDependencies = new Hashtable<Character, Set<Character> >();
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();

    // First Output Steps to dependencies
    builder.append("Step -> Dependencies\n");
    for (Character step : mStepsToDependencies.keySet())
    {
      builder.append(step + " -> ");

      for (Character dependency : mStepsToDependencies.get(step))
      {
        builder.append(dependency + ",");
      }

      // Remove last ","
      builder.deleteCharAt(builder.length() - 1);
      builder.append("\n");
    }

    // Dependency to steps
    builder.append("Dependency -> Steps\n");
    for (Character dependency : mDependenciesToSteps.keySet())
    {
      builder.append(dependency + " -> ");

      for (Character step : mDependenciesToSteps.get(dependency))
      {
        builder.append(step + ",");
      }

      // Remove last ","
      builder.deleteCharAt(builder.length() - 1);
      builder.append("\n");
    }

    return builder.toString();
  }

  public void addDependency(Dependency dep)
  {
    Character step = dep.getStep();
    Character dependency = dep.getDependency();

    // Add step to appropriate variables
    Set<Character> dependencies;
    if (!mStepsToDependencies.containsKey(step))
    {
      dependencies = new HashSet<Character>();
    }
    else
    {
      dependencies = mStepsToDependencies.get(step);
    }

    dependencies.add(dependency);
    mStepsToDependencies.put(step, dependencies);

    if (!mDependenciesToSteps.containsKey(step))
    {
      mDependenciesToSteps.put(step, new ArrayList<Character>());
    }

    // Add Dependency to appropriate variables
    List<Character> steps;
    if (!mDependenciesToSteps.containsKey(dependency))
    {
      steps = new ArrayList<Character>();
    }
    else
    {
      steps = mDependenciesToSteps.get(dependency);
    }

    steps.add(step);
    mDependenciesToSteps.put(dependency, steps);

    if (!mStepsToDependencies.containsKey(dependency))
    {
      mStepsToDependencies.put(dependency, new HashSet<Character>());
    }
  }

  public String getOrderToExecute()
  {
    PriorityQueue<Character> pq = new PriorityQueue<Character>();

    Hashtable<Character, Set<Character>> workingStepsToDependencies =
      (Hashtable<Character, Set<Character>>)mStepsToDependencies.clone();
    StringBuilder performedSteps = new StringBuilder();

    do
    {
      // Add available steps to PriorityQueue'
      ArrayList<Character> stepsToRemove = new ArrayList<Character>();
      for (Character step : workingStepsToDependencies.keySet())
      {
        if (workingStepsToDependencies.get(step).isEmpty())
        {
          pq.add(step);

          stepsToRemove.add(step);
        }
      }

      // Remove the steps we have added to the queue
      for (Character step : stepsToRemove)
      {
        workingStepsToDependencies.remove(step);
      }

      // "Perform" step by adding to StringBuilder
      Character stepToPerform = pq.poll();
      performedSteps.append(stepToPerform);

      // Now that we have performed the step, remove the dependency from
      // steps that require it
      for (Character dependency : mDependenciesToSteps.get(stepToPerform))
      {
        if (workingStepsToDependencies.containsKey(dependency))
        {
          workingStepsToDependencies.get(dependency).remove(stepToPerform);
        }
      }

    } while (!workingStepsToDependencies.isEmpty() ||
             !pq.isEmpty());

    return performedSteps.toString();
  }

  class WorkerConfig
  {
    public int freeAtSecond;
    public Character step;

    public WorkerConfig()
    {
      reset();
    }

    public void reset()
    {
      freeAtSecond = -1;
      step = null;
    }
  }

  public DependencyGraphExecutionOrderResult executeWithWorkers(
    int extraSecondsPerItem,
    int numWorkers
  )
  {
    PriorityQueue<Character> pq = new PriorityQueue<Character>();

    Hashtable<Character, Set<Character>> workingStepsToDependencies =
      (Hashtable<Character, Set<Character>>)mStepsToDependencies.clone();
    StringBuilder performedSteps = new StringBuilder();
    int second = -1;
    WorkerConfig[] workers = new WorkerConfig[numWorkers];
    // Initialize workers
    for (int i = 0; i < numWorkers; i++)
    {
      workers[i] = new WorkerConfig();
    }

    boolean stop = false;
    do
    {
      second++;

      // See if workers have completed any jobs, and free them
      for (int i = 0; i < numWorkers; i++)
      {
        WorkerConfig worker = workers[i];
        if (worker.freeAtSecond > -1 &&
            second >= worker.freeAtSecond)
        {
          performedSteps.append(worker.step);

          // Now that we have performed the step, remove the dependency from
          // steps that require it
          for (Character dependency : mDependenciesToSteps.get(worker.step))
          {
            if (workingStepsToDependencies.containsKey(dependency))
            {
              workingStepsToDependencies.get(dependency).remove(worker.step);
            }
          }

          // free worker
          worker.reset();
        }
      }

      // Add available steps to PriorityQueue'
      ArrayList<Character> stepsToRemove = new ArrayList<Character>();
      for (Character step : workingStepsToDependencies.keySet())
      {
        if (workingStepsToDependencies.get(step).isEmpty())
        {
          pq.add(step);

          stepsToRemove.add(step);
        }
      }

      // Remove the steps we have added to the queue
      for (Character step : stepsToRemove)
      {
        workingStepsToDependencies.remove(step);
      }

      // See if we have any free workers and queue items if needed
      if (!pq.isEmpty())
      {
        for (int i = 0; i < numWorkers; i++)
        {
          WorkerConfig worker = workers[i];

          if (worker.freeAtSecond == -1)
          {
            // Queue item
            Character stepToPerform = pq.poll();

            worker.step = stepToPerform;
            worker.freeAtSecond = second + extraSecondsPerItem + (int)(worker.step.charValue() - 'A') + 1;

            if (pq.isEmpty())
            {
              break;
            }
          }
        }
      }

      // Determine if we should stop the simulation
      boolean allWorkersFree = true;
      for (WorkerConfig worker : workers)
      {
        if (worker.freeAtSecond > -1)
        {
          allWorkersFree = false;
          break;
        }
      }

      System.out.print("Work at second: " + second + ", workers: ");
      for (int i = 0; i < numWorkers; i++)
      {
        System.out.print(i + ":" + workers[i].step + " ");
      }
      System.out.println();

      stop = allWorkersFree &&
            workingStepsToDependencies.isEmpty() &&
            pq.isEmpty();
    } while (!stop);

    DependencyGraphExecutionOrderResult result = new DependencyGraphExecutionOrderResult(second, performedSteps.toString());
    return result;
  }
}

class DependencyGraphExecutionOrderResult
{
  private int mSecondsTaken;
  private String mExecutionOrder;

  public DependencyGraphExecutionOrderResult(int secondsTaken, String executionOrder)
  {
    mSecondsTaken = secondsTaken;
    mExecutionOrder = executionOrder;
  }

  public int getSecondsTaken()
  {
    return mSecondsTaken;
  }

  public String getExecutionOrder()
  {
    return mExecutionOrder;
  }
}
