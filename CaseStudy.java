import java.util.HashMap;

public class CaseStudy
{   
    protected static HashMap<String, Double> MEALS_ITEMS      = new HashMap<>();
    protected static HashMap<String, Double> SANDWICH_ITEMS   = new HashMap<>();
    protected static HashMap<String, Double> DRINKS_ITEMS     = new HashMap<>();

    private static final ErrorHandler erh = new ErrorHandler();
    private static final GSystem gsystem = new GSystem();

    public static void main(String[] args)
    {
        CaseStudy cs = new CaseStudy();
        boolean running = true;

        //* AUTO ADD ITEM */
        MEALS_ITEMS.put("Burger Steak", 15.20);
        MEALS_ITEMS.put("Spaghetti", 20.20);
        MEALS_ITEMS.put("Chicken", 18.20);
        MEALS_ITEMS.put("Test Long Words one two three four five six seven eight nine ten eleven twelve thirteen fourteen fifteen", 18.20);

        SANDWICH_ITEMS.put("Egg Sandwich", 15.20);
        SANDWICH_ITEMS.put("Bacon Sandwich", 20.20);
        SANDWICH_ITEMS.put("Cheese Sandwich", 18.20);
        
        DRINKS_ITEMS.put("Water", 15.20);
        DRINKS_ITEMS.put("Coke", 20.20);
        DRINKS_ITEMS.put("Iced Tea", 18.20);
        //* AUTO ADD ITEM */
        
        while (running) running = cs.MainMenu();

        gsystem.GENERATE_TITLE("null");
        gsystem.PRINTLN("THE END");
    }

    private boolean MainMenu()
    {
        gsystem.CLS();
        gsystem.HEADER();
        gsystem.GENERATE_TITLE("main_menu");
        gsystem.PRINTLN();
        gsystem.PRINTLN("[1] - ORDER FOOD\n");
        gsystem.PRINTLN("[2] - ADMINISTRATION\n");
        gsystem.PRINTLN("[3] - EXIT\n");
        gsystem.PRINTLN();
        gsystem.PRINT("ENTER CHOICE:  ");
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