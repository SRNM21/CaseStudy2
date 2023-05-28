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
    protected static ArrayList<ArrayList<Object>> ORDER_INFO = new ArrayList<>();

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
        double price = 0;

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
                price = erh.getAmount();
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
                gsystem.PRINTLN(45, "PRICE       :  Php " + price);
                gsystem.PRINTLN(44, gsystem.FILL(66, '='));
                System.out.println();
                gsystem.PRINT(50, "ARE YOU SURE TO ADD THIS ITEM? (y/n):  ");
                boolean confirm = erh.getConfirmation();

                System.out.println();

                if (confirm)
                {
                    MAP.put(item, price);
                    gsystem.AddToFile(CAT, item, price);
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
                gsystem.PRINTLN(50, "PRICE       :  Php " + MAP.get(item));
                System.out.println();
                gsystem.PRINT(50, "ARE YOU SURE TO DELETE THIS ITEM? (y/n):  ");
                boolean confirm = erh.getConfirmation();

                if (confirm)
                {
                    MAP.remove(item); 
                    gsystem.RemoveToFile(CAT, item);
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
        PrintMenu(MainProcess.MEALS_ITEMS, "=[ MEALS ]=");
        
        System.out.println("\n");
        PrintMenu(MainProcess.SANDWICH_ITEMS, "=[ SANDWICH ]=");
        
        System.out.println("\n");  
        PrintMenu(MainProcess.DRINKS_ITEMS, "=[ DRINKS ]=");
            
        System.out.println();
        gsystem.GENERATE_TITLE("null");
        gsystem.PAUSE();
    }

    protected void PrintMenu(HashMap<String, Double> MENU, String CAT)
    {
        String format = "%-7s%-64s%-12s%n";
        double alg = (87 - CAT.length()) / 2.0;
        String title = gsystem.FILL(((int) Math.round(alg)), '-') + CAT;

        gsystem.PRINTLN(34, title + gsystem.FILL(87 - title.length(), '-'));
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
    }

    private void Report()
    {
        gsystem.CLS();
        gsystem.HEADER();
        gsystem.GENERATE_TITLE("report");
        System.out.println();

        if (ORDER_INFO.isEmpty())
        {
            gsystem.PRINTLN(68, "THERE ARE NO ORDERS\n");
            System.out.println();
        }
        else
        {
            String format = "%-15s%-16s%-21s%-12s%n";

            gsystem.PRINTLN(44, gsystem.FILL(68, '-'));
            gsystem.PRINTF(45, format, "DATE", "TIME", "REFERENCE NUMBER", "AMOUNT");
            gsystem.PRINTLN(44, gsystem.FILL(68, '-'));

            for (ArrayList<Object> info : ORDER_INFO)
            {   
                String date     = info.get(0).toString();
                String time     = info.get(1).toString();
                String refNum   = info.get(2).toString();
                double amount   = new BigDecimal(info.get(3).toString()).doubleValue();
                String amountFormat = "Php " + amount;

                gsystem.PRINTF(45, format, date, time, refNum, amountFormat);
            }
            
            gsystem.PRINTLN(44, gsystem.FILL(68, '-'));
            System.out.println();
        }

        gsystem.GENERATE_TITLE("null");
        gsystem.PAUSE();
    }
}