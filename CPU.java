

public class CPU {
    RegisterFile registers;
    ProgramCounter PC;
    DataMemory Memory;
    ControlUnit controlUnit;
    Instruction instruction;


    public CPU() {
        this.PC = new ProgramCounter(0);
        this.registers = new RegisterFile();
        this.Memory = new DataMemory();
        this.controlUnit = new ControlUnit();

    }

    public void loadWord(InstructionMemory loadMemory) {
        
        instruction = loadMemory.getInstruction(PC);
         
        int Rt = instruction.getRt();
        int Rs = instruction.getRs();
        int Offset = instruction.getImmediate();
        int address = registers.readRegister(Rs)+Offset;
        int value = Memory.readData(address,controlUnit.controlSignals(instruction.getOpcode())[3]);

        registers.writeRegister(Rt, value, controlUnit.controlSignals(instruction.getOpcode())[8]);
        PC.incrementPC();
    }

    public void storeWord(InstructionMemory storeMemory) {
        
        instruction = storeMemory.getInstruction(PC);
         
        int Rt = instruction.getRt();
        int Rs = instruction.getRs();
        int Offset = instruction.getImmediate();
        int address = registers.readRegister(Rs)+Offset;
        int value = registers.readRegister(Rt);
        
        Memory.writeData(address,value,controlUnit.controlSignals(instruction.getOpcode())[6]);
        PC.incrementPC();
    }

    public void shiftLeftLogical(InstructionMemory shiftRd) {

        instruction = shiftRd.getInstruction(PC);

        int Rt = instruction.getRt();
        int Shamt = instruction.getShamt();
        int Rd = instruction.getRd();
        int value = registers.readRegister(Rt)<<Shamt;

        registers.writeRegister(Rd,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
        PC.incrementPC();
    }
    
}