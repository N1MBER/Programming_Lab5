public class SayException extends RuntimeException{
    private String exc;
    public SayException(String str){
        super(str);
    }
    public void getExc(){
        System.out.print("Так и будем молчать?");
    }
}