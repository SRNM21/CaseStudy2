import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  The {@code OrderFood} class is where the user select category and pick the food they want to order.
 * 
 *  <p>
 *  This System requires the {@code ITEMS.txt} file to provide user selctions. It reads the file, retrieve the items,
 *  and display it to the menu. The orders from this class will be pass into the {@code Administration}.
 *  </p>
 * 
 *  @author Neelian Mata
 *  @author Jarius Maui Pineda
 *  @author Niño Greg Gregorio
 *  @since 1.0
 */
public class OrderFood 
{   
    // class
    private final ErrorHandler erh = new ErrorHandler(); 
    private final GSystem gsystem = new GSystem();

    // cart and amount 
    private ArrayList<ArrayList<Object>> CART;
    private BigDecimal TOTAL_AMOUNT = new BigDecimal(0);
       
    // where user gets their cart and start choosing items
    OrderFood()
    {
        CART = new ArrayList<>();
        
        while (true)
        {
            gsystem.cls();
            gsystem.printHeader();
            gsystem.generateTitle("order_food");
            System.out.println();
            gsystem.button(0, "BACK");
            gsystem.button(1, "MY CART");
            gsystem.button(2, "MEALS");
            gsystem.button(3, "SANDWICH");
            gsystem.button(4, "DRINKS");
            System.out.println();
            gsystem.printLine(55,"ENTER CHOICE");
            gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
            int cat = erh.getChoice(0, 4);
            
            switch (cat)
            {
                case 0 -> { return; }
                case 1 -> myCart(CART);
                case 2 -> order(CART, MainProcess.MEALS_ITEMS,    "order_food_meals");
                case 3 -> order(CART, MainProcess.SANDWICH_ITEMS, "order_food_sandwich");
                case 4 -> order(CART, MainProcess.DRINKS_ITEMS,   "order_food_drinks");
            }
        }
    }

    // check the items on the cart 
    private void myCart(ArrayList<ArrayList<Object>> CART)
    {
        while (true)
        {
            gsystem.cls();
            gsystem.printHeader();
            gsystem.generateTitle("my_cart");
            System.out.println(); 

            // display if the cart is empty
            if (CART.isEmpty())
            {
                gsystem.printLine(62, "THERE ARE NO ITEMS IN THIS CART\n");
                System.out.println();
                gsystem.generateTitle("null");
                gsystem.pause();

                return;
            }
            // display items if the cart is not empty
            else
            {
                String format = "%-7s%-45s%-7s%-12s%n";
                gsystem.button(0, "BACK");
                gsystem.button(1, "REMOVE ITEM");
                gsystem.button(2, "CHECK OUT");
    
                gsystem.printLine(39, gsystem.fill(75, '-') + gsystem.YEL);
                gsystem.printFormat(40, format, "CODE", "ITEM", "QTY", "PRICE");
                gsystem.printLine(39, gsystem.RES + gsystem.fill(75, '-'));

                int counter = 1;

                // display all items quantity and amount
                for (ArrayList<Object> cartItems : CART) 
                {   
                    String item         = cartItems.get(0).toString();
                    int quantity        = (int) cartItems.get(1);
                    double amount       = new BigDecimal(cartItems.get(2).toString()).doubleValue();
                    String amountFormat = "Php " + amount;

                    if (item.length() > 32) 
                    {
                        ArrayList<String> multiLine = gsystem.wrapText(item, 42);

                        gsystem.printFormat(40, format, counter++, multiLine.get(0), quantity, amountFormat);
                        
                        for (int i = 1; i < multiLine.size(); i++)
                            gsystem.printFormat(40, format, "", multiLine.get(i), "", "");
                    } 
                    else 
                    {
                        gsystem.printFormat(40, format, counter++, item, quantity, amountFormat);
                    }
                }
                
                gsystem.printLine(39, gsystem.fill(75, '-'));
                System.out.println();
                gsystem.printLine(50, gsystem.YEL + "TOTAL AMOUNT" + gsystem.RES + ":  " + TOTAL_AMOUNT.setScale(2, RoundingMode.DOWN) + "\n");
                System.out.println();
                gsystem.printLine(55,"ENTER CHOICE");
                gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
                int mc = erh.getChoice(0, 2);
        
                switch (mc)
                {
                    case 0 -> { return; }
                    case 1 -> removeToCart(CART);
                    case 2 -> checkOut(CART);
                }
            }
        }
    }

