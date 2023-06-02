import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class Administration 
{    
    // class
    private final ErrorHandler erh   = new ErrorHandler();
    private final GSystem gsystem = new GSystem();

    // imports and orders report
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
    protected static ArrayList<ArrayList<Object>> ORDER_REPORTS = new ArrayList<>();

    // display admin menu
    Administration()
    {
        while (true)
        {
            gsystem.cls();
            gsystem.printHeader();
            gsystem.generateTitle("administration");
            System.out.println();
            gsystem.button(0, "BACK");
            gsystem.button(1, "MANAGE MENU");
            gsystem.button(2, "REPORT");
            System.out.println();
            gsystem.printLine(55,"ENTER CHOICE");
            gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
            int ad = erh.getChoice(0, 2);

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
            gsystem.cls();
            gsystem.printHeader();
            gsystem.generateTitle("manage_menu");
            System.out.println();
            gsystem.button(0, "BACK");
            gsystem.button(1, "MEALS");
            gsystem.button(2, "SANDWICH");
            gsystem.button(3, "DRINKS");
            gsystem.button(4, "SHOW ITEMS");
            System.out.println();
            gsystem.printLine(55, "ENTER CHOICE");
            gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
            int admm1 = erh.getChoice(0, 4);

            switch (admm1)
            {
                case 0 -> { return; }
                case 1 -> manageMenu2(MainProcess.MEALS_ITEMS,    "manage_menu_meals");
                case 2 -> manageMenu2(MainProcess.SANDWICH_ITEMS, "manage_menu_sandwich");
                case 3 -> manageMenu2(MainProcess.DRINKS_ITEMS,   "manage_menu_drinks");
                case 4 -> showItems();
            }
        }
    }

    // third phase of admin where the admin can add or delete items on the category's mmenu
    private void manageMenu2(HashMap<String, Double> MAP, String CAT)
    {
        gsystem.cls();
        gsystem.printHeader();
        gsystem.generateTitle(CAT);
        System.out.println();
        gsystem.button(0, "BACK");
        gsystem.button(1, "ADD ITEM");
        gsystem.button(2, "DELETE ITEM");
        System.out.println();
        gsystem.printLine(55, "ENTER CHOICE");
        gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
        int admm2 = erh.getChoice(0, 2);

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
        String item = null;
        double price = 0;

        do 
        {
            runAgain = false;

            gsystem.cls();
            gsystem.printHeader();
            gsystem.generateTitle(CAT);
            System.out.println();

            try 
            {
                // get the item's name
                gsystem.printLine(50, "ENTER DESIRED ITEM THAT YOU WANT TO ADD ON THIS MENU  "); 
                gsystem.prints(50, gsystem.GRE + ">> " + gsystem.RES);
                item = br.readLine();
    
                // get the item's price
                System.out.println();
                gsystem.printLine(50, "ENTER ITEM'S AMOUNT:  ");
                gsystem.prints(50, gsystem.GRE + ">> " + gsystem.RES);
                price = erh.getAmount();
            } 
            catch (IOException e) 
            { 
                e.printStackTrace(); 
            }
            
            System.out.println();

            // ask to add other item if the given item exist in the selected category
            if (MAP.containsKey(item))
            {
                gsystem.printLine(50, "THIS ITEM IS ALREADY EXIST");
                gsystem.printLine(50, "DO YOU WANT TO ADD OTHER ITEM INSTEAD? (y/n)");
                gsystem.prints(50, gsystem.GRE + ">> " + gsystem.RES);
                runAgain = erh.getConfirmation();
            }
            // otherwise, confirm the admin before adding it to the category's menu
            else
            {
                gsystem.printLine(44, gsystem.fill(66, '='));
                gsystem.printLine(45, gsystem.YEL + "ITEM" + gsystem.RES + "        :  " + item);
                gsystem.printLine(45, gsystem.YEL + "PRICE" + gsystem.RES + "       :  Php " + price);
                gsystem.printLine(44, gsystem.fill(66, '='));
                System.out.println();
                gsystem.printLine(50, "ARE YOU SURE TO ADD THIS ITEM? (y/n)");
                gsystem.prints(50, gsystem.GRE + ">> " + gsystem.RES);
                boolean confirm = erh.getConfirmation();

                System.out.println();

                if (confirm)
                {
                    MAP.put(item, price);
                    gsystem.addToFile(CAT, item, price);
                    gsystem.printLine(64, "ITEM IS ADDED SUCCESSFULLY!");
                }
                else
                { 
                    gsystem.printLine(58, "ADDING ITEM IS CANCELLED SUCCESSFULLY!");
                }
            }
        }
        while (runAgain);
        
        gsystem.generateTitle("null");
        gsystem.pause();
    }

    // function to delete item to the selected category
    private void deleteItem(HashMap<String, Double> MAP, String CAT)
    {  
        boolean runAgain;
        String item = null;

        do 
        {
            runAgain = false;

            gsystem.cls();
            gsystem.printHeader();
            gsystem.generateTitle(CAT); 

            try
            {
                // get the item's name
                gsystem.printLine(50, "ENTER ITEM THAT YOU WANT TO DELETE");
                gsystem.prints(50, gsystem.GRE + ">> " + gsystem.RES);
                item = br.readLine();
            } 
            catch (IOException e) 
            { 
                e.printStackTrace(); 
            }
            
            System.out.println();
            
            // display the given item's name and price and confirm to the admin if the item exist in the category
            if (MAP.containsKey(item))
            {
                gsystem.printLine(50, gsystem.YEL + "ITEM" + gsystem.RES + "        :  " + item);
                gsystem.printLine(50, gsystem.YEL + "PRICE" + gsystem.RES + "       :  Php " + MAP.get(item));
                System.out.println();
                gsystem.printLine(50, "ARE YOU SURE TO DELETE THIS ITEM? (y/n)");
                gsystem.prints(50, gsystem.GRE + ">> " + gsystem.RES);
                boolean confirm = erh.getConfirmation();

                if (confirm)
                {
                    MAP.remove(item); 
                    gsystem.removeToFile(CAT, item);
                    gsystem.printLine(50, "ITEM IS DELETED SUCCESSFULLY!");
                }
                else 
                { 
                    gsystem.printLine(50, "DELETING ITEM IS CANCELLED SUCCESSFULLY!");
                }
            }
            // otherwise, display that the given item does not exist and ask the admin to delete other item instead
            else 
            { 
                gsystem.printLine(50, "ITEM DOES NOT EXIST");
                gsystem.printLine(50, "DO YOU WANT TO DELETE OTHER ITEM INSTEAD? (y/n)");
                gsystem.prints(50, gsystem.GRE + ">> " + gsystem.RES);
                runAgain = erh.getConfirmation();
            }
        }
        while (runAgain);

        gsystem.generateTitle("null");
        gsystem.pause();
    }

    // function to show all category's items
    private void showItems()
    {
        gsystem.cls();
        gsystem.printHeader();
        gsystem.generateTitle("administration");

        System.out.println();
        printMenu(MainProcess.MEALS_ITEMS, "MEALS");
        
        System.out.println("\n");
        printMenu(MainProcess.SANDWICH_ITEMS, "SANDWICH");
        
        System.out.println("\n");  
        printMenu(MainProcess.DRINKS_ITEMS, "DRINKS");
            
        System.out.println();
        gsystem.generateTitle("null");
        gsystem.pause();
    }

    // function that will help showItems function to display item in formatted way
    protected void printMenu(HashMap<String, Double> MENU, String CAT)
    {
        String format = "%-7s%-64s%-12s%n";
        String title = gsystem.RED + "=" + gsystem.RES + "[ " + gsystem.YEL + CAT + gsystem.RES + " ]" + gsystem.RED + "=" + gsystem.RES;
        switch (CAT)
        {
            case "MEALS"    -> gsystem.printLine(34, gsystem.fill(38, '-') + title + gsystem.fill(38, '-') + gsystem.YEL);
            case "SANDWICH" -> gsystem.printLine(34, gsystem.fill(37, '-') + title + gsystem.fill(36, '-') + gsystem.YEL);
            case "DRINKS"   -> gsystem.printLine(34, gsystem.fill(38, '-') + title + gsystem.fill(37, '-') + gsystem.YEL);
        }
        
        gsystem.printFormat(35, format, "CODE", "ITEM", "PRICE");
        gsystem.printLine(34, gsystem.RES + gsystem.fill(87, '-'));
        int counter = 1;

        // display all the passed category's items
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
        
        gsystem.printLine(34, gsystem.fill(87, '-'));
    }

    // function to show the orders report/information
    private void report()
    {
        gsystem.cls();
        gsystem.printHeader();
        gsystem.generateTitle("report");
        System.out.println();

        // display if the reports is empty
        if (ORDER_REPORTS.isEmpty())
        {
            gsystem.printLine(68, "THERE ARE NO ORDERS\n");
        }
        // otherwise, display reports
        else
        {
            String format = "%-15s%-16s%-21s%-12s%n";

            gsystem.printLine(44, gsystem.fill(68, '-') + gsystem.YEL);
            gsystem.printFormat(45, format, "DATE", "TIME", "REFERRENCE NUMBER", "AMOUNT");
            gsystem.printLine(44, gsystem.RES + gsystem.fill(68, '-'));

            for (ArrayList<Object> info : ORDER_REPORTS)
            {   
                String date     = info.get(0).toString();
                String time     = info.get(1).toString();
                String refNum   = info.get(2).toString();
                double amount   = new BigDecimal(info.get(3).toString()).doubleValue();
                String amountFormat = "Php " + amount;

                gsystem.printFormat(45, format, date, time, refNum, amountFormat);
            }
            
            gsystem.printLine(44, gsystem.fill(68, '-'));
        }

        System.out.println();
        gsystem.generateTitle("null");
        gsystem.pause();
    }
}