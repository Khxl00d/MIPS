public class ALU {
    public int result;
    public ALU(int result){
        this.result = result;
    }

    public int ALUOutput(int readData1, int readData2, int ALUCont){
        switch(ALUCont){
            case 0: result = readData1 + readData2; break;
            case 1: result = readData1 - readData2; break;
            case 2: result = readData1 & readData2; break;
            case 3: result = readData1 | readData2; break;
            default:
                result = 0;
        }
        return result;
    }
    
    
}

