import java.util.LinkedList;
import java.util.Collection;
/**
 * Class which reduces polymer by factoring reactions
 * 
 * A polymer is a string of letters where upper case and lower case represent differing polarities
 * 
 * Adjacent differing polarities will react and be removed from polymer
 */
public class PolymerReducer
{
    public String reducePolymer(String polymerText)
    {
        if (polymerText.length() < 2)
        {
            return polymerText;
        }
        else
        {
            LinkedList<Character> polymer = new LinkedList<Character>();

            // initialize the polymer with the first unit 
            int index = 1;
            polymer.add(polymerText.charAt(0));

            while (index < polymerText.length())
            {
                Character currentUnit = polymerText.charAt(index);
                if (!polymer.isEmpty() &&
                    doUnitsReact(currentUnit, polymer.peekLast()))
                {
                    // Since we racted with the last element of the polymer, we need to remove it
                    polymer.removeLast();
                }
                else
                {
                    polymer.add(currentUnit);
                }

                index++;
            }

            // Transfer into string
            return getPolymerText(polymer);
        }
        
    }

    // Returns true if the two given "units" react. Here a unit is a character representation where lower and upper case
    // represent polarity
    private static boolean doUnitsReact(char unit1, char unit2)
    {
        // We need to check for inequality because otherwise 'A' and 'A' would return true but that is not desired
        return unit1 != unit2 &&
            (Character.toUpperCase(unit1) == unit2 ||
             Character.toUpperCase(unit2) == unit1);
    }

    private static String getPolymerText(Collection<Character> units)
    {
        StringBuilder builder = new StringBuilder();
        for (Character c : units)
        {
            builder.append(c);
        }

        return builder.toString();
    }
}