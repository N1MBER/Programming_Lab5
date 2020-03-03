import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;

public class ConsoleManager {

    private Scanner scanner = null;
    private ManagerForPlants MFP = null;
    /**
     * boolean needWork - values to determinate the need for work of program
     */
    private boolean needWork = false;

    /**
     * This method get information about file path
     * @return - path of the file
     */
    private String getFilePath(){
        String path = System.getenv("PATH_CSV");
        System.out.println(System.getProperty("user.dir"));
        if (path == null){
            System.out.println("----\nПуть через переменную окружения PATH_CSV не указан\nНапишите адрес вручную(в консоль)\n----");
            return scanner.nextLine();
        } else {
            return path;
        }
    }


    public ConsoleManager(){
        try {
            scanner = new Scanner(System.in);
            String pathToFile = getFilePath();
            if (pathToFile == null)
                System.out.println("----\nПуть не указан, дальнейшая работа не возможна.\n----");
            else {
                MFP = new ManagerForPlants(new File(pathToFile));
            }
            if (MFP.isWork()){
                beginWork();
                if (needWork){
                    help();
                    System.out.println("----\nВведите команду.\n----");
                    scanAndExit();
                }
                else MFP.saveAndExit();
            }
        }catch (NoSuchElementException e){
            e.getMessage();
        }
    }

    /**
     * This method learns about the need of include interactive mod
     */
    private void beginWork(){
        System.out.println("----\nВключить интерактивный режим?\nВведите \"да\" \"нет\".\n----");
        switch (scanner.nextLine()){
            case "да":
                needWork = true;
                System.out.println("----\nЧтобы выйти, напишите \"exit\".\n----");
                break;
            case "ДА":
                needWork = true;
                System.out.println("----\nЧтобы выйти, напишите \"exit\".\n----");
                break;
            case "нет":
                MFP.saveAndExit();
                break;
            case "НЕТ":
                MFP.saveAndExit();
                break;
                default:
                    System.out.println("----\nВведите \"да\" \"нет\".\n----");
                    beginWork();
        }
    }

    /**
     * This method print information about all accessible commands
     */
    private void help(){
        System.out.println("----\nСписок доступных команд:\n" +
                "1. help: показать доступные команды.\n" +
                "2. remove {element}: удалить элемент из коллекции по его значению.\n" +
                "3. info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.), элемент должен быть введен в формате json.\n" +
                "4. remove_lower {element}: удалить из коллекции все элементы, меньше заданного, элемент должен быть введён в формате json.\n" +
                "5. add {element}: добавить новый элемент в коллекцию, элемент должен быть введён в формате json.\n" +
                "6. add_if_max {element}: добавить новый элемент в коллекцию, если он превышает максимальный, элемент должен быть введён в формате json.\n" +
                "7. remove_greater {element}: удалить из коллекции все элементы, превышающие заданный, элемент должен быть введён в формате json.\n" +
                "8. show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении.\n" +
                "9. exit: закончить работу.\n" +
                "Пример элемента ввода:\n" +
                "{\n" +
                "  \"name\": \"Ромашка\",\n" +
                "  \"characteristic\": \"White\",\n" +
                "  \"location\":{\n" +
                "  \t\"namelocation\": \"Поле\"\n" +
                "  }\n" +
                "}\n" +
                "----");
    }

