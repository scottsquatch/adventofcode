import java.util.Hashtable;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Comparator;
import java.util.Arrays;

public class DiseaseSimulation
{
  private Army army1;
  private Army army2;

  public DiseaseSimulation(String army1Name, String[] army1Lines, String army2Name, String[] army2Lines)
  {
    army1 = new Army(army1Name, army1Lines);
    army2 = new Army(army2Name, army2Lines);
  }

  @Override
  public String toString()
  {
    return army1.toString() + "\n\n" + army2.toString();
  }

  public void printArmyStatus(Army a)
  {
    System.out.println(a.name + ":");
    for (Group g : a.groups())
    {
      System.out.println("Group " + g.getGroupNumber() + " contains " + g.numUnits + " units");
    }
  }

  public DiseaseSimulationResult simulate()
  {
    while (!army1.allGroupsDead() && !army2.allGroupsDead())
    {
      System.out.println("Round strt\n-----------");
      printArmyStatus(army1);
      printArmyStatus(army2);
      Hashtable<AttackConfig, AttackConfig> targets = targetSelectionPhase();
      attackPhase(targets);
    }

    Army winningArmy;
    if (army1.allGroupsDead())
    {
      winningArmy = army2;
    }
    else
    {
      winningArmy = army1;
    }

    return new DiseaseSimulationResult(winningArmy.name, winningArmy.groups());
  }

  public Hashtable<AttackConfig, AttackConfig> targetSelectionPhase()
  {
    Hashtable<AttackConfig, AttackConfig> targets = new Hashtable<AttackConfig, AttackConfig>();
    HashSet<Integer> army1AvailableTargets = new HashSet<Integer>();
    HashSet<Integer> army2AvailableTargets = new HashSet<Integer>();
    PriorityQueue<PriorityQueueEntry> targetSelectOrder = new PriorityQueue<PriorityQueueEntry>(new TargetSelectOrderComparator());
    for (Group g : army1.groups())
    {
      army1AvailableTargets.add(g.getGroupNumber());
      targetSelectOrder.add(new PriorityQueueEntry(g, 2));
    }

    for (Group g : army2.groups())
    {
      army2AvailableTargets.add(g.getGroupNumber());
      targetSelectOrder.add(new PriorityQueueEntry(g, 1));
    }

    System.out.println("\nStart target selection phase:");
    while (!targetSelectOrder.isEmpty())
    {
      PriorityQueueEntry currentQueueEntry = targetSelectOrder.poll();
      Group current = currentQueueEntry.group;
      int defendingArmyNumber = currentQueueEntry.otherArmyNumber;
      int attackingArmyNumber = defendingArmyNumber % 2 + 1;
      Army otherArmy = getArmyFromNumber(defendingArmyNumber);
      HashSet<Integer> availableDefenders;
      if (defendingArmyNumber == 1)
      {
        availableDefenders = army1AvailableTargets;
      }
      else
      {
        availableDefenders = army2AvailableTargets;
      }

      PriorityQueue<Group> potentialEnemies = new PriorityQueue<Group>(new TargetSelectionComparator(current));
      for (Integer groupNumber : otherArmy.groupNumbers())
      {
        if (availableDefenders.contains(groupNumber))
        {
          potentialEnemies.add(otherArmy.get(groupNumber));
        }
      }

      while (!potentialEnemies.isEmpty())
      {
        Group target = potentialEnemies.poll();
        int potentialDamage = current.getDamage(target);
        System.out.println(getArmyFromNumber(attackingArmyNumber).name + " group " + current.getGroupNumber() + " would deal defending group "
        + target.getGroupNumber() + " " +  potentialDamage + " damage");
        if (potentialDamage > 0)
        {
          availableDefenders.remove(target.getGroupNumber());
          targets.put(new AttackConfig(attackingArmyNumber, current.getGroupNumber()), new AttackConfig(defendingArmyNumber, target.getGroupNumber()));
          break;
        }
      }
    }

    return targets;
  }

  public void attackPhase(Hashtable<AttackConfig, AttackConfig> targets)
  {
    PriorityQueue<PriorityQueueEntry> attackOrder = new PriorityQueue<PriorityQueueEntry>(new AttackOrderComparator());

    for (Group g : army1.groups())
    {
      attackOrder.add(new PriorityQueueEntry(g, 2));
    }

    for (Group g : army2.groups())
    {
      attackOrder.add(new PriorityQueueEntry(g, 1));
    }

    System.out.println("\nStart attack phase:");
    while (!attackOrder.isEmpty())
    {
      PriorityQueueEntry currentQueueEntry = attackOrder.poll();
      Group attacker = currentQueueEntry.group;
      int defendingArmyNumber = currentQueueEntry.otherArmyNumber;
      Army otherArmy = getArmyFromNumber(defendingArmyNumber);
      int attackingArmyNumber = defendingArmyNumber % 2 + 1;
      AttackConfig attackerConfig = new AttackConfig(attackingArmyNumber, attacker.getGroupNumber());
      AttackConfig defenderConfig = targets.get(attackerConfig);

      if (defenderConfig != null)
      {
        Group target = getArmyFromNumber(defenderConfig.armyNumber).get(defenderConfig.groupNumber);
        // Make sure the unit hasn't already been killed
        if (!target.isDead())
        {
          int unitsBefore = target.numUnits;
          int damage = attacker.getDamage(target);
          target.takeDamage(damage);

          System.out.println(getArmyFromNumber(attackingArmyNumber).name + " group " + attacker.getGroupNumber() + " attacks defending group " + target.getGroupNumber() + ", killing " + (unitsBefore - target.numUnits) + " units");
        }
      }
    }
  }

  public Army getArmyFromNumber(int number)
  {
    if (number == 1)
    {
      return army1;
    }
    else if (number == 2)
    {
      return army2;
    }

    return null;
  }
}

class DiseaseSimulationResult
{
  public final String winningTeam;
  public final Iterable<Group> remainingUnits;

  public DiseaseSimulationResult(String team, Iterable<Group> remainingUnits)
  {
    this.winningTeam = team;
    this.remainingUnits = remainingUnits;
  }
}

class PriorityQueueEntry
{
  public final Group group;
  public final int otherArmyNumber;

  public PriorityQueueEntry(Group group, int otherArmyNumber)
  {
    this.group = group;
    this.otherArmyNumber = otherArmyNumber;
  }
}

class AttackConfig
{
  public final int armyNumber;
  public final int groupNumber;

  public AttackConfig(int armyNumber, int groupNumber)
  {
    this.armyNumber = armyNumber;
    this.groupNumber = groupNumber;
  }

  @Override
  public int hashCode()
  {
    int hash = 27;
    hash += armyNumber * 27;
    hash *= groupNumber * 27;

    return hash;
  }

  @Override
  public boolean equals(Object other)
  {
    if (this == other)
    {
      return true;
    }
    else if (other == null)
    {
      return false;
    }
    else if (getClass() != other.getClass())
    {
      return false;
    }

    AttackConfig otherAttackConfig = (AttackConfig)other;

    return otherAttackConfig.armyNumber == armyNumber &&
          otherAttackConfig.groupNumber == groupNumber;
  }
}
