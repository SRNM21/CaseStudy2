import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 *  The {@code Administration} class is where the admin will add and remove items from the category's menu.
 * 
 *  <p>
 *  {@code Administration} will receive the report from the {@code OrderFood} class and add it to {@code REPORTS.txt} file.
 *  This class can manipulate the data of the {@code ITEMS.txt} file.
 *  </p>
 * 
 *  @author Neelian Mata
 *  @author Jarius Maui Pineda
 *  @author Niño Greg Gregorio
 *  @since 1.0
 */
public class Administration extends ErrorHandler
{    
    // reports
    protected static ArrayList<ArrayList<Object>> ORDER_REPORTS = new ArrayList<>();
    protected static BigDecimal GRAND_TOTAL_INCOME = new BigDecimal(0);

    // credentials
    private final String USERNAME = "kwago123";
    private final String PASSWORD = "kwago123";

    // display admin menu
    Administration()
    {
        if (loginAdmin())
        {
            while (true)
            {
                printHeader("administration");
                button(0, "BACK");
                button(1, "MANAGE MENU");
                button(2, "REPORT");
                button(3, "SUPPLIER");
                line();
                printLine(55, WHI("ENTER CHOICE"));
                pointer();
                int ad = getChoice(0, 3);

                switch (ad)
                {
                    case 0 -> { return; }
                    case 1 -> manageMenu1();
                    case 2 -> report(); 
                    case 3 -> supplier();
                }
            }
        }
    }    

    // function for loging into administration
    private boolean loginAdmin() 
    { 
        boolean loginAgain = true;

        while (true)
        {    

            printHeader("administration");        
            printAdmin(); 

            line(); 
            printLine(55, WHI("ENTER USERNAME")); 
            pointer();
            String username = getLine();

            line();
            printLine(55, WHI("ENTER PASSWORD")); 
            pointer();
            String password = getLine();

            // check if the credentials are correct
            line();
            if (username.equals(USERNAME) && password.equals(PASSWORD))
            {
                printLine(70, GRE("LOGIN SUCCESSFUL!")); 
                generateTitle("null");
                pause();
                return true;
            }
            else
            {
                printLine(55, RED("WRONG CREDENTIALS")); 
                printLine(55, WHI("INVALID USERNAME OR PASSWORD, TRY AGAIN? (Y/N)")); 
                pointer();
                loginAgain = getConfirmation();
            }

            if (!loginAgain) 
            {
                printLine(68, RED("LOGIN UNSUCCESSFUL!")); 
                generateTitle("null");
                pause();
                return false;
            }
        }
    }

    // second phase of admin menu where the admin can make changes to the selected category's menu
    private void manageMenu1()
    {
        while (true)
        {
            printHeader("manage_menu");
            button(0, "BACK");
            button(1, "MEALS");
            button(2, "SANDWICH");
            button(3, "DRINKS");
            button(4, "SHOW ITEMS");
            line();
            printLine(55, WHI("ENTER CHOICE"));
            pointer();
            int admm1 = getChoice(0, 4);

            switch (admm1)
            {
                case 0 -> { return; }
                case 1 -> manageMenu2(OrderFood.MEALS_ITEMS,    "manage_menu_meals");
                case 2 -> manageMenu2(OrderFood.SANDWICH_ITEMS, "manage_menu_sandwich");
                case 3 -> manageMenu2(OrderFood.DRINKS_ITEMS,   "manage_menu_drinks");
                case 4 -> showItems();
            }
        }
    }

    // third phase of admin where the admin can add or delete items on the category's mmenu
    private void manageMenu2(ArrayList<Object[]> MENU, String CAT)
    {
        printHeader(CAT);
        button(0, "BACK");
        button(1, "ADD ITEM");
        button(2, "DELETE ITEM");
        line();
        printLine(55, WHI("ENTER CHOICE"));
        pointer();
        int admm2 = getChoice(0, 2);

        switch (admm2) 
        {
            case 0 -> { return; }
            case 1 -> addItem(MENU, CAT);
            case 2 -> deleteItem(MENU, CAT);
        }
    }

