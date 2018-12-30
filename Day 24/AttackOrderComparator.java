import java.util.Comparator;

public class AttackOrderComparator implements Comparator<PriorityQueueEntry>
{
  @Override
  public int compare(PriorityQueueEntry pqe1, PriorityQueueEntry pqe2)
  {
    Group g1 = pqe1.group;
    Group g2 = pqe2.group;
    
    // sort by initiatve DESC
    if (g1.initiative > g2.initiative)
    {
      return -1;
    }
    else if (g2.initiative > g1.initiative)
    {
      return 1;
    }

    return 0;
  }
}
