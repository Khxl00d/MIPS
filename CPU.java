import java.util.Stack;
public class CPU {
    RegisterFile registers;
    ProgramCounter PC;
    DataMemory Memory;
    ControlUnit controlUnit;
    Instruction instruction;
    InstructionMemory instMem;
    Adder adder;
    Stack<Integer> TargetStack = new Stack<>();


    public CPU(RegisterFile sharedRegisters, InstructionMemory sharedMemory) {
        this.PC = new ProgramCounter(0);
        this.registers = sharedRegisters;
        this.instMem = sharedMemory;
        this.Memory = new DataMemory();
        this.controlUnit = new ControlUnit();      
    }

    public void executeCPU() {

        this.instruction = instMem.getInstruction(PC);

        if (instruction == null) return;

        if (instruction.getOpcode() == 35) {
            loadWord();
        }
        else if (instruction.getOpcode() == 43) {
            storeWord();
        }
        else if (instruction.getOpcode() == 0 && instruction.getFunct() == 0) {
            shiftLeftLogical();
        }
        else if (instruction.getOpcode() == 0 && instruction.getFunct() == 32) {
            addition();
        }
        else if (instruction.getOpcode() == 8) {
            additionImmediate();
        }
        else if (instruction.getOpcode() == 0 && instruction.getFunct() == 42) {
            setLessThan();
        }
        else if (instruction.getOpcode() == 0 && instruction.getFunct() == 37) {
            or();
        }
        else if (instruction.getOpcode() == 12) {
            orImmediate();
        }
        else if (instruction.getOpcode() == 0 && instruction.getFunct() == 36) {
            nor();
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
    
    public void addition() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        int value=registers.readRegister(Rs)+registers.readRegister(Rt);

        registers.writeRegister(Rd,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
    }

    public void additionImmediate() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int immediate=instruction.getImmediate();

        int value=registers.readRegister(Rs)+immediate;

        registers.writeRegister(Rt,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
    }
    
    public void setLessThan() {

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
    public void or(){

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        int value=registers.readRegister(Rs)|registers.readRegister(Rt);

        registers.writeRegister(Rd,value,controlUnit.controlSignals(instruction.getOpcode())[8]);

    }

    public void orImmediate() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int immediate=instruction.getImmediate();

        int value=registers.readRegister(Rs)|immediate;

        registers.writeRegister(Rt,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
    }

    public void nor() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        int value=~(registers.readRegister(Rs)|registers.readRegister(Rt));

        registers.writeRegister(Rd,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
    }

    public void jump(){
        PC.equals(instruction.getTarget());
    }
    public void jumpAndLink(){
        PC.equals(instruction.getTarget());
        registers.writeRegister(31, adder.NewAddress(instruction.getTarget(), 4), 1);
        TargetStack.push(adder.NewAddress(instruction.getTarget(), 4));
    }
}

