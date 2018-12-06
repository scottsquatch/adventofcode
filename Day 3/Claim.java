import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Claim
{
	// Private Variables
	private String mId;
	private int mInchesFromLeftEdge;
	private int mInchesFromTopEdge;
	private int mWidth;
	private int mHeight;

	private static final Pattern CLAIM_PATTERN = Pattern.compile("#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)");

	/** Create a claim form the given text
	 * example: # 123 @ 3,2: 5x4 **/
	public Claim(String textClaim)
	{
		Matcher matcher = CLAIM_PATTERN.matcher(textClaim);

		if (!matcher.matches())
		{
			throw new IllegalArgumentException("Text claim " + textClaim + " is invalid");
		}
		else
		{
			mId = matcher.group(1);
			mInchesFromLeftEdge = Integer.parseInt(matcher.group(2));
			mInchesFromTopEdge = Integer.parseInt(matcher.group(3));
			mWidth = Integer.parseInt(matcher.group(4));
			mHeight = Integer.parseInt(matcher.group(5));
		}
	}

	// Getters
	public String getId()
	{
		return mId;
	}

	public int getInchesFromLeftEdge()
	{
		return mInchesFromLeftEdge;
	}

	public int getInchesFromTopEdge()
	{
		return mInchesFromTopEdge;
	}

	public int getWidth()
	{
		return mWidth;
	}

	public int getHeight()
	{
		return mHeight;
	}
}
