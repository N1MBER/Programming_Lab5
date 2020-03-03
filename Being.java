abstract class Being implements Getname, DoAny, Notice, ToAttach{

    private String name;
    private Active LastActive;
    private Place place;
    public void see(Object p){
        System.out.println(this.toString() + " Смотрит на " + p.toString());
    }
    public String getName(){
        return this.name;
    }
    public void toDo(Active a) throws ToDoException{
        if (a != null) {
            System.out.println(this.getName() + " совершает действие " + a);
            this.LastActive=a;
        }
        else throw new ToDoException("Действие не действительно.");
    }
    public void notice(Active a, Object p, Place b){
        this.place=b;
        System.out.println(this.getName() + " заметил, что " + p.toString() + " совершает действие " + a + " в " + b.getName());
    }
    @Override
    public String toString(){
        return name;
    }
    public Being(String n){
        this.name = n;
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
    public int hashCode() {
        final float kek = 14.88f;
        int sis = 17;
        sis = ((int) kek * sis) / 3 + hashCode();
        return sis;
    }
}