    // provide user selction of items
    private void order(ArrayList<ArrayList<Object>> CART, HashMap<String, Double> MENU, String CAT) 
    {
        boolean orderAgain;

        do
        { 
            orderAgain = false;
            int od = 0;

            gsystem.cls();
            gsystem.printHeader();
            gsystem.generateTitle(CAT);
            System.out.println();
            gsystem.button(0, "CANCEL");

            // display if the category's menu is empty
            if (MENU.isEmpty())
            {
                gsystem.printLine(50, "THERE ARE NO ITEMS IN THIS MENU\n");
                System.out.println();
                gsystem.printLine(55,"ENTER CHOICE");
                gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
                od = erh.getChoice(0, 0);
            }
            // display items if the category's menu is not empty
            else
            {
                String format = "%-7s%-64s%-12s%n";

                gsystem.printLine(34, gsystem.fill(87, '-') + gsystem.YEL);
                gsystem.printFormat(35, format, "CODE", "ITEM", "PRICE");
                gsystem.printLine(34, gsystem.RES + gsystem.fill(87, '-'));
                int counter = 1;

                // display all category's menu items and their price
                for (String item : MENU.keySet()) 
                {
                    double price = MENU.get(item);
                    String priceFormat = "Php " + price;
                    
                    if (item.length() > 32) 
                    {
                        ArrayList<String> multiLine = gsystem.wrapText(item, 64);

                        gsystem.printFormat(35, format, counter++, multiLine.get(0), priceFormat);
                        
                        for (int i = 1; i < multiLine.size(); i++)
                            gsystem.printFormat(35, format, "", multiLine.get(i), "");
                    } 
                    else 
                    {
                        gsystem.printFormat(35, format, counter++, item, priceFormat);
                    }
                }

                // get the item's code
                gsystem.printLine(34, gsystem.fill(87, '-'));
                System.out.println();
                gsystem.printLine(55, "ENTER CODE OF YOUR DESIRED MENU ITEM");
                gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
                od = erh.getChoice(0, MENU.size());

            }
            
            switch (od)
            {
                case 0  -> { return; }
                default -> orderAgain = addToCart(CART, MENU, od);
            }
        }
        while (orderAgain);
    }

    // function that will add item to the cart
    private boolean addToCart(ArrayList<ArrayList<Object>> CART, HashMap<String, Double> MENU, int itemID) 
    {
        String item = MENU.keySet().toArray()[itemID - 1].toString();

        System.out.println();
        gsystem.printLine(55, "ENTER QUANTITY");
        gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
        int quantity = erh.getQuantity();

        double price = MENU.get(item);

        System.out.println();
        gsystem.printLine(44, gsystem.fill(66, '='));

        // dsplay item's information
        if (item.length() > 32)
        {
            ArrayList<String> lines = gsystem.wrapText(item, 53);
            
            gsystem.printLine(45, gsystem.YEL + "ITEM" + gsystem.RES + "    :  " + lines.get(0));

            for (int i = 1; i < lines.size(); i++) 
                gsystem.printLine(45, gsystem.fill(11, ' ') + lines.get(i));
        }
        else 
        { 
            gsystem.printLine(45, gsystem.YEL + "ITEM" + gsystem.RES + "    :  " + item); 
        }

        gsystem.printLine(45, gsystem.YEL + "PRICE" + gsystem.RES + "   :  " + "Php " + price);
        gsystem.printLine(45, gsystem.YEL + "QUANTITY" + gsystem.RES + ":  " + quantity + "x");
        gsystem.printLine(44, gsystem.fill(66, '='));
        System.out.println();
        
        gsystem.printLine(55, "ARE YOU SURE YOU WANT TO ADD THIS ITEM? (y/n)");
        gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
        boolean confirmAdd = erh.getConfirmation();

        // add item to cart if confirmed
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
            gsystem.printLine(60, "ITEM HAS BEEN PLACED SUCCESSFULLY!");
        }
        // otherwise, cancel adding item to cart
        else 
        {
            System.out.println();
            gsystem.printLine(50, "DO YOU WANT TO ORDER OTHER ITEM INSTEAD? (y/n)");
            gsystem.prints(50, gsystem.GRE + ">> " + gsystem.RES);
            boolean confirm = erh.getConfirmation();

            if (confirm) return true;
        }    

