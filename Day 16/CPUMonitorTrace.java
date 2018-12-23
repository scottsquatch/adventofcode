public class CPUMonitorTrace
{
	private RegisterState before;
	private RegisterState after;
	private Instruction instr;

	public CPUMonitorTrace(RegisterState before, Instruction instr, RegisterState after)
	{
		this.before = before;
		this.after= after;
		this.instr = instr;
	}

	public RegisterState getBefore()
	{
		return before;
	}

	public RegisterState getAfter()
	{
		return after;
	}

	public Instruction getInstruction()
	{
		return instr;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("Before: " + before + "\n");
		builder.append(instr.toString() + "\n");
		builder.append("After: " + after + "\n");

		return builder.toString();
	}
}