    // function to add item to the selected category
    private void addItem(ArrayList<Object[]> MENU, String CAT)
    {  
        boolean runAgain;
        boolean confirm;

        String item = null;
        double price = 0;

        do 
        {
            runAgain = false;
            confirm = false;

            printHeader(CAT);
            
            try
            {
                // get the item's name
                printLine(55, WHI("ENTER DESIRED ITEM THAT YOU WANT TO ADD ON THIS MENU")); 
                pointer();
                item = getLine();  

                String searchItem = item; 
                if (MENU.stream().anyMatch(itemDes -> itemDes[0].equals(searchItem))) throw new Exception();

                // get the item's price
                line();
                printLine(55, WHI("ENTER ITEM'S AMOUNT"));
                pointer();
                price = getAmount();
                
                line();
                printLine(44, WHI(fill(66, '=')));
                printLine(45, YEL("ITEM") + "        :  " + item);
                printLine(45, YEL("PRICE") + "       :  Php " + price);
                printLine(44, WHI(fill(66, '=')));
                line();

                printLine(55, WHI("ARE YOU SURE TO ADD THIS ITEM? (y/n)"));
                pointer();
                confirm = getConfirmation();

                line();
                if (confirm)
                {
                    MENU.add(new Object[] {item, price, 100});
                    addToFile(CAT, item, price, 100);
                    printLine(64, GRE("ITEM IS ADDED SUCCESSFULLY!"));
                }
                else
                { 
                    printLine(58, RED("ADDING ITEM IS CANCELLED SUCCESSFULLY!"));
                }
            }
            catch (Exception e)
            {   
                line();
                printLine(55, RED("THIS ITEM IS ALREADY EXIST"));
                printLine(55, WHI("DO YOU WANT TO ADD OTHER ITEM INSTEAD? (y/n)"));
                pointer();
                runAgain = getConfirmation();

                if (!runAgain) 
                {
                    line();
                    printLine(58, RED("ADDING ITEM IS CANCELLED SUCCESSFULLY!"));
                }
            }
        }
        while (runAgain);
        
        generateTitle("null");
        pause();
    }

    // function to delete item to the selected category
    private void deleteItem(ArrayList<Object[]> MENU, String CAT)
    {  
        boolean runAgain;
        boolean confirm;

        String item = null;

        do 
        {
            runAgain = false;
            confirm = false;

            printHeader(CAT);

            // get the item's name
            printLine(55, WHI("ENTER ITEM'S NAME THAT YOU WANT TO DELETE"));
            pointer();
            item = getLine();
            line();

            // display the given item's name and price and confirm to the admin if the item exist in the category
            String searchItem = item;
            int index = IntStream.range(0, MENU.size()).filter(i -> MENU.get(i)[0].equals(searchItem)).findFirst().orElse(-1);

            if (index >= 0)
            {
                printLine(44, WHI(fill(66, '=')));
                printLine(45, YEL("ITEM") + "        :  " + item);
                printLine(45, YEL("PRICE") + "       :  Php " + MENU.get(index)[1]);
                printLine(45, YEL("STOCKS") + "      :  " + MENU.get(index)[2]);
                printLine(44, WHI(fill(66, '=')));
                line();

                printLine(55, WHI("ARE YOU SURE TO DELETE THIS ITEM? (y/n)"));
                pointer();
                confirm = getConfirmation();

                line();
                
                if (confirm)
                {
                    MENU.remove(index);
                    removeToFile(CAT, index);
                    printLine(63, GRE("ITEM IS DELETED SUCCESSFULLY!"));
                }
                else 
                {
                    printLine(58, RED("DELETING ITEM IS CANCELLED SUCCESSFULLY!"));
                }
            }
            // otherwise, display that the given item does not exist and ask the admin to delete other item instead
            else 
            { 
                line();
                printLine(55, RED("ITEM DOES NOT EXIST"));
                printLine(55, WHI("DO YOU WANT TO DELETE OTHER ITEM INSTEAD? (y/n)"));
                pointer();
                runAgain = getConfirmation();
            }
        }
        while (runAgain);

        generateTitle("null");
        pause();
    }

    // function to show all category's items
    private void showItems()
    {
        printHeader("administration");
        printMenu(OrderFood.MEALS_ITEMS, "MEALS");
        
        line("\n");
        printMenu(OrderFood.SANDWICH_ITEMS, "SANDWICH");
        
        line("\n");  
        printMenu(OrderFood.DRINKS_ITEMS, "DRINKS");
            
        line();
        generateTitle("null");
        pause();
    }

