import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Error Handler implements {@link InputStreamReader} as a line reader 
 * and wrapping it to a {@link  BufferedReader} to handle user's invalid 
 * inputs and provide them a chance to re-input.
 * 
 * @author GREGORIO-MATA-PINEDA
 * @since 1.0
 */
public class ErrorHandler 
{   
    private final GSystem gsystem = new GSystem();
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));   
    
    /**
     * Returns a {@code int} value between the specified
     * origin (inclusive) and the specified bound (exclusive).
     * 
     * @param origin the least value that can be returned
     * @param bound the upper bound (exclusive) for the returned value
     *
     * @return a chosen {@code int} value between the
     *         origin (inclusive) and the bound (exclusive)
     */
    protected int getChoice(int origin, int bound) 
    {
        final String INVALID = "INVALID INPUT, PLEASE ENTER NUMBER BETWEEN " 
            + origin + " AND " + bound + ":  ";
        
        while (true) 
        {
            String input = null;

            try { input = br.readLine(); } 
            catch (IOException e) { gsystem.PRINT(50, INVALID); } 
            
            try  
            {
                int i = Integer.parseInt(input);
                
                if (i >= origin && i <= bound) return i;
                else gsystem.PRINT(50,INVALID);
            } 
            catch (NumberFormatException e) { gsystem.PRINT(50,INVALID); }
        }
    }

    /**
     * Returns a completely {@code double} value inputted by the user
     * 
     * @return a completely {@code double} value inputted by the user
     */
    protected double getAmount() 
    {
        final String INVALID = "INVALID INPUT, PLEASE ENTER VALID AMOUNT:  ";

        double price = 0;
        boolean invalid;
        
        do
        {
            String input = null;
            invalid = false;

            try { input = br.readLine(); } 
            catch (IOException e) { gsystem.PRINT(50,INVALID); } 
            
            try { price = Double.parseDouble(input); } 
            catch (NumberFormatException e) 
            {  
                gsystem.PRINT(50,INVALID);
                invalid = true;
            }
        }
        while (invalid);

        return price;
    }

    /**
     * Returns a completely {@code int} value inputted by the user
     * 
     * @return a completely {@code int} value inputted by the user
     */
    protected int getQuantity() 
    {
        final String INVALID = "INVALID INPUT, MIN = 0, MAX = 999:  ";

        int quantity = 0;
        boolean invalid;
        
        do
        {
            String input = null;
            invalid = false;

            try { input = br.readLine(); } 
            catch (IOException e) { gsystem.PRINT(50,INVALID); } 
            
            try 
            {
                quantity = Integer.parseInt(input); 

                if (quantity <= 0 || quantity >= 999) 
                {
                    gsystem.PRINT(50,INVALID);
                    invalid = true; 
                }
            } 
            catch (NumberFormatException e) 
            {  
                gsystem.PRINT(50,INVALID);
                invalid = true;
            }
        }
        while (invalid);

        return quantity;
    }

    /**
     * Returns {@code true} if the input is {@code 'y'} and {@code false} if {@code 'n'}
     *
     * @return {@code true} if the input is {@code 'y'} and {@code false} if {@code 'n'}
     */
    protected boolean getConfirmation()
    {
        final String INVALID = "INVALID INPUT, PLEASE ENTER 'y' OR 'n':  ";

        boolean invalid;
        String input;

        do
        {
            invalid = false;

            try 
            { 
                input = br.readLine();

                switch (input)
                {
                    case "y" -> { return true; }
                    case "n" -> { return false; }
                    default ->
                    {
                        gsystem.PRINT(50,INVALID);
                        invalid = true;
                    }
                }
            } 
            catch (IOException e) { gsystem.PRINT(50,INVALID); } 
        }
        while (invalid);

        return true;
    }
}