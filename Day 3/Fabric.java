public class Fabric
{
	// Private Vairables
	private String[][] mFabric;
	private int mHeight;
	private int mWidth;
	private int mOverlap;
	
	private static final String MULTIPLE_CLAIMS = "X";

	public Fabric (int height, int width)
	{
		mHeight = height;
		mWidth = width;
		
		// Each cell represents the id of the claim. If it is an "x" that means there are two or more claims
		mFabric = new String[height][width];
		mOverlap = 0;
	}

	public void makeClaim(Claim claim)
	{
		int startingRow = claim.getInchesFromTopEdge();
		int startingCol = claim.getInchesFromLeftEdge();

		int endingRow = startingRow + claim.getHeight();
		int endingCol = startingCol + claim.getWidth();

		for (int i = startingRow; i < endingRow; i++)
		{
			for (int j = startingCol; j < endingCol; j++)
			{
				String fabricClaim = mFabric[i][j];
				if (fabricClaim == null)
				{
					mFabric[i][j] = claim.getId();
				}
				else if (!fabricClaim.equals(MULTIPLE_CLAIMS))
				{
					// We have multiple claims keep running total of overlap
					mOverlap++;
					mFabric[i][j] = MULTIPLE_CLAIMS;
				}	
			}
		}
	}

	/** Return the square inches of fabric which has overlapping claims **/
	public int getOverlap()
	{
		return mOverlap;
	}
}