    // function that will help showItems function to display item in formatted way
    protected void printMenu(ArrayList<Object[]> MENU, String CAT)
    {
        String format = "%-8s%-7s%-64s%-12s%n";
        String codeInit = String.valueOf(CAT.charAt(0));
        String title = RED("=") + WHI("[ ") + YEL(CAT) + " ]" + RED("=");
        switch (CAT)
        {
            case "MEALS"    -> printLine(29, WHI(fill(42, '-') + title + fill(42, '-')) + YEL);
            case "SANDWICH" -> printLine(29, WHI(fill(41, '-') + title + fill(40, '-')) + YEL);
            case "DRINKS"   -> printLine(29, WHI(fill(42, '-') + title + fill(41, '-')) + YEL);
        }
        
        printFormat(30, format, "STOCKS", "CODE", "ITEM", "PRICE");
        printLine(29, WHI(fill(95, '-')));

        // display all the passed category's items
        int count = 1;
        for (Object[] itemDes : MENU) 
        {
            String code = codeInit + count++;
            String item  = itemDes[0].toString();
            double price = (double) itemDes[1];
            int stocks   = (int) itemDes[2];

            String priceFormat = "Php " + price;

            if (item.length() > 32)
            {
                ArrayList<String> multiLine = wrapText(item, 64);
                printFormat(30, format, stocks, code, multiLine.get(0), priceFormat);
                
                for (int i = 1; i < multiLine.size(); i++)
                    printFormat(30, format, "", "", multiLine.get(i), "");
            }
            else 
            {
                printFormat(30, format, stocks, code, item, priceFormat);
            }
        }
        
        printLine(29, WHI(fill(95, '-')));
    }

    // function to show the orders report/information
    private void report()
    {
        printHeader("report");
        String grandTotalEarnings = GRE("GRAND TOTAL EARNINGS: ") + "Php " + WHI(Administration.GRAND_TOTAL_INCOME.setScale(2, RoundingMode.HALF_UP).toPlainString());
        printLine(59, grandTotalEarnings);
        line();  

        // display if the reports is empty
        if (ORDER_REPORTS.isEmpty())
        {
            printNull();
            printLine(68, WHI("THERE ARE NO ORDERS\n"));
        }
        // otherwise, display reports
        else
        {
            String format = "%-15s%-16s%-21s%-12s%n";

            printLine(44, WHI(fill(68, '-')) + YEL);
            printFormat(45, format, "DATE", "TIME", "REFERRENCE NUMBER", "AMOUNT");
            printLine(44, WHI(fill(68, '-')) + WHI);

            for (ArrayList<Object> info : ORDER_REPORTS)
            {   
                String date     = info.get(0).toString();
                String time     = info.get(1).toString();
                String refNum   = info.get(2).toString();
                double amount   = new BigDecimal(info.get(3).toString()).doubleValue();
                String amountFormat = "Php " + amount;

                printFormat(45, format, date, time, refNum, amountFormat);
            }
            
            printLine(44, WHI(fill(68, '-')));
        }

        line();
        generateTitle("null");
        pause();
    }

    private void supplier() 
    {
        printHeader("supplier");
        button(0, "BACK");
        button(1, "RESTOCK ITEMS");
        line();
        printLine(55, WHI("ENTER CHOICE"));
        pointer();
        int sc = getChoice(0, 1);
        
        switch (sc)
        {
            case 0 -> { return; }
            case 1 -> restockPhase1();
        }
    }

