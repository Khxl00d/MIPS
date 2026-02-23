public class SignExtender {

   public int signExtend(int imm) {
    return (imm << 16) >> 16;
}
}

