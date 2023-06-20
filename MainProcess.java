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
public class MainProcess extends ErrorHandler
{
    // run the MainProcess thru main function if the system is not ran in Windows Command Prompt
    public static void main(String[] args) { new MainProcess(); }   
        
    // run the MainProcess Constructor if the System is ran in Windows Command Prompt
    MainProcess() 
    {
        load();
        pause();
        cls();

        // loop the System's flow until the user exit
        boolean running = true;
        while (running) running = MainMenu();

        // prints when the user exit
        printHeader("exit");
        printExit();
        printLine(62, WHI("THANK YOU FOR USING OUR SYSTEM!"));
        line("\n");
        generateTitle("null");
        pause();
    }

    // menu of the system
    private boolean MainMenu()
    {
        printHeader("main_menu");
        button(1, "ORDER FOOD");
        button(2, "ADMINISTRATION");
        button(3, "EXIT");
        line();
        printLine(55, WHI("ENTER CHOICE"));
        pointer();
        int menuChoice = getChoice(1, 3);
    
        switch (menuChoice)
        {
            case 1 -> new OrderFood();
            case 2 -> new Administration();
            case 3 -> { return false; }
        }

        return true;
    }
}