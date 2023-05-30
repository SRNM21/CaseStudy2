/**
 *  This case study is for finals in intermediate programming in Java, specifically for first-year students in their second semester. 
 *  This system requires version {@code JavaSE-20} and must be run in {@code Windows}.
 * 
 *  @author Neelian Mata
 *  @author Jarius Maui Pineda
 *  @author Niño Greg Gregorio
 */
public class CaseStudy  
{   
    /*
        ! RUN THE SYSTEM
        ! 2 SEC DELAY AFTER RUNNING
    */
    public static void main(String[] args)
    {
        if (System.getProperty("os.name").contains("Windows")) 
            new GSystem().START_SYSTEM();
        else 
            new MainProcess();
    }
}