    /**
     * This method scan text which write user and send commands to execute
     */
    private void scanAndExit(){
        while (needWork){
            String command[] = new String[50];
            command[0] = scanner.nextLine();
            String helpcom[] = command[0].split(" ",2);
            switch (helpcom[0]){
                case "help":
                    if (helpcom.length > 1) {
                        if (helpcom[1].matches(" +") | helpcom[1].matches("")) {
                            help();
                        } else {
                            System.out.println("----\nДанная команда не должна содержать аргументов.\n----");
                        }
                    }else
                        help();
                    break;
                case "remove":
                    try {
                        if (getBracket(helpcom[1],'}') == 2)
                            MFP.remove(getElements(helpcom[1]));
                        else
                            MFP.remove(getElements(scanElements(helpcom[1])));
                        System.out.println("----");
                    }catch (JSONException e){
                        System.out.println("----\nОбнаружена ошибка при парсинге элемента: " + e.getMessage() + "\n----");
                    }
                    break;
                case "info":
                    if (helpcom.length > 1){
                        if (helpcom[1].matches(" +") | helpcom[1].matches("")){
                            MFP.info();
                        }else
                            System.out.println("----\nДанная команда не должна содержать аргументов.\n----");
                    }else{
                        MFP.info();
                    }
                    break;
                case "remove_lower":
                    try {
                        if (getBracket(helpcom[1],'}') == 2)
                            MFP.remove_lower(getElements(helpcom[1]));
                        else
                            MFP.remove_lower(getElements(scanElements(helpcom[1])));
                        System.out.println("----");
                    }catch (JSONException e){
                        System.out.println("----\nОбнаружена ошибка при парсинге элемента: " + e.getMessage() + "\n----");
                    }
                    break;
                case "remove_greater":
                    try {
                        if (getBracket(helpcom[1],'}') == 2)
                            MFP.remove_greater(getElements(helpcom[1]));
                        else
                             MFP.remove_greater(getElements(scanElements(helpcom[1])));
                        System.out.println("----");
                    }catch (JSONException e){
                        System.out.println("----\nОбнаружена ошибка при парсинге элемента: " + e.getMessage() + "\n----");
                    }
                    break;
                case "add":
                    try {
                        if (getBracket(helpcom[1],'}') == 2)
                            MFP.add(getElements(helpcom[1]));
                        else
                            MFP.add(getElements(scanElements(helpcom[1])));
                        System.out.println("----");
                    }catch (JSONException e){
                        System.out.println("----\nОбнаружена ошибка при парсинге элемента: " + e.getMessage() + "\n----");
                    }
                    break;
                case "add_if_max":
                    try {
                        if (getBracket(helpcom[1],'}') == 2)
                            MFP.add_if_max(getElements(helpcom[1]));
                        else
                            MFP.add_if_max(getElements(scanElements(helpcom[1])));
                        System.out.println("----");
                    }catch (JSONException e){
                        System.out.println("----\nОбнаружена ошибка при парсинге элемента: " + e.getMessage() + "\n----");
                    }
                    break;
                case "show":
                    if (helpcom.length > 1){
                        if (helpcom[1].matches(" +") | helpcom[1].matches("")) {
                            MFP.show();
                        }else {
                            System.out.println("----\nДанная команда не должна содержать аргументов.\n----");
                        }
                    }else
                        MFP.show();
                    break;
                case "exit":
                    needWork = false;
                    if (helpcom.length > 1){
                        if (helpcom[1].matches(" +") | helpcom[1].matches("")){
                            MFP.exit();
                        }else{
                            System.out.println("----\nДанная команда не должна содержать аргументов.\n----");
                        }
                    }else
                        MFP.exit();
                    break;
                    default:
                        System.out.println("Неизвестная команда.\n----");
                        break;

            }
        }

    }

    /**
     * This method scan entered elements and implements multiline input
     * @param helpcom - string for help in determining the input
     * @return - text which user enter
     */
    private String scanElements(String helpcom){
        int rightbrecket = 0;
        int leftbrecket = 0;
        String command[] = new String[15];
        int k = 0;
        String plz = helpcom;
//        for(int j=0; j < 15;j++){
//            command[k] = scanner.nextLine();
//            rightbrecket += getBracket(command[k],'}');
//            plz += command[k];
//            k++;
//            if (rightbrecket == 2)
//                break;
//        }
        while(leftbrecket != rightbrecket ) {
            command[k] = scanner.nextLine();
            command[k].trim();
            rightbrecket += getBracket(command[k],'}');
            leftbrecket += getBracket(command[k],'{');
//            System.out.print(command[k]);
//            System.out.print(rightbrecket);
            plz += command[k];
            k++;
            if (k == 11){
                System.out.println("----\nОшибка ввода элемента.\n----");
                break;
            }
//            if (rightbrecket == 2)
//                break;
        }
        return plz;
    }

    /**
     * This method return Plants class object based on enter data
     * @param txt - information about objects in JSON
     * @return - Plants class object
     */
    private Plants getElements(String txt){
        int countright = getBracket(txt,'}');
        int countleft = getBracket(txt, '{');
        while (!(countleft == countright)){
            String str = scanner.nextLine();
            countright += getBracket(str , '}');
            countleft += getBracket(str, '{');
            txt += str;
        }
        txt = txt.trim();
        JSONParser jsonParser = new JSONParser();
            return jsonParser.objParse(txt);
    }

    /**
     * This method return number of the char
     * @param str - text for analysis
     * @param bracket - char for search
     * @return
     */
    private int getBracket(String str,char bracket){
        int count = 0;
        for(char c : str.toCharArray()){
            if (c == bracket)
                count++;
        }
        return count;
    }
}
