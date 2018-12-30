import java.util.Comparator;

public class TargetSelectOrderComparator implements Comparator<PriorityQueueEntry>
{
  @Override
  public int compare(PriorityQueueEntry pqe1, PriorityQueueEntry pqe2)
  {
    Group g1 = pqe1.group;
    Group g2 = pqe2.group;

    // SORT by effectivePower DESC, initiative DESC
    if (g1.getEffectivePower() > g2.getEffectivePower())
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
