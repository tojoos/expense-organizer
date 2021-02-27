package pl.OlszowkaJan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Incomes {
        private File files = new File("Files");
        private File income = new File("Files\\Incomes.txt");
        private Map<String,Float> hashIncomes = new HashMap<>();
        private float totalIncomesValue=0;

        public Incomes () throws IOException {
            if(!income.exists())
                income.createNewFile();
            Scanner fileScan = new Scanner(income);
            String temp;
            while(fileScan.hasNext())
            {
                temp= fileScan.nextLine();
                hashIncomes.put(temp.substring(0,temp.indexOf(" ")),Float.parseFloat(temp.substring(temp.indexOf(" ")+1)));
            }
            fileScan.close();
        }

        public void editIncomes() throws IOException {
            Scanner scanner = new Scanner(System.in);
            int choice=6;
            boolean shouldRunExp=true;
            while(shouldRunExp) {
                System.out.println("1. Enter income\n2. Edit income\n3. Remove income\n4. Show income list\n5. Clear income list\n6. Go back to main Menu");
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
                        System.out.println("Enter income name and its value (Space separated): ");
                        String fullIncome = scanner.nextLine();
                        if(fullIncome.contains(" ")) {
                            String keyIncome = fullIncome.substring(0, fullIncome.indexOf(" "));
                            if (fullIncome.contains(",")) fullIncome = fullIncome.replace(",", ".");
                            if (fullIncome.contains("zl")) fullIncome = fullIncome.replace("zl", "");
                            if (fullIncome.contains("zł")) fullIncome = fullIncome.replace("zł", "");
                            float valueExpense = Float.parseFloat(fullIncome.substring(fullIncome.indexOf(" ") + 1));
                            hashIncomes.put(keyIncome, valueExpense);
                            System.out.println("You have successfully added: " + keyIncome + " " + valueExpense + " zł");
                        }
                        else System.out.println("Wrong format, please try again");
                    }

                    case 2 -> {
                        int i=1;
                        for(String key : hashIncomes.keySet()) {
                            System.out.println(i + ". " + key + " " + hashIncomes.get(key) + " zł");
                            i++;
                        }
                        System.out.println();
                        scanner.nextLine();
                        System.out.println("Choose income by its name to edit it: ");
                        String editChoice = scanner.nextLine();
                        System.out.println("You have chosen: " + editChoice + " " + hashIncomes.get(editChoice));
                        String oldHash= editChoice + " " + hashIncomes.get(editChoice);
                        System.out.println("What to edit? (Name/amount):");
                        String toReplace = scanner.nextLine();
                        if(toReplace.equals("Name") || toReplace.equals("name"))
                        {
                            System.out.println("Enter new name: ");
                            String nameToReplace=scanner.nextLine();
                            float valueToRemember=hashIncomes.get(editChoice);
                            hashIncomes.remove(editChoice);
                            hashIncomes.put(nameToReplace,valueToRemember);
                            System.out.println("You have successfully edited: " + oldHash + " with " + nameToReplace + " " + hashIncomes.get(nameToReplace));
                        }
                        else if (toReplace.equals("Amount") || toReplace.equals("amount"))
                        {
                            System.out.println("Enter new amount: ");
                            String newAmount = scanner.nextLine();
                            hashIncomes.replace(editChoice, hashIncomes.get(editChoice), Float.parseFloat(newAmount));
                            System.out.println("You have successfully edited: " + oldHash + "  with " + editChoice + " " + hashIncomes.get(editChoice));
                        }
                        else
                            System.out.println("Wrong answer, going back");
                    }

                    case 3 ->
                            {
                                int i=1;
                                for(String key : hashIncomes.keySet()) {
                                    System.out.println(i + ". " + key + " " + hashIncomes.get(key) + " zł");
                                    i++;
                                }
                                scanner.nextLine();
                                System.out.println("Chose income to remove by its name: ");
                                String toRemove = scanner.nextLine();
                                if(hashIncomes.containsKey(toRemove)) {
                                    System.out.println("You have successfully removed: " + toRemove + " " + hashIncomes.get(toRemove));
                                    hashIncomes.remove(toRemove);
                                }
                                else
                                    System.out.println("Name not found");
                            }

                    case 4 -> showIncomeList();


                    case 5 -> {
                        scanner.nextLine();
                        System.out.println("Are you sure to delete whole income list? (Yes/No)");
                        String clearAnswer = scanner.nextLine();
                        if(clearAnswer.trim().equals("Yes") || clearAnswer.trim().equals("yes")) {
                            hashIncomes.clear();
                            FileWriter fileWriter = new FileWriter(income);
                            fileWriter.close();
                            System.out.println("You have successfully cleared income list");
                        }
                        totalIncomesValue=0;
                    }

                    case 6 -> shouldRunExp = false;
                    default -> System.out.println("Choose correct option");

                }
                updateFile();
            }
        }

        public void calculateTotalIncomesValue()
        {
            totalIncomesValue=0;
            for(String key : hashIncomes.keySet()) {
                this.totalIncomesValue=this.totalIncomesValue + hashIncomes.get(key);
            }

            System.out.println("Total Incomes: " + totalIncomesValue + " zł");
        }

        public float returnTotalIncomesValue()
        {
            totalIncomesValue=0;
            for(String key : hashIncomes.keySet()) {
                this.totalIncomesValue=this.totalIncomesValue + hashIncomes.get(key);
            }

            return this.totalIncomesValue;
        }

        public  void updateFile() throws IOException {
            FileWriter fileWriter = new FileWriter(income);
            fileWriter.close();
            fileWriter = new FileWriter(income);
            for(String key : hashIncomes.keySet())
                fileWriter.write(key + " " + hashIncomes.get(key) +"\n");
            fileWriter.close();
        }

        public void showIncomeList()
        {
            int i=1;
            System.out.println("Actual Income list: ");
            for(String key : hashIncomes.keySet())
            {
                System.out.println(i + ". "+ key + " " + hashIncomes.get(key) + " zł");
                i++;
            }
            calculateTotalIncomesValue();
            System.out.println();
        }
    }

