import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
    private ArrayList<HashMap<String, String>> ORIGINAL_ITEMS = new ArrayList<>()
    {{
        add(new HashMap<>()
        {{
            put("Burger Steak", "99.20");
            put("Fried Chicken", "80.30"); 
            put("Spaghetti", "90.20");
        }});

        add(new HashMap<>()
        {{
            put("Fried Chicken Sandwich", "50.80");
            put("Vegan Sandwich", "40.10"); 
            put("Egg Sandwich", "29.99");
        }});

        add(new HashMap<>()
        {{
            put("Water", "10.00");
            put("Iced Tea", "15.30"); 
            put("Coca Cola", "20.20");
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
            line(WHI + "Press any key..." + RES);
    
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

    // loading animation while retrieving/creating the contents of the system
    protected void load() 
    {
        // run animation if the system is on Windows command prompt
        if (System.getProperty("os.name").contains("Windows"))
        {
            StringBuilder loadingBar = new StringBuilder(fill(50, ' '));
            String loadingTitle = "Loading...";

            for (int i = 0, j = 2; i < 50; i++, j += 2) 
            {
                loadingBar.setCharAt(i, '#');
                String loadBar = loadingBar.toString();

                cls();

                line(fill(15, '\n'));

                printLine(49, WHI + loadingTitle + RES);
                printLine(49, WHI + "[" + GRE + loadBar + WHI + "] " + j + "%" + RES);

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
            printLine(49, WHI + "Loading, Please Wait..." + RES);

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
        
        // check the files if they exist
        checkReports();

        if (foundFile()) printLine(64, WHI + "FILE IS SUCCESFULLY LOADED!" + RES);
        else printLine(61, WHI + "NEW FILE IS SUCCESSFULLY CREATED!" + RES);
        
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
    protected void pointer() 
    {
        prints(55, GRE + ">> " + WHI);    
    }
    
    // function to print new line 
    protected void line(String x) 
    {
        System.out.println(x);
    }

    // function to print blank line 
    protected void line() 
    {
        System.out.println();
    }

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
        try (BufferedReader reader = new BufferedReader(new FileReader(itemData)))
        {   
            String line;
            int mapIndex = 0;

            // read file until it reaches 'x'
            while ((line = reader.readLine()) != null) 
            {
                if (line.equals("x")) break;

                if (line.charAt(0) == '-') 
                {
                    mapIndex++;
                    continue;
                }

                String item = line;
                double price = Double.parseDouble(reader.readLine());

                // put retrieve data into its specific maps
                switch (mapIndex) 
                {
                    case 1 -> OrderFood.MEALS_ITEMS.put(item, price);
                    case 2 -> OrderFood.SANDWICH_ITEMS.put(item, price);
                    case 3 -> OrderFood.DRINKS_ITEMS.put(item, price);
                }
            }

            reader.close();
        } 
        catch (IOException e) 
        { 
            e.printStackTrace(); 
        }
    }

    // function to add data to the item file
    protected void addToFile(String cat, String item, double price) 
    {
        switch (cat)
        {
            case "manage_menu_meals"    -> ORIGINAL_ITEMS.get(0).put(item, Double.toString(price));
            case "manage_menu_sandwich" -> ORIGINAL_ITEMS.get(1).put(item, Double.toString(price));
            case "manage_menu_drinks"   -> ORIGINAL_ITEMS.get(2).put(item, Double.toString(price));
        }

        // update the contents of the item file
        updateFile();
    }

    // function to delete data from the item file
    protected void removeToFile(String cat, String item) 
    {
        switch (cat)
        {
            case "manage_menu_meals"    -> ORIGINAL_ITEMS.get(0).remove(item);
            case "manage_menu_sandwich" -> ORIGINAL_ITEMS.get(1).remove(item);
            case "manage_menu_drinks"   -> ORIGINAL_ITEMS.get(2).remove(item);
        }

        // update the contents of the item file
        updateFile();
    }
    
    // function to update items file
    protected void updateFile() 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(itemData)))
        {
            int idx = 0;
            writer.write("-MENU1\n");

            // re-write all dynamically added data to the item file
            for (HashMap<String, String> MENU : ORIGINAL_ITEMS) 
            {
                for (String items : MENU.keySet()) 
                {
                    writer.write(items + "\n");
                    writer.write(MENU.get(items) + "\n");
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

        printLine(64, WHI + "   ___________________"+ RED +",," + WHI + "___");
        printLine(64, "  /                       /");
        printLine(64, " /  [ " + YEL + num + WHI +" ] " + line + "/");
        printLine(64, "/_______________________/" + RES);
    }
    
    // function to print null
    protected void printNull()
    {
        line();
        printLine(60, RED + " ___   ___  ________  ___   ___");
        printLine(60, RED + "/\\  \\ /\\  \\/\\   __  \\/\\  \\ /\\  \\");
        printLine(60, RED + "\\ \\  \\\\_\\  \\ \\  \\|\\  \\ \\  \\\\_\\  \\");
        printLine(60, RED + " \\ \\_____   \\ \\  \\\\\\  \\ \\_____   \\");
        printLine(60, RED + "  \\|_____|\\  \\ \\  \\\\\\  \\|_____|\\  \\");
        printLine(60, RED + "         \\ \\__\\ \\_______\\     \\ \\__\\");
        printLine(60, RED + "          \\|__|\\|_______|      \\|__|" + RES);
        line();
    }
    
    // function to print on exit
    protected void printExit() 
    {
        line();
        printLine(30, RED + "  ,ad8888ba,                                      88     88888888ba ");
        printLine(30, RED + " d8\"'    `\"8b                                     88     88      \"8b  ");
        printLine(30, RED + "d8'                                               88     88      ,8P  ");
        printLine(30, RED + "88              ,adPPYba,    ,adPPYba,    ,adPPYb,88     88aaaaaa8P'  8b       d8   ,adPPYba,  ");
        printLine(30, RED + "88      88888  a8\"     \"8a  a8\"     \"8a  a8\"    `Y88     88\"\"\"\"\"\"8b,  `8b     d8'  a8P_____88 ");
        printLine(30, RED + "Y8,        88  8b       d8  8b       d8  8b       88     88      `8b   `8b   d8'   8PP\"\"\"\"\"\"\"  ");
        printLine(30, RED + " Y8a.    .a88  \"8a,   ,a8\"  \"8a,   ,a8\"  \"8a,   ,d88     88      a8P    `8b,d8'    \"8b,   ,aa  ");
        printLine(30, RED + "  `\"Y88888P\"    `\"YbbdP\"'    `\"YbbdP\"'    `\"8bbdP\"Y8     88888888P\"       Y88'      `\"Ybbd8\"'  ");
        printLine(30, RED + "                                                                          d8'      ");
        printLine(30, RED + "                                                                         d8' " + RES);
        line();
    }

    // prints the header 
    protected void printHeader()
    {
        line();
        line();
        line(WHI + "                                                           (\\_(\\");
        line("                                                           ( " + RED + "-" + WHI + "." + RED + "-" + WHI + ")           ^ ^");
        line("                   _______                     " + RED + "$" + WHI + "  _________c(\")(\")_________(" + RED + "-" + WHI + "." + RED + "-" + WHI + ")_________________________  " + RED + "$");
        line(WHI + "                  |       |                   [_]|                                                       |[_]");
        line("                  | " + RED + "KWAGO" + WHI + " |                    | |   F A S T   F O O D   O R D E R I N G   S Y S T E M   | | ");
        line(WHI + "                  | ______|                    | |                " + YEL + "GREGORIO" + WHI + " - " + YEL + "MATA" + WHI + " - " + YEL + "PINEDA" + WHI + "               | | ");
        line("            ,___, |/                           | |_______________________________________________________| | ");
        line("            ("+ YEL +"o,o" + WHI + ")                              0" + RED + "=" + WHI + "----------------------" + RED + "=" + WHI + "[ " + GRE + "~~~~" + RED + "{{@" + WHI + " ]" + RED + "=" + WHI + "----------------------" + RED + "=" + WHI + "0 ");   
        line("            /)_)                               " + RES);   
    }

    // function to generate title/line to the system
    protected void generateTitle(String x)
    {
        switch (x)
        {
            case "null"                 -> line(WHI + "00" + RED + "=" + WHI + "---------------------------------------------------------------------" + RED + "=" + WHI + "[ " + GRE + "~~~" + RED + "{@" + WHI + " ]" + RED + "=" + WHI + "---------------------------------------------------------------------" + RED + "=" + WHI + "00" + RES);
            case "main_menu"            -> line(WHI + "00" + RED + "=" + WHI + "----------m-m--------------------------------------------------" + RED + "=" + WHI + "[ " + YEL + "M A I N   M E N U" + WHI + " ]" + RED + "=" + WHI + "---------------------------------------------------------------" + RED + "=" + WHI + "00" + RES);
            case "order_food"           -> line(WHI + "00" + RED + "=" + WHI + "----------m-m-------------------------------------------------" + RED + "=" + WHI + "[ " + YEL + "O R D E R   F O O D" + WHI + " ]" + RED + "=" + WHI + "--------------------------------------------------------------" + RED + "=" + WHI + "00" + RES);
            case "order_food_meals"     -> line(WHI + "00" + RED + "=" + WHI + "----------m-m-------------------------------------------------" + RED + "=" + WHI + "[ " + YEL + "O R D E R   F O O D" + WHI + " ]" + RED + "=" + WHI + "----" + RED + "=" + YEL + "( " + WHI + "MEALS )" + RED + "=" + WHI + "-----------------------------------------------" + RED + "=" + WHI + "00" + RES);
            case "order_food_sandwich"  -> line(WHI + "00" + RED + "=" + WHI + "----------m-m-------------------------------------------------" + RED + "=" + WHI + "[ " + YEL + "O R D E R   F O O D" + WHI + " ]" + RED + "=" + WHI + "----" + RED + "=" + YEL + "( " + WHI + "SANDWICH )" + RED + "=" + WHI + "--------------------------------------------" + RED + "=" + WHI + "00" + RES);
            case "order_food_drinks"    -> line(WHI + "00" + RED + "=" + WHI + "----------m-m-------------------------------------------------" + RED + "=" + WHI + "[ " + YEL + "O R D E R   F O O D" + WHI + " ]" + RED + "=" + WHI + "----" + RED + "=" + YEL + "( " + WHI + "DRINKS )" + RED + "=" + WHI + "----------------------------------------------" + RED + "=" + WHI + "00" + RES);
            case "my_cart"              -> line(WHI + "00" + RED + "=" + WHI + "----------m-m----------------------------------------------------" + RED + "=" + WHI + "[ " + YEL + "M Y   C A R T" + WHI + " ]" + RED + "=" + WHI + "-----------------------------------------------------------------" + RED + "=" + WHI + "00" + RES);
            case "administration"       -> line(WHI + "00" + RED + "=" + WHI + "----------m-m---------------------------------------------" + RED + "=" + WHI + "[ " + YEL + "A D M I N I S T R A T I O N" + WHI + " ]" + RED + "=" + WHI + "----------------------------------------------------------" + RED + "=" + WHI + "00" + RES);
            case "manage_menu"          -> line(WHI + "00" + RED + "=" + WHI + "----------m-m------------------------------------------------" + RED + "=" + WHI + "[ " + YEL + "M A N A G E   M E N U" + WHI + " ]" + RED + "=" + WHI + "-------------------------------------------------------------" + RED + "=" + WHI + "00" + RES);  
            case "manage_menu_meals"    -> line(WHI + "00" + RED + "=" + WHI + "----------m-m------------------------------------------------" + RED + "=" + WHI + "[ " + YEL + "M A N A G E   M E N U" + WHI + " ]" + RED + "=" + WHI + "----" + RED + "=" + YEL + "( " + WHI + "MEALS" + YEL + " )" + RED + "=" + WHI + "----------------------------------------------" + RED + "=" + WHI + "00" + RES);  
            case "manage_menu_sandwich" -> line(WHI + "00" + RED + "=" + WHI + "----------m-m------------------------------------------------" + RED + "=" + WHI + "[ " + YEL + "M A N A G E   M E N U" + WHI + " ]" + RED + "=" + WHI + "----" + RED + "=" + YEL + "( " + WHI + "SANDWICH" + YEL + " )" + RED + "=" + WHI + "-------------------------------------------" + RED + "=" + WHI + "00" + RES);  
            case "manage_menu_drinks"   -> line(WHI + "00" + RED + "=" + WHI + "----------m-m------------------------------------------------" + RED + "=" + WHI + "[ " + YEL + "M A N A G E   M E N U" + WHI + " ]" + RED + "=" + WHI + "----" + RED + "=" + YEL + "( " + WHI + "DRINKS" + YEL + " )" + RED + "=" + WHI + "---------------------------------------------" + RED + "=" + WHI + "00" + RES);  
            case "report"               -> line(WHI + "00" + RED + "=" + WHI + "----------m-m-----------------------------------------------------" + RED + "=" + WHI + "[ " + YEL + "R E P O R T" + WHI + " ]" + RED + "=" + WHI + "------------------------------------------------------------------" + RED + "=" + WHI + "00" + RES);
            case "exit"                 -> line(WHI + "00" + RED + "=" + WHI + "----------m-m-------------------------------------------------------" + RED + "=" + WHI + "[ " + YEL + "E X I T" + WHI + " ]" + RED + "=" + WHI + "--------------------------------------------------------------------" + RED + "=" + WHI + "00" + RES);
        }
    }
}