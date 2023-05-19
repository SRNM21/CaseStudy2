import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GSystem 
{
    protected void CLS()
    {   
        final String os = System.getProperty("os.name");

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

    protected void WAIT() 
    {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        new GSystem().PRINT("Press any key...");

        try { input.readLine(); } 
        catch (Exception e) { e.printStackTrace(); }
    }

    protected void PRINTLN()
    {
        System.out.println("\t\t\t\t\t\t");
    }

    protected void PRINTLN(String x)
    {
        System.out.println("\t\t\t\t\t\t" + x);
    }

    protected void PRINT(String x)
    {
        System.out.print("\t\t\t\t\t\t" + x);
    }

    protected void PRINTF(String format, Object ... args)
    {
        System.out.print("\t\t\t\t\t\t");
        System.out.printf(format, args);
    }

    protected String FILL(int count, char x) 
    {
        if (count <= 0) return null;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) sb.append(x);

        return sb.toString();
    }

    protected ArrayList<String> MULTILINE(String x, int column)
    {
        ArrayList<String> multiLine = new ArrayList<>();
        String[] words = x.split(" ");
        StringBuilder line = new StringBuilder();
        
        for (String word : words) 
        {
            if (line.length() + word.length() < column)
            {
                line.append(word + " ");
            }
            else 
            {
                multiLine.add(line.toString());
                line.setLength(0);
                line.append(word + " ");
            }
        }

        return multiLine;
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
