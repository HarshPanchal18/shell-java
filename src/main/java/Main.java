import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String[] commands = {"echo", "exit", "type", "cd", "cat"};

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            String[] inputs = input.split(" ");
            String command = inputs[0];
            String parameter = "";

            if (inputs.length > 2) {
                for (int i = 1; i < inputs.length; i++) {
                    if (i < inputs.length - 1)
                        parameter += inputs[i] + " ";
                    else
                        parameter += inputs[i];
                }
            } else if (inputs.length > 1) {
                parameter = inputs[1];
            }

            switch (command) {
                case "exit":
                    if (parameter.equals("0"))
                        System.exit(0);
                    else
                        System.out.println(command + ": not found");
                    break;

                case "echo":
                    System.out.println(parameter);
                    break;

                case "type":
                    if (Arrays.asList(commands).contains(parameter)) {
                        System.out.println(parameter + " is a shell builtin");
                    } else {
                        String filePath = getFilePath(parameter);
                        if (filePath != null)
                            System.out.println(parameter + " is " + filePath);
                        else
                            System.out.println(parameter + ": not found");
                    }
                    break;

                default:
                    System.out.println(input + ": command not found");
                    break;

            }
        }
    }

    private static String getFilePath(String parameter) {
        for (String path : System.getenv("PATH").split(File.pathSeparator)) {
            Path fullPath = Path.of(path, parameter);

            if (Files.isRegularFile(fullPath))
                return fullPath.toString();

        }

        return null;
    }
}