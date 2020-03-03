/**
 * @author Makes
 * This class reads information from file, remakes it in LinkedHashSet and has some functions for managment.
 * The path of file contains in environment variable with name "COLLECTION_PATH".
 */



import java.io.*;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Date;

public class ManagerForPlants {
    /**
     * boolean isWork - values of the need to work
     */
    private boolean isWork = false;
    /**
     *LinkedHashSet LinkedHSPlants - collection of the Plants class object
     */
    private LinkedHashSet<Plants> LinkHSPlants;
    /**
     * Date date - date now
     */
    private Date date;
    /**
     * File sourceFile - file for reading or writing
     */
    private File sourceFile = null;

    public String textOfFile;

    private boolean IsCSV=false;

    protected ManagerForPlants(File fileraw){
        readFile(fileraw);
        sourceFile = fileraw;
    }

    /**
     * This method read information from a file
     * @param file - file for a reading
     * @return - text of the file
     */
    public String readCSV(File file) {
        String txt = "";
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                txt += scanner.nextLine();
                //System.out.println(txt);
            }
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                saveAndExit();
            }));
            System.out.println("----\nФайл считан\n----");
        } catch (IOException e){
            System.out.println("----\nОшибка чтения файла.\nРабота невозможна.\n----");
            isWork = false;
            }
        try{
            FileWriter fileWrite = new FileWriter(file);
            fileWrite.write(txt);
        }catch (IOException e){
            e.getMessage();
        }
        //AfterReadSave(txt);
        this.textOfFile = txt;
        return txt;
    }

//    protected void AfterReadSave(String txt){
//        try {
//            FileWriter jj = new FileWriter(sourceFile);
//            try (BufferedWriter bufferedWriter = new BufferedWriter(jj)) {
//                bufferedWriter.write(txt);
//            }catch (IOException j){
//                j.getMessage();
//            }
//        }catch (IOException e){
//            e.getMessage();
//        }
//    }

    protected void BeforeSaveDelete(File file){
        String txt = "";
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                txt += scanner.nextLine();
            }
        } catch (IOException e){
        }
    }
    /**
     * This method analysis information about file and send file to method JSONtoLHS
     * @param impFile - analyzed file
     */
    public void readFile(File impFile){
        try{
            if(!(impFile.isFile()))
                throw new FileNotFoundException("----\nПуть ведёт не к файлу.\nРабота невозможна.\n----");
            if(!(impFile.exists()))
                throw new FileNotFoundException("----\nПо указанному пути файл не найден.\nДальнейшая работа невозможна.\n----");
            if (!(impFile.canRead()))
                throw new SecurityException("----\nНет прав на чтение.\nДальнейшая работа невозможна.\n----");
            CSVtoLHS(readCSV(impFile));
        }catch (FileNotFoundException | SecurityException e){
            System.out.println("----\nОшибка чтения файла.\n----");
            isWork = false;
        }
    }

    public void CSVtoLHS(String infoCSV){
        CSVWriter csvWriter = new CSVWriter();
        LinkHSPlants = csvWriter.getArrPlants(infoCSV);
        if (LinkHSPlants.size()>0)
            IsCSV =true;
        System.out.println("----\nЭлементов было добавлено: " + LinkHSPlants.size() + "\n----");
        date = new Date();
        isWork = true;
    }
//
//    /**
//     * This method send information to parser for conversation
//     * @param infoJSON - information about collection
//     */
//    public void JSONtoLHS(String infoJSON){
//        JSONParser jsonParser = new JSONParser();
//        LinkHSPlants = jsonParser.getArrPlants(infoJSON);
//        System.out.println("----\nЭлементов было добавлено: " + LinkHSPlants.size() + "\n----");
//        date = new Date();
//        isWork = true;
//    }

    /**
     * This method return values of the need to work
     * @return - if true machine on, if false machine off
     */
    protected boolean isWork(){
        return  isWork;
    }

    /**
     * This method exit from program
     */
    protected void exit(){
        isWork = false;
        System.out.println("Выход...");
    }

