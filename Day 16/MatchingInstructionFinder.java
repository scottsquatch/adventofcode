import java.util.ArrayList;

public class MatchingInstructionFinder
{
	public OperationType[] getMatchingTypes(RegisterState before, Instruction instruction, RegisterState after)
	{
		ArrayList<OperationType> matchingTypes = new ArrayList<OperationType>();
		for (OperationType operation : OperationType.values())
		{
			RegisterState opResult = OperationType.applyOperation(operation, before, instruction);

			if (opResult.equals(after))
			{
				matchingTypes.add(operation);
			}
		}


		return matchingTypes.toArray(new OperationType[matchingTypes.size()]);
	}

	public static void main(String[] args)
	{
		System.out.println("Testing RegistryState class");
		// Use example from AdventOfCode test
		RegisterState before = new RegisterState(3, 2, 1, 1);
		RegisterState after = new RegisterState(3, 2, 2, 1);
		Instruction instr = new Instruction(9, 2, 1, 2);

		MatchingInstructionFinder testFinder = new MatchingInstructionFinder();
		OperationType[] matchingOps = testFinder.getMatchingTypes(before, instr, after);
		assert(3 == matchingOps.length);
		assert(OperationType.ADDI == matchingOps[0]);
		assert(OperationType.MULR == matchingOps[1]);
		assert(OperationType.SETI == matchingOps[2]);

		System.out.println("All test passed!");
	}
}
