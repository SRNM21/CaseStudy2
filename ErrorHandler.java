import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

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
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));   

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
        final String INVALID = RED("INVALID INPUT, PLEASE TRY AGAIN");
        String input;

        // get valid input
        while (true) 
        {
            input = null;

            try 
            { 
                input = bufferedReader.readLine(); 

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
     * Provides an input reader where the user can input any text.
     * <p>
     * This method handle any input and output exception.
     * </p>
     *
     * @return valid item code
     */
    protected String getCode(ArrayList<Object[]> MENU, boolean cart)
    {
        final String INVALID = RED(" DOES NOT EXIST ON THIS MENU, PLEASE ENTER VALID CODE");
        String input;
        String[] codeInit = {"M", "S", "D"};

        // get valid input
        while (true) 
        {
            input = null;

            try 
            { 
                input = bufferedReader.readLine(); 
                
                if (input.equals("0")) return "0";
                String init = String.valueOf(input.charAt(0));
                int code = input.charAt(1) - '0';
 
                String searchCode = input;
                if (cart) 
                {
                    if (MENU.stream().anyMatch(itemDes -> itemDes[0].equals(searchCode))) return input;
                    else throw new Exception();
                }
                else
                {
                    if ((input.length() == 2 && (Arrays.stream(codeInit).anyMatch(ci -> ci.equals(init)) && (code <= MENU.size() && code > 0)))) return input;
                    else throw new Exception();
                }

            }
            catch (Exception e) 
            { 
                printLine(55, RED(input) + INVALID); 
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
        final String INVALID = RED("INVALID INPUT, PLEASE ENTER NUMBER BETWEEN ") + YEL(String.valueOf(origin)) + RED(" AND ") + YEL(String.valueOf(bound));
        String input;
        int choice = 0;

        // get valid input
        while (true) 
        {
            input = null;

            try 
            { 
                input = bufferedReader.readLine(); 
                choice = Integer.parseInt(input);
                
                // return the input if its between or equal to origin and bound
                if (choice >= origin && choice <= bound) return choice;
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
        final String INVALID = RED("INVALID INPUT, PLEASE ENTER VALID AMOUNT");
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
                input = bufferedReader.readLine(); 
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
    protected int getQuantity(int stocks) 
    {
        final String INVALID = RED("INSUFFICIENT STOCK, MAXIMUM QUANTITY IS " + stocks);
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
                input = bufferedReader.readLine();
                quantity = Integer.parseInt(input); 

                // throws exception if the input contains invalid characters 
                // or the input is 0 or 1000 or more
                if (quantity <= 0 || quantity > stocks) throw new IOException();
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
        final String INVALID = RED("INVALID INPUT, PLEASE ENTER 'Y' OR 'N'");

        boolean invalid;
        String input;

        // get valid input
        do
        {
            invalid = false;

            try 
            { 
                input = bufferedReader.readLine();

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