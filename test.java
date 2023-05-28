import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class test
{
    private final static File itemData = new File("ITEMS.txt");
    private static ArrayList<HashMap<String, String>> ORIGINAL_ITEMS = new ArrayList<>()
    {{
        add(new HashMap<>()
        {{
            put("Burger Steak", "99.20");
            put("Fried Chicken", "80.30"); 
            put("Spaghetti", "90.20");
        }});

        add(new HashMap<>()
        {{
            put("Fried Chicken Sandwich", "50.80");
            put("Vegan Sandwich", "40.10"); 
            put("Egg Sandwich", "29.99");
        }});

        add(new HashMap<>()
        {{
            put("Water", "10.00");
            put("Iced Tea", "15.30"); 
            put("Coca Cola", "20.20");
        }});
    }};
    //private static final GSystem gsystem = new GSystem();
    public static void main(String[] args)
    {   
        System.out.println("NEW FILE IS SUCCESSFULLY CREATED!".length());
    }
} 