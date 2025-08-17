public class Individu {
    public String name; // Assuming you might want to add a name field
    public int age; // Assuming you might want to add an age field
    public int id; // Assuming you might want to add an ID field
    public double TotalWealth; // Make sure this field exists

    public Individu() {
        this.name = "";
        this.age = 0;
        this.id = 0;
        this.TotalWealth = 0.0;
    }
    public Individu(String name, int age, int id, double totalWealth) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.TotalWealth = totalWealth;
    }
    
public void displayInfo() {
        System.out.println("\nEntered inforamtion Individu Information:");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("ID: " + id);
        System.out.println("Total Wealth: " + TotalWealth);
        System.out.println("Zakat Due: " + calcZakat());
        System.out.println("-----------------------------\n");
    }

    public double calcZakat() {
        if (TotalWealth < 5000) {
            System.out.println("Your wealth is below the nisab. No Zakat due.");
            return 0.0;
        } else {
            final double ZAKAT_RATE = 0.025; // 2.5%
            return TotalWealth * ZAKAT_RATE;
        }
    }
}
