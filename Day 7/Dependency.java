import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Class to represent a dependency for building a a Sleigh.
 */
public class Dependency
{
  private Character mStep;
  private Character mDependency;

  private static final Pattern STEP_STRING =
    Pattern.compile("Step ([A-Z]) must be finished before step ([A-Z]) can begin.");

  public Dependency(Character step, Character dependency)
  {
    mStep = step;
    mDependency = dependency;
  }

  public Character getStep()
  {
    return mStep;
  }

  public Character getDependency()
  {
    return mDependency;
  }

  @Override
  public String toString()
  {
    return "Step " + mDependency + " must be finished before step " + mStep + " can begin.";
  }

  public static Dependency parse(String dependencyString)
  {
    Matcher match = STEP_STRING.matcher(dependencyString);

    if (!match.matches())
    {
      return null;
    }
    
    Character dependency = match.group(1).charAt(0);
    Character step = match.group(2).charAt(0);

    return new Dependency(step, dependency);
  }
}
