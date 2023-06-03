import java.util.HashMap;

/**
 *  The {@code MainProcess} class is the main menu of the system. Loop the flow until the user exits from it.
 * 
 *  <p>
 *  This is where the items are stored and manipulated. When called, this class will load the item and reports from
 *  {@code ITEMS.txt} and {@code REPORTS.txt}.
 *  </p>
 * 
 *  @author Neelian Mata
 *  @author Jarius Maui Pineda
 *  @author Niño Greg Gregorio
 *  @since 1.0
 */
public class MainProcess 
{
    // retrieved items from the file
    protected static HashMap<String, Double> MEALS_ITEMS      = new HashMap<>();
    protected static HashMap<String, Double> SANDWICH_ITEMS   = new HashMap<>();
    protected static HashMap<String, Double> DRINKS_ITEMS     = new HashMap<>();

    // class
    private static final ErrorHandler erh = new ErrorHandler();
    private static final GSystem gsystem = new GSystem();

    // run the MainProcess thru main function if the system is not ran in Windows Command Prompt
    public static void main(String[] args) { new MainProcess(); }   
        
    // run the MainProcess Constructor if the System is ran in Windows Command Prompt
    MainProcess() 
    {

        gsystem.load();
        gsystem.pause();
        gsystem.cls();

        // loop the System's flow until the user exit
        boolean running = true;
        while (running) running = MainMenu();

        // prints when the user exit
        gsystem.cls();
        gsystem.printHeader();
        gsystem.generateTitle("exit");

        System.out.println("\n\n");
        gsystem.printLine(62,"THANK YOU FOR USING OUR SYSTEM!");
        System.out.println("\n\n");
        
        gsystem.generateTitle("null");
        gsystem.pause();
    }

    // menu of the system
    private boolean MainMenu()
    {
        gsystem.cls();
        gsystem.printHeader();
        gsystem.generateTitle("main_menu");
        System.out.println();
        gsystem.button(1, "ORDER FOOD");
        gsystem.button(2, "ADMINISTRATION");
        gsystem.button(3, "EXIT");
        System.out.println();
        gsystem.printLine(55,"ENTER CHOICE");
        gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
        int mm = erh.getChoice(1, 3);
    
        switch (mm)
        {
            case 1 -> new OrderFood();
            case 2 -> new Administration();
            case 3 -> { return false; }
        }

        return true;
    }
}