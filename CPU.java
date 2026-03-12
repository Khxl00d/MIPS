public class CPU {
    RegisterFile registers;
    ProgramCounter PC;
    DataMemory Memory;
    ControlUnit controlUnit;
    Instruction instruction;
    InstructionMemory instMem;
    Adder adder;
    ALU ALUOP;
    ALUControl ALUCont;


    public CPU(RegisterFile sharedRegisters, InstructionMemory sharedMemory, ProgramCounter pc) {
        this.PC = pc;
        this.registers = sharedRegisters;
        this.instMem = sharedMemory;
        this.Memory = new DataMemory();
        this.controlUnit = new ControlUnit();
        this.ALUOP = new ALU();
        this.ALUCont = new ALUControl();
        this.adder = new Adder();   
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
        else if (instruction.getOpcode() == 13) {
            orImmediate();
        }
        else if (instruction.getOpcode() == 0 && instruction.getFunct() == 39) {
            nor();
        }
        else if (instruction.getOpcode() == 2) {
            jump();
        }
        else if (instruction.getOpcode() == 3) {
            jumpAndLink();
        }
        else if (instruction.getOpcode() == 0 && instruction.getFunct() == 8) {
            jumpRegister();
        }
        else if(instruction.getOpcode() == 4){
            branchEqual();
        }
        else if(instruction.getOpcode() == 0 && instruction.getFunct() == 36){
            and();
        }
        else if(instruction.getOpcode() == 12 ){
            andImmediate();
        }
    }

    public void loadWord() {

        PC.incrementPC();
         
        int Rt = instruction.getRt();
        int Rs = instruction.getRs();
        int Offset = instruction.getImmediate();
        int address = adder.NewAddress(registers.readRegister(Rs),Offset);
        int value = Memory.readData(address,controlUnit.controlSignals(instruction.getOpcode())[3]);

        registers.writeRegister(Rt, value, controlUnit.controlSignals(instruction.getOpcode())[8]);
    }

    public void storeWord() {
        
        PC.incrementPC();
         
        int Rt = instruction.getRt();
        int Rs = instruction.getRs();
        int Offset = instruction.getImmediate();
        int address = adder.NewAddress(registers.readRegister(Rs),Offset);
        int value = registers.readRegister(Rt);
        
        Memory.writeData(address,value,controlUnit.controlSignals(instruction.getOpcode())[6]);
    }

    public void shiftLeftLogical() {

        PC.incrementPC();

        int Rt = instruction.getRt();
        int Shamt = instruction.getShamt();
        int Rd = instruction.getRd();
        int value = registers.readRegister(Rt)<<Shamt;

        registers.writeRegister(Rd,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
    }
    
    public void addition() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        int value=(ALUOP.ALUOutput(registers.readRegister(Rs),registers.readRegister(Rt),ALUCont.getALUControl(controlUnit.controlSignals(instruction.getOpcode())[5],instruction.getFunct(), instruction.getOpcode())));

        registers.writeRegister(Rd,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
    }

    public void additionImmediate() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int immediate=instruction.getImmediate();

        int value=(ALUOP.ALUOutput(registers.readRegister(Rs),immediate,ALUCont.getALUControl(controlUnit.controlSignals(instruction.getOpcode())[5],instruction.getFunct(), instruction.getOpcode())));

        registers.writeRegister(Rt,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
    }
    
    public void setLessThan() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        registers.writeRegister(Rd, ALUOP.ALUOutput(registers.readRegister(Rs),registers.readRegister(Rt),ALUCont.getALUControl(controlUnit.controlSignals(instruction.getOpcode())[5],instruction.getFunct(), instruction.getOpcode())),controlUnit.controlSignals(instruction.getOpcode())[8]);
    }
    public void or(){

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        int value=(ALUOP.ALUOutput(registers.readRegister(Rs),registers.readRegister(Rt),ALUCont.getALUControl(controlUnit.controlSignals(instruction.getOpcode())[5],instruction.getFunct(), instruction.getOpcode())));


        registers.writeRegister(Rd,value,controlUnit.controlSignals(instruction.getOpcode())[8]);

    }

    public void orImmediate() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int immediate=instruction.getImmediate();

        int value=(ALUOP.ALUOutput(registers.readRegister(Rs),immediate,ALUCont.getALUControl(controlUnit.controlSignals(instruction.getOpcode())[5],instruction.getFunct(),instruction.getOpcode())));

        registers.writeRegister(Rt,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
    }

    public void nor() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        int value=~(ALUOP.ALUOutput(registers.readRegister(Rs),registers.readRegister(Rt),ALUCont.getALUControl(controlUnit.controlSignals(instruction.getOpcode())[5],instruction.getFunct(), instruction.getOpcode())));

        registers.writeRegister(Rd,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
    }

    public void jump(){
        PC.setPC(instruction.getTarget());
    }
    public void jumpAndLink(){
        registers.writeRegister(31, adder.NewAddress(PC.getPC(), 4), 1);
        PC.setPC(instruction.getTarget());
    }
    public void jumpRegister(){
        PC.setPC(registers.readRegister(instruction.getRs()));
    }


    public void branchEqual(){

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Offset = instruction.getImmediate();
        
        if (ALUOP.ALUOutput(registers.readRegister(Rs),registers.readRegister(Rt),ALUCont.getALUControl(controlUnit.controlSignals(instruction.getOpcode())[5],instruction.getFunct(),instruction.getOpcode())) == 0) {
             PC.setPC(PC.getPC() + Offset);
        }
    }


    
    public void and(){
        
        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        int value=(ALUOP.ALUOutput(registers.readRegister(Rs),registers.readRegister(Rt),ALUCont.getALUControl(controlUnit.controlSignals(instruction.getOpcode())[5],instruction.getFunct(),instruction.getOpcode())));
        registers.writeRegister(Rd,value,controlUnit.controlSignals(instruction.getOpcode())[8]);

    }

    

    public void andImmediate(){

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int immediate=instruction.getImmediate();

        int value=(ALUOP.ALUOutput(registers.readRegister(Rs),immediate,ALUCont.getALUControl(controlUnit.controlSignals(instruction.getOpcode())[5],instruction.getFunct(),instruction.getOpcode())));
        registers.writeRegister(Rt,value,controlUnit.controlSignals(instruction.getOpcode())[8]);


    }
}
