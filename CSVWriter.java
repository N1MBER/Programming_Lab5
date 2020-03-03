import java.util.Iterator;
import java.util.LinkedHashSet;

public class CSVWriter {
    protected String writting(LinkedHashSet<Plants> lhs){
        String txt ="";

        return txt;
    }

    protected LinkedHashSet<Plants> getArrPlants(String txt){
        LinkedHashSet<Plants> lhs = new LinkedHashSet<Plants>();
        String str ="\"\"";
        String[] arrstring = txt.split(str);
        arrstring[0] = arrstring[0] + "\"";
        arrstring[arrstring.length-1] = "\"" + arrstring[arrstring.length-1];
        for (int j = 1; j<arrstring.length-1;j++) {
            arrstring[j] = "\"" + arrstring[j] + "\"";
        }
        for (int i =0;i<arrstring.length;i++)
            try {
                lhs.add(objectParse(arrstring[i],arrstring.length));
            }catch (CSVException e) {
                System.out.println("----\nОбнаружена ошибка при парсинге элемента №" + i + e.getMessage()+ "\n----");
            }
//        for (int i = 0; i < arrstring.length;i++) {
//            try {
//                lhs.add(objectParse(arrstring[0] + "\""));
//            } catch (CSVException e) {
//                System.out.println("----\nОбнаружена ошибка при парсинге элемента №" + 0 + e.getMessage()+ "\n----");
//            }
//            try {
//                for (int j = 1;j<arrstring.length-2;j++)
//                    lhs.add(objectParse("\"" + arrstring[i] + "\""));
//            }catch (CSVException e){
//                System.out.println("----\nОбнаружена ошибка при парсинге элемента №" + i + e.getMessage()+ "\n----");
//            }
//            try{
//                lhs.add(objectParse("\"" + arrstring[arrstring.length-1] ));
//            }catch (CSVException e){
//                System.out.println("----\nОбнаружена ошибка при парсинге элемента №" + (arrstring.length-1) + e.getMessage()+ "\n----");
//            }

        return lhs;
    }

    protected Plants objectParse(String str,int l){
        boolean name = false;
        boolean characteristic = false;
        boolean location = false;
        boolean namelocation = false;
        String Pname = null;
        String Pchar;
        Place plantsLocation = null;
        PlantsCharacteristic plantsCharacteristic = null;
        int countOfField = 3;
        while (str.contains(",")){
            if (countOfField<0)
                throw new CSVException("Неверный ввод или недопустимое количество полей");
            else{
                switch (countOfField){
                    case 3:
                        String helpstr = str.substring(0,str.indexOf(","));
//                        if (helpstr.indexOf("\"",2)  != helpstr.length() -1)
//                            System.out.println("sss" );
                        if (helpstr.indexOf("\"") != 0 || helpstr.indexOf("\"",2)  != helpstr.length() -1)
                            throw new CSVException("ошибка ввода поля name");
                        else {
                            Pname = str.substring(str.indexOf("\"") + 1,str.indexOf("\"",2));
                        }
                        if(Pname.matches(".*[0-9].*")){
                            throw new CSVException("в name не может быть цифр");
                        }
                        name = true;
                        countOfField--;
                        if (!str.contains(","))
                            throw new CSVException("поле characteristic отсутствует или указанно неверно");
                        str = str.substring(str.indexOf(","),str.length()-1) + "\"";
                        break;
                    case 2:
                        Pchar = str.substring(str.indexOf(",")+1,str.indexOf(",",2));
                        switch (Pchar){
                            case "Blooming":
                                plantsCharacteristic = PlantsCharacteristic.Blooming;
                                characteristic = true;
                                break;
                            case "Woody":
                                plantsCharacteristic = PlantsCharacteristic.Woody;
                                characteristic = true;
                                break;
                            case "White":
                                plantsCharacteristic = PlantsCharacteristic.White;
                                characteristic = true;
                                break;
                            case "High":
                                plantsCharacteristic = PlantsCharacteristic.High;
                                characteristic = true;
                                break;
                            case "Nothing":
                                plantsCharacteristic = PlantsCharacteristic.Nothing;
                                characteristic = true;
                                break;
                                default:
                                    throw new CSVException("characteristic отсутствует или указан неверно");
                        }
                        if (!str.contains(","))
                            throw new CSVException("поле location отсутствует или указанно неверно");
                        countOfField--;
                        str = str.substring(str.indexOf(",",2),str.length()-1) + "\"";
                        break;
                    case 1:
                        String loc = str.substring(str.indexOf("\"") + 1, str.indexOf("\"",2));
                        if (loc.matches(".*[0-9].*"))
                            throw new CSVException("в поле namelocation не может быть цифр");
                        plantsLocation = new Place(loc);
                        location = true;
                        namelocation = true;
                        countOfField--;
                        str = str.substring(str.indexOf("\"",2),str.length()-1);
                        break;
                        default:
                            break;
                }
            }
        }
        if (!name) throw new CSVException("отсутствует поле name");
        if (!characteristic) throw new CSVException("отсутствует поле characteristic");
        if (!namelocation) throw new CSVException("отсутствует поле namelocation");
        if (!location) throw new CSVException("отсутствует поле location");
        return new Plants(Pname, plantsCharacteristic,plantsLocation);
    }

    private String getPlants(Plants plants) {
        return "\"" + plants.getName() + "\"," + plants.getChar().returnType() + ",\"" + plants.getLocation() + "\"" ;
    }

    protected String getWrittenPlants(LinkedHashSet<Plants> lhsp){
        String str = "";
        Iterator<Plants> iterator = lhsp.iterator();
        for(int i = 0; i < lhsp.size() ; i++){
            str += getPlants(iterator.next()) + "\n";
        }
        return str;
    }
}
