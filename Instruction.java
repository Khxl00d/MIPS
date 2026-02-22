public class Instruction{
    private int opcode;
    private int rs;// first source
    private int rt;// second source if R type / destination if I type
    private int rd;// destination
    private int shamt; //Shift
    private int funct;// R type
    private int constant;// I type
    private int address;// J type

    public Instruction(int opcode, int rs, int rt, int rd, int shamt, int funct, int constant, int address){
        this.opcode= opcode;
        this.rs= rs;
        this.rt= rt;
        this.rd= rd;
        this.shamt= shamt;
        this.funct= funct;
        this.constant= constant;
        this.address= address;
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
    public int getConstant() {
        return constant;
    }
    public int getAddress() {
        return address;
    }
}