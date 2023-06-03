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
public class GSystem extends Color
{
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
            System.out.println("Press any key...");
    
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

                System.out.print(fill(15, '\n'));

                printLine(49, loadingTitle);
                printLine(49, "[" + GRE + loadBar + RES + "] " + j + "%");

                try 
                {
                    Thread.sleep(100);
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

            System.out.println(fill(15, '\n'));
            printLine(49, "Loading, Please Wait...");

            try 
            { 
                Thread.sleep(2000); 
            } 
            catch (InterruptedException e) 
            { 
                e.printStackTrace(); 
            }
        }    

        System.out.println("\n");
        
        // check the files if they exist
        checkReports();

        if (foundFile()) printLine(64, "FILE IS SUCCESFULLY LOADED!");
        else printLine(61, "NEW FILE IS SUCCESSFULLY CREATED!");
        
        System.out.println(fill(15, '\n'));
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
                    case 1 -> MainProcess.MEALS_ITEMS.put(item, price);
                    case 2 -> MainProcess.SANDWICH_ITEMS.put(item, price);
                    case 3 -> MainProcess.DRINKS_ITEMS.put(item, price);
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
                
                if (idx++ < 3)
                    writer.write("-MENU" + (idx + 2) +"\n");
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

        printLine(64, "   ___________________"+ RED +",," + RES + "___");
        printLine(64, "  /                       /");
        printLine(64, " /  [ " + YEL + num + RES +" ] " + line + "/");
        printLine(64, "/_______________________/");
    }

    // prints the header 
    protected void printHeader()
    {
        System.out.println();
        System.out.println();
        System.out.println("                                                           (\\_(\\");
        System.out.println("                                                           ( " + RED + "-" + RES + "." + RED + "-" + RES + ")           ^ ^");
        System.out.println("                   _______                     " + RED + "$" + RES + "  _________c(\")(\")_________(" + RED + "-" + RES + "." + RED + "-" + RES + ")_________________________  " + RED + "$" + RES + " ");
        System.out.println("                  |       |                   [_]|                                                       |[_]");
        System.out.println("                  | KWAGO |                    | |   F A S T   F O O D   O R D E R I N G   S Y S T E M   | | ");
        System.out.println("                  | ______|                    | |                " + YEL + "GREGORIO" + RES + " - " + YEL + "MATA" + RES + " - " + YEL + "PINEDA" + RES + "               | | ");
        System.out.println("            ,___, |/                           | |_______________________________________________________| | ");
        System.out.println("            ("+ YEL +"o,o" + RES + ")                              0" + RED + "=" + RES + "----------------------" + RED + "=" + RES + "[ " + GRE + "~~~~" + RED + "{{@" + RES + " ]" + RED + "=" + RES + "----------------------" + RED + "=" + RES + "0 ");   
        System.out.println("            /)_)                               ");   
    }

    // function to generate title/line to the system
    protected void generateTitle(String x)
    {
        switch (x)
        {
            case "null"                 -> System.out.println("00" + RED + "=" + RES + "---------------------------------------------------------------------" + RED + "=" + RES + "[ " + GRE + "~~~" + RED + "{@" + RES + " ]" + RED + "=" + RES + "---------------------------------------------------------------------" + RED + "=" + RES + "00");
            case "main_menu"            -> System.out.println("00" + RED + "=" + RES + "----------m-m--------------------------------------------------" + RED + "=" + RES + "[ " + YEL + "M A I N   M E N U" + RES + " ]" + RED + "=" + RES + "---------------------------------------------------------------" + RED + "=" + RES + "00");
            case "order_food"           -> System.out.println("00" + RED + "=" + RES + "----------m-m-------------------------------------------------" + RED + "=" + RES + "[ " + YEL + "O R D E R   F O O D" + RES + " ]" + RED + "=" + RES + "--------------------------------------------------------------" + RED + "=" + RES + "00");
            case "order_food_meals"     -> System.out.println("00" + RED + "=" + RES + "----------m-m-------------------------------------------------" + RED + "=" + RES + "[ " + YEL + "O R D E R   F O O D" + RES + " ]" + RED + "=" + RES + "----" + RED + "=" + YEL + "( " + RES + "MEALS )" + RED + "=" + RES + "-----------------------------------------------" + RED + "=" + RES + "00");
            case "order_food_sandwich"  -> System.out.println("00" + RED + "=" + RES + "----------m-m-------------------------------------------------" + RED + "=" + RES + "[ " + YEL + "O R D E R   F O O D" + RES + " ]" + RED + "=" + RES + "----" + RED + "=" + YEL + "( " + RES + "SANDWICH )" + RED + "=" + RES + "--------------------------------------------" + RED + "=" + RES + "00");
            case "order_food_drinks"    -> System.out.println("00" + RED + "=" + RES + "----------m-m-------------------------------------------------" + RED + "=" + RES + "[ " + YEL + "O R D E R   F O O D" + RES + " ]" + RED + "=" + RES + "----" + RED + "=" + YEL + "( " + RES + "DRINKS )" + RED + "=" + RES + "----------------------------------------------" + RED + "=" + RES + "00");
            case "my_cart"              -> System.out.println("00" + RED + "=" + RES + "----------m-m----------------------------------------------------" + RED + "=" + RES + "[ " + YEL + "M Y   C A R T" + RES + " ]" + RED + "=" + RES + "-----------------------------------------------------------------" + RED + "=" + RES + "00");
            case "administration"       -> System.out.println("00" + RED + "=" + RES + "----------m-m---------------------------------------------" + RED + "=" + RES + "[ " + YEL + "A D M I N I S T R A T I O N" + RES + " ]" + RED + "=" + RES + "----------------------------------------------------------" + RED + "=" + RES + "00");
            case "manage_menu"          -> System.out.println("00" + RED + "=" + RES + "----------m-m------------------------------------------------" + RED + "=" + RES + "[ " + YEL + "M A N A G E   M E N U" + RES + " ]" + RED + "=" + RES + "-------------------------------------------------------------" + RED + "=" + RES + "00");  
            case "manage_menu_meals"    -> System.out.println("00" + RED + "=" + RES + "----------m-m------------------------------------------------" + RED + "=" + RES + "[ " + YEL + "M A N A G E   M E N U" + RES + " ]" + RED + "=" + RES + "----" + RED + "=" + YEL + "( " + RES + "MEALS" + YEL + " )" + RED + "=" + RES + "----------------------------------------------" + RED + "=" + RES + "00");  
            case "manage_menu_sandwich" -> System.out.println("00" + RED + "=" + RES + "----------m-m------------------------------------------------" + RED + "=" + RES + "[ " + YEL + "M A N A G E   M E N U" + RES + " ]" + RED + "=" + RES + "----" + RED + "=" + YEL + "( " + RES + "SANDWICH" + YEL + " )" + RED + "=" + RES + "-------------------------------------------" + RED + "=" + RES + "00");  
            case "manage_menu_drinks"   -> System.out.println("00" + RED + "=" + RES + "----------m-m------------------------------------------------" + RED + "=" + RES + "[ " + YEL + "M A N A G E   M E N U" + RES + " ]" + RED + "=" + RES + "----" + RED + "=" + YEL + "( " + RES + "DRINKS" + YEL + " )" + RED + "=" + RES + "---------------------------------------------" + RED + "=" + RES + "00");  
            case "report"               -> System.out.println("00" + RED + "=" + RES + "----------m-m-----------------------------------------------------" + RED + "=" + RES + "[ " + YEL + "R E P O R T" + RES + " ]" + RED + "=" + RES + "------------------------------------------------------------------" + RED + "=" + RES + "00");
            case "exit"                 -> System.out.println("00" + RED + "=" + RES + "----------m-m-------------------------------------------------------" + RED + "=" + RES + "[ " + YEL + "E X I T" + RES + " ]" + RED + "=" + RES + "--------------------------------------------------------------------" + RED + "=" + RES + "00");
        }
    }
}

/**
 *  This class is used to provide {@code ANSI} color code
 * 
 *  @author Neelian Mata
 *  @author Jarius Maui Pineda
 *  @author Niño Greg Gregorio
 *  @since 1.0
 */
class Color 
{
    final String RES = "\u001B[0m";
    final String BLA = "\u001B[30m";
    final String RED = "\u001B[31m";
    final String GRE = "\u001B[32m";
    final String YEL = "\u001B[33m";
    final String BLU = "\u001B[34m";
    final String PUR = "\u001B[35m";
    final String CYA = "\u001B[36m";
    final String WHI = "\u001B[37m";
}