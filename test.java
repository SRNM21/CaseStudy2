import java.util.Random;

public class test
{
    private static final GSystem gsystem = new GSystem();
    public static void main(String[] args)
    {  
        // 39 ------------------------------------=[ SANDWICH ]=-------------------------------------
        // 38 --------------------------------------=[ MEALS ]=--------------------------------------
        gsystem.PRINTLN(73, "[ MEALS ]");
        gsystem.PRINTLN(72, "[ SANDWICH ]");
        gsystem.PRINTLN(73, "[ DRINKS ]");
        
        // double a = ("---------------------------------------------------------------------------------------".length() - "=[ MEALS ]=".length()) / 2.0;
        // double b = ("---------------------------------------------------------------------------------------".length() - "=[ SANDWICH ]=".length()) / 2.0;
        // double c = ("---------------------------------------------------------------------------------------".length() - "=[ DRINKS ]=".length()) / 2.0;
        // System.out.println("---------------------------------------------------------------------------------------".length());
        // System.out.println(Math.round(a));
        // System.out.println(Math.round(b));
        // System.out.println(Math.round(c));

        printa("=[ MEALS ]=");
        printa("=[ SANDWICH ]=");
        printa("=[ DRINKS ]=");
        
    }

    public static void printa(String CAT)
    {
        double alg = (87 - CAT.length()) / 2.0;
        String title = gsystem.FILL(((int) Math.round(alg)), '-') + CAT;
        gsystem.PRINTLN(34, title + gsystem.FILL(87 - title.length(), '-'));
    }
} 