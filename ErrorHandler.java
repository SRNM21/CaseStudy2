import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  The {@code Error Handler} class implements {@link InputStreamReader} class as a line reader 
 *  and wrapping it to a {@link  BufferedReader} class to handle user's invalid 
 *  inputs and provide them a chance to re-input.
 * 
 *  @author Neelian Mata
 *  @author Jarius Maui Pineda
 *  @author Niño Greg Gregorio
 *  @since 1.0
 */
public class ErrorHandler 
{   
    // class and imports
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
            + gsystem.YEL + origin + gsystem.RES + " AND " + gsystem.YEL + bound + gsystem.RES;
        
        // get valid input
        while (true) 
        {
            String input = null;

            try 
            { 
                input = br.readLine(); 
                int i = Integer.parseInt(input);
                
                // return the input if its between or equal to origin and bound
                if (i >= origin && i <= bound) return i;
                // otherwise, throw exception
                else throw new NumberFormatException();
            } 
            catch (NumberFormatException | IOException e) 
            { 
                gsystem.printLine(55, INVALID); 
                gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
            }
        }
    }

    /**
     * Returns a completely {@code double} value inputted by the user
     * 
     * @return a completely {@code double} value inputted by the user
     */
    protected double getAmount() 
    {
        final String INVALID = "INVALID INPUT, PLEASE ENTER VALID AMOUNT";

        double price = 0;
        boolean invalid;
        
        // get valid input
        do
        {
            String input = null;
            invalid = false;

            try 
            { 
                input = br.readLine(); 
                price = Double.parseDouble(input); 
                // throws exception if the input contains invalid characters
            } 
            catch (NumberFormatException | IOException e) 
            {  
                gsystem.printLine(55, INVALID);
                gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
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
        final String INVALID = "INVALID INPUT, MINIMUM IS 0 AND MAXIMUM IS 999";

        int quantity = 0;
        boolean invalid;
        
        // get valid input
        do
        {
            String input = null;
            invalid = false;

            try 
            {
                input = br.readLine();
                quantity = Integer.parseInt(input); 

                // throws exception if the input contains invalid characters 
                // or the input is 0 or 1000 or more
                if (quantity <= 0 || quantity >= 999) throw new IOException();
            } 
            catch (NumberFormatException | IOException e) 
            {  
                gsystem.printLine(55,INVALID);
                gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
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
        final String INVALID = "INVALID INPUT, PLEASE ENTER 'y' OR 'n'";

        boolean invalid;
        String input;

        // get valid input
        do
        {
            invalid = false;

            try 
            { 
                input = br.readLine();

                // check if the input is 'y' or 'n' 
                switch (input)
                {
                    case "y" -> { return true; }
                    case "n" -> { return false; }
                    default  -> throw new IOException();
                }
            } 
            catch (IOException e) 
            { 
                gsystem.printLine(55,INVALID);
                gsystem.prints(55, gsystem.GRE + ">> " + gsystem.RES);
                invalid = true;
            } 
        }
        while (invalid);

        return true;
    }
}