import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Administration 
{    
    private static final ErrorHandler erh   = new ErrorHandler();
    private static final GSystem gsystem = new GSystem();

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  

    Administration()
    {
        while (true)
        {
            gsystem.CLS();
            gsystem.HEADER();
            gsystem.GENERATE_TITLE("administration");
            gsystem.PRINTLN();
            gsystem.PRINTLN("[0] - BACK\n");
            gsystem.PRINTLN("[1] - MANAGE MENU\n");
            gsystem.PRINTLN("[2] - REPORT\n");
            gsystem.PRINTLN();
            gsystem.PRINT("ENTER CHOICE:  ");
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
            gsystem.PRINTLN();
            gsystem.PRINTLN("[0] - BACK\n");
            gsystem.PRINTLN("[1] - MEALS\n");
            gsystem.PRINTLN("[2] - SANDWICH\n");
            gsystem.PRINTLN("[3] - DRINKS\n");
            gsystem.PRINTLN("[4] - SHOW ITEMS\n");
            gsystem.PRINTLN();
            gsystem.PRINT("ENTER CHOICE:  ");
            int admm1 = erh.getChoice(0, 4);

            switch (admm1)
            {
                case 0 -> { return; }
                case 1 -> ManageMenu2(CaseStudy.MEALS_ITEMS,    "manage_menu_meals");
                case 2 -> ManageMenu2(CaseStudy.SANDWICH_ITEMS, "manage_menu_sandwich");
                case 3 -> ManageMenu2(CaseStudy.DRINKS_ITEMS,   "manage_menu_drinks");
                case 4 -> ShowItems();
            }
        }
    }

    private void ManageMenu2(HashMap<String, Double> MAP, String CAT)
    {
        gsystem.CLS();
        gsystem.HEADER();
        gsystem.GENERATE_TITLE(CAT);
        gsystem.PRINTLN();
        gsystem.PRINTLN("[0] - BACK\n");
        gsystem.PRINTLN("[1] - ADD ITEM\n");
        gsystem.PRINTLN("[2] - DELETE ITEM\n");
        gsystem.PRINTLN();
        gsystem.PRINT("ENTER CHOICE:  ");
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
            gsystem.PRINTLN();

            try 
            {
                gsystem.PRINTLN("ENTER DESIRED ITEM THAT YOU WANT TO ADD ON THIS MENU  "); 
                gsystem.PRINT(">>  ");    
                item = br.readLine();
    
                gsystem.PRINTLN();

                gsystem.PRINTLN("ENTER ITEM'S AMOUNT:  ");
                gsystem.PRINT(">>  ");   
                amount = erh.getAmount();
            } 
            catch (IOException e) { e.printStackTrace(); }
            
            gsystem.PRINTLN();

            if (MAP.containsKey(item))
            {
                gsystem.PRINTLN("THIS ITEM IS ALREADY EXIST");
                gsystem.PRINT("DO YOU WANT TO ADD OTHER ITEM INSTEAD? (y/n):  ");
                runAgain = erh.getConfirmation();
            }
            else
            { 
                gsystem.PRINTLN("ITEM        :  " + item);
                gsystem.PRINTLN("AMOUNT      :  " + "Php " + amount);
                gsystem.PRINTLN();
                gsystem.PRINT("ARE YOU SURE TO ADD THIS ITEM? (y/n):  ");
                boolean confirm = erh.getConfirmation();

                if (confirm)
                {
                    MAP.put(item, amount);
                    gsystem.PRINTLN("ITEM IS ADDED SUCCESSFULLY!");
                }
                else 
                { 
                    gsystem.PRINTLN("ADDING ITEM IS CANCELLED SUCCESSFULLY!");
                }
            }
        }
        while (runAgain);
        
        gsystem.GENERATE_TITLE("null");
        gsystem.WAIT();
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
                gsystem.PRINT("ENTER ITEM THAT YOU WANT TO DELETE:  ");
                item = br.readLine();
            } 
            catch (IOException e) { e.printStackTrace(); }
            
            gsystem.PRINTLN();
            
            if (MAP.containsKey(item))
            {
                gsystem.PRINTLN("ITEM        :  " + item);
                gsystem.PRINTLN("AMOUNT      :  " + "Php " + MAP.get(item));
                gsystem.PRINTLN();
                gsystem.PRINT("ARE YOU SURE TO DELETE THIS ITEM? (y/n):  ");
                boolean confirm = erh.getConfirmation();

                if (confirm)
                {
                    MAP.remove(item); 
                    gsystem.PRINTLN("ITEM IS DELETED SUCCESSFULLY!");
                }
                else 
                { 
                    gsystem.PRINTLN("DELETING ITEM IS CANCELLED SUCCESSFULLY!");
                }
            }
            else 
            { 
                gsystem.PRINTLN("ITEM DOES NOT EXIST");
                gsystem.PRINT("DO YOU WANT TO DELETE OTHER ITEM INSTEAD? (y/n):  ");
                runAgain = erh.getConfirmation();
            }
        }
        while (runAgain);

        gsystem.GENERATE_TITLE("null");
        gsystem.WAIT();
    }

    private void ShowItems()
    {
        gsystem.CLS();
        gsystem.HEADER();
        gsystem.GENERATE_TITLE("administration");

        gsystem.PRINTLN();
        gsystem.PRINTLN("MEALS");
        for (String item : CaseStudy.MEALS_ITEMS.keySet()) 
            gsystem.PRINTLN(item + " = " + CaseStudy.MEALS_ITEMS.get(item));
        
        gsystem.PRINTLN();
        gsystem.PRINTLN("SANDWICH");
        for (String item : CaseStudy.SANDWICH_ITEMS.keySet()) 
            gsystem.PRINTLN(item + " = " + CaseStudy.SANDWICH_ITEMS.get(item));
        
        gsystem.PRINTLN();
        gsystem.PRINTLN("DRINKS");
        for (String item : CaseStudy.DRINKS_ITEMS.keySet()) 
            gsystem.PRINTLN(item + " = " + CaseStudy.DRINKS_ITEMS.get(item));
            
        gsystem.GENERATE_TITLE("null");
        gsystem.WAIT();
    }

    //*  TEMPORTARY PRINTER OF ITEMS */
    private void Report()
    {
        gsystem.CLS();
        gsystem.HEADER();
        gsystem.GENERATE_TITLE("report");
        gsystem.WAIT();
    }
}