    private void restockPhase1() 
    {
        printHeader("supplier");

        String format = "%-7s%-64s%-8s%-7s%-12s%n";
        String[] CAT = {"M", "S", "D"};

        int countNeedRestock = 0;
        countNeedRestock += (int) OrderFood.MEALS_ITEMS.stream().mapToInt(items -> (int) items[2]).filter(value -> value < 100).count();
        countNeedRestock += (int) OrderFood.SANDWICH_ITEMS.stream().mapToInt(items -> (int) items[2]).filter(value -> value < 100).count();
        countNeedRestock += (int) OrderFood.DRINKS_ITEMS.stream().mapToInt(items -> (int) items[2]).filter(value -> value < 100).count();
                                
        if (countNeedRestock > 0)
        {
            printLine(27, WHI(fill(102, '-')) + YEL);
            printFormat(28, format, "CODE", "ITEM", "STOCKS", "ORDER", "PRICE (50%)");
            printLine(27, WHI(fill(102, '-')));

            double totalAmountToPay = 0;
            int totalItems = 0;

            ArrayList<ArrayList<Object[]>> ALL_MENU = new ArrayList<>() 
            {{
                add(OrderFood.MEALS_ITEMS);
                add(OrderFood.SANDWICH_ITEMS);
                add(OrderFood.DRINKS_ITEMS);
            }};

            int menuCount = 0; 
            for (ArrayList<Object[]> MENU : ALL_MENU) 
            {
                int itemCount = 1;
                for (Object[] ITEMS : MENU) 
                {
                    int stocks = (int) ITEMS[2];
                    String code = CAT[menuCount] + itemCount++;
                    
                    if (stocks < 100)
                    {
                        
                        String item = ITEMS[0].toString();
                        int orders = 100 - stocks;
                        double halfPrice = ((double) ITEMS[1]) / 2;
                        double price = orders * halfPrice;
                        totalAmountToPay += price;
                        totalItems += orders;
                        BigDecimal totalPrice = new BigDecimal(price);
                        
                        if (item.length() > 32)
                        {
                            ArrayList<String> multiLine = wrapText(item, 64);
                            printFormat(28, format, code, multiLine.get(0), stocks, orders, totalPrice.setScale(2, RoundingMode.HALF_UP));
                            
                            for (int i = 1; i < multiLine.size(); i++)
                                printFormat(28, format, "", multiLine.get(i), "", "", "");
                        }
                        else 
                        {
                            printFormat(28, format, code, item, stocks, orders, totalPrice.setScale(2, RoundingMode.HALF_UP));
                        }
                    }
                }

                menuCount++;
            }

            printLine(27, WHI(fill(102, '-')));

            line();
            printLine(44, WHI(fill(66, '=')));
            printLine(45, YEL("GRAND TOTAL INCOME") + " :  Php " + GRAND_TOTAL_INCOME.setScale(2, RoundingMode.HALF_UP));
            printLine(45, YEL("TOTAL ITEMS") + "        :  " + totalItems);
            BigDecimal TOTAL_AMOUNT_TO_PAY = new BigDecimal(totalAmountToPay);
            printLine(45, YEL("TOTAL AMOUNT TO PAY") + ":  Php " + TOTAL_AMOUNT_TO_PAY.setScale(2, RoundingMode.HALF_UP));
            printLine(44, WHI(fill(66, '=')));
            line();
            
            printLine(55, WHI("DO YOU WANT TO CONTINUE RESTOCKING? (y/n)"));
            pointer();
            boolean confirmAdd = getConfirmation();

            if (confirmAdd) restockPhase2(totalAmountToPay);
            else printLine(59, GRE("RESTOCKING IS CANCELLED SUCCESSFULLY!"));
        }
        else
        {
            printNull();
            printLine(64, WHI("ALL ITEMS ARE FULLY STOCKED\n"));
            line();
            generateTitle("null");
            pause();

            return;
        }

        generateTitle("null");
        pause();
    }
    
    private void restockPhase2(double totalAmountToPay) 
    {
        String printTotalAmount = String.valueOf(totalAmountToPay);
        int center = (155 - printTotalAmount.length()) / 2;

        printHeader("supplier");
        printSupplier();
        line();

        loadingAnim("Restocking...");
        OrderFood.MEALS_ITEMS.forEach(items -> items[2] = 100);
        OrderFood.DRINKS_ITEMS.forEach(items -> items[2] = 100);
        OrderFood.SANDWICH_ITEMS.forEach(items -> items[2] = 100);
        GRAND_TOTAL_INCOME = GRAND_TOTAL_INCOME.subtract(new BigDecimal(totalAmountToPay));

        updateFile();
        printLine(center, printTotalAmount);
        printLine(53, GRE("HAS BEEN DEDUCTED FROM YOUR GRAND TOTAL EARNINGS"));
        line();
        printLine(56, GRE("RESTOCKING HAS BEEN SUCCESSFULLY COMPLETED!"));
    }
}