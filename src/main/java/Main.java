import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String[] commands = {"echo", "exit", "type", "pwd", "cd"};
        String cwd = Path.of("").toAbsolutePath().toString();

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

                case "pwd":
                    System.out.println(cwd);
                    break;

                case "cd":
                    if (!parameter.startsWith("/"))
                        parameter = cwd + "/" + parameter;

                    Path parameterPath = Path.of(parameter);

                    if (Files.isDirectory(parameterPath))
                        cwd = parameterPath.normalize().toString();
                    else
                        System.out.println("cd: " + parameter + ": No such file or directory");

                    break;

                default:
                    if (!parameter.isEmpty()) {
                        String path = getFilePath(command);
                        if (path != null) {
                            String[] fullPath = new String[]{command, parameter};
                            Process ps = Runtime.getRuntime().exec(fullPath);
                            ps.getInputStream().transferTo(System.out);
                        } else {
                            System.out.println(command + ": command not found");
                        }
                    } else {
                        System.out.println(input + ": command not found");
                    }

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