        gsystem.generateTitle("null");
        gsystem.pause();
        return false;
    }

    // function that will remove item to the cart
    private void removeToCart(ArrayList<ArrayList<Object>> CART) 
    {
        System.out.println();
        gsystem.printLine(50, "GIVE THE ITEM CODE THAT YOU WANT TO REMOVE");
        gsystem.prints(50, gsystem.GRE + ">> " + gsystem.RES);
        int rc = erh.getChoice(0, CART.size());

        // find the item's code
        switch (rc)
        {
            case 0 -> { return; }
            default -> 
            {
                // display item, quantity, and its amount
                String item     = CART.get(rc - 1).get(0).toString();
                int quantity    = (int) CART.get(rc - 1).get(1);
                double amount   = new BigDecimal(CART.get(rc - 1).get(2).toString()).doubleValue();

                System.out.println();
                gsystem.printLine(44, gsystem.fill(66, '='));

                if (item.length() > 32)
                {
                    ArrayList<String> lines = gsystem.wrapText(item, 52);
                    gsystem.printLine(45, gsystem.YEL + "ITEM" + gsystem.RES + "    :  " + lines.get(0));

                    for (int i = 1; i < lines.size(); i++) 
                        gsystem.printLine(45, gsystem.fill(11, ' ') + lines.get(i));
                }
                else 
                { 
                    gsystem.printLine(45, gsystem.YEL + "ITEM" + gsystem.RES + "    :  " + item); 
                }
        
                gsystem.printLine(45, gsystem.YEL + "QUANTITY" + gsystem.RES + ":  " + quantity + "x");
                gsystem.printLine(45, gsystem.YEL + "AMOUNT" + gsystem.RES + "  :  " + "Php " + amount);
                gsystem.printLine(44, gsystem.fill(66, '='));

                System.out.println();
                gsystem.printLine(45, "ARE YOU SURE YOU WANT TO REMOVE THIS ITEM? (y/n)");
                gsystem.prints(45, gsystem.GRE + ">> " + gsystem.RES);
                boolean confirm = erh.getConfirmation();
                System.out.println();

                // remove item if confirmed
                if (confirm)
                {
                    CART.remove(rc - 1); 
                    TOTAL_AMOUNT = TOTAL_AMOUNT.subtract(new BigDecimal(amount));
                    gsystem.printLine(60, "ITEM HAS BEEN REMOVED SUCCESSFULLY!");
                }
                // otherwise, cancel removing item to cart
                else 
                {  
                    gsystem.printLine(55, "ITEM REMOVAL HAS BEEN CANCELLED SUCCESSFULLY!");
                }
            }
        }

        gsystem.generateTitle("null");
        gsystem.pause();
    }

    // function where user check out their items
    private void checkOut(ArrayList<ArrayList<Object>> CART)
    {
        System.out.println();
        gsystem.printLine(55, "DO YOU HAVE SENIOR CITIZEN ID / PWD ID (y/n)");
        gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
        boolean haveDiscount = erh.getConfirmation();

        // give discount if the customer is senior/pwd
        BigDecimal discountPercent = new BigDecimal(haveDiscount ? 0.20 : 0);
        BigDecimal DISCOUNT = TOTAL_AMOUNT.multiply(discountPercent);
        BigDecimal TOTAL_AMOUNT_DIS = TOTAL_AMOUNT.subtract(DISCOUNT);

        // display customer's order information
        System.out.println();
        gsystem.printLine(44, gsystem.fill(66, '='));
        gsystem.printLine(45, gsystem.YEL + "AMOUNT" + gsystem.RES + "      :  Php " + TOTAL_AMOUNT.setScale(2, RoundingMode.DOWN));
        gsystem.printLine(45, gsystem.YEL + "DISCOUNT" + gsystem.RES + "    :  Php " + DISCOUNT.setScale(2, RoundingMode.DOWN));
        gsystem.printLine(45, gsystem.YEL + "TOTAL AMOUNT" + gsystem.RES + ":  Php " + TOTAL_AMOUNT_DIS.setScale(2, RoundingMode.HALF_UP));
        gsystem.printLine(44, gsystem.fill(66, '='));
        System.out.println();

        gsystem.printLine(55, "CHECK OUT? (y/n)");
        gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
        boolean checkout = erh.getConfirmation();
        System.out.println();
        
        // clear out the cart
        if (checkout)
        {
            TOTAL_AMOUNT = TOTAL_AMOUNT_DIS;

            saveOrderInfo();

            TOTAL_AMOUNT = new BigDecimal(0);
            CART.clear();

            gsystem.printLine(65, "CHECKED OUT SUCCESSFULLY!");
        }
        // otherwise, cancel check out
        else 
        { 
            gsystem.printLine(61, "CHECK OUT CANCELLED SUCCESSFULLY!"); 
        }

        gsystem.generateTitle("null");
        gsystem.pause();
    }

    // function to save users order information
    protected void saveOrderInfo()
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");

        String DAT  = now.format(formatter);
        String date = DAT.substring(0, 10);
        String time = DAT.substring(11);

        String refNum = gsystem.generateRefNum();
        
        gsystem.addToReports(date, time, refNum, TOTAL_AMOUNT);
    }
}