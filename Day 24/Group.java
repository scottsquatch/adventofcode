import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Hashtable;

public class Group
{
  private static Pattern GROUP_REGEX = Pattern.compile("([0-9]+) units each with ([0-9]+) hit points (\\(.+\\) ){0,1}with an attack that does ([0-9]+) ([a-z]+) damage at initiative ([0-9]+)");

  public int numUnits;
  public int hpPerUnit;
  public int initiative;
  public int attackDamage;
  public String attackType;
  private int groupNumber;
  private Hashtable<String, Integer> specialAttackModifier;

  public Group(String s, int groupNumber)
  {
    this.groupNumber = groupNumber;
    specialAttackModifier = new Hashtable<String, Integer>();
    Matcher matcher = GROUP_REGEX.matcher(s);

    if (!matcher.matches())
    {
      throw new IllegalArgumentException(s + " is not a valid group string");
    }

    numUnits = Integer.parseInt(matcher.group(1));
    hpPerUnit = Integer.parseInt(matcher.group(2));
    parseSpecialAttackModifiers(matcher.group(3));
    attackDamage = Integer.parseInt(matcher.group(4));
    attackType = matcher.group(5);
    initiative = Integer.parseInt(matcher.group(6));
  }

  private void parseSpecialAttackModifiers(String s)
  {
    if (s != null)
    {
      // String will have leading '(' character and trailing ')' and ' ' characters.
      String rawModifiers = s.substring(1, s.length() - 2);

      String[] splitModifiers = rawModifiers.split(";");

      for (String modifier : splitModifiers)
      {
        String[] split = modifier.split(" to ");
        String modifierType = split[0].trim();
        int damageModifier;
        if (modifierType.equals("immune"))
        {
          damageModifier = 0;
        }
        else if (modifierType.equals("weak"))
        {
          damageModifier = 2;
        }
        else
        {
          throw new IllegalArgumentException(modifierType + " is not a known modifier type");
        }

        String[] damageTypes = split[1].split(", ");
        for (String damageType : damageTypes)
        {
          specialAttackModifier.put(damageType.trim(), damageModifier);
        }
      }
    }
  }

  public int getEffectivePower()
  {
    return numUnits * attackDamage;
  }

  public int getGroupNumber()
  {
    return groupNumber;
  }

  public boolean isDead()
  {
    return numUnits < 1;
  }

  public int getDamage(Group other)
  {
    // First get damage modifier
    int modifier = 1;
    if (other.specialAttackModifier.containsKey(attackType))
    {
      modifier = other.specialAttackModifier.get(attackType);
    }

    return modifier * getEffectivePower();
  }

  public void takeDamage(int damage)
  {
    // We can only lose whole units
    numUnits = Math.max(0, numUnits - damage / hpPerUnit);
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    for (String attackType : specialAttackModifier.keySet())
    {
      builder.append(" " + attackType + ": " + specialAttackModifier.get(attackType));
    }
    return "Group " + groupNumber + " contains " + numUnits + " units with " + hpPerUnit + "hp and " + attackDamage + " " + attackType +" damage. Initiative is " + initiative
      + " modifiers are: " + builder.toString();
  }

  @Override
  public int hashCode()
  {
    // only use static values
    int hash = 27;
    hash += 27 * initiative;
    hash += 27 * attackDamage;
    hash += 27 * attackType.hashCode();
    hash += 27 * groupNumber;
    hash += 27 * specialAttackModifier.hashCode();

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

    Group otherGroup = (Group)other;
    return groupNumber == otherGroup.groupNumber;
  }
}
