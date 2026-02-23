public class ZeroExtender{
    public String getZeroExtender(int imm){
        return (imm<<16) >>> 16;
    }
}

