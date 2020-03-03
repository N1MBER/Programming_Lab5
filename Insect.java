public class Insect implements DoAny,Spinning, Getname, Notice{
    private String name;
    private Place WhereInception;
    private Active LastActive;
    public Insect(String n){
        this.name = n;
    }

    public void Spin(Bee b, Wasp w, Plants.Flower f){
        f.NearSpin(w,b);
        System.out.println(b.getName() + " вместе с " + w.getName() + " кружатся вокруг " + f.getCharicter() + " " + f.getName());
    }
    public String getName(){
        return this.name;
    }
    public void toDo(Active a) throws ToDoException{
        if(a != null) {
            System.out.println(this.getName() + " совершает действие " + a);
            this.LastActive=a;
        }
        else throw new ToDoException("Действие не действительно");
    }
    public void notice(Active a, Object p, Place b){
        this.WhereInception=b;
        System.out.println(this.getName() + " заметил, что " + p.toString() + " совершает действие " + a + " в " + b.getName());
    }

    @Override
    public int hashCode()
    {
        final float kek = 14.88f;
        int sis = 17;
        sis = ((int)kek * sis)/3 + hashCode();
        return sis;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.toString() == this.toString()) {
            return true;
        }
        else
            return false;
    }
    @Override
    public String toString(){
        return this.name;
    }
    public class Spider extends Insect {
        public Spider(String n){
            super(n);
        }
    }
    public Spider createSpider(String n){
        return new Spider(n);
    }
}
