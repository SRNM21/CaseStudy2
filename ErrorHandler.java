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
public class ErrorHandler extends GSystem
{
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));   

    /**
     * Provides an input reader where the user can input any text.
     * <p>
     * This method handle any input and output exception.
     * </p>
     *
     * @return valid user input
     */
    protected String getLine()
    {
        final String INVALID = RED + "INVALID INPUT, PLEASE TRY AGAIN" + RES;
        String input;

        // get valid input
        while (true) 
        {
            input = null;

            try 
            { 
                input = br.readLine(); 

                if (!input.isEmpty()) return input;
                else throw new IOException();
            } 
            catch (IOException e) 
            { 
                printLine(55, INVALID); 
                pointer();
            }
        }
    }
    
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
        final String INVALID = RED + "INVALID INPUT, PLEASE ENTER NUMBER BETWEEN " + YEL + origin + RED + " AND " + YEL + bound + RES;
        String input;
        int i = 0;

        // get valid input
        while (true) 
        {
            input = null;

            try 
            { 
                input = br.readLine(); 
                i = Integer.parseInt(input);
                
                // return the input if its between or equal to origin and bound
                if (i >= origin && i <= bound) return i;
                // otherwise, throw exception
                else throw new NumberFormatException();
            } 
            catch (NumberFormatException | IOException e) 
            { 
                printLine(55, INVALID); 
                pointer();
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
        final String INVALID = RED + "INVALID INPUT, PLEASE ENTER VALID AMOUNT" + RES;
        String input;
        double price = 0;
        boolean invalid;
        
        // get valid input
        do
        {
            input = null;
            invalid = false;

            try 
            { 
                input = br.readLine(); 
                price = Double.parseDouble(input); 
                // throws exception if the input contains invalid characters
            } 
            catch (NumberFormatException | IOException e) 
            {  
                printLine(55, INVALID);
                pointer();
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
        final String INVALID = RED + "INVALID INPUT, MINIMUM IS 1 AND MAXIMUM IS 999" + RES;
        String input;
        int quantity = 0;
        boolean invalid;
        
        // get valid input
        do
        {
            input = null;
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
                printLine(55, INVALID);
                pointer();
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
        final String INVALID = RED + "INVALID INPUT, PLEASE ENTER 'y' OR 'n'" + RES;

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
                switch (input.toLowerCase())
                {
                    case "y" -> { return true; }
                    case "n" -> { return false; }
                    default  -> throw new IOException();
                }
            } 
            catch (IOException e) 
            { 
                printLine(55, INVALID);
                pointer();
                invalid = true;
            } 
        }
        while (invalid);

        return true;
    }
}