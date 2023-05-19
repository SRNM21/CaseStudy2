import java.util.ArrayList;

public class test
{
    public static void main(String[] args)
    {   
        String item = "Test Long Words one two three four five six seven eight nine ten eleven twelve thirteen fourteen fifteen";  
        String[] words = item.split(" ");
        ArrayList<String> multiLine = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        
        System.out.println("\n32 characters per line test");
        System.out.println("++++++++++++++++++++++++++++++++");
        for (String word : words) 
        {
            if (line.length() + word.length() < 32)
            {
                line.append(word + " ");
            }
            else 
            {
                multiLine.add(line.toString());
                line.setLength(0);
                line.append(word + " ");
            }
        }

        for (String string : multiLine) System.out.println(string);
    }
}
