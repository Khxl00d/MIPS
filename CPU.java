import java.util.Stack;
public class CPU {
    RegisterFile registers;
    ProgramCounter PC;
    DataMemory Memory;
    ControlUnit controlUnit;
    Instruction instruction;
    InstructionMemory instMem;
    Adder adder;
    Multiplexer MUX;
    ALU ALUOP;
    ALUControl ALUCont;
    Stack<Integer> TargetStack = new Stack<>();


    public CPU(RegisterFile sharedRegisters, InstructionMemory sharedMemory) {
        this.PC = new ProgramCounter(0);
        this.registers = sharedRegisters;
        this.instMem = sharedMemory;
        this.Memory = new DataMemory();
        this.controlUnit = new ControlUnit();  
        adder = new Adder();
        MUX = new Multiplexer();
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
        else if (instruction.getOpcode() == 0 && instruction.getFunct() == 39) {
            nor();
        }
        else if (instruction.getOpcode() == 2) {
            jump();
        }
        else if (instruction.getOpcode() == 3) {
            jumpAndLink();
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
        int value = registers.readRegister(Rt)*2<<Shamt;

        registers.writeRegister(Rd,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
    }
    
    public void addition() {

      PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        //control wires
        int RegDst = controlUnit.controlSignals(instruction.getOpcode())[0];
        int ALUSrc = controlUnit.controlSignals(instruction.getOpcode())[7];
        int MemtoReg = controlUnit.controlSignals(instruction.getOpcode())[4];

        int Write_register = MUX.select(Rt,Rd,RegDst);

        //R-type instruction so we assume offset equals 1
        int ALU_Input = MUX.select(registers.readRegister(Rt),1,ALUSrc);

        int value=(ALUOP.ALUOutput(registers.readRegister(Rs),ALU_Input,ALUCont.getALUControl(instruction.getOpcode(),instruction.getFunct())));

        int MemtoReg_mux = MUX.select(value,1,MemtoReg);
        if(MemtoReg_mux==value){
          registers.writeRegister(Write_register,value,controlUnit.controlSignals(instruction.getOpcode())[8]);  
        }
    }

    public void additionImmediate() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int immediate=instruction.getImmediate();

        //control wires
        int RegDst = controlUnit.controlSignals(instruction.getOpcode())[0];
        int ALUSrc = controlUnit.controlSignals(instruction.getOpcode())[7];
        int MemtoReg = controlUnit.controlSignals(instruction.getOpcode())[4];

        //I-type instruction assume that Rd equals 1
        int Write_register = MUX.select(Rt,1,RegDst);

        int ALU_Input = MUX.select(registers.readRegister(Rt),immediate,ALUSrc);

        int value=(ALUOP.ALUOutput(registers.readRegister(Rs),ALU_Input,ALUCont.getALUControl(instruction.getOpcode(),instruction.getFunct())));

        int MemtoReg_mux = MUX.select(value,1,MemtoReg);
        if(MemtoReg_mux==value){
         registers.writeRegister(Write_register,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
        }
    }
    
    public void setLessThan() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        registers.writeRegister(Rd, ALUOP.ALUOutput(registers.readRegister(Rs),registers.readRegister(Rt),ALUCont.getALUControl(instruction.getOpcode(),instruction.getFunct())),controlUnit.controlSignals(instruction.getOpcode())[8]);
    }

     public void or(){

       PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        //control wires
        int RegDst = controlUnit.controlSignals(instruction.getOpcode())[0];
        int ALUSrc = controlUnit.controlSignals(instruction.getOpcode())[7];
        int MemtoReg = controlUnit.controlSignals(instruction.getOpcode())[4];

        int Write_register = MUX.select(Rt,Rd,RegDst);

        //R-type instruction so we assume offset equals 1
        int ALU_Input = MUX.select(registers.readRegister(Rt),1,ALUSrc);

        int value=(ALUOP.ALUOutput(registers.readRegister(Rs),ALU_Input,ALUCont.getALUControl(instruction.getOpcode(),instruction.getFunct())));

        int MemtoReg_mux = MUX.select(value,1,MemtoReg);
        if(MemtoReg_mux==value){
          registers.writeRegister(Write_register,value,controlUnit.controlSignals(instruction.getOpcode())[8]);  
        }
    }

    
    public void orImmediate() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int immediate=instruction.getImmediate();

        //control wires
        int RegDst = controlUnit.controlSignals(instruction.getOpcode())[0];
        int ALUSrc = controlUnit.controlSignals(instruction.getOpcode())[7];
        int MemtoReg = controlUnit.controlSignals(instruction.getOpcode())[4];

        //I-type instruction assume that Rd equals 1
        int Write_register = MUX.select(Rt,1,RegDst);

        int ALU_Input = MUX.select(registers.readRegister(Rt),immediate,ALUSrc);

        int value=(ALUOP.ALUOutput(registers.readRegister(Rs),ALU_Input,ALUCont.getALUControl(instruction.getOpcode(),instruction.getFunct())));

        int MemtoReg_mux = MUX.select(value,1,MemtoReg);
        if(MemtoReg_mux==value){
         registers.writeRegister(Write_register,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
        }
    }
    

    public void nor() {

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        //control wires
        int RegDst = controlUnit.controlSignals(instruction.getOpcode())[0];
        int ALUSrc = controlUnit.controlSignals(instruction.getOpcode())[7];
        int MemtoReg = controlUnit.controlSignals(instruction.getOpcode())[4];

        int Write_register = MUX.select(Rt,Rd,RegDst);

        //R-type instruction so we assume offset equals 1
        int ALU_Input = MUX.select(registers.readRegister(Rt),1,ALUSrc);

        int value=(ALUOP.ALUOutput(registers.readRegister(Rs),ALU_Input,ALUCont.getALUControl(instruction.getOpcode(),instruction.getFunct())));

        int MemtoReg_mux = MUX.select(value,1,MemtoReg);
        if(MemtoReg_mux==value){
          registers.writeRegister(Write_register,value,controlUnit.controlSignals(instruction.getOpcode())[8]);  
        }

    }
    

      public void jump(){
        PC.incrementPC();

        //control wire
        int Jump = controlUnit.controlSignals(instruction.getOpcode())[1];

        // since our simulator is a small project we assume that the apper 4 bits of PC+4 are always 0000 
        // so we skip the concatenation part
        int target = instruction.getTarget() * 4;
        // we are in a J-type instruction so we assume the branch value is 0
        int Jump_mux = MUX.select(0,target ,Jump);
        PC.setPC(Jump_mux);
    }



    
   public void jumpAndLink(){
        PC.incrementPC();

        //store the value of PC+4 into $ra
        registers.writeRegister(31, PC.getPC(), 1);

        // since our simulator is a small project we assume that the apper 4 bits of PC+4 are always 0000 
        // so we skip the concatenation part
        int target = instruction.getTarget() * 4;

        TargetStack.push(target);
        PC.setPC(target);
    }


   public void branchEqual(){

        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Offset = instruction.getImmediate();

       //control wires
        int Branch = controlUnit.controlSignals(instruction.getOpcode())[2];
        int ALUSrc = controlUnit.controlSignals(instruction.getOpcode())[7];
        int Jump = controlUnit.controlSignals(instruction.getOpcode())[1];
        int Zero;

        //returns value of Rt 
        int ALU_Input = MUX.select(registers.readRegister(Rt),Offset,ALUSrc);
        
        //set the value of wire Zero
        if (ALUOP.ALUOutput(registers.readRegister(Rs),ALU_Input,ALUCont.getALUControl(instruction.getOpcode(),instruction.getFunct())) == 0) {
             Zero = 1;
        }else {Zero = 0;}

        int Branch_target =adder.NewAddress(PC.getPC() , Offset*4);

        int Branch_mux = MUX.select(PC.getPC(),Branch_target, Branch & Zero);

        //jump address wont be used so we assume its value is equal to 1
        int Jump_mux = MUX.select(Branch_mux,1,Jump);
        
        //sets the PC value to Branch_target
        PC.setPC(Jump_mux);



    }


    
   public void and(){
        
        PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int Rd=instruction.getRd();

        //control wires
        int RegDst = controlUnit.controlSignals(instruction.getOpcode())[0];
        int ALUSrc = controlUnit.controlSignals(instruction.getOpcode())[7];
        int MemtoReg = controlUnit.controlSignals(instruction.getOpcode())[4];

        int Write_register = MUX.select(Rt,Rd,RegDst);

        //R-type instruction so we assume offset equals 1
        int ALU_Input = MUX.select(registers.readRegister(Rt),1,ALUSrc);

        int value=(ALUOP.ALUOutput(registers.readRegister(Rs),ALU_Input,ALUCont.getALUControl(instruction.getOpcode(),instruction.getFunct())));

        int MemtoReg_mux = MUX.select(value,1,MemtoReg);
        if(MemtoReg_mux==value){
          registers.writeRegister(Write_register,value,controlUnit.controlSignals(instruction.getOpcode())[8]);  
        }

    }

    

   public void andImmediate(){

       PC.incrementPC();

        int Rs=instruction.getRs();
        int Rt=instruction.getRt();
        int immediate=instruction.getImmediate();

        //control wires
        int RegDst = controlUnit.controlSignals(instruction.getOpcode())[0];
        int ALUSrc = controlUnit.controlSignals(instruction.getOpcode())[7];
        int MemtoReg = controlUnit.controlSignals(instruction.getOpcode())[4];

        //I-type instruction assume that Rd equals 1
        int Write_register = MUX.select(Rt,1,RegDst);

        int ALU_Input = MUX.select(registers.readRegister(Rt),immediate,ALUSrc);

        int value=(ALUOP.ALUOutput(registers.readRegister(Rs),ALU_Input,ALUCont.getALUControl(instruction.getOpcode(),instruction.getFunct())));

        int MemtoReg_mux = MUX.select(value,1,MemtoReg);
        if(MemtoReg_mux==value){
         registers.writeRegister(Write_register,value,controlUnit.controlSignals(instruction.getOpcode())[8]);
        }
    }
    
}
