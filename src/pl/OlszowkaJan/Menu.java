package pl.OlszowkaJan;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private boolean shouldRun=true;

    public void showMenu() throws IOException {
        Expenses expenses = new Expenses();
        Incomes incomes = new Incomes();
        Backup backup = new Backup();

        System.out.println("Welcome to Expense organizer v. 1.0!\nPress Enter key to start.");
        try {
             System.in.read();
        } catch (Exception e) {
        }
        while (shouldRun) {
            System.out.println("Menu:\n1. Expenses\n2. Incomes\n3. Show current balance status (incomes, outcomes)\n4. Create backup\n5. Exit");
            int choice=5;
            Scanner scanner = new Scanner(System.in);
            try {
                choice = scanner.nextInt();
            }
            catch(InputMismatchException e)
            {
                System.err.println("Wrong option, leaving.");
            }
            switch (choice) {
                case 1 -> expenses.editExpenses();
                case 2 -> incomes.editIncomes();
                case 3 -> {
                        expenses.showExpenseList();
                        incomes.showIncomeList();
                        System.out.print("You current balance is: ");
                        System.out.println(incomes.returnTotalIncomesValue() - expenses.returnTotalExpenseValue() + " zł.\n");
                }
                case 4 -> backup.createBackup();
                case 5 -> shouldRun = false;
                default -> System.out.println("Wrong option");
            }

        }
        System.exit(0);
    }

}
