public class ALUControl {

    public int getALUControl(int ALUop, int function, int opCode) {

        //R-type
        if (ALUop == 2) {
            if (function == 32) { //add
                return 0;
            }
            else if (function == 34) { //sub
                return 1;
            }
            else if (function == 36) { //and
                return 2;
            }
            else if (function == 37) { //or
                return 3;
            }
            else if (function == 42) { //slt
                return 4;
            }
            else if (function == 39){ //nor
                return 5;
            }
        }
        //I-type
        else if (opCode == 8){ //addi
            return 0;
        }
        else if (opCode == 12){ //andi
            return 2;
        }
        else if (opCode == 13){ //ori
            return 3;
        }
        //sw lw
        else if (ALUop == 0) {
            return 0;
        }
        //beq
        else if (ALUop == 1) {
            return 1;
        }

        return -1;
    }
}

