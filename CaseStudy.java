/**
 *  This case study is for finals in intermediate programming in Java. 
 *  This system requires version {@code JavaSE-20} and must be run in {@code Windows}.
 * 
 *  @author Neelian Mata
 *  @author Jarius Maui Pineda
 *  @author Niño Greg Gregorio
 *  @since 1.0
 */
public class CaseStudy  
{   
    /*
        ! RUN THE SYSTEM
        ! 2 SEC DELAY AFTER RUNNING
        ! AUTOMATICALLY RUN TO COMMAND PROMPT (IN WINDOWS)
    */
    public static void main(String[] args)
    {
        if (System.getProperty("os.name").contains("Windows")) 
            new GSystem().startSystem();
        else 
            new MainProcess();
    }
}