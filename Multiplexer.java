public class Multiplexer{
    public static int select(int input0, int input1, int control){
        if(control == 0){
            return input0;
        } else {
            return input1;
        }
    }
}