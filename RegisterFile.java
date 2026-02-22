public class RegisterFile {
    private int[] register;

    public RegisterFile() {
        this.register = new int[32];
        register[0] = 0;
    }

    public int readRegister(int reg) {
            return register[reg];
    }

    public void writeRegister(int reg, int value, boolean regWrite) {
        if (reg != 0 && regWrite) {
            register[reg] = value;
        }
    }
    public void displayReg() {
        for(int i = 0; i < 32; i++) {
            System.out.println(register[i]);
        }
    }
}