//    protected String JSONReader(File file){
//        String txt = "";
//        String str;
//        try(Scanner scan = new Scanner(file)){
//            while ((str = scan.next()) != "EOF"){
//                txt +=" "  + str;
//            }
//            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//                saveAndExit();
//            }
//            ));
//            System.out.println("----\nФайл успешно записан.\n----");
//        }catch (FileNotFoundException e){
//            System.out.println("----\nОшибка чтения файла.\n----" + e.getMessage());
//            isWork = false;
//        }
//        return txt;
//    }

    /**
     * This method save information about collection to file or if file missing, create new file
     */
    protected void saveAndExit(){
        String format = sourceFile.getName().substring(sourceFile.getName().indexOf(".") +1);
        if (!format.equals("csv"))
            save();
            if (!format.equals("csv") | sourceFile == null | !(sourceFile.canWrite())) {
                System.out.println("----\nФайл не существует или в него нельзя записать данные или он не соответствует необходимому для записи формату.\n----");
                String newFile = "CSVObject" + new Integer((int)(Math.random()*100)).toString();
                String directory = System.getProperty("user.dir");
                String separator = System.getProperty("file.separator");
                sourceFile = new File(directory + separator + newFile + ".csv");
                if (!sourceFile.exists() | !sourceFile.isFile()) {
                    try {
                         if (sourceFile.createNewFile()) {
                            System.out.println("----\nНовый файл успешно создан.\nИмя файла:" + newFile +"\n----");
                            AskSave();
                         }
                    } catch (IOException e) {
                      System.out.println("----\nОшибка при создании файла:\n----" + e.getMessage());
                    }
                } else {
                    AskSave();
                }
            } else {
            AskSave();
        }
    }

    protected void AskSave(){
        try {
            if (IsCSV)
                save();
            else {
                FileWriter fileWritercsv = new FileWriter(sourceFile);
                try (BufferedWriter bufferedWriter = new BufferedWriter(fileWritercsv)) {
                    bufferedWriter.write("");
                    System.out.println("----\nКоллекция пуста\n---- ");
                } catch (IOException e) {
                    System.out.println("----\nОшибка записи\n----");
                }
            }
        }catch (IOException e){
            System.out.println("----\nОшибка записи\n----" + e.getMessage());
        }
    }

//    protected void extraSave(){
//    try {
//        BeforeSaveDelete(sourceFile);
//        FileWriter fileWriterCSV = new FileWriter(sourceFile);
//        try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriterCSV)) {
//            if(LinkHSPlants != null) {
//                if (IsCSV) {
//                    CSVWriter csvWriter = new CSVWriter();
//                    bufferedWriter.write(csvWriter.getWrittenPlants(LinkHSPlants));
//                } else {
//                    //System.out.println(textOfFile);
//                    bufferedWriter.write(textOfFile);
//                }
//            }
//            else{
//                bufferedWriter.write(textOfFile);
//            }
//        }catch(IOException e ){
//            System.out.println("----\nОшибка записи файла\n----" + e.getMessage());
//        }
//    }catch(IOException e){
//        System.out.println("----\nОшибка записи файла\n----" + e.getMessage());
//    }
//}
    /**
     * This method save information about collection to file
     */
    protected void save(){
        try {
            BeforeSaveDelete(sourceFile);
            FileWriter fileWriterCSV = new FileWriter(sourceFile);
            try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriterCSV)) {
                if(LinkHSPlants != null) {
                    if (IsCSV) {
                        CSVWriter csvWriter = new CSVWriter();
                        bufferedWriter.write(csvWriter.getWrittenPlants(LinkHSPlants));
                        System.out.println("----\nКоллекция сохранена в файле: " + sourceFile.getAbsolutePath() + "\n----");
                    } else {
                        //System.out.println(textOfFile);
                        bufferedWriter.write(textOfFile);
                        System.out.println("----\nСохранено изначальное значение файла\n----");
                    }
                }
                else{
                    bufferedWriter.write(textOfFile);
                }
                System.out.println("----\nРабота над данной коллекцией завершена.\n----");
            }catch(IOException e ){
                System.out.println("----\nОшибка записи файла\n----" + e.getMessage());
            }
        }catch(IOException e){
            System.out.println("----\nОшибка записи файла\n----" + e.getMessage());
        }
    }


    /**This method show information about this collection.
     */
    protected void info(){
        System.out.println("----\n Информация о колекции:\n----\n" + "\t Тип: LinkedHashSet\n" + "\tСодержимое: экземпляры класса Plants\n" + "\tДата: " + date + "\n\tРазмер: " + LinkHSPlants.size() + "\n----");
    }

    /**This method show elements in collection.
     */
    protected void show(){
        System.out.println("----\n" + LinkHSPlants + "\n----");
    }

    /**This method add new elements in collection.
     * @param additionalPlants - Plants.class object, which you want to add.
     */
    protected void add(Plants additionalPlants){
        LinkHSPlants.add(additionalPlants);
        IsCSV = true;
        System.out.println("----\nЭлемент добавлен.\n----");
    }

    /**This method add new elements in collection, if this element(object)
     * greater than the maximum element in the collection.
     * @param additionalPlants - Plants.class object, which you want to add.
     */
    protected void add_if_max(Plants additionalPlants){
        Iterator<Plants> iterator = LinkHSPlants.iterator();
        Comparator<Plants> comparator = Comparator.naturalOrder();
        Plants max = iterator.next();
        while(iterator.hasNext()){
            if (comparator.compare(max,iterator.next()) == -1)
                max = iterator.next();
        }
        if (comparator.compare(max, additionalPlants) == 1) {
            LinkHSPlants.add(additionalPlants);
            System.out.println("----\nЭлемент добавлен\n----");
        }
    }

    /**This method remove all elements, which lower that element.
     * @param removablePlants - Plants.class object, which will be compare with elements in collection.
     */
    protected void remove_lower(Plants removablePlants){
        Iterator<Plants> iterator = LinkHSPlants.iterator();
        Comparator<Plants> comparator = Comparator.naturalOrder();
        int count = 0;
        while (iterator.hasNext()){
            Plants element = iterator.next();
            if (comparator.compare(element,removablePlants) == -1){
                iterator.remove();
                count++;
            }
        }
        System.out.println("----\nКоличество удалённых элементов: " + count + "\n----");
    }

    /**This method remove all elements, which lower that element.
     * @param removablePlants - Plants.class object, which will be compare with elements in collection.
     */
    protected void remove_greater(Plants removablePlants){
        Iterator<Plants> iterator = LinkHSPlants.iterator();
        Comparator<Plants> comparator = Comparator.naturalOrder();
        int count = 0;
        while (iterator.hasNext()){
            Plants element = iterator.next();
            if (comparator.compare(element,removablePlants) == 1){
                iterator.remove();
                count++;
            }
        }
        System.out.println("----\nКоличество удалённых элементов: " + count + "\n----");
    }

    /**This method remove element in collection equal to parametr.
     * @param plants - Plants.class object,which will be compared with element in collection.
     */
    protected void remove(Plants plants) {
        Iterator<Plants> iterator = LinkHSPlants.iterator();
        Comparator<Plants> comparator = Comparator.naturalOrder();
        int count = 0;
        int HowMany = 0;
        boolean delete = false;
        while (iterator.hasNext()) {
            Plants element = iterator.next();
            //System.out.println(comparator.compare(plants,element));
            if (comparator.compare(plants, element) == 0) {
                iterator.remove();
                count++;
                delete = true;
                HowMany++;
            }
        }
            if (HowMany == 1){
                System.out.println("----\nЭлемент успешно удалён.\n----");
            }else
                if (HowMany > 1)
                    System.out.println("----\nЭлементы успешно удалены.\n----");
            if (!delete)
                System.out.println("----\nЭлемент не найден.\n----");
        }

//    protected void remove_all(Plants plants){
//        Iterator<Plants> iterator = LinkHSPlants.iterator();
//        int count = 0;
//        while (iterator.hasNext()) {
//            Plants pl = iterator.next();
//            if (comparator.compare(plants, pl) != 0) {
//                iterator.remove();
//                count++;
//            }
//        }
//    }
}
