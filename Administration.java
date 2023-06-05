import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

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

    // display admin menu
    Administration()
    {
        while (true)
        {
            cls();
            printHeader();
            generateTitle("administration");
            line();
            button(0, "BACK");
            button(1, "MANAGE MENU");
            button(2, "REPORT");
            line();
            printLine(55, WHI + "ENTER CHOICE" + RES);
            pointer();
            int ad = getChoice(0, 2);

            switch (ad)
            {
                case 0 -> { return; }
                case 1 -> manageMenu1();
                case 2 -> report(); 
            }
        }
    }    

    // second phase of admin menu where the admin can make changes to the selected category's menu
    private void manageMenu1()
    {
        while (true)
        {
            cls();
            printHeader();
            generateTitle("manage_menu");
            line();
            button(0, "BACK");
            button(1, "MEALS");
            button(2, "SANDWICH");
            button(3, "DRINKS");
            button(4, "SHOW ITEMS");
            line();
            printLine(55, WHI + "ENTER CHOICE" + RES);
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
    private void manageMenu2(HashMap<String, Double> MAP, String CAT)
    {
        cls();
        printHeader();
        generateTitle(CAT);
        line();
        button(0, "BACK");
        button(1, "ADD ITEM");
        button(2, "DELETE ITEM");
        line();
        printLine(55, WHI + "ENTER CHOICE" + RES);
        pointer();
        int admm2 = getChoice(0, 2);

        switch (admm2) 
        {
            case 0 -> { return; }
            case 1 -> addItem(MAP, CAT);
            case 2 -> deleteItem(MAP, CAT);
        }
    }

    // function to add item to the selected category
    private void addItem(HashMap<String, Double> MAP, String CAT)
    {  
        boolean runAgain;
        boolean confirm;
        String item = null;
        double price = 0;

        do 
        {
            runAgain = false;
            confirm = false;

            cls();
            printHeader();
            generateTitle(CAT);
            line();

            // get the item's name
            printLine(55, WHI + "ENTER DESIRED ITEM THAT YOU WANT TO ADD ON THIS MENU" + RES); 
            pointer();
            item = getLine();   

            // get the item's price
            line();
            printLine(55, WHI + "ENTER ITEM'S AMOUNT" + RES);
            pointer();
            price = getAmount();
            
            line();

            // ask to add other item if the given item exist in the selected category
            if (MAP.containsKey(item))
            {
                printLine(55, RED + "THIS ITEM IS ALREADY EXIST" + RES);
                printLine(55, WHI + "DO YOU WANT TO ADD OTHER ITEM INSTEAD? (y/n)" + RES);
                pointer();
                runAgain = getConfirmation();

                if (!runAgain) 
                {
                    line();
                    printLine(58, RED + "ADDING ITEM IS CANCELLED SUCCESSFULLY!" + RES);
                }
            }
            // otherwise, confirm the admin before adding it to the category's menu
            else
            {
                printLine(44, WHI + fill(66, '=') + RES);
                printLine(45, YEL + "ITEM" + RES + "        :  " + item + RES);
                printLine(45, YEL + "PRICE" + RES + "       :  Php " + price + RES);
                printLine(44, WHI + fill(66, '='));
                line();

                printLine(55, WHI + "ARE YOU SURE TO ADD THIS ITEM? (y/n)" + RES);
                pointer();
                confirm = getConfirmation();

                line();

                if (confirm)
                {
                    MAP.put(item, price);
                    addToFile(CAT, item, price);
                    printLine(64, GRE + "ITEM IS ADDED SUCCESSFULLY!" + RES);
                }
                else
                { 
                    printLine(58, RED + "ADDING ITEM IS CANCELLED SUCCESSFULLY!" + RES);
                }
            }
        }
        while (runAgain);
        
        generateTitle("null");
        pause();
    }

    // function to delete item to the selected category
    private void deleteItem(HashMap<String, Double> MAP, String CAT)
    {  
        boolean runAgain;
        boolean confirm;
        String item = null;

        do 
        {
            runAgain = false;
            confirm = false;

            cls();
            printHeader();
            generateTitle(CAT); 
            line();

            // get the item's name
            printLine(55, WHI + "ENTER ITEM THAT YOU WANT TO DELETE" + RES);
            pointer();
            item = getLine();
            
            line();
            
            // display the given item's name and price and confirm to the admin if the item exist in the category
            if (MAP.containsKey(item))
            {
                printLine(44, WHI + fill(66, '=') + RES);
                printLine(45, YEL + "ITEM" + RES + "        :  " + item + RES);
                printLine(45, YEL + "PRICE" + RES + "       :  Php " + MAP.get(item) + RES);
                printLine(44, WHI + fill(66, '=') + RES);
                line();

                printLine(55, WHI + "ARE YOU SURE TO DELETE THIS ITEM? (y/n)" + RES);
                pointer();
                confirm = getConfirmation();

                line();
                
                if (confirm)
                {
                    MAP.remove(item); 
                    removeToFile(CAT, item);
                    printLine(63, GRE + "ITEM IS DELETED SUCCESSFULLY!" + RES);
                }
                else 
                { 
                    printLine(58, RED + "DELETING ITEM IS CANCELLED SUCCESSFULLY!" + RES);
                }
            }
            // otherwise, display that the given item does not exist and ask the admin to delete other item instead
            else 
            { 
                printLine(55, RED + "ITEM DOES NOT EXIST" + RES);
                line();
                printLine(55, WHI + "DO YOU WANT TO DELETE OTHER ITEM INSTEAD? (y/n)" + RES);
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
        cls();
        printHeader();
        generateTitle("administration");

        line();
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
    protected void printMenu(HashMap<String, Double> MENU, String CAT)
    {
        String format = "%-7s%-64s%-12s%n";
        String title = RED + "=" + WHI + "[ " + YEL + CAT + WHI + " ]" + RED + "=" + RES;
        switch (CAT)
        {
            case "MEALS"    -> printLine(34, WHI + fill(38, '-') + title + fill(38, '-') + YEL);
            case "SANDWICH" -> printLine(34, WHI + fill(37, '-') + title + fill(36, '-') + YEL);
            case "DRINKS"   -> printLine(34, WHI + fill(38, '-') + title + fill(37, '-') + YEL);
        }
        
        printFormat(35, format, "CODE", "ITEM", "PRICE");
        printLine(34, WHI + fill(87, '-') + RES);
        int counter = 1;

        // display all the passed category's items
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
        
        printLine(34, WHI + fill(87, '-') + RES);
    }

    // function to show the orders report/information
    private void report()
    {
        cls();
        printHeader();
        generateTitle("report");
        line();

        // display if the reports is empty
        if (ORDER_REPORTS.isEmpty())
        {
            printNull();
            printLine(68, WHI + "THERE ARE NO ORDERS\n" + RES);
        }
        // otherwise, display reports
        else
        {
            String format = "%-15s%-16s%-21s%-12s%n";

            printLine(44, WHI + fill(68, '-') + YEL);
            printFormat(45, format, "DATE", "TIME", "REFERRENCE NUMBER", "AMOUNT");
            printLine(44, WHI + fill(68, '-') + WHI);

            for (ArrayList<Object> info : ORDER_REPORTS)
            {   
                String date     = info.get(0).toString();
                String time     = info.get(1).toString();
                String refNum   = info.get(2).toString();
                double amount   = new BigDecimal(info.get(3).toString()).doubleValue();
                String amountFormat = "Php " + amount;

                printFormat(45, format, date, time, refNum, amountFormat);
            }
            
            printLine(44, WHI + fill(68, '-')+ RES);
        }

        line();
        generateTitle("null");
        pause();
    }
}