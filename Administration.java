import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class Administration 
{    
    private final ErrorHandler erh   = new ErrorHandler();
    private final GSystem gsystem = new GSystem();

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
    protected static ArrayList<ArrayList<Object>> CART_ITEMS = new ArrayList<>();

    Administration()
    {
        while (true)
        {
            gsystem.CLS();
            gsystem.HEADER();
            gsystem.GENERATE_TITLE("administration");
            System.out.println();
            gsystem.PRINTLN(50, "[0] - BACK\n");
            gsystem.PRINTLN(50, "[1] - MANAGE MENU\n");
            gsystem.PRINTLN(50, "[2] - REPORT\n");
            System.out.println();
            gsystem.PRINT(50, "ENTER CHOICE:  ");
            int ad = erh.getChoice(0, 2);

            switch (ad)
            {
                case 0 -> { return; }
                case 1 -> ManageMenu1();
                case 2 -> Report(); 
            }
        }
    }    

    private void ManageMenu1()
    {
        while (true)
        {
            gsystem.CLS();
            gsystem.HEADER();
            gsystem.GENERATE_TITLE("manage_menu");
            System.out.println();
            gsystem.PRINTLN(50, "[0] - BACK\n");
            gsystem.PRINTLN(50, "[1] - MEALS\n");
            gsystem.PRINTLN(50, "[2] - SANDWICH\n");
            gsystem.PRINTLN(50, "[3] - DRINKS\n");
            gsystem.PRINTLN(50, "[4] - SHOW ITEMS\n");
            System.out.println();
            gsystem.PRINT(50, "ENTER CHOICE:  ");
            int admm1 = erh.getChoice(0, 4);

            switch (admm1)
            {
                case 0 -> { return; }
                case 1 -> ManageMenu2(MainProcess.MEALS_ITEMS,    "manage_menu_meals");
                case 2 -> ManageMenu2(MainProcess.SANDWICH_ITEMS, "manage_menu_sandwich");
                case 3 -> ManageMenu2(MainProcess.DRINKS_ITEMS,   "manage_menu_drinks");
                case 4 -> ShowItems();
            }
        }
    }

    private void ManageMenu2(HashMap<String, Double> MAP, String CAT)
    {
        gsystem.CLS();
        gsystem.HEADER();
        gsystem.GENERATE_TITLE(CAT);
        System.out.println();
        gsystem.PRINTLN(50, "[0] - BACK\n");
        gsystem.PRINTLN(50, "[1] - ADD ITEM\n");
        gsystem.PRINTLN(50, "[2] - DELETE ITEM\n");
        System.out.println();
        gsystem.PRINT(50, "ENTER CHOICE:  ");
        int admm2 = erh.getChoice(0, 2);

        switch (admm2) 
        {
            case 0 -> { return; }
            case 1 -> AddItem(MAP, CAT);
            case 2 -> DeleteItem(MAP, CAT);
        }
    }

    private void AddItem(HashMap<String, Double> MAP, String CAT)
    {  
        boolean runAgain;
        String item = null;
        double amount = 0;

        do 
        {
            runAgain = false;

            gsystem.CLS();
            gsystem.HEADER();
            gsystem.GENERATE_TITLE(CAT);
            System.out.println();

            try 
            {
                gsystem.PRINTLN(50, "ENTER DESIRED ITEM THAT YOU WANT TO ADD ON THIS MENU  "); 
                gsystem.PRINT(50, ">>  ");    
                item = br.readLine();
    
                System.out.println();

                gsystem.PRINTLN(50, "ENTER ITEM'S AMOUNT:  ");
                gsystem.PRINT(50, ">>  ");   
                amount = erh.getAmount();
            } 
            catch (IOException e) { e.printStackTrace(); }
            
            System.out.println();

            if (MAP.containsKey(item))
            {
                gsystem.PRINTLN(50, "THIS ITEM IS ALREADY EXIST");
                gsystem.PRINT(50, "DO YOU WANT TO ADD OTHER ITEM INSTEAD? (y/n):  ");
                runAgain = erh.getConfirmation();
            }
            else
            { 
                gsystem.PRINTLN(44, gsystem.FILL(66, '='));
                gsystem.PRINTLN(45, "ITEM        :  " + item);
                gsystem.PRINTLN(45, "AMOUNT      :  Php " + amount);
                gsystem.PRINTLN(44, gsystem.FILL(66, '='));
                System.out.println();
                gsystem.PRINT(50, "ARE YOU SURE TO ADD THIS ITEM? (y/n):  ");
                boolean confirm = erh.getConfirmation();

                System.out.println();

                if (confirm)
                {
                    MAP.put(item, amount);
                    gsystem.PRINTLN(64, "ITEM IS ADDED SUCCESSFULLY!");
                }
                else 
                { 
                    gsystem.PRINTLN(58, "ADDING ITEM IS CANCELLED SUCCESSFULLY!");
                }
            }
        }
        while (runAgain);
        
        gsystem.GENERATE_TITLE("null");
        gsystem.PAUSE();
    }

    private void DeleteItem(HashMap<String, Double> MAP, String CAT)
    {  
        boolean runAgain;
        String item = null;

        do 
        {
            runAgain = false;

            gsystem.CLS();
            gsystem.HEADER();
            gsystem.GENERATE_TITLE(CAT); 

            try
            {
                gsystem.PRINT(50, "ENTER ITEM THAT YOU WANT TO DELETE:  ");
                item = br.readLine();
            } 
            catch (IOException e) { e.printStackTrace(); }
            
            System.out.println();
            
            if (MAP.containsKey(item))
            {
                gsystem.PRINTLN(50, "ITEM        :  " + item);
                gsystem.PRINTLN(50, "AMOUNT      :  Php " + MAP.get(item));
                System.out.println();
                gsystem.PRINT(50, "ARE YOU SURE TO DELETE THIS ITEM? (y/n):  ");
                boolean confirm = erh.getConfirmation();

                if (confirm)
                {
                    MAP.remove(item); 
                    gsystem.PRINTLN(50, "ITEM IS DELETED SUCCESSFULLY!");
                }
                else 
                { 
                    gsystem.PRINTLN(50, "DELETING ITEM IS CANCELLED SUCCESSFULLY!");
                }
            }
            else 
            { 
                gsystem.PRINTLN(50, "ITEM DOES NOT EXIST");
                gsystem.PRINT(50, "DO YOU WANT TO DELETE OTHER ITEM INSTEAD? (y/n):  ");
                runAgain = erh.getConfirmation();
            }
        }
        while (runAgain);

        gsystem.GENERATE_TITLE("null");
        gsystem.PAUSE();
    }

    private void ShowItems()
    {
        gsystem.CLS();
        gsystem.HEADER();
        gsystem.GENERATE_TITLE("administration");

        System.out.println();
        gsystem.PRINTLN(75, "MEALS");
        PrintMenu(MainProcess.MEALS_ITEMS);
        
        System.out.println("\n");
        gsystem.PRINTLN(73, "SANDWICH");
        PrintMenu(MainProcess.SANDWICH_ITEMS);
        
        System.out.println("\n");    
        gsystem.PRINTLN(74, "DRINKS");
        PrintMenu(MainProcess.DRINKS_ITEMS);
            
        gsystem.GENERATE_TITLE("null");
        gsystem.PAUSE();
    }

    protected void PrintMenu(HashMap<String, Double> MENU)
    {
        String format = "%-7s%-64s%-12s%n";

        gsystem.PRINTLN(35, gsystem.FILL(83, '-'));
        gsystem.PRINTF(36, format, "CODE", "ITEM", "PRICE");
        gsystem.PRINTLN(35, gsystem.FILL(83, '-'));
        int counter = 1;

        for (String item : MENU.keySet()) 
        {
            double price = MENU.get(item);

            String priceFormat = "Php " + price;

            if (item.length() > 32)
            {
                ArrayList<String> multiLine = gsystem.MULTILINE(item, 64);

                gsystem.PRINTF(36, format, counter++, multiLine.get(0), priceFormat);
                
                for (int i = 1; i < multiLine.size(); i++)
                    gsystem.PRINTF(36, format, "", multiLine.get(i), "");
            }
            else 
            {
                gsystem.PRINTF(36, format, counter++, item, priceFormat);
            }
        }
        
        gsystem.PRINTLN(35, gsystem.FILL(83, '-'));
    }

    //*  TEMPORTARY PRINTER OF ITEMS */
    private void Report()
    {
        System.out.println();
        if (CART_ITEMS.isEmpty())
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
            gsystem.PRINTLN(74, "REPORT\n");

            gsystem.PRINTLN(39, gsystem.FILL(75, '-'));
            gsystem.PRINTF(40, format, "CODE", "ITEM", "QTY", "PRICE");
            gsystem.PRINTLN(39, gsystem.FILL(75, '-'));
            int counter = 1;

            for (ArrayList<Object> cartItems : CART_ITEMS)
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
        }

        gsystem.GENERATE_TITLE("null");
        gsystem.PAUSE();
    }
}