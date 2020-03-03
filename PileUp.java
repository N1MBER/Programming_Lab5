interface PileUp{
    default void pileup(Place p){
        System.out.println(this.toString() + " сгрудился около " + p);
    }
}
