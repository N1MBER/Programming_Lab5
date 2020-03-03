public class TreeException extends Exception {
    public TreeException(String n){
        super(n);
    }
    public void getExc(){
        System.out.println("Ствол не изгибается - история не продолжается");
    }
}
