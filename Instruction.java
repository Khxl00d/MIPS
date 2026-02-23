public class Instruction{
    private int opcode;
    private int rs;// first source
    private int rt;// second source if R type / destination if I type
    private int rd;// destination if R type
    private int shamt; //Shift
    private int funct;// R type
    private int immediate;// I type
    private int target;// J type

    //I-Type instruction
    public Instruction(int opcode, int rs, int rt, int immediate){
        this.opcode= opcode;
        this.rs= rs;
        this.rt= rt;
        this.immediate= immediate;
    }

    //J-Type instruction
    public Instruction(int opcode, int target){
        this.opcode= opcode;
        this.target= target;
    }

    //R-Type instruction
    public Instruction(int opcode, int rs, int rt, int rd, int shamt, int funct){
        this.opcode= opcode;
        this.rs= rs;
        this.rt= rt;
        this.rd= rd;
        this.shamt= shamt;
        this.funct= funct;
    }

    public int getOpcode() {
        return opcode;
    }
    public int getRs() {
        return rs;
    }
    public int getRt() {
        return rt;
    }
    public int getRd() {
        return rd;
    }
    public int getShamt() {
        return shamt;
    }
    public int getFunct() {
        return funct;
    }
    public int getImmediate() {
        return immediate;
    }
    public int getTarget() {
        return target;
    }
}