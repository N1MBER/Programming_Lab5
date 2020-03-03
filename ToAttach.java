interface ToAttach{
    default void attach(String n,Object p){
        class SomethingObject implements Getname{
            private String name;
            private Object place;
            public SomethingObject(String n){
                this.name = n;
            }
            public String getName(){
                return this.name;
            }

            public void setPlace(Object place) {
                this.place = place;
            }
        }
        SomethingObject a = new SomethingObject(n);
        System.out.println(this.toString() + " прикрепили " + a.getName() + " к " + p.toString());
        a.setPlace(p);
    }
}
