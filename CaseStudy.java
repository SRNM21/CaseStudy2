public class CaseStudy
{   
    public static void main(String[] args)
    {
        if (System.getProperty("os.name").contains("Windows")) 
            new GSystem().START_SYSTEM();
        else 
            new MainProcess();
    }
}