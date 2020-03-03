public class Tree extends Plants {
    private boolean trunk;
    private Human inTree;
    public Tree(String n, PlantsCharacteristic ch, boolean yep){
        super(n,ch,null);
        trunk = yep;
    }
    public String toString(){
        return super.getName();
    }
    public void education(Branch v, Human hu) throws TreeException{
        if(trunk==true && v.getCurv() == true){
            System.out.println("Ствол красиво изогнулся, образовав вместе с веткой колыбельку, в которой может быть " + hu.getName());
            inTree=hu;
        }else throw new TreeException("Ствол не изгибается - история не продолжается");
    }
}
