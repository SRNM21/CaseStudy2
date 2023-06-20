import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

/**
 * The {@code GSystem} class is the main system of the program, it includes creating and loading files,
 * modifying system print, and system commands. 
 * 
 * <p>
 * {@code Note:} This class is Windows biased.
 * </p>
 * 
 *  @author Neelian Mata
 *  @author Jarius Maui Pineda
 *  @author Niño Greg Gregorio
 *  @since 1.0
 */
public class GSystem
{
    // ANSI color code
    protected final String RES = "\u001B[0m";
    protected final String BLA = "\033[1;90m";
    protected final String RED = "\033[1;91m";
    protected final String GRE = "\033[1;92m";
    protected final String YEL = "\033[1;93m";
    protected final String BLU = "\033[1;94m";
    protected final String PUR = "\033[1;95m";
    protected final String CYA = "\033[1;96m";
    protected final String WHI = "\033[1;97m";

    // get systems' operating system
    private final String os = System.getProperty("os.name");
    
    // files 
    private final File itemData = new File("ITEMS.txt");
    private final File OrderReports = new File("REPORTS.txt");

    // contents
    protected static ArrayList<ArrayList<Object[]>> ORIGINAL_ITEMS = new ArrayList<>()
    {{
        add(new ArrayList<>()
        {{
            add(new Object[] {"Burger Steak", 99.20, 100});
            add(new Object[] {"Fried Chicken", 80.30, 100}); 
            add(new Object[] {"Spaghetti", 90.20, 100});
        }});

        add(new ArrayList<>()
        {{
            add(new Object[] {"Fried Chicken Sandwich", 50.80, 100});
            add(new Object[] {"Vegan Sandwich", 40.10, 100}); 
            add(new Object[] {"Egg Sandwich", 29.99, 100});
        }});

        add(new ArrayList<>()
        {{
            add(new Object[] {"Water", 10.00, 100});
            add(new Object[] {"Iced Tea", 15.30, 100}); 
            add(new Object[] {"Coca Cola", 20.20, 100});
        }});
    }};
    
    // function to run the system in command prompt
    protected void startSystem() 
    {
        if (os.contains("Windows"))
        {
            try
            {          
                Runtime.getRuntime().exec(new String[]{"cmd", "/k", "javac", "MainProcess.java"});
                Thread.sleep(2000);
                Runtime.getRuntime().exec(new String[]{"cmd", "/k", "start", "java", "MainProcess"});
            }
            catch (Exception e) 
            { 
                e.printStackTrace(); 
            }
        }
    }

    // function to clear the screen in command prompt
    protected void cls()
    {   
        // execute "cls" command if the system is on Windows command prompt
        if (os.contains("Windows"))
        {
            try 
            { 
                new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start()
                    .waitFor(); 
            } 
            catch (Exception e) 
            { 
                e.printStackTrace(); 
            }   
        }
        // otherwise, use flush
        else
        {
            System.out.print("\033[H\033[2J");
            System.out.flush();  
        }
    }

