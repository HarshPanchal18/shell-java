import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String[] commands = {"echo", "exit", "type", "cd", "cat"};

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();

            if (input.equals("exit 0")) {
                break;
            } else if (input.startsWith("echo ")) {
                int n = input.length();
                System.out.println(input.substring(5, n));
            } else if (input.startsWith("type")) {
                String typeSubstring = input.substring(5);
                if (Arrays.asList(commands).contains(typeSubstring)) {
                    System.out.println(typeSubstring + " is a shell builtin");
                } else {
                    System.out.println(typeSubstring + " not found");
                }
            } else {
                System.out.println(input + ": command not found");
            }

        }

        scanner.close();
    }
}