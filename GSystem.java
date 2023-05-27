import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class GSystem 
{
    private final String os = System.getProperty("os.name");
    
    protected void START_SYSTEM() 
    {
        if (os.contains("Windows"))
        {
            try
            {          
                Runtime.getRuntime().exec(new String[]{"cmd", "/k", "javac", "MainProcess.java"});
                Thread.sleep(2000);
                Runtime.getRuntime().exec(new String[]{"cmd", "/k", "start", "java", "MainProcess"});
            }
            catch (Exception e) { e.printStackTrace(); }
        }
    }

    protected void CLS()
    {   
        if (os.contains("Windows"))
        {
            try { new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); } 
            catch (Exception e) { e.printStackTrace(); }   
        }
        else
        {
            System.out.print("\033[H\033[2J");
            System.out.flush();  
        }
    }

    protected void PAUSE() 
    {
        if (os.contains("Windows"))
        {
            try { new ProcessBuilder("cmd", "/c", "pause").inheritIO().start().waitFor(); } 
            catch (Exception e) { e.printStackTrace(); }   
        }
        else
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Press any key...");
    
            try { input.readLine(); } 
            catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    protected void PRINT(int spaces, String x)
    {
        StringBuilder sb = new StringBuilder(x);
        for (int i = 0; i < spaces; i++) sb.insert(0, ' ');
        System.out.print(sb);
    }

    protected void PRINTLN(int spaces, String x)
    {
        StringBuilder sb = new StringBuilder(x);
        for (int i = 0; i < spaces; i++) sb.insert(0, ' ');
        System.out.println(sb);
    }

    protected void PRINTF(int spaces, String format, Object ... args)
    {        
        for (int i = 0; i < spaces; i++) System.out.print(' ');
        System.out.printf(format, args);
    }

    protected String FILL(int count, char x) 
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) sb.append(x);

        return sb.toString();
    }

    protected ArrayList<String> MULTILINE(String x, int column)
    {
        String[] words = x.split(" ");
        ArrayList<String> multiLine = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        
        for (String word : words) 
        {
            line.append(word + " ");

            if (line.length() + word.length() > column)
            {
                multiLine.add(line.toString());
                line.setLength(0);
            }
        }

        if (!line.isEmpty()) multiLine.add(line.toString());

        return multiLine;
    }
    
    protected String GENERATE_REF_NUM()
    {
        StringBuilder sb = new StringBuilder();
        boolean exist = false; 

        do 
        { 
            while (sb.length() < 14)
            {
                int randomNumber = new Random().nextInt(36);
                if (sb.length() == 4 || sb.length() == 9) sb.append('-');
                if (randomNumber < 26) sb.append((char) (randomNumber + 65));
                else sb.append((char) (randomNumber - 26 + 48));
            }
        
            for (ArrayList<Object> info : Administration.ORDER_INFO) 
                if (info.get(0).equals(sb)) 
                { 
                    sb.setLength(0);
                    exist = true; 
                    break;
                }
        }
        while(exist);

        return sb.toString();
    }

    protected void HEADER()
    {
        System.out.println("                                               ___________________________________________________________ ");
        System.out.println("                   _______                    |  _______________________________________________________  |");
        System.out.println("                  |       |                   | |                                                       | |");
        System.out.println("                  | KWAGO |                   | |   F A S T   F O O D   O R D E R I N G   S Y S T E M   | |");
        System.out.println("                  | ______|                   | |                  Gregorio-Mata-Pineda                 | |");
        System.out.println("            ,___, |/                          | |_______________________________________________________| |");
        System.out.println("            (o,o)                             |___________________________________________________________|");   
        System.out.println("            /)_)                                                                                           ");                     
    }

    protected void GENERATE_TITLE(String x)
    {
        switch (x)
        {
            case "null"                 -> System.out.println("00=---------------------------------------------------------------------=[ ~~~{@ ]=---------------------------------------------------------------------=00");
            case "main_menu"            -> System.out.println("00=----------m-m--------------------------------------------------=[ M A I N   M E N U ]=---------------------------------------------------------------=00");
            case "order_food"           -> System.out.println("00=----------m-m-------------------------------------------------=[ O R D E R   F O O D ]=--------------------------------------------------------------=00");
            case "order_food_meals"     -> System.out.println("00=----------m-m-------------------------------------------------=[ O R D E R   F O O D ]=----=( MEALS )=-----------------------------------------------=00");
            case "order_food_sandwich"  -> System.out.println("00=----------m-m-------------------------------------------------=[ O R D E R   F O O D ]=----=( SANDWICH )=--------------------------------------------=00");
            case "order_food_drinks"    -> System.out.println("00=----------m-m-------------------------------------------------=[ O R D E R   F O O D ]=----=( DRINKS )=----------------------------------------------=00");
            case "my_cart"              -> System.out.println("00=----------m-m----------------------------------------------------=[ M Y   C A R T ]=-----------------------------------------------------------------=00");
            case "administration"       -> System.out.println("00=----------m-m---------------------------------------------=[ A D M I N I S T R A T I O N ]=----------------------------------------------------------=00");
            case "manage_menu"          -> System.out.println("00=----------m-m------------------------------------------------=[ M A N A G E   M E N U ]=-------------------------------------------------------------=00");  
            case "manage_menu_meals"    -> System.out.println("00=----------m-m------------------------------------------------=[ M A N A G E   M E N U ]=----=( MEALS )=----------------------------------------------=00");  
            case "manage_menu_sandwich" -> System.out.println("00=----------m-m------------------------------------------------=[ M A N A G E   M E N U ]=----=( SANDWICH )=-------------------------------------------=00");  
            case "manage_menu_drinks"   -> System.out.println("00=----------m-m------------------------------------------------=[ M A N A G E   M E N U ]=----=( DRINKS )=---------------------------------------------=00");  
            case "report"               -> System.out.println("00=----------m-m-----------------------------------------------------=[ R E P O R T ]=------------------------------------------------------------------=00");
        }
    }
}
