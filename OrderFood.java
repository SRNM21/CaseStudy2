import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.IntStream;

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
    protected static ArrayList<Object[]> MEALS_ITEMS      = new ArrayList<>();
    protected static ArrayList<Object[]> SANDWICH_ITEMS   = new ArrayList<>();
    protected static ArrayList<Object[]> DRINKS_ITEMS     = new ArrayList<>();

    // cart and amount
    private ArrayList<Object[]> CART;
    private BigDecimal TOTAL_AMOUNT = new BigDecimal(0);
    
    // where user gets their cart and start choosing items
    OrderFood()
    {
        CART = new ArrayList<>();

        while (true)
        {
            printHeader("order_food");
            button(0, "BACK");
            button(1, "MY CART");
            button(2, "MEALS");
            button(3, "SANDWICH");
            button(4, "DRINKS");
            line();
            printLine(55, WHI("ENTER CHOICE"));
            pointer();
            int cat = getChoice(0, 4);
            
            switch (cat)
            {
                case 0 -> { if (checkCart()) return; }
                case 1 -> myCart();
                case 2 -> order(MEALS_ITEMS,    "order_food_meals");
                case 3 -> order(SANDWICH_ITEMS, "order_food_sandwich");
                case 4 -> order(DRINKS_ITEMS,   "order_food_drinks");
            }
        }
    }

    // Check if the cart has items
    private boolean checkCart()
    {
        if (!CART.isEmpty())
        {
            line();
            printLine(55, RED("ARE YOU SURE TO GO BACK TO MENU"));
            printLine(55, WHI("ALL ITEMS IN THE CART WILL BE DISCARD (Y/N)"));
            pointer();
            boolean confirm = getConfirmation();

            // remove items from the cart if confirmed
            if (confirm)
            {
                for (Object[] items : CART) 
                {
                    String code     = items[0].toString();
                    char menu       = code.charAt(0);
                    int codeidx     = (code.charAt(1) - '0') - 1;
                    int quantity    = (int) items[2];

                    switch (menu)
                    {
                        case 'M' -> OrderFood.MEALS_ITEMS.get(codeidx)[2] = (int) OrderFood.MEALS_ITEMS.get(codeidx)[2] + quantity;
                        case 'S' -> OrderFood.SANDWICH_ITEMS.get(codeidx)[2] = (int) OrderFood.SANDWICH_ITEMS.get(codeidx)[2] + quantity;
                        case 'D' -> OrderFood.DRINKS_ITEMS.get(codeidx)[2] = (int)  OrderFood.DRINKS_ITEMS.get(codeidx)[2] + quantity;
                    }
                }
            }
            else
            {
                return false;
            }
        }

        return true;
    }

    // check the items on the cart 
    private void myCart()
    {
        while (true)
        {
            printHeader("my_cart");

            // display if the cart is empty
            if (CART.isEmpty())
            {
                printNull();
                printLine(62, WHI("THERE ARE NO ITEMS IN THIS CART\n"));
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
    
                printLine(39, WHI(fill(75, '-')) + YEL);
                printFormat(40, format, "CODE", "ITEM", "QTY", "AMOUNT");
                printLine(39, WHI(fill(75, '-')));

                // display all items quantity and amount
                for (Object[] cartItems : CART)
                {
                    String code         = cartItems[0].toString();
                    String item         = cartItems[1].toString();
                    int quantity        = (int) cartItems[2];
                    double amount       = new BigDecimal(cartItems[3].toString()).setScale(2, RoundingMode.UP).doubleValue();
                    String amountFormat = "Php " + amount;

                    if (item.length() > 32) 
                    {
                        ArrayList<String> multiLine = wrapText(item, 42);

                        printFormat(40, format, code, multiLine.get(0), quantity, amountFormat);
                        
                        for (int i = 1; i < multiLine.size(); i++)
                            printFormat(40, format, "", multiLine.get(i), "", "");
                    }
                    else
                    {
                        printFormat(40, format, code, item, quantity, amountFormat);
                    }
                }
                
                printLine(39, WHI(fill(75, '-')));
                line();
                printLine(55, YEL("TOTAL AMOUNT") + ":  " + TOTAL_AMOUNT.setScale(2, RoundingMode.UP) + "\n");
                line();
                printLine(55, WHI("ENTER CHOICE"));
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

    // function where user check out their items
    private void checkOut()
    {
        printHeader("my_cart");
        button(0, "BACK");
        button(1, "CASH");
        button(2, "CREDIT CARD");
        line();
        printLine(55, WHI("ENTER TYPE OF PAYMENT"));
        pointer();
        int mop = getChoice(0, 2);

        if (mop == 0) return;

        line();
        printLine(55, WHI("DO YOU HAVE SENIOR CITIZEN ID / PWD ID (y/n)"));
        pointer();
        boolean haveDiscount = getConfirmation();
        
        // give discount if the customer is senior/pwd
        BigDecimal discountPercent = new BigDecimal(haveDiscount ? 0.20 : 0);
        BigDecimal DISCOUNT = TOTAL_AMOUNT.multiply(discountPercent);
        BigDecimal TOTAL_AMOUNT_DIS = TOTAL_AMOUNT.subtract(DISCOUNT);

        // display customer's order information
        line();
        printLine(44, WHI(fill(66, '=')));
        printLine(45, YEL("AMOUNT") + "      :  Php " + TOTAL_AMOUNT.setScale(2, RoundingMode.HALF_UP));
        printLine(45, YEL("DISCOUNT") + "    :  Php " + DISCOUNT.setScale(2, RoundingMode.HALF_UP));
        printLine(45, YEL("TOTAL AMOUNT") + ":  Php " + TOTAL_AMOUNT_DIS.setScale(2, RoundingMode.HALF_UP));
        printLine(44, WHI(fill(66, '=')));

        line();
        printLine(55, WHI("CHECK OUT? (y/n)"));
        pointer();
        boolean checkout = getConfirmation();
        line();
        
        // clear out the cart
        if (checkout) 
        {
            printHeader("my_cart");

            switch (mop)    
            {   
                case 1 -> 
                {
                    while (true)
                    {
                        printHeader("my_cart");
                        printCash();
                        line();
                        printLine(55, WHI("AMOUNT TO PAY"));
                        pointer();  
                        printLine(0, TOTAL_AMOUNT.setScale(2, RoundingMode.HALF_UP).toPlainString());
                        line();
                        printLine(55, WHI("ENTER CASH"));
                        pointer();  
                        double cash = getAmount();
                        
                        if (cash <= TOTAL_AMOUNT.doubleValue()) 
                        {
                            line();
                            printLine(55, RED("INSUFFICIENT CASH"));
                            printLine(55, WHI("PLEASE ENTER THE RIGHT AMOUNT"));

                            generateTitle("null");
                            pause();
                        }
                        else 
                        {
                            line();
                            printLine(44, WHI(fill(66, '=')));
                            printLine(45, YEL("TOTAL AMOUNT") + ":  Php " + TOTAL_AMOUNT.setScale(2, RoundingMode.HALF_UP));
                            printLine(45, YEL("CASH") + ":          Php " + cash);
                            BigDecimal change = new BigDecimal((cash - TOTAL_AMOUNT.doubleValue()));
                            printLine(45, YEL("CHANGE") + ":        Php " + change.setScale(2, RoundingMode.HALF_UP));
                            printLine(44, WHI(fill(66, '=')));
                            line();

                            printLine(64, GRE("ORDER IS PAID SUCCESSFULLY!"));
                            break;
                        }
                    }
                }
                case 2 -> 
                {
                    printCard();
                    line();
                    printLine(60, WHI("PRESS ANY KEY TO SWIPE YOUR CARD..."));
                    line();

                    generateTitle("null");
                    pause();

                    printHeader("my_cart");
                    printCard();

                    String total = "Php "+ TOTAL_AMOUNT.setScale(2, RoundingMode.HALF_UP).toPlainString();
                    int centerAmount = (155 - total.length()) / 2;

                    line();
                    printLine(centerAmount, WHI(total));
                    printLine(55, GRE("HAS BEEN DEDUCTED TO YOUR CARD SUCCESSFULLY!"));
                }
            }

            saveOrderInfo();
        }
        // otherwise, cancel check out
        else 
        {
            printLine(61,  RED("CHECK OUT CANCELLED SUCCESSFULLY!")); 
        }

        generateTitle("null");
        pause();
    }

    // provide user selction of items
    private void order(ArrayList<Object[]> MENU, String CAT) 
    {
        boolean orderAgain;
        String itemCode = null;
        String codeInit = String.valueOf(CAT.charAt(11)).toUpperCase();

        do
        {
            orderAgain = false;

            printHeader(CAT);
            button(0, "CANCEL");
            line();

            // display if the category's menu is empty
            if (MENU.isEmpty())
            {
                printNull();
                printLine(60, WHI("THERE ARE NO ITEMS IN THIS MENU\n"));
                line();
                printLine(55, WHI("ENTER CHOICE"));
                pointer();
            }
            // display items if the category's menu is not empty
            else
            {
                String format = "%-7s%-64s%-12s%n";

                printLine(34, WHI(fill(87, '-')) + YEL);
                printFormat(35, format, "CODE", "ITEM", "PRICE");
                printLine(34, WHI(fill(87, '-')));

                int count = 1;
                // display all category's menu items and their price
                for (Object[] items : MENU) 
                {
                    String code     = codeInit + count++;
                    String item     = items[0].toString();
                    double amount   = (double) items[1];
                    int stocks      = (int) items[2];

                    String amountFormat = "Php " + amount;
                    if (stocks <= 0) item += " (OUT OF STOCK)";

                    if (item.length() > 32) 
                    {
                        ArrayList<String> multiLine = wrapText(item, 64);

                        printFormat(35, format, code, multiLine.get(0), amountFormat);
                        
                        for (int i = 1; i < multiLine.size(); i++)
                            printFormat(35, format, "", multiLine.get(i), "");
                    } 
                    else 
                    {
                        printFormat(35, format, code, item, amountFormat);
                    }
                }

                // get the item's code
                printLine(34, WHI(fill(87, '-')));
                line();
                printLine(55, WHI("ENTER CODE OF YOUR DESIRED MENU ITEM"));
                pointer();
            }

            itemCode = getCode(MENU, false);

            switch (itemCode)
            {
                case "0" -> { return; }
                default -> orderAgain = addToCart(MENU, itemCode);
            }
        }
        while (orderAgain);
    }

    // function that will add item to the cart
    private boolean addToCart(ArrayList<Object[]> MENU, String itemCode) 
    {
        int index           = (itemCode.charAt(1) - '0') - 1;
        char menu           = itemCode.charAt(0);
        Object[] itemDes    = MENU.get(index);

        String item     = itemDes[0].toString();
        double price    = (double) itemDes[1];
        int stocks      = (int) itemDes[2];

        if (stocks <= 0)
        {
            line();
            printLine(55, RED("THIS ITEM IS OUT OF STOCK"));
            printLine(55, WHI("PLEASE TRY OTHER ITEMS"));
            generateTitle("null");
            pause();
            return true;
        }

        line();
        printLine(55, WHI("ENTER QUANTITY"));
        pointer();
        int quantity = getQuantity(stocks);

        BigDecimal pr = new BigDecimal(price);
        BigDecimal amount = pr.multiply(new BigDecimal(quantity));

        // dsplay item's information
        line();
        printLine(44, fill(66, '='));
        printLine(45, YEL("CODE") + "        :  " + itemCode);

        if (item.length() > 32)
        {
            ArrayList<String> lines = wrapText(item, 53);
            
            printLine(45, YEL("ITEM") + "        :  " + lines.get(0));
            
            for (int i = 1; i < lines.size(); i++) 
                printLine(45, fill(12, ' ') + lines.get(i));
        }
        else 
        {
            printLine(45, YEL("ITEM") + "        :  " + item); 
        }

        printLine(45, YEL("QUANTITY") + "    :  " + quantity + "x");
        printLine(45, YEL("TOTAL PRICE") + " :  Php " + amount.setScale(2, RoundingMode.HALF_UP));
        printLine(44, WHI(fill(66, '=')));
        line();
        
        printLine(55, WHI("ARE YOU SURE YOU WANT TO ADD THIS ITEM? (y/n)"));
        pointer();
        boolean confirmAdd = getConfirmation();

        // add item to cart if confirmed
        if (confirmAdd)
        {
            TOTAL_AMOUNT = TOTAL_AMOUNT.add(amount);
            
            CART.add(new Object[] {itemCode, item, quantity, amount});

            switch (menu)
            {
                case 'M' -> OrderFood.MEALS_ITEMS.get(index)[2] = (int) OrderFood.MEALS_ITEMS.get(index)[2] - quantity;
                case 'S' -> OrderFood.SANDWICH_ITEMS.get(index)[2] = (int) OrderFood.SANDWICH_ITEMS.get(index)[2] - quantity;
                case 'D' -> OrderFood.DRINKS_ITEMS.get(index)[2] = (int)  OrderFood.DRINKS_ITEMS.get(index)[2] - quantity;
            }       

            line();
            printLine(60, GRE("ITEM HAS BEEN PLACED SUCCESSFULLY!"));
        }
        // otherwise, cancel adding item to cart
        else
        {
            line();
            printLine(55, WHI("DO YOU WANT TO ORDER OTHER ITEM INSTEAD? (y/n)"));
            pointer();
            boolean confirm = getConfirmation();

            line();
            if (confirm) return true;
            else printLine(58, RED("ADDING ITEM IS CANCELLED SUCCESSFULLY!"));
        }    

        generateTitle("null");
        pause();
        return false;
    }

    // function that will remove item to the cart
    private void removeToCart() 
    {
        line();
        printLine(55, WHI("GIVE THE ITEM CODE THAT YOU WANT TO REMOVE"));
        pointer();
        String rc = getCode(CART, true);

        // find the item's code
        switch (rc)
        {
            case "0" -> { return; }
            default -> 
            {            
                int index = IntStream.range(0, CART.size()).filter(i -> CART.get(i)[0].equals(rc)).findFirst().orElse(-1);
                Object[] itemDes = CART.get(index);

                // display item, quantity, and its amount
                String code     = itemDes[0].toString();
                char menu       = code.charAt(0);
                int codeidx     = (code.charAt(1) - '0') - 1;
                String item     = itemDes[1].toString();
                int quantity    = (int) itemDes[2];
                BigDecimal amount   = new BigDecimal(itemDes[3].toString());

                line();
                printLine(44, fill(66, '='));
                printLine(45, YEL("CODE") + "    :  " + code);

                if (item.length() > 32)
                {
                    ArrayList<String> lines = wrapText(item, 52);
                    printLine(45, YEL("ITEM") + "    :  " + lines.get(0));

                    for (int i = 1; i < lines.size(); i++) 
                        printLine(45, fill(11, ' ') + lines.get(i));
                }
                else 
                { 
                    printLine(45, YEL("ITEM") + "    :  " + item); 
                }

                printLine(45, YEL("QUANTITY") + ":  " + quantity + "x");
                printLine(45, YEL("AMOUNT") + "  :  Php " + amount.setScale(2, RoundingMode.HALF_UP));
                printLine(44, fill(66, '='));

                line();
                printLine(55, WHI("ARE YOU SURE YOU WANT TO REMOVE THIS ITEM? (y/n)"));
                pointer();
                boolean confirm = getConfirmation();
                line();

                // remove item if confirmed
                if (confirm)
                {
                    CART.remove(index); 
                    
                    switch (menu)
                    {
                        case 'M' -> OrderFood.MEALS_ITEMS.get(codeidx)[2] = (int) OrderFood.MEALS_ITEMS.get(codeidx)[2] + quantity;
                        case 'S' -> OrderFood.SANDWICH_ITEMS.get(codeidx)[2] = (int) OrderFood.SANDWICH_ITEMS.get(codeidx)[2] + quantity;
                        case 'D' -> OrderFood.DRINKS_ITEMS.get(codeidx)[2] = (int)  OrderFood.DRINKS_ITEMS.get(codeidx)[2] + quantity;
                    }

                    TOTAL_AMOUNT = TOTAL_AMOUNT.subtract(amount);
                    printLine(60, GRE("ITEM HAS BEEN REMOVED SUCCESSFULLY!"));
                }
                // otherwise, cancel removing item to cart
                else 
                {  
                    printLine(55, RED("ITEM REMOVAL HAS BEEN CANCELLED SUCCESSFULLY!"));
                }
            }
        }

        generateTitle("null");
        pause();
    }

    // function to save users order information
    private void saveOrderInfo()
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");

        String DAT  = now.format(formatter);
        String date = DAT.substring(0, 10);
        String time = DAT.substring(11);
        String refNum = generateRefNum();

        bagItems();
        addToReports(date, time, refNum, TOTAL_AMOUNT);

        Administration.GRAND_TOTAL_INCOME = Administration.GRAND_TOTAL_INCOME.add(TOTAL_AMOUNT);        
        updateFile();

        TOTAL_AMOUNT = new BigDecimal(0);
        CART.clear();

        line();
        printLine(61, WHI("REFERENCE NUMBER:  ") + GRE(refNum));
    }

    // function to decrement the stocks on the menu
    private void bagItems() 
    {
        for (Object[] items : CART) 
        {
            String code     = items[0].toString();
            char menu       = code.charAt(0);
            int idx         = (code.charAt(1) - '0') - 1;
            int quantity    = (int) items[2];

            switch (menu)
            {
                case 'M' -> ORIGINAL_ITEMS.get(0).get(idx)[2] = (int) ORIGINAL_ITEMS.get(0).get(idx)[2] - quantity;
                case 'S' -> ORIGINAL_ITEMS.get(1).get(idx)[2] = (int) ORIGINAL_ITEMS.get(1).get(idx)[2] - quantity;
                case 'D' -> ORIGINAL_ITEMS.get(2).get(idx)[2] = (int) ORIGINAL_ITEMS.get(2).get(idx)[2] - quantity;
            }
        }
    }
}