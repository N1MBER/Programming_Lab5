public enum Active{
    ShakeAHead("Качать головой"),
    Shrugged("Пожимать плечами"),
    Firk("Фыркнуть"),
    Cleaning("Мести пол"),
    Stand("Стоять"),
    Fly("Летать"),
    WavePaws("Размахивать лапами"),
    FeelExcitemente("Чувствовать волнение"),
    Swim("Плыть"),
    Say("Говорить");
    private final String Type;
    Active(String act){
        Type = act;
    }
    @Override
    public String toString(){
        return Type;
    }
}