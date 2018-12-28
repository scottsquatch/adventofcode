import java.util.Hashtable;

public class WristDeviceEmulator
{
    private Instruction[] instructionStack;
    private int ipRegister;
    private int ip;
    private Hashtable<Integer, RegisterState> previousStates;
    private int numInstructions = 0;

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
      numInstructions = 0;
      previousStates = new Hashtable<Integer, RegisterState>();
	    RegisterState state = (RegisterState)initialState.clone();

	    while (ip >= 0 && ip < instructionStack.length)
	    {
		    state = step(state);
        numInstructions++;
	    }

	    return state;
    }

    public RegisterState step(RegisterState currentState)
    {
        Instruction instr = instructionStack[ip];
        RegisterState next = (RegisterState)currentState.clone();

        // Update ip Register to ip value
        next.setRegisterValue(ipRegister, ip);

        // To determine a loop we check if the state is the same as the previous time we executed this command
        if (!previousStates.containsKey(ip))
        {
          previousStates.put(ip, (RegisterState)currentState.clone());
        }
        else
        {
            RegisterState previousState = previousStates.get(ip);
            //System..out.println(ip + ": "  + instr + ", "+ previousState + " vs. " + currentState);
            if (previousState.equals(currentState))
            {
              throw new RuntimeException("Loop detected");
            }
            else
            {
              // Need to update the value
              previousStates.put(ip, (RegisterState)currentState.clone());
            }
        }

        // Based on hand analysis:
        // Instruction 28 is the only time the program references register 0
        // At this point we are checking the equality of registers 4 and 0, so
        // the value to halt the program the earliest is the value of register 4 at this point
        if (ip == 28)
        {
          int value = next.getRegisterValue(4);
          System.out.println("Value of R0 which forces the earliest termination is " + value);

          next.setRegisterValue(0, value);
        }

        System.out.print(next + " " + instr);
        next = OperationType.applyOperation(instr.getop(), next, instr);
        System.out.println(" " + next);

        ip = next.getRegisterValue(ipRegister);

        ip++;

        return next;
    }

    public int getNumInstructions()
    {
      return numInstructions;
    }
}
