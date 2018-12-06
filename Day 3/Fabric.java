import java.util.HashSet;
import java.util.ArrayList;

public class Fabric
{
	// Private Vairables
	private ArrayList<String>[][] mFabric;
	private int mHeight;
	private int mWidth;
	private int mOverlap;
	private HashSet<String> mNonOverlappingClaims;
	
	public Fabric (int height, int width)
	{
		mHeight = height;
		mWidth = width;
		
		// Each cell represents the id of the claim. If it is an "x" that means there are two or more claims
		mFabric = new ArrayList[height][width];
		mOverlap = 0;
		mNonOverlappingClaims = new HashSet<String>();
	}

	public void makeClaim(Claim claim)
	{
		int startingRow = claim.getInchesFromTopEdge();
		int startingCol = claim.getInchesFromLeftEdge();

		int endingRow = startingRow + claim.getHeight();
		int endingCol = startingCol + claim.getWidth();

		Boolean overlap = false;
		for (int i = startingRow; i < endingRow; i++)
		{
			for (int j = startingCol; j < endingCol; j++)
			{
				ArrayList<String> fabricClaim = mFabric[i][j];
				if (fabricClaim == null)
				{
					fabricClaim = new ArrayList<String>();
				}
				else
				{
					// We have multiple claims keep running total of overlap
					if (fabricClaim.size() == 1)
					{
						mOverlap++;
					}

					overlap = true;

					mNonOverlappingClaims.removeAll(fabricClaim);
				}	

				fabricClaim.add(claim.getId());
				mFabric[i][j] = fabricClaim;
			}
		}

		// IF we did not find an overlap, then add to set of non overlapping IDs
		if (!overlap)
		{
			mNonOverlappingClaims.add(claim.getId());
		}
	}

	/** Return the square inches of fabric which has overlapping claims **/
	public int getOverlap()
	{
		return mOverlap;
	}

	public HashSet<String> getNonOverlappingClaims()
	{
		return mNonOverlappingClaims;
	}
}



