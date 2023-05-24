public class test
{
    public static void main(String[] args)
    {   
        OtherClass oc = new OtherClass();

        oc.setDate("24-05-2023");
        oc.setRainChance(20);
        oc.setTemp(36);

        System.out.println(oc.getDate());
        System.out.println(oc.getRainChance());
        System.out.println(oc.getTemp());
    }
} 

class OtherClass
{
    private String date;
    private int rainChance;
    private int temp;

    void setDate(String date) { this.date = date; }
    void setRainChance(int rainChance) { this.rainChance = rainChance; }
    void setTemp(int temp) { this.temp = temp; }

    String getDate() { return this.date; }
    int getRainChance() { return this.rainChance; }
    int getTemp() { return this.temp; }
}
