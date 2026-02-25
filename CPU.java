public class CPU {
    ProgramCounter PC;
    public CPU() {
        this.PC = new ProgramCounter(0);
    }
    //to be continued...
    public void loadWord(InstructionMemory loadMemory) {
        DataMemory LDM = new DataMemory();
        Instruction loadInst = loadMemory.getInstruction(PC);
        int Rt = loadInst.getRt();
        int address= loadInst.getRs()+loadInst.getImmediate();
        Rt = LDM.readData(address,0);
    }
}