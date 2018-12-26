public class RegisterState implements Cloneable
{
	private static final int REGISTER_SIZE = 6;

	private final int[] registers;
	private final int instructionPointerRegister;


	public RegisterState(int register0, int register1, int register2, int register3, int register4, int register5,
		int instructionPointerRegister)
	{
		registers = new int[REGISTER_SIZE];

		registers[0] = register0;
		registers[1] = register1;
		registers[2] = register2;
		registers[3] = register3;
		registers[4] = register4;
		registers[5] = register5;

		this.instructionPointerRegister = instructionPointerRegister;
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
		return new RegisterState(getRegisterValue(0), getRegisterValue(1), getRegisterValue(2), getRegisterValue(3),
			getRegisterValue(4), getRegisterValue(5), instructionPointerRegister);
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
}
