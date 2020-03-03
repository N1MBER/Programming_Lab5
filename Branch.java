public class Branch extends Plants{
    public Branch(String n, PlantsCharacteristic ch){
        super(n, ch, null);
    }
    boolean curv = false;
    public void bend(){
        System.out.println("Ствол красиво изогнулся.");
        curv=true;
    }
    public boolean getCurv(){
        return this.curv;
    }
}
