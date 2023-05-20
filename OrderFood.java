import java.util.ArrayList;
import java.util.HashMap;

public class OrderFood 
{
    private static final ErrorHandler erh = new ErrorHandler(); 
    private static final GSystem gsystem = new GSystem();
        
    private static ArrayList<ArrayList<Object>> CART;
    private static double TOTAL_AMOUNT = 0;
       
    OrderFood()
    {
        CART = new ArrayList<>();
        
        while (true)
        {
            gsystem.CLS();
            gsystem.HEADER();
            gsystem.GENERATE_TITLE("order_food");
            System.out.println();
            gsystem.PRINTLN(50, "[0] - BACK\n");
            gsystem.PRINTLN(50, "[1] - MY CART\n");
            gsystem.PRINTLN(50, "[2] - MEALS\n");
            gsystem.PRINTLN(50, "[3] - SANDWICH\n");
            gsystem.PRINTLN(50, "[4] - DRINKS\n");
            System.out.println();
            gsystem.PRINT(50,"ENTER CHOICE:  ");
            int cat = erh.getChoice(0, 4);
            
            switch (cat)
            {
                case 0 -> { return; }
                case 1 -> MyCart(CART);
                case 2 -> Order(CART, CaseStudy.MEALS_ITEMS,    "order_food_meals");
                case 3 -> Order(CART, CaseStudy.SANDWICH_ITEMS, "order_food_sandwich");
                case 4 -> Order(CART, CaseStudy.DRINKS_ITEMS,   "order_food_drinks");
            }
        }
    }

    private void MyCart(ArrayList<ArrayList<Object>> CART)
    {
        while (true)
        {
            gsystem.CLS();
            gsystem.HEADER();
            gsystem.GENERATE_TITLE("my_cart");
            System.out.println();

            if (CART.isEmpty())
            {
                gsystem.PRINTLN(50, "[0] - BACK\n");
                gsystem.PRINTLN(50, "THERE ARE NO ITEMS IN THIS CART\n");
                System.out.println();
                gsystem.PRINT(50, "ENTER CHOICE  :  ");
                int mc = erh.getChoice(0, 0);
        
                if (mc == 0) return;
            }
            else
            {
                gsystem.PRINTLN(50, "[0] - BACK\n");
                gsystem.PRINTLN(50, "[1] - REMOVE ITEM\n");
                gsystem.PRINTLN(50, "[2] - CHECK OUT\n");
        
                int counter = 1;
    
                System.out.println("CODE:\t\t\t\tITEM:\t\t\t\tQUANTITY:\t\t\t\tPRICE:");
                
                for (int i = 0; i < CART.size(); i++) 
                    System.out.println(
                        counter++ + "\t\t" +
                        CART.get(i).get(0) + "\t\t\t\t" + 
                        CART.get(i).get(1) + "\t\t" + 
                        CART.get(i).get(2) + "\t\t\t\t"
                    ); 

                System.out.println();
                gsystem.PRINTLN(50, "TOTAL AMOUNT:  " + TOTAL_AMOUNT + "\n");
                System.out.println();
                gsystem.PRINT(50, "ENTER CHOICE:  ");
                int mc = erh.getChoice(0, 2);
        
                switch (mc)
                {
                    case 0 -> { return; }
                    case 1 -> RemoveToCart(CART);
                    case 2 -> CheckOut(CART);
                }
            }
        }
    }

    private void Order(ArrayList<ArrayList<Object>> CART, HashMap<String, Double> MENU, String CAT) 
    {
        boolean orderAgain;

        do
        { 
            orderAgain = false;

            gsystem.CLS();
            gsystem.HEADER();
            gsystem.GENERATE_TITLE(CAT);
            System.out.println();
            gsystem.PRINTLN(50, "[0] - CANCEL\n");

            if (MENU.isEmpty())
            {
                gsystem.PRINTLN(50, "THERE ARE NO ITEMS IN THIS MENU\n");
                System.out.println();
                gsystem.PRINT(50, "ENTER CHOICE:  ");
                int od = erh.getChoice(0, 0);
        
                if (od == 0) return;
            }
            else
            {
                gsystem.PRINTLN(45, gsystem.FILL(64, '-'));
                gsystem.PRINTF(45, "%-7s%-45s%-12s%n", "CODE", "ITEM", "PRICE");
                gsystem.PRINTLN(45, gsystem.FILL(64, '-') + "\n");
                int counter = 1;

                for (String item : MENU.keySet()) 
                {
                    if (item.length() > 32) 
                    {
                        ArrayList<String> multiLine = gsystem.MULTILINE(item, 64);

                        gsystem.PRINTF(45, "%-7s%-45s%-12s%n", counter++, multiLine.get(0), "Php " + MENU.get(item));
                        
                        for (int i = 1; i < multiLine.size(); i++)
                            gsystem.PRINTF(45, "%-7s%-45s%-12s%n", "", multiLine.get(i), "");
                    } 
                    else 
                    {
                        gsystem.PRINTF(45, "%-7s%-45s%-12s%n", counter++, item, "Php " + MENU.get(item));
                    }
                }

                System.out.println();
                gsystem.PRINT(50, "ENTER CODE OF YOUR DESIRED MENU ITEM:  ");
                int itemID = erh.getChoice(0, MENU.size());

                switch (itemID)
                {
                    case 0  -> { return; }
                    default -> orderAgain = AddToCart(CART, MENU, itemID);
                }
            }
        }
        while (orderAgain);
    }

