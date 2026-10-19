
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        loadTasks(tasks);
        boolean running = true;

        while (running) {
            System.out.println("\n===== STUDENT TASK MANAGER =====");
            System.out.println("1. View tasks");
            System.out.println("2. Add a task");
            System.out.println("3. Mark a task as completed");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    for (int i = 0; i < tasks.size(); i++) {
                        Task task = tasks.get(i);
                        String status = task.isCompleted() ? "[X]" : "[ ]";
                        System.out.println((i + 1) + ". " + status + " " + task.getTitle());
                    }
                    break;

                case "2":
                    System.out.print("Enter a new task: ");
                    String title = scanner.nextLine();

                    if (!title.isBlank()) {
                        tasks.add(new Task(title));
                        System.out.println("Task added!");
                    } else {
                        System.out.println("Task title cannot be empty.");
                    }
                    break;

                case "3":
                    System.out.print("Enter the task number to complete: ");
                    String number = scanner.nextLine();

                    try {
                        int index = Integer.parseInt(number) - 1;

                        if (index >= 0 && index < tasks.size()) {
                            tasks.get(index).markAsCompleted();
                            System.out.println("Task completed!");
                        } else {
                            System.out.println("Invalid task number.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }
                    break;

                case "4":
                    saveTasks(tasks);
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Choose 1, 2, 3, or 4.");
            }
        }

        scanner.close();
    }
    public static void saveTasks(ArrayList<Task> tasks) {
    try (PrintWriter writer = new PrintWriter("tasks.txt")) {
        for (Task task : tasks) {
            writer.println(task.isCompleted() + "|" + task.getTitle());
        }
        System.out.println("Tasks saved successfully!");
    } catch (IOException e) {
        System.out.println("Error saving tasks: " + e.getMessage());
    }
}

public static void loadTasks(ArrayList<Task> tasks) {
    File file = new File("tasks.txt");

    if (!file.exists()) {
        return;
    }

    try (Scanner fileScanner = new Scanner(file)) {
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split("\\|", 2);

            if (parts.length == 2) {
                Task task = new Task(parts[1]);

                if (Boolean.parseBoolean(parts[0])) {
                    task.markAsCompleted();
                }

                tasks.add(task);
            }
        }
        System.out.println("Tasks loaded successfully!");
    } catch (IOException e) {
        System.out.println("Error loading tasks: " + e.getMessage());
    }
}

}
