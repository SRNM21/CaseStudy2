import java.util.ArrayList;

public class test
{
    public static void main(String[] args)
    {   
        String x = "Test Long Words one two three four five six seven eight nine ten eleven twelve thirteen fourteen fifteen";

        ArrayList<String> multiLine = new ArrayList<>();
        String[] words = x.split(" ");
        StringBuilder line = new StringBuilder();
        
        for (String word : words) 
        {
            line.append(word + " ");

            if (line.length() + word.length() > 42)
            {
                multiLine.add(line.toString());
                line.setLength(0);
            }
        }

        if (!line.isEmpty()) multiLine.add(line.toString());

        for (String string : multiLine) {
            System.out.println(string);
        }
    }
}
