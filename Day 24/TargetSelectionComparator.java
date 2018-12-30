import java.util.Comparator;

public class TargetSelectionComparator implements Comparator<Group>
{
  private Group g;
  public TargetSelectionComparator(Group g)
  {
    this.g = g;
  }

  @Override
  public int compare(Group g1, Group g2)
  {
    int damageToG1 = g.getDamage(g1);
    int damageToG2 = g.getDamage(g2);
    // SORT by damage DESC, effectivePower DESC, initiative DESC
    if (damageToG1 > damageToG2)
    {
      return -1;
    }
    else if (damageToG2 > damageToG1)
    {
      return 1;
    }
    else if (g1.getEffectivePower() > g2.getEffectivePower())
    {
      return -1;
    }
    else if (g1.getEffectivePower() < g2.getEffectivePower())
    {
      return 1;
    }
    else if (g1.initiative > g2.initiative)
    {
      return -1;
    }
    else if (g1.initiative < g2.initiative)
    {
      return 1;
    }

    return 0;
  }
}
