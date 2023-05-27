import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class OrderFood 
{   
    private final ErrorHandler erh = new ErrorHandler(); 
    private final GSystem gsystem = new GSystem();

    private ArrayList<ArrayList<Object>> CART;
    private BigDecimal TOTAL_AMOUNT = new BigDecimal(0);
       
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
                case 2 -> Order(CART, MainProcess.MEALS_ITEMS,    "order_food_meals");
                case 3 -> Order(CART, MainProcess.SANDWICH_ITEMS, "order_food_sandwich");
                case 4 -> Order(CART, MainProcess.DRINKS_ITEMS,   "order_food_drinks");
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

            gsystem.PRINTLN(50, "[0] - BACK\n");

            if (CART.isEmpty())
            {
                gsystem.PRINTLN(50, "THERE ARE NO ITEMS IN THIS CART\n");
                System.out.println();
                gsystem.PRINT(50, "ENTER CHOICE  :  ");
                int mc = erh.getChoice(0, 0);
        
                if (mc == 0) return;
            }
            else
            {
                String format = "%-7s%-45s%-7s%-12s%n";
                gsystem.PRINTLN(50, "[1] - REMOVE ITEM\n");
                gsystem.PRINTLN(50, "[2] - CHECK OUT\n");
    
                gsystem.PRINTLN(39, gsystem.FILL(75, '-'));
                gsystem.PRINTF(40, format, "CODE", "ITEM", "QTY", "PRICE");
                gsystem.PRINTLN(39, gsystem.FILL(75, '-'));

                int counter = 1;

                for (ArrayList<Object> cartItems : CART) 
                {   
                    String item     = cartItems.get(0).toString();
                    int quantity    = (int) cartItems.get(1);
                    double amount   = new BigDecimal(cartItems.get(2).toString()).doubleValue();
                    String amountFormat = "Php " + amount;

                    if (item.length() > 32) 
                    {
                        ArrayList<String> multiLine = gsystem.MULTILINE(item, 42);

                        gsystem.PRINTF(40, format, counter++, multiLine.get(0), quantity, amountFormat);
                        
                        for (int i = 1; i < multiLine.size(); i++)
                            gsystem.PRINTF(40, format, "", multiLine.get(i), "", "");
                    } 
                    else 
                    {
                        gsystem.PRINTF(40, format, counter++, item, quantity, amountFormat);
                    }
                }
                gsystem.PRINTLN(39, gsystem.FILL(75, '-'));
                
                System.out.println();
                gsystem.PRINTLN(50, "TOTAL AMOUNT:  " + TOTAL_AMOUNT.setScale(2, RoundingMode.DOWN) + "\n");
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
            int od = 0;

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
                od = erh.getChoice(0, 0);
            }
            else
            {
                String format = "%-7s%-64s%-12s%n";

                gsystem.PRINTLN(34, gsystem.FILL(87, '-'));
                gsystem.PRINTF(35, format, "CODE", "ITEM", "PRICE");
                gsystem.PRINTLN(34, gsystem.FILL(87, '-'));
                int counter = 1;

                for (String item : MENU.keySet()) 
                {
                    double price = MENU.get(item);

                    String priceFormat = "Php " + price;
                    
                    if (item.length() > 32) 
                    {
                        ArrayList<String> multiLine = gsystem.MULTILINE(item, 64);

                        gsystem.PRINTF(35, format, counter++, multiLine.get(0), priceFormat);
                        
                        for (int i = 1; i < multiLine.size(); i++)
                            gsystem.PRINTF(35, format, "", multiLine.get(i), "");
                    } 
                    else 
                    {
                        gsystem.PRINTF(35, format, counter++, item, priceFormat);
                    }
                }

                gsystem.PRINTLN(34, gsystem.FILL(87, '-'));
                
                System.out.println();
                gsystem.PRINT(50, "ENTER CODE OF YOUR DESIRED MENU ITEM:  ");
                od = erh.getChoice(0, MENU.size());

            }
            
            switch (od)
            {
                case 0  -> { return; }
                default -> orderAgain = AddToCart(CART, MENU, od);
            }
        }
        while (orderAgain);
    }

    private boolean AddToCart(ArrayList<ArrayList<Object>> CART, HashMap<String, Double> MENU, int itemID) 
    {
        String item = MENU.keySet().toArray()[itemID - 1].toString();

        System.out.println();
        gsystem.PRINT(50, "ENTER QUANTITY:  ");
        int quantity = erh.getQuantity();

        double price = MENU.get(item);

        System.out.println();
        gsystem.PRINTLN(44, gsystem.FILL(66, '='));

        if (item.length() > 32)
        {
            ArrayList<String> lines = gsystem.MULTILINE(item, 53);
            gsystem.PRINTLN(45, "ITEM    :  " + lines.get(0));

            for (int i = 1; i < lines.size(); i++) 
                gsystem.PRINTLN(45, gsystem.FILL(11, ' ') + lines.get(i));
        }
        else { gsystem.PRINTLN(45, "ITEM    :  " + item); }

        gsystem.PRINTLN(45, "PRICE   :  " + "Php " + price);
        gsystem.PRINTLN(45, "QUANTITY:  " + quantity + "x");
        gsystem.PRINTLN(44, gsystem.FILL(66, '='));
        System.out.println();
        
        gsystem.PRINT(50, "ARE YOU SURE YOU WANT TO ADD THIS ITEM? (y/n):  ");
        boolean confirmAdd = erh.getConfirmation();

        if (confirmAdd)
        {
            BigDecimal pr = new BigDecimal(Double.toString(price));
            
            BigDecimal amount = pr.multiply(new BigDecimal(quantity));
            TOTAL_AMOUNT = TOTAL_AMOUNT.add(amount);
            
            CART.add(new ArrayList<>() 
            {{
                add(item);
                add(quantity);
                add(amount);  
            }});
            
            System.out.println();
            gsystem.PRINTLN(60, "ITEM HAS BEEN PLACED SUCCESSFULLY!");
        }
        else 
        {
            System.out.println();
            gsystem.PRINT(50, "DO YOU WANT TO ORDER OTHER ITEM INSTEAD? (y/n):  ");
            boolean confirm = erh.getConfirmation();

            if (confirm) return true;
        }    

        gsystem.GENERATE_TITLE("null");
        gsystem.PAUSE();
        return false;
    }

    private void RemoveToCart(ArrayList<ArrayList<Object>> CART) 
    {
        System.out.println();
        gsystem.PRINT(50, "GIVE THE ITEM CODE THAT YOU WANT TO REMOVE:  ");
        int rc = erh.getChoice(0, CART.size());

        switch (rc)
        {
            case 0 -> { return; }
            default -> 
            {
                String item = CART.get(rc - 1).get(0).toString();
                int quantity = (int) CART.get(rc - 1).get(1);
                double amount = new BigDecimal(CART.get(rc - 1).get(2).toString()).doubleValue();

                System.out.println();
                gsystem.PRINTLN(44, gsystem.FILL(66, '='));

                if (item.length() > 32)
                {
                    ArrayList<String> lines = gsystem.MULTILINE(item, 52);
                    gsystem.PRINTLN(45, "ITEM    :  " + lines.get(0));
                    for (int i = 1; i < lines.size(); i++) 
                    gsystem.PRINTLN(45, gsystem.FILL(11, ' ') + lines.get(i));
                }
                else { gsystem.PRINTLN(45, "ITEM    :  " + item); }
        
                gsystem.PRINTLN(45, "QUANTITY:  " + quantity + "x");
                gsystem.PRINTLN(45, "AMOUNT  :  " + "Php " + amount);
                gsystem.PRINTLN(44, gsystem.FILL(66, '='));

                System.out.println();
                gsystem.PRINT(45, "ARE YOU SURE YOU WANT TO REMOVE THIS ITEM? (y/n):  ");
                boolean confirm = erh.getConfirmation();

                System.out.println();
                if (confirm)
                {
                    CART.remove(rc - 1); 
                    TOTAL_AMOUNT = TOTAL_AMOUNT.subtract(new BigDecimal(amount));
                    gsystem.PRINTLN(60, "ITEM HAS BEEN REMOVED SUCCESSFULLY!");
                }
                else 
                {  
                    gsystem.PRINTLN(55, "ITEM REMOVAL HAS BEEN CANCELLED SUCCESSFULLY!");
                }
            }
        }

        gsystem.GENERATE_TITLE("null");
        gsystem.PAUSE();
    }

    private void CheckOut(ArrayList<ArrayList<Object>> CART)
    {
        System.out.println();
        gsystem.PRINT(50, "DO YOU HAVE SENIOR CITIZEN ID / PWD ID (y/n):  ");
        boolean haveDiscount = erh.getConfirmation();
        BigDecimal discountPercent = new BigDecimal(haveDiscount ? 0.20 : 0);
        
        BigDecimal DISCOUNT = TOTAL_AMOUNT.multiply(discountPercent);
        BigDecimal TOTAL_AMOUNT_DIS = TOTAL_AMOUNT.subtract(DISCOUNT);

        System.out.println();
        gsystem.PRINTLN(44, gsystem.FILL(66, '='));
        gsystem.PRINTLN(45, "AMOUNT      :  Php " + TOTAL_AMOUNT.setScale(2, RoundingMode.DOWN));
        gsystem.PRINTLN(45, "DISCOUNT    :  Php " + DISCOUNT.setScale(2, RoundingMode.DOWN));
        gsystem.PRINTLN(45, "TOTAL AMOUNT:  Php " + TOTAL_AMOUNT_DIS.setScale(2, RoundingMode.HALF_UP));
        gsystem.PRINTLN(44, gsystem.FILL(66, '='));

        System.out.println();
        gsystem.PRINT(50, "CHECK OUT? (y/n):  ");
        boolean checkout = erh.getConfirmation();

        System.out.println();
        if (checkout)
        {
            TOTAL_AMOUNT = new BigDecimal(0);
            
            int RefNum = new Random().nextInt(100000000, 999999999);

            CART.clear();
            gsystem.PRINTLN(65, "CHECKED OUT SUCCESSFULLY!");
        }
        else { gsystem.PRINTLN(61, "CHECK OUT CANCELLED SUCCESSFULLY!"); }

        gsystem.GENERATE_TITLE("null");
        gsystem.PAUSE();
    }
}