    // function to pause in command prompt
    protected void pause() 
    {
        // execute "pause" command if the system is on Windows command prompt
        if (os.contains("Windows"))
        {
            try 
            { 
                new ProcessBuilder("cmd", "/c", "pause")
                    .inheritIO()
                    .start()
                    .waitFor(); 
            } 
            catch (Exception e) 
            { 
                e.printStackTrace(); 
            }   
        }
        // otherwise, use user input
        else
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            line(WHI("Press any key..."));
    
            try 
            { 
                input.readLine(); 
            } 
            catch (Exception e) 
            { 
                e.printStackTrace();
            }
        }
    }

    // loading animation
    protected void loadingAnim(String x) 
    {
        // run animation if the system is on Windows command prompt
        if (System.getProperty("os.name").contains("Windows"))
        {
            StringBuilder loadingBar = new StringBuilder(fill(50, ' '));
            String loadingTitle = WHI(x);

            for (int i = 0, j = 2; i < 50; i++, j += 2) 
            {
                loadingBar.setCharAt(i, '#');
                String loadBar = GRE(loadingBar.toString());

                cls();

                line(fill(15, '\n'));

                if (x.equals("Restocking..."))
                { 
                    printHeader("supplier");
                    printSupplier();
                }

                printLine(49, loadingTitle);
                printLine(49, WHI("[") + loadBar + WHI("] " + j + "%"));

                try 
                {
                    Thread.sleep(50);
                } 
                catch (InterruptedException e) 
                {
                    e.printStackTrace();
                }
            }
        }
        // otherwise, pause in printed "loading" for 2 sec while retrieving/creating the contents of the system
        else 
        {
            cls();
            fill(15, '\n');
            printLine(49, WHI("Loading, Please Wait..."));

            try 
            { 
                Thread.sleep(2000); 
            } 
            catch (InterruptedException e) 
            { 
                e.printStackTrace(); 
            }
        }    

        line("\n");
    }

    // retrieving/creating the contents of the system
    protected void load() 
    {
        loadingAnim("Loading...");

        // check the files if they exist
        checkReports();

        if (foundFile()) printLine(64, WHI("FILE IS SUCCESFULLY LOADED!"));
        else printLine(61, WHI("NEW FILE IS SUCCESSFULLY CREATED!"));
        
        line(fill(15, '\n'));
    }
    
    // system's modified print
    protected void prints(int spaces, String x)
    {
        StringBuilder line = new StringBuilder(x);
        for (int i = 0; i < spaces; i++) line.insert(0, ' ');
        System.out.print(line);
    }

    // system's modified print line 
    protected void printLine(int spaces, String x)
    {
        StringBuilder line = new StringBuilder(x);
        for (int i = 0; i < spaces; i++) line.insert(0, ' ');
        System.out.println(line);
    }

    // system's modified print format
    protected void printFormat(int spaces, String format, Object ... args)
    {        
        for (int i = 0; i < spaces; i++) System.out.print(' ');
        System.out.printf(format, args);
    }

    // function to generate line with specified character
    protected String fill(int count, char c) 
    {
        StringBuilder filler = new StringBuilder();
        for (int i = 0; i < count; i++) filler.append(c);

        return filler.toString();
    }
    
    // function to print pointer 
    protected void pointer() { prints(55, GRE(">> ")); }
    
    // function to print new line 
    protected void line(String x) { System.out.println(x); }

    // function to print blank line 
    protected void line() { System.out.println(); }

    // return color black string
    protected String BLA(String x) { return BLA + x + WHI; }

    // return color red string
    protected String RED(String x) { return RED + x + WHI; }

    // return color green string
    protected String GRE(String x) { return GRE + x + WHI; }
    
    // return color yellow string
    protected String YEL(String x) { return YEL + x + WHI; }

    // return color blue string
    protected String BLU(String x) { return BLU + x + WHI; }
    
    // return color purple string
    protected String PUR(String x) { return PUR + x + WHI; }
    
    // return color cyan string
    protected String CYA(String x) { return CYA + x + WHI; }

    // return color white string
    protected String WHI(String x) { return WHI + x + WHI; }

    // function that will split long String and return it as ArrayList with specified size Strings
    protected ArrayList<String> wrapText(String x, int column)
    {
        // split words
        String[] words = x.split(" ");
        ArrayList<String> wrapped = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        
        // build String with specified size 
        for (String word : words) 
        {
            line.append(word + " ");

            if (line.length() + word.length() > column)
            {
                wrapped.add(line.toString());
                line.setLength(0);
            }
        }

        if (!line.isEmpty()) wrapped.add(line.toString());

        return wrapped;
    }
    
    // function to generate unique reference number
    protected String generateRefNum()
    {
        StringBuilder sb = new StringBuilder();
        boolean exist = false; 

        do 
        { 
            // generate random reference number 
            while (sb.length() < 14)
            {
                int randomNumber = new Random().nextInt(36);

                if (sb.length() == 4 || sb.length() == 9) sb.append('-');
                
                if (randomNumber < 26) sb.append((char) (randomNumber + 65));
                else sb.append((char) (randomNumber - 26 + 48));
            }
        
            // check if the generated reference number 
            for (ArrayList<Object> info : Administration.ORDER_REPORTS) 
            {
                if (info.get(0).equals(sb)) 
                { 
                    sb.setLength(0);
                    exist = true; 

                    break;
                }
            }
        }
        while(exist);

        return sb.toString();
    }

    // function to check if the file with items exist
    protected boolean foundFile() 
    {
        try 
        {
            // create file and insert items if not exist 
            if (itemData.createNewFile()) 
            {
                updateFile();
                loadFile(); 

                return false;
            } 
            // otherwise, retrieve data from the file
            else 
            { 
                loadFile(); 
            }
        } 
        catch (IOException e) 
        { 
            e.printStackTrace(); 
        }

        return true;    
    }

    // function to retrieve data from the item file
    protected void loadFile() 
    {
        OrderFood.MEALS_ITEMS.clear();
        OrderFood.SANDWICH_ITEMS.clear();
        OrderFood.DRINKS_ITEMS.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(itemData)))
        {   
            String line;
            int mapIndex = 0;

            Administration.GRAND_TOTAL_INCOME = new BigDecimal(reader.readLine());

            // read file until it reaches 'x'
            while ((line = reader.readLine()) != null) 
            {
                if (line.equals("x")) break;

                if (line.charAt(0) == '-') 
                {
                    mapIndex++;
                    continue;
                }

                String item     = line;
                double price    = Double.parseDouble(reader.readLine());
                int stocks      = Integer.parseInt(reader.readLine());

                // put retrieve data into its specific maps
                switch (mapIndex) 
                {
                    case 1 -> OrderFood.MEALS_ITEMS.add(new Object[] {item, price, stocks});
                    case 2 -> OrderFood.SANDWICH_ITEMS.add(new Object[] {item, price, stocks});
                    case 3 -> OrderFood.DRINKS_ITEMS.add(new Object[] {item, price, stocks});
                }
            }

            reader.close();
        } 
        catch (IOException e) 
        { 
            e.printStackTrace(); 
        }
    }
    
    // function to update items file
    protected void updateFile() 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(itemData)))
        {
            int idx = 0;
            writer.write(Administration.GRAND_TOTAL_INCOME.toPlainString() + "\n");
            writer.write("-MENU1\n");

            // re-write all dynamically added data to the item file
            for (ArrayList<Object[]> MENU : ORIGINAL_ITEMS) 
            {
                for (Object[] items : MENU) 
                {
                    String item     = items[0].toString();
                    double price    = (double) items[1];
                    int stocks      = (int) items[2];

                    writer.write(item + "\n" + price + "\n" + stocks + "\n");
                }
                
                if (idx++ < 2)
                    writer.write("-MENU" + (idx + 1) +"\n");
            }
            
            // write 'x' to the of the content
            writer.write("x");
            writer.close();
        } 
        catch (IOException e) 
        { 
            e.printStackTrace(); 
        }
    }

    // function to add data to the item file
    protected void addToFile(String cat, String item, double price, int stocks) 
    {
        switch (cat)
        {
            case "manage_menu_meals"    -> ORIGINAL_ITEMS.get(0).add(new Object[] {item, price, stocks});
            case "manage_menu_sandwich" -> ORIGINAL_ITEMS.get(1).add(new Object[] {item, price, stocks});
            case "manage_menu_drinks"   -> ORIGINAL_ITEMS.get(2).add(new Object[] {item, price, stocks});
        }

        // update the contents of the item file 
        updateFile();
    }

    // function to delete data from the item file
    protected void removeToFile(String cat, int index) 
    {
        switch (cat)
        {
            case "manage_menu_meals"    -> ORIGINAL_ITEMS.get(0).remove(index);
            case "manage_menu_sandwich" -> ORIGINAL_ITEMS.get(1).remove(index);
            case "manage_menu_drinks"   -> ORIGINAL_ITEMS.get(2).remove(index);
        }

        // update the contents of the item file
        updateFile();
    }

    // function to check the reports file
    protected void checkReports()   
    {
        try 
        { 
            // retrieve reports from the reports file if the file exist
            if (!OrderReports.createNewFile()) loadReports(); 
        } 
        catch (IOException e) 
        { 
            e.printStackTrace(); 
        }  
    }

    // function to retrieve reports from the reports file
    protected void loadReports() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(OrderReports)))
        {   
            String line;

            // read file until it reaches 'x'
            while ((line = reader.readLine()) != null) 
            {
                if (line.equals("x")) break;

                String date         = line;
                String time         = reader.readLine();
                String refNum       = reader.readLine();
                BigDecimal TOTAL    = new BigDecimal(reader.readLine());

                Administration.ORDER_REPORTS.add(new ArrayList<>() 
                {{
                    add(date);
                    add(time);
                    add(refNum);
                    add(TOTAL);
                }});
            }

            reader.close();
        } 
        catch (IOException e) 
        { 
            e.printStackTrace(); 
        }    
    }

    // function to dynamically add reports to the file 
    protected void addToReports(String date, String time, String refNum, BigDecimal TOTAL) 
    {
        Administration.ORDER_REPORTS.add(new ArrayList<>()
        {{
            add(date);
            add(time);
            add(refNum);
            add(TOTAL);
        }});

        updateReports();
    }
    
    // function to update the contents of the reports file
    protected void updateReports() 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OrderReports)))
        {
            // re-write all dynamically added reports to the reports file
            for (ArrayList<Object> report : Administration.ORDER_REPORTS) 
                for (Object line : report) 
                    writer.write(String.valueOf(line) + "\n");
            
            writer.write("x");
            writer.close();
        } 
        catch (IOException e) 
        { 
            e.printStackTrace(); 
        }
    }

    // function to generate ASCII art button 
    protected void button(int num, String x)
    {
        StringBuilder line = new StringBuilder(x);

        while (line.length() < 15) line.append(" ");

        printLine(64, WHI("   ___________________") + RED(",,") + WHI("___"));
        printLine(64, "  /                       /");
        printLine(64, " /  [ " + YEL(String.valueOf(num)) + " ] " + line + "/");
        printLine(64, "/_______________________/");
    }
    
    // function to print null
    protected void printNull()
    {
        line();
        printLine(60, RED(" ___   ___  ________  ___   ___"));
        printLine(60, RED("/\\  \\ /\\  \\/\\   __  \\/\\  \\ /\\  \\"));
        printLine(60, RED("\\ \\  \\\\_\\  \\ \\  \\|\\  \\ \\  \\\\_\\  \\"));
        printLine(60, RED(" \\ \\_____   \\ \\  \\\\\\  \\ \\_____   \\"));
        printLine(60, RED("  \\|_____|\\  \\ \\  \\\\\\  \\|_____|\\  \\"));
        printLine(60, RED("         \\ \\__\\ \\_______\\     \\ \\__\\"));
        printLine(60, RED("          \\|__|\\|_______|      \\|__|"));
        line();
    }
    
    protected void printCash() 
    {
        line();
        printLine(69, RED("   _....._      "));
        printLine(69, RED("  ';-.--';'     "));
        printLine(69, RED("    }===={      "));
        printLine(69, RED("   .   __  .    "));
        printLine(69, RED("  ': _|__\\_ '  "));
        printLine(69, RED(" /::  |__/   \\ "));
        printLine(69, RED(" |::  |       | "));
        printLine(69, RED(" \\::. |       /"));
        printLine(69, RED("  '::_     _.'  "));
        printLine(69, RED("      '''''     "));
        line();
    }

    protected void printCard() 
    {
        printLine(62, RED(" ____________________________ "));
        printLine(62, RED("|                            |"));
        printLine(62, RED("|  KWAGO CARD           )))  |"));
        printLine(62, RED("|                            |"));
        printLine(62, RED("|  [{}]                      |"));
        printLine(62, RED("|                            |"));
        printLine(62, RED("|  0000 1111 2222 3333 4444  |"));
        printLine(62, RED("|  G.Dredd             4/20  |"));
        printLine(62, RED("|____________________________|"));      
    }

    // function to print on exit
    protected void printExit() 
    {
        line();
        printLine(30, RED("  ,ad8888ba,                                      88     88888888ba "));
        printLine(30, RED(" d8\"'    `\"8b                                     88     88      \"8b  "));
        printLine(30, RED("d8'                                               88     88      ,8P  "));
        printLine(30, RED("88              ,adPPYba,    ,adPPYba,    ,adPPYb,88     88aaaaaa8P'  8b       d8   ,adPPYba,  "));
        printLine(30, RED("88      88888  a8\"     \"8a  a8\"     \"8a  a8\"    `Y88     88\"\"\"\"\"\"8b,  `8b     d8'  a8P_____88 "));
        printLine(30, RED("Y8,        88  8b       d8  8b       d8  8b       88     88      `8b   `8b   d8'   8PP\"\"\"\"\"\"\"  "));
        printLine(30, RED(" Y8a.    .a88  \"8a,   ,a8\"  \"8a,   ,a8\"  \"8a,   ,d88     88      a8P    `8b,d8'    \"8b,   ,aa  "));
        printLine(30, RED("  `\"Y88888P\"    `\"YbbdP\"'    `\"YbbdP\"'    `\"8bbdP\"Y8     88888888P\"       Y88'      `\"Ybbd8\"'  "));
        printLine(30, RED("                                                                          d8'      "));
        printLine(30, RED("                                                                         d8' "));
        line();
    }

    //function to print admin
    protected void printAdmin() 
    {
        printLine(63, RED("      ...                   "));
        printLine(63, RED(".=+*#%%%.                   "));
        printLine(63, RED("#%%%%%%+                    "));
        printLine(63, RED("%%%%%%%=            :----.  "));
        printLine(63, RED("%%%#*+++-:.      .::::--::. "));
        printLine(63, RED("%%%===+##*+:     =*%*=-::::."));
        printLine(63, RED("#%%:     -**    .+:         "));
        printLine(63, RED("*%%#::::..:**.   :  .:::: .:"));
        printLine(63, RED("+#::--====+%%*     :-==--::."));
        printLine(63, RED("==        +%%#              "));
        printLine(63, RED("--       +%%%#              "));
        printLine(63, RED(":+      =%%%%=       .      "));
        printLine(63, RED(" #.:==-:.:=#%=  .:.  .::=:: "));
        printLine(63, RED(" == +=+.    +%#%*:    -*:=  "));
        printLine(63, RED("  *=:-:+++*##= .+##+++= -:  "));
        printLine(63, RED("   ++:-    .::::::.    =:   "));
        printLine(63, RED("    :*:-.    -++=.   :-     "));
        printLine(63, RED("      =-..    %%:   :.      "));
        printLine(63, RED("       :=:.  -%%+  .        "));
        printLine(63, RED("         ::  -%%+           "));
        printLine(63, RED("              #%.           "));
        printLine(63, RED("              :=            "));
    }

    // prints the header 
    protected void printHeader(String x)
    {
        cls();
        line();
        line();
        line(WHI("                                                           (\\_(\\"));
        line("                                                           ( " + RED("-") + "." + RED("-") + ")           ^ ^");
        line("                   _______                     " + RED("$") + "  _________c(\")(\")_________(" + RED("-") + "." + RED("-") + ")_________________________  " + RED("$"));
        line("                  |       |                   [_]|                                                       |[_]");
        line("                  | " + RED("KWAGO") + " |                    | |   F A S T   F O O D   O R D E R I N G   S Y S T E M   | | ");
        line("                  | ______|                    | |                " + YEL("GREGORIO") + " - " + YEL("MATA") + " - " + YEL("PINEDA") + "               | | ");
        line("            ,___, |/                           | |_______________________________________________________| | ");
        line("            ("+ YEL("o,o") + ")                              0" + RED("=") + "----------------------" + RED("=") + "[ " + GRE("~~~~") + RED("{{@") + " ]" + RED("=") + "----------------------" + RED("=") + "0 ");   
        line("            /)_)                               ");   
        generateTitle(x);
        line();
    }   

    // function to print supplier
    protected void printSupplier() 
    {
        line();
        printLine(39, RED("        ________________________________________                             "));
        printLine(39, RED("       /                                       /|    _______                 "));
        printLine(39, RED("      /_______________________________________/ |// /      /\\               "));
        printLine(39, RED("      |                                      |  //|/______/  \\________      "));
        printLine(39, RED("      |                         ,_,          |  |||       \\  /       /|     "));
        printLine(39, RED("______|	     KWAGO FAST FOOD  {O,o}         |  |||________\\/_______/ |______"));
        printLine(39, RED("      |                        /)__)         |  |||        |       |  |      "));
        printLine(39, RED("      |                         \" \"          | /|||        |       |  |    "));
        printLine(39, RED("      |______________________________________|/_|||________|______ |  |      "));
        printLine(39, RED("___  _|  ____   ____	           ____   ____ |_/         ____  \\| /__  ___"));
        printLine(39, RED("      |_/    \\ /    \\ _____________/    \\ /    \\|_________ /    \\__|/   "));
        printLine(39, RED("       |  ()  |  ()  ||           |  ()  |  ()  ||        |  ()  ||          "));
        printLine(39, RED("        \\____//\\____//             \\____//\\____//          \\____//      "));
        printLine(39, RED("_____________________________________________________________________________"));
        line();    
    }

    // function to generate title/line to the system
    protected void generateTitle(String x)
    {
        switch (x)
        {
            case "null"                 -> line(WHI("00") + RED("=") + "---------------------------------------------------------------------" + RED("=") + "[ " + GRE("~~~") + RED("{@") + " ]" + RED("=") + "---------------------------------------------------------------------" + RED("=") + "00");
            case "main_menu"            -> line(WHI("00") + RED("=") + "----------m-m--------------------------------------------------" + RED("=") + "[ " + YEL("M A I N   M E N U") + " ]" + RED("=") + "---------------------------------------------------------------" + RED("=") + "00");
            case "order_food"           -> line(WHI("00") + RED("=") + "----------m-m-------------------------------------------------" + RED("=") + "[ " + YEL("O R D E R   F O O D") + " ]" + RED("=") + "--------------------------------------------------------------" + RED("=") + "00");
            case "order_food_meals"     -> line(WHI("00") + RED("=") + "----------m-m-------------------------------------------------" + RED("=") + "[ " + YEL("O R D E R   F O O D") + " ]" + RED("=") + "----" + RED("=") + YEL("( ") + WHI("MEALS") + YEL(" )") + RED("=") + "-----------------------------------------------" + RED("=") + "00");
            case "order_food_sandwich"  -> line(WHI("00") + RED("=") + "----------m-m-------------------------------------------------" + RED("=") + "[ " + YEL("O R D E R   F O O D") + " ]" + RED("=") + "----" + RED("=") + YEL("( ") + WHI("SANDWICH") + YEL(" )") + RED("=") + "--------------------------------------------" + RED("=") + "00");
            case "order_food_drinks"    -> line(WHI("00") + RED("=") + "----------m-m-------------------------------------------------" + RED("=") + "[ " + YEL("O R D E R   F O O D") + " ]" + RED("=") + "----" + RED("=") + YEL("( ") + WHI("DRINKS") + YEL(" )") + RED("=") + "----------------------------------------------" + RED("=") + "00");
            case "my_cart"              -> line(WHI("00") + RED("=") + "----------m-m----------------------------------------------------" + RED("=") + "[ " + YEL("M Y   C A R T") + " ]" + RED("=") + "-----------------------------------------------------------------" + RED("=") + "00");
            case "administration"       -> line(WHI("00") + RED("=") + "----------m-m---------------------------------------------" + RED("=") + "[ " + YEL("A D M I N I S T R A T I O N") + " ]" + RED("=") + "----------------------------------------------------------" + RED("=") + "00");
            case "manage_menu"          -> line(WHI("00") + RED("=") + "----------m-m------------------------------------------------" + RED("=") + "[ " + YEL("M A N A G E   M E N U") + " ]" + RED("=") + "-------------------------------------------------------------" + RED("=") + "00");  
            case "manage_menu_meals"    -> line(WHI("00") + RED("=") + "----------m-m------------------------------------------------" + RED("=") + "[ " + YEL("M A N A G E   M E N U") + " ]" + RED("=") + "----" + RED("=") + YEL("( ") + WHI("MEALS") + YEL(" )")  + RED("=") + "----------------------------------------------" + RED("=") + "00");  
            case "manage_menu_sandwich" -> line(WHI("00") + RED("=") + "----------m-m------------------------------------------------" + RED("=") + "[ " + YEL("M A N A G E   M E N U") + " ]" + RED("=") + "----" + RED("=") + YEL("( ") + WHI("SANDWICH") + YEL(" )")  + RED("=") + "-------------------------------------------" + RED("=") + "00");  
            case "manage_menu_drinks"   -> line(WHI("00") + RED("=") + "----------m-m------------------------------------------------" + RED("=") + "[ " + YEL("M A N A G E   M E N U") + " ]" + RED("=") + "----" + RED("=") + YEL("( ") + WHI("DRINKS") + YEL(" )")  + RED("=") + "---------------------------------------------" + RED("=") + "00");  
            case "report"               -> line(WHI("00") + RED("=") + "----------m-m-----------------------------------------------------" + RED("=") + "[ " + YEL("R E P O R T") + " ]" + RED("=") + "------------------------------------------------------------------" + RED("=") + "00");
            case "supplier"             -> line(WHI("00") + RED("=") + "----------m-m---------------------------------------------------" + RED("=") + "[ " + YEL("S U P P L I E R") + " ]" + RED("=") + "----------------------------------------------------------------" + RED("=") + "00");
            case "exit"                 -> line(WHI("00") + RED("=") + "----------m-m-------------------------------------------------------" + RED("=") + "[ " + YEL("E X I T") + " ]" + RED("=") + "--------------------------------------------------------------------" + RED("=") + "00");
        }
    }
}