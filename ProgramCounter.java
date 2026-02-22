public class ProgramCounter{
    private int PC;
    
    public ProgramCounter(int firstInstructionAdd){
        this.PC= firstInstructionAdd;
    }
    public int getPC(){
        return this.PC;
    }
    public void setPC(int updatedPC){
        this.PC = updatedPC;
    }
    public void incrementPC(){
        this.PC += 4;
    }
}