    private boolean AddToCart(ArrayList<ArrayList<Object>> CART, HashMap<String, Double> MENU, int itemID) 
    {
        String item = MENU.keySet().toArray()[itemID - 1].toString();

        System.out.println();
        gsystem.PRINT(50, "ENTER QUANTITY  :  ");
        int quantity = erh.getQuantity();

        double value = MENU.get(item);
        double amount = value * quantity;
        TOTAL_AMOUNT += amount;

        System.out.println();
        gsystem.PRINTLN(50, gsystem.FILL(57, '='));
        if (item.length() > 32)
        {
            ArrayList<String> lines = gsystem.MULTILINE(item, 42);
            gsystem.PRINTLN(50, "ITEM        :  " + lines.get(0));
            for (int i = 1; i < lines.size(); i++) 
            gsystem.PRINTLN(50, gsystem.FILL(15, ' ') + lines.get(i));
        }
        else { gsystem.PRINTLN(50, "ITEM        :  " + item); }

        gsystem.PRINTLN(50, "QUANTITY    :  " + quantity + "x");
        gsystem.PRINTLN(50, "AMOUNT      :  " + "Php " + amount);
        gsystem.PRINTLN(50, "TOTAL AMOUNT:  " + "Php " + TOTAL_AMOUNT);
        gsystem.PRINTLN(50, gsystem.FILL(57, '='));
        System.out.println();
        gsystem.PRINT(50, "ARE YOU SURE YOU WANT TO ADD THIS ITEM? (y/n)  :");
        boolean confirmAdd = erh.getConfirmation();

        if (confirmAdd)
        {
            CART.add(new ArrayList<>() 
            {
                {
                    add(item);
                    add(quantity);
                    add(amount);
                }
            });

            System.out.println();
            gsystem.PRINTLN(50, "ITEM HAS BEEN PLACED SUCCESSFULLY!");
        }
        else 
        {
            System.out.println();
            gsystem.PRINT(50, "DO YOU WANT TO ORDER OTHER ITEM INSTEAD? (y/n):  ");
            boolean confirm = erh.getConfirmation();

            if (confirm) return true;
        }    

        gsystem.GENERATE_TITLE("null");
        gsystem.WAIT();
        return false;
    }

    private void RemoveToCart(ArrayList<ArrayList<Object>> CART) 
    {
        gsystem.PRINT(50, "GIVE THE ITEM CODE THAT YOU WANT TO REMOVE:  ");
        int rc = erh.getChoice(0, CART.size());

        switch (rc)
        {
            case 0 -> { return; }
            default -> 
            {
                String item = CART.get(rc - 1).get(0).toString();
                int quantity = Integer.parseInt(CART.get(rc - 1).get(1).toString());
                double amount = Double.parseDouble(CART.get(rc - 1).get(2).toString());

                System.out.println();
                gsystem.PRINTLN(50, gsystem.FILL(57, '='));
                if (item.length() > 32)
                {
                    ArrayList<String> lines = gsystem.MULTILINE(item, 42);
                    gsystem.PRINTLN(50, "ITEM        :  " + lines.get(0));
                    for (int i = 1; i < lines.size(); i++) 
                    gsystem.PRINTLN(50, gsystem.FILL(15, ' ') + lines.get(i));
                }
                else { gsystem.PRINTLN(50, "ITEM        :  " + item); }
        
                gsystem.PRINTLN(50, "QUANTITY    :  " + quantity + "x");
                gsystem.PRINTLN(50, "AMOUNT  : " + "Php " + amount);
                gsystem.PRINTLN(50, gsystem.FILL(57, '='));
                System.out.println();
                gsystem.PRINT(50, "ARE YOU SURE YOU WANT TO REMOVE THIS ITEM? (y/n):  ");

                boolean confirm = erh.getConfirmation();

                if (confirm)
                {
                    CART.remove(rc - 1); 
                    TOTAL_AMOUNT -= amount;
                    gsystem.PRINTLN(50, "ITEM HAS BEEN REMOVED SUCCESSFULLY!");
                }
                else 
                {  
                    gsystem.PRINTLN(50, "ITEM REMOVAL HAS BEEN CANCELLED SUCCESSFULLY!");
                }
            }
        }

        gsystem.GENERATE_TITLE("null");
        gsystem.WAIT();
    }

    private void CheckOut(ArrayList<ArrayList<Object>> CART)
    {
        gsystem.PRINT(50, "DO YOU HAVE SENIOR CITIZEN ID / PWD ID (y/n):  ");
        boolean haveDiscount = erh.getConfirmation();
        int totalItems = 0;
        
        for (int i = 0; i < CART.size(); i++) 
            totalItems += Integer.parseInt(CART.get(i).get(1).toString());

            System.out.println();
        gsystem.PRINTLN(50, "TOTAL ITEMS     :  " + totalItems);
        gsystem.PRINTLN(50, "ORIGINAL TOTAL  :  Php " + TOTAL_AMOUNT);

        if (haveDiscount)
        {
            gsystem.PRINTLN(50, "DISCOUNT        :  20%");
            gsystem.PRINTLN(50, "DISCOUNTED TOTAL:  Php " + (TOTAL_AMOUNT - (TOTAL_AMOUNT * 0.20)));
        }
        
        System.out.println();
        gsystem.PRINT(50, "CHECK OUT? (y/n):  ");
        boolean checkout = erh.getConfirmation();

        if (checkout)
        {
            TOTAL_AMOUNT = 0;
            CART.clear();
            gsystem.PRINTLN(50, "CHECKED OUT SUCCESSFULLY!");
        }

        gsystem.GENERATE_TITLE("null");
        gsystem.WAIT();
    }
}
