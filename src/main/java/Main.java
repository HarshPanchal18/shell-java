// Uncomment this block to pass the first stage
// import java.util.Scanner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("$ ");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        do {
            System.out.println(input + ": command not found");
            System.out.print("$ ");

            input = scanner.nextLine();

            if (input.equals("exit 0"))
                System.exit(0);

        } while (!input.isEmpty());

        scanner.close();
    }
}