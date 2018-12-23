import java.util.ArrayList;

public class CPUTraceLogParser
{
	private CPUMonitorTrace[] traces;

	public CPUTraceLogParser(String[] lines)
	{
		ArrayList<CPUMonitorTrace> traces = new ArrayList<CPUMonitorTrace>();
		int lineNum = 0;

		while (lineNum < (lines.length - 2))
		{
			RegisterState before = RegisterState.parseRegisterState(lines[lineNum++]);
			Instruction instr = Instruction.parseInstruction(lines[lineNum++]);
			RegisterState after = RegisterState.parseRegisterState(lines[lineNum++]);

			traces.add(new CPUMonitorTrace(before, instr, after));
		}

		this.traces = traces.toArray(new CPUMonitorTrace[traces.size()]);
	}

	public CPUMonitorTrace[] getTraces()
	{
		return traces;
	}

	@Override 
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("[");

		for (int i = 0; i < traces.length; i++)
		{
			builder.append(traces[i].toString());
			if (i < (traces.length - 1))
			{
				builder.append(", ");
			}
		}

		builder.append("]");

		return builder.toString();
	}


	public static void main(String[] args)
	{
		System.out.println("Starting tests for CPUTraceLogParser class");
		String[] lines = 
		{
			"Before: [1, 2, 4, 6]",
			"3 4 5 1",
			"After: [5, 5, 1, 5]",
			"Before: [4, 1, 5, 6]",
			"12 4 1 3",
			"After: [4, 1, 4, 1]"
		};

		CPUTraceLogParser parser = new CPUTraceLogParser(lines);

		assert(2 == parser.getTraces().length);

		assert(parser.getTraces()[0].getBefore().equals(new RegisterState(1, 2, 4, 6)));
		Instruction i = parser.getTraces()[0].getInstruction();
		assert(3 == i.getOpCode());
		assert(4 == i.getA());
		assert(5 == i.getB());
		assert(1 == i.getC());
		assert(parser.getTraces()[0].getAfter().equals(new RegisterState(5, 5, 1, 5)));

		
		assert(parser.getTraces()[1].getBefore().equals(new RegisterState(4, 1, 5, 6)));
		i = parser.getTraces()[1].getInstruction();
		assert(12 == i.getOpCode());
		assert(4 == i.getA());
		assert(1 == i.getB());
		assert(3 == i.getC());
		assert(parser.getTraces()[1].getAfter().equals(new RegisterState(4, 1, 4, 1)));


		System.out.println("All tests passed!");
	}


}
