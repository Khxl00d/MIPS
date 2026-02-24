public class ALUControl {

    public int getALUControl(int ALUop, int function) {

        if (ALUop == 2) {
            if (function == 32) {
                return 0;
            }
            else if (function == 34) {
                return 1;
            }
            else if (function == 36) {
                return 2;
            }
            else if (function == 37) {
                return 3;
            }
            else if (function == 42) {
                return 4;
            }
        }
        else if (ALUop == 0) {
            return 0;
        }
        else if (ALUop == 1) {
            return 1;
        }

        return -1;
    }
}
