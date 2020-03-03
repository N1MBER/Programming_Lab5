interface ForgetFear{
    default void forgear(Human hu){
        System.out.println(this.toString() + " перестал бояться " + hu);
    }
}
