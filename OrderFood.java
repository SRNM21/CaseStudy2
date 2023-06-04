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
public class OrderFood extends ErrorHandler
{   
    // menu items
    protected static HashMap<String, Double> MEALS_ITEMS      = new HashMap<>();
    protected static HashMap<String, Double> SANDWICH_ITEMS   = new HashMap<>();
    protected static HashMap<String, Double> DRINKS_ITEMS     = new HashMap<>();

    // cart and amount
    private ArrayList<ArrayList<Object>> CART;
    private BigDecimal TOTAL_AMOUNT = new BigDecimal(0);
       
    // where user gets their cart and start choosing items
    OrderFood()
    {
        CART = new ArrayList<>();
        int cat = 0;

        while (true)
        {
            cls();
            printHeader();
            generateTitle("order_food");
            line();
            button(0, "BACK");
            button(1, "MY CART");
            button(2, "MEALS");
            button(3, "SANDWICH");
            button(4, "DRINKS");
            line();
            printLine(55,WHI + "ENTER CHOICE" + RES);
            pointer();
            cat = getChoice(0, 4);
            
            switch (cat)
            {
                case 0 -> { return; }
                case 1 -> myCart();
                case 2 -> order(MEALS_ITEMS,    "order_food_meals");
                case 3 -> order(SANDWICH_ITEMS, "order_food_sandwich");
                case 4 -> order(DRINKS_ITEMS,   "order_food_drinks");
            }
        }
    }

    // check the items on the cart 
    private void myCart()
    {
        while (true)
        {
            cls();
            printHeader();
            generateTitle("my_cart");
            line(); 

            // display if the cart is empty
            if (CART.isEmpty())
            {
                printNull();
                printLine(62, WHI + "THERE ARE NO ITEMS IN THIS CART\n" + RES);
                line();
                generateTitle("null");
                pause();

                return;
            }
            // display items if the cart is not empty
            else
            {
                String format = "%-7s%-45s%-7s%-12s%n";

                button(0, "BACK");
                button(1, "REMOVE ITEM");
                button(2, "CHECK OUT");
    
                printLine(39, WHI + fill(75, '-') + YEL);
                printFormat(40, format, "CODE", "ITEM", "QTY", "PRICE");
                printLine(39, WHI + fill(75, '-'));

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
                        ArrayList<String> multiLine = wrapText(item, 42);

                        printFormat(40, format, counter++, multiLine.get(0), quantity, amountFormat);
                        
                        for (int i = 1; i < multiLine.size(); i++)
                            printFormat(40, format, "", multiLine.get(i), "", "");
                    } 
                    else 
                    {
                        printFormat(40, format, counter++, item, quantity, amountFormat);
                    }
                }
                
                printLine(39, WHI + fill(75, '-')+ RES);
                line();
                printLine(55, YEL + "TOTAL AMOUNT" + WHI + ":  " + TOTAL_AMOUNT.setScale(2, RoundingMode.DOWN) + "\n");
                line();
                printLine(55, WHI + "ENTER CHOICE" + RES);
                pointer();
                int choice = getChoice(0, 2);
        
                switch (choice)
                {
                    case 0 -> { return; }
                    case 1 -> removeToCart();
                    case 2 -> 
                    { 
                        checkOut(); 
                        return;
                    }
                }
            }
        }
    }

    // provide user selction of items
    private void order(HashMap<String, Double> MENU, String CAT) 
    {
        boolean orderAgain;

        do
        { 
            orderAgain = false;
            int choice = 0;

            cls();
            printHeader();
            generateTitle(CAT);
            line();
            button(0, "CANCEL");
            line();

            // display if the category's menu is empty
            if (MENU.isEmpty())
            {
                printNull();
                printLine(60, WHI + "THERE ARE NO ITEMS IN THIS MENU\n" + RES);
                line();
                printLine(55,WHI + "ENTER CHOICE" + RES);
                pointer();
                choice = getChoice(0, 0);
            }
            // display items if the category's menu is not empty
            else
            {
                String format = "%-7s%-64s%-12s%n";

                printLine(34, WHI + fill(87, '-') + YEL);
                printFormat(35, format, "CODE", "ITEM", "PRICE");
                printLine(34, WHI + fill(87, '-'));
                int counter = 1;

                // display all category's menu items and their price
                for (String item : MENU.keySet()) 
                {
                    double price = MENU.get(item);
                    String priceFormat = "Php " + price;
                    
                    if (item.length() > 32) 
                    {
                        ArrayList<String> multiLine = wrapText(item, 64);

                        printFormat(35, format, counter++, multiLine.get(0), priceFormat);
                        
                        for (int i = 1; i < multiLine.size(); i++)
                            printFormat(35, format, "", multiLine.get(i), "");
                    } 
                    else 
                    {
                        printFormat(35, format, counter++, item, priceFormat);
                    }
                }

                // get the item's code
                printLine(34, WHI + fill(87, '-'));
                line();
                printLine(55, WHI + "ENTER CODE OF YOUR DESIRED MENU ITEM");
                pointer();
                choice = getChoice(0, MENU.size());
            }
            
            switch (choice)
            {
                case 0  -> { return; }
                default -> orderAgain = addToCart(MENU, choice);
            }
        }
        while (orderAgain);
    }

    // function that will add item to the cart
    private boolean addToCart(HashMap<String, Double> MENU, int itemID) 
    {
        String item = MENU.keySet().toArray()[itemID - 1].toString();

        line();
        printLine(55, WHI + "ENTER QUANTITY" + RES);
        pointer();
        int quantity = getQuantity();

        double price = MENU.get(item);

        line();
        printLine(44, fill(66, '='));

        // dsplay item's information
        if (item.length() > 32)
        {
            ArrayList<String> lines = wrapText(item, 53);
            
            printLine(45, YEL + "ITEM" + WHI + "    :  " + lines.get(0));

            for (int i = 1; i < lines.size(); i++) 
                printLine(45, fill(11, ' ') + lines.get(i));
        }
        else 
        { 
            printLine(45, YEL + "ITEM" + WHI + "    :  " + item); 
        }

        printLine(45, YEL + "PRICE" + WHI + "   :  " + "Php " + price);
        printLine(45, YEL + "QUANTITY" + WHI + ":  " + quantity + "x");
        printLine(44, WHI + fill(66, '='));
        line();
        
        printLine(55, WHI + "ARE YOU SURE YOU WANT TO ADD THIS ITEM? (y/n)" + RES);
        pointer();
        boolean confirmAdd = getConfirmation();

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
            
            line();
            printLine(60, GRE + "ITEM HAS BEEN PLACED SUCCESSFULLY!" + RES);
        }
        // otherwise, cancel adding item to cart
        else 
        {
            line();
            printLine(55, WHI + "DO YOU WANT TO ORDER OTHER ITEM INSTEAD? (y/n)" + RES);
            pointer();
            boolean confirm = getConfirmation();

            line();
            if (confirm) return true;
            else printLine(58, RED + "ADDING ITEM IS CANCELLED SUCCESSFULLY!" + RES);
        }    

        generateTitle("null");
        pause();
        return false;
    }

    // function that will remove item to the cart
    private void removeToCart() 
    {
        line();
        printLine(55, WHI + "GIVE THE ITEM CODE THAT YOU WANT TO REMOVE" + RES);
        pointer();
        int rc = getChoice(0, CART.size());

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

                line();
                printLine(44, fill(66, '='));

                if (item.length() > 32)
                {
                    ArrayList<String> lines = wrapText(item, 52);
                    printLine(45, YEL + "ITEM" + WHI + "    :  " + lines.get(0));

                    for (int i = 1; i < lines.size(); i++) 
                        printLine(45, fill(11, ' ') + lines.get(i));
                }
                else 
                { 
                    printLine(45, YEL + "ITEM" + WHI + "    :  " + item); 
                }
        
                printLine(45, YEL + "QUANTITY" + WHI + ":  " + quantity + "x");
                printLine(45, YEL + "AMOUNT" + WHI + "  :  " + "Php " + amount);
                printLine(44, fill(66, '='));

                line();
                printLine(55, WHI + "ARE YOU SURE YOU WANT TO REMOVE THIS ITEM? (y/n)" + RES);
                pointer();
                boolean confirm = getConfirmation();
                line();

                // remove item if confirmed
                if (confirm)
                {
                    CART.remove(rc - 1); 
                    TOTAL_AMOUNT = TOTAL_AMOUNT.subtract(new BigDecimal(amount));
                    printLine(60, GRE + "ITEM HAS BEEN REMOVED SUCCESSFULLY!" + RES);
                }
                // otherwise, cancel removing item to cart
                else 
                {  
                    printLine(55, RED + "ITEM REMOVAL HAS BEEN CANCELLED SUCCESSFULLY!" + RES);
                }
            }
        }

        generateTitle("null");
        pause();
    }

    // function where user check out their items
    private void checkOut()
    {
        line();
        printLine(55, WHI + "DO YOU HAVE SENIOR CITIZEN ID / PWD ID (y/n)" + RES);
        pointer();
        boolean haveDiscount = getConfirmation();

        // give discount if the customer is senior/pwd
        BigDecimal discountPercent = new BigDecimal(haveDiscount ? 0.20 : 0);
        BigDecimal DISCOUNT = TOTAL_AMOUNT.multiply(discountPercent);
        BigDecimal TOTAL_AMOUNT_DIS = TOTAL_AMOUNT.subtract(DISCOUNT);

        // display customer's order information
        line();
        printLine(44, WHI + fill(66, '=') + RES);
        printLine(45, YEL + "AMOUNT" + WHI + "      :  Php " + TOTAL_AMOUNT.setScale(2, RoundingMode.DOWN));
        printLine(45, YEL + "DISCOUNT" + WHI + "    :  Php " + DISCOUNT.setScale(2, RoundingMode.DOWN));
        printLine(45, YEL + "TOTAL AMOUNT" + WHI + ":  Php " + TOTAL_AMOUNT_DIS.setScale(2, RoundingMode.HALF_UP));
        printLine(44, WHI + fill(66, '=') + RES);
        line();

        printLine(55, WHI + "CHECK OUT? (y/n)" + RES);
        pointer();
        boolean checkout = getConfirmation();
        line();
        
        // clear out the cart
        if (checkout)
        {
            TOTAL_AMOUNT = TOTAL_AMOUNT_DIS;
            String refNum = generateRefNum();

            saveOrderInfo(refNum);

            TOTAL_AMOUNT = new BigDecimal(0);
            CART.clear();

            printLine(61, WHI + "REFERENCE NUMBER:  " + GRE + refNum + RES);
            line();
            printLine(65, GRE + "CHECKED OUT SUCCESSFULLY!" + RES); 
        }
        // otherwise, cancel check out
        else 
        { 
            printLine(61,  RED + "CHECK OUT CANCELLED SUCCESSFULLY!" + RES); 
        }

        generateTitle("null");
        pause();
    }

    // function to save users order information
    protected void saveOrderInfo(String refNum)
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");

        String DAT  = now.format(formatter);
        String date = DAT.substring(0, 10);
        String time = DAT.substring(11);
        
        addToReports(date, time, refNum, TOTAL_AMOUNT);
    }
}