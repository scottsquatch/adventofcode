import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegisterState implements Cloneable
{
	private static final Pattern REGISTER_STATE_REGEX = Pattern.compile("(?:Before|After):\\s+\\[([0-9]+),\\s+([0-9]+),\\s+([0-9]+),\\s+([0-9]+)\\]");
	private static final int REGISTER_SIZE = 4;

	private final int[] registers;

	public RegisterState(int register0, int register1, int register2, int register3)
	{
		registers = new int[REGISTER_SIZE];

		registers[0] = register0;
		registers[1] = register1;
		registers[2] = register2;
		registers[3] = register3;
	}

	public int getRegisterValue(int registerNum)
	{
		return registers[registerNum];
	}

	public void setRegisterValue(int registerNum, int value)
	{
		registers[registerNum] = value;
	}

	@Override
	public Object clone()
	{
		return new RegisterState(getRegisterValue(0), getRegisterValue(1), getRegisterValue(2), getRegisterValue(3));
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("[");
		for (int i = 0; i < REGISTER_SIZE; i++)
		{
			builder.append(getRegisterValue(i));
			if (i < (REGISTER_SIZE - 1))
			{
				builder.append(", ");
			}
		}
		
		builder.append("]");

		return builder.toString();
	}

	public static RegisterState parseRegisterState(String s)
	{
		Matcher matcher = REGISTER_STATE_REGEX.matcher(s);

		if (!matcher.matches())
		{
			return null;
		}


		int r0, r1, r2, r3;
		r0 = Integer.parseInt(matcher.group(1));
		r1 = Integer.parseInt(matcher.group(2));
		r2 = Integer.parseInt(matcher.group(3));
		r3 = Integer.parseInt(matcher.group(4));

		return new RegisterState(r0, r1, r2, r3);
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

		// We now know we have the same object types
		RegisterState otherState = (RegisterState)other;

		for (int i = 0; i < REGISTER_SIZE; i++)
		{
			if (otherState.getRegisterValue(i) != getRegisterValue(i))
			{
				return false;
			}
		}

		return true;
	}

	public static void main(String[] args)
	{
		System.out.println("Stating tests for Register class...");

		RegisterState testReg = RegisterState.parseRegisterState("Before: [4, 5, 5, 7]");

		assert(4 == testReg.getRegisterValue(0));
		assert(5 == testReg.getRegisterValue(1));
		assert(5 == testReg.getRegisterValue(2));
		assert(7 == testReg.getRegisterValue(3));

		assert("[4, 5, 5, 7]".equals(testReg.toString()));

		testReg = RegisterState.parseRegisterState("After: [4, 5, 5, 7]");

		assert(4 == testReg.getRegisterValue(0));
		assert(5 == testReg.getRegisterValue(1));
		assert(5 == testReg.getRegisterValue(2));
		assert(7 == testReg.getRegisterValue(3));

		testReg.setRegisterValue(1, 55);
		assert(4 == testReg.getRegisterValue(0));
		assert(55 == testReg.getRegisterValue(1));
		assert(5 == testReg.getRegisterValue(2));
		assert(7 == testReg.getRegisterValue(3));


		RegisterState cloned = (RegisterState)testReg.clone();
		assert(cloned.registers != testReg.registers);
		assert(4 == cloned.getRegisterValue(0));
		assert(55 == cloned.getRegisterValue(1));
		assert(5 == cloned.getRegisterValue(2));
		assert(7 == cloned.getRegisterValue(3));

		assert(cloned.equals(testReg));

		cloned.setRegisterValue(1, 5);
		assert(!cloned.equals(testReg));

		System.out.println("All tests pass!");
	}
}
