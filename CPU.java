

public class CPU {
    RegisterFile registers;
    ProgramCounter PC;
    DataMemory Memory;
    ControlUnit controlUnit;
    Instruction instruction;
    InstructionMemory instMem;


    public CPU() {
        this.PC = new ProgramCounter(0);
        this.registers = new RegisterFile();
        this.Memory = new DataMemory();
        this.controlUnit = new ControlUnit();
        this.instruction = instMem.getInstruction(PC);

        if (instruction.getOpcode() == 35) {
            loadWord();
        }
        if (instruction.getOpcode() == 43) {
            storeWord();
        }
    }

    public void loadWord() {

        PC.incrementPC();
         
        int Rt = instruction.getRt();
        int Rs = instruction.getRs();
        int Offset = instruction.getImmediate();
        int address = registers.readRegister(Rs)+Offset;
        int value = Memory.readData(address,controlUnit.controlSignals(instruction.getOpcode())[3]);

        registers.writeRegister(Rt, value, controlUnit.controlSignals(instruction.getOpcode())[8]);
    }

    public void storeWord() {
        
        PC.incrementPC();
         
        int Rt = instruction.getRt();
        int Rs = instruction.getRs();
        int Offset = instruction.getImmediate();
        int address = registers.readRegister(Rs)+Offset;
        int value = registers.readRegister(Rt);
        
        Memory.writeData(address,value,controlUnit.controlSignals(instruction.getOpcode())[6]);
    }

    public void shiftLeftLogical() {

        PC.incrementPC();

        int Rt = instruction.getRt();
        int Shamt = instruction.getShamt();
        int Rd = instruction.getRd();
        int value = registers.readRegister(Rt)*2<<Shamt;

        registers.writeRegister(Rd,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
    }
    
    public void add() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        int value=registers.readRegister(Rs)+registers.readRegister(Rt);

        registers.writeRegister(Rd,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
    }

    public void addi() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int immediate=instruction.getImmediate();

        int value=registers.readRegister(Rs)+immediate;

        registers.writeRegister(Rt,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
    }
    
    public void slt() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        if (registers.readRegister(Rs)<registers.readRegister(Rt)) {
            registers.writeRegister(Rd,1,controlUnit.controlSignals(instruction.getOpcode())[8]);
        }
        else {
            registers.writeRegister(Rd,0,controlUnit.controlSignals(instruction.getOpcode())[8]);
        }
    }
}