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
}
