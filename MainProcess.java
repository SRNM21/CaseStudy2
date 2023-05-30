import java.util.HashMap;

public class MainProcess 
{
    protected static HashMap<String, Double> MEALS_ITEMS      = new HashMap<>();
    protected static HashMap<String, Double> SANDWICH_ITEMS   = new HashMap<>();
    protected static HashMap<String, Double> DRINKS_ITEMS     = new HashMap<>();

    private static final ErrorHandler erh = new ErrorHandler();
    private static final GSystem gsystem = new GSystem();

    public static void main(String[] args) { new MainProcess(); }   
    
    MainProcess() 
    {
        gsystem.LOADING();
        gsystem.PAUSE();
        gsystem.CLS();

        boolean running = true;
        while (running) running = MainMenu();

        gsystem.CLS();
        gsystem.HEADER();
        gsystem.GENERATE_TITLE("exit");
        System.out.println("\n\n");
        gsystem.PRINTLN(62,"THANK YOU FOR USING OUR SYSTEM!");
        System.out.println("\n\n");
        gsystem.GENERATE_TITLE("null");
        gsystem.PAUSE();
    }

    private boolean MainMenu()
    {
        gsystem.CLS();
        gsystem.HEADER();
        gsystem.GENERATE_TITLE("main_menu");
        System.out.println();
        gsystem.PRINTLN(50,"[1] - ORDER FOOD\n");
        gsystem.PRINTLN(50,"[2] - ADMINISTRATION\n");
        gsystem.PRINTLN(50,"[3] - EXIT\n");
        System.out.println();
        gsystem.PRINT(50,"ENTER CHOICE:  ");
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
