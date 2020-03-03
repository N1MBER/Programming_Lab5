public class Ant extends Insect implements ForgetFear, PileUp, ToAttach{
    private String charecter;
    public Ant(String n,String ch){
        super(n); this.charecter=ch;
    }
    public String getCharecter(){
        return this.charecter;
    }
}
