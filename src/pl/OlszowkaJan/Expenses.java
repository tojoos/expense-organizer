package pl.OlszowkaJan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Expenses {
    private File files = new File("Files");
    private File outcome = new File("Files\\Expenses.txt");
    private Map<String,Float> hashExpense = new HashMap<>();
    private float totalExpenseValue=0;


        public Expenses () throws IOException {
            if(!files.exists())
                Files.createDirectory(files.toPath());
            if(!outcome.exists())
                outcome.createNewFile();

            Scanner fileScan = new Scanner(outcome);
            String temp;
            while(fileScan.hasNext())
            {
                temp= fileScan.nextLine();
                hashExpense.put(temp.substring(0,temp.indexOf(" ")),Float.parseFloat(temp.substring(temp.indexOf(" ")+1)));
            }
            fileScan.close();
        }

    public void editExpenses() throws IOException {
        Scanner scanner = new Scanner(System.in);
        int choice=6;
        boolean shouldRunExp=true;
        while(shouldRunExp) {
            System.out.println("1. Enter expense\n2. Edit expense\n3. Remove expense\n4. Show expense list\n5. Clear expense list\n6. Go back to main Menu");
            try {
                choice = scanner.nextInt();
            }
            catch(InputMismatchException e)
            {
                System.err.println("Wrong option, leaving.");
                System.exit(0);
            }
            switch (choice) {
                case 1 -> {
                    scanner.nextLine();
                    System.out.println("Enter expense name and its value (Space separated): ");
                    String fullExpense = scanner.nextLine();
                    if(fullExpense.contains(" ")) {
                        String keyExpense = fullExpense.substring(0, fullExpense.indexOf(" "));
                        if (fullExpense.contains(",")) fullExpense = fullExpense.replace(",", ".");
                        if (fullExpense.contains("zl")) fullExpense = fullExpense.replace("zl", "");
                        if (fullExpense.contains("zł")) fullExpense = fullExpense.replace("zł", "");
                        float valueExpense = Float.parseFloat(fullExpense.substring(fullExpense.indexOf(" ") + 1));
                        hashExpense.put(keyExpense, valueExpense);
                        System.out.println("You have successfully added: " + keyExpense + " " + valueExpense + " zł");
                    }
                    else System.out.println("Wrong format, please try again");
                }

                case 2 -> {
                    int i=1;
                    for(String key : hashExpense.keySet()) {
                     System.out.println(i + ". " + key + " " + hashExpense.get(key) + " zł");
                     i++;
                    }
                    System.out.println();
                    scanner.nextLine();
                    System.out.println("Choose expense by its name to edit it: ");
                    String editChoice = scanner.nextLine();
                    System.out.println("You have chosen: " + editChoice + " " + hashExpense.get(editChoice));
                    String oldHash= editChoice + " " + hashExpense.get(editChoice);
                    System.out.println("What to edit? (Name/amount):");
                    String toReplace = scanner.nextLine();
                    if(toReplace.equals("Name") || toReplace.equals("name")) {
                        System.out.println("Enter new name: ");
                        String nameToReplace=scanner.nextLine();
                        float valueToRemember=hashExpense.get(editChoice);
                        hashExpense.remove(editChoice);
                        hashExpense.put(nameToReplace,valueToRemember);
                        System.out.println("You have successfully edited: " + oldHash + " with " + nameToReplace + " " + hashExpense.get(nameToReplace)+ " zł");
                    }
                    else if (toReplace.equals("Amount") || toReplace.equals("amount")) {
                        System.out.println("Enter new amount: ");
                        String newAmount =scanner.nextLine();
                        hashExpense.replace(editChoice, hashExpense.get(editChoice), Float.parseFloat(newAmount));
                        System.out.println("You have successfully edited: " + oldHash + " zł  with " + editChoice + " " + hashExpense.get(editChoice) + " zł");
                    }
                    else System.out.println("Wrong answer, going back");

                }

                case 3 -> {
                    showExpenseList();
                        int i=1;
                        for(String key : hashExpense.keySet()) {
                            System.out.println(i + ". " + key + " " + hashExpense.get(key) + " zł");
                            i++;
                        }
                        scanner.nextLine();
                        System.out.println("Chose expense to remove by its name: ");
                        String toRemove = scanner.nextLine();
                        if(hashExpense.containsKey(toRemove)) {
                            System.out.println("You have successfully removed: " + toRemove + " " + hashExpense.get(toRemove));
                            hashExpense.remove(toRemove);
                        }
                        else
                            System.out.println("name not found");
                }

                case 4 -> showExpenseList();

                case 5 -> {
                    scanner.nextLine();
                    System.out.println("Are you sure to delete whole expense list? (Yes/No)");
                    String clearAnswer = scanner.nextLine();
                    if(clearAnswer.trim().equals("Yes") || clearAnswer.trim().equals("yes")) {
                        hashExpense.clear();
                        FileWriter fileWriter = new FileWriter(outcome);
                        fileWriter.close();
                        System.out.println("You have successfully cleared expense list.");
                    }

                    totalExpenseValue=0;
                }

                case 6 -> shouldRunExp = false;
            }

        updateFile();
        }
    }


    public void calculateTotalExpenseValue()
    {
        totalExpenseValue=0;
        for(String key : hashExpense.keySet()) {
            this.totalExpenseValue=this.totalExpenseValue + hashExpense.get(key);
        }

        System.out.println("Total Expenses: " + totalExpenseValue + " zł");
    }

    public float returnTotalExpenseValue()
    {
        totalExpenseValue=0;
        for(String key : hashExpense.keySet()) {
            this.totalExpenseValue=this.totalExpenseValue + hashExpense.get(key);
        }

        return this.totalExpenseValue;
    }

    public void updateFile() throws IOException {
        FileWriter fileWriter = new FileWriter(outcome);
        fileWriter.close();
        fileWriter = new FileWriter(outcome);
        for(String key : hashExpense.keySet())
            fileWriter.write(key + " " + hashExpense.get(key) +"\n");
        fileWriter.close();
    }

    public void showExpenseList()
    {
        int i=1;
        System.out.println("Actual Expense list: ");
        for(String key : hashExpense.keySet())
        {
            System.out.println(i + ". "+ key + " " + hashExpense.get(key) + " zł");
            i++;
        }
        calculateTotalExpenseValue();
        System.out.println();
    }
}
