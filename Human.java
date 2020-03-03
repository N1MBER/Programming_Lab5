public class Human extends Being implements Say, Meet, ForgetFear, PileUp{
    private Place NowLocation;
    private Insect MeetInc;
    public Human(String n){
        super(n);
    }
    public void say(String n){
        if (n == null) throw new SayException("Она немая");
        System.out.println(this.getName() + " сказала " + n);
    }
    public void meet(Ant a, Place p, Place pl){
        this.MeetInc = a;
        System.out.println(this.getName() + " повстречала " + a.getCharecter() +" " + a.getName() + " на " + p.getName() + " в " + pl.getName());
        this.NowLocation=pl;
    }
}
