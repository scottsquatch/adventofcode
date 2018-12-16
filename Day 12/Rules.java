import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Rules
{
	private Dictionary<String, Boolean> ruleTable;

	private static final Pattern RULE_REGEX = Pattern.compile("([.#]{5}) => ([.#])");

	public Rules()
	{
		ruleTable = new Hashtable<String, Boolean>();
	}

	// Adds the rule to the set of rules.
	// An example of rule text is: ..##. => #
	public void addRule(String rule)
	{
		Matcher ruleMatcher = RULE_REGEX.matcher(rule);
		if (ruleMatcher.matches())
		{
			String rulePattern = ruleMatcher.group(1);
			String ruleResult = ruleMatcher.group(2);
			boolean fillPot = ruleResult.equals("#");

			ruleTable.put(rulePattern, fillPot);
		}
		else
		{
			throw new IllegalArgumentException("Invalid format for rule " + rule);	
		}
	}

	// Given the pot text, do we need to update the current to be filled?
	public boolean fillCurrentPot(String potText)
	{
		// Assume by default plants will stay the same if they don't have a rule
		Boolean ruleResult = ruleTable.get(potText);
		if (ruleResult == null)
		{

			return false;
		}

		return ruleTable.get(potText);
	}
}
