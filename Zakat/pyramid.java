public class pyramid {

    public static void main(String[] args) {
        int num = 5;
        int rows = 5;

        // First pyramid: star pattern
        for (int i = 1; i < num; i++) {
            for (int j = num - i; j > 0; j--) {
                System.out.print("#");
            }
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }

        // Second pyramid: number pattern
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j < i; j++) {
                System.out.print("%");
            }
            for (int j = i; j <= rows; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}
