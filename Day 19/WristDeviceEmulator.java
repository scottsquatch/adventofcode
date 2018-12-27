import java.util.Hashtable;

public class WristDeviceEmulator
{
    private Instruction[] instructionStack;
    private int ipRegister;
    private int ip;
    private Hashtable<Integer, JumpConfig> previousAbsoluteJumps;

    class JumpConfig
    {
        public int jumpPoint;
        public RegisterState stateBeforeJump;

        @Override
        public int hashCode()
        {
            int hash = 27;
            hash += 27 * jumpPoint;
            hash += 27 * stateBeforeJump.hashCode();

            return hash;
        }
    }

    public WristDeviceEmulator(String[] lines)
    {
        instructionStack = new Instruction[lines.length - 1];
        ip = 0;

        // First line should always set the IP register
        ipRegister = Integer.parseInt(lines[0].replace("#ip ", ""));

        for (int i = 1; i < lines.length; i++)
        {
            instructionStack[i - 1] = Instruction.parseInstruction(lines[i]);
        }
    }


    public RegisterState run()
    {
        return run(new RegisterState(0, 0, 0, 0, 0, 0));
    }

    public RegisterState run(RegisterState initialState)
    {
	    previousAbsoluteJumps = new Hashtable<Integer, JumpConfig>();
	    RegisterState state = (RegisterState)initialState.clone();

	    while (ip >= 0 && ip < instructionStack.length)
	    {
		    state = step(state);
	    }

	    return state;
    }

    public RegisterState step(RegisterState currentState)
    {
        Instruction instr = instructionStack[ip];
        RegisterState next = (RegisterState)currentState.clone();

        // Update ip Register to ip value
        next.setRegisterValue(ipRegister, ip);

	//StringBuilder builder = new StringBuilder();
	//builder.append("ip=" + ip + " " + next + " " + instr);

        next = OperationType.applyOperation(instr.getop(), next, instr);

	//builder.append(" " + next);
	//String stateStr = builder.toString();
	//if (previousStates.contains(stateStr))
	//{
		//throw new IllegalArgumentException("Loop detected");
	//}
	//else
	//{
		//previousStates.add(stateStr);
	//}

        ip = next.getRegisterValue(ipRegister);

        ip++;

        return next;
    }
}
