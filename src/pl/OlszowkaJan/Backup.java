package pl.OlszowkaJan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;



public class Backup {
    public void createBackup() throws IOException {
        LocalDateTime localDateTime = LocalDateTime.now();
        File backupFolder = new File("Files\\backup_(" + localDateTime.toLocalDate() +")");
        File expenses = new File("Files\\Expenses.txt");
        File incomes = new File("Files\\Incomes.txt");
        File expBackup = new File(backupFolder.getAbsolutePath() + "\\expBackup.txt");
        File incomeBackup = new File(backupFolder.getAbsolutePath() + "\\incomeBackup.txt");

        if(backupFolder.mkdirs()) {
            System.out.println("Creating backup folder...\"backup_(" + localDateTime.toLocalDate() +")\"");
        }
        else{
            System.out.println("Overriding today's backup folder");
        }

        if(expenses.exists()) {
            Files.copy(expenses.toPath(), expBackup.toPath(), StandardCopyOption.REPLACE_EXISTING);
            FileWriter expFileWriter = new FileWriter(expBackup, true);
            expFileWriter.write("\nBackup date: " + localDateTime);
            expFileWriter.close();
        }
        else
            System.out.println("No expense data to backup");

        if(incomes.exists()) {
            Files.copy(incomes.toPath(), incomeBackup.toPath(), StandardCopyOption.REPLACE_EXISTING);
            FileWriter incomeFileWriter = new FileWriter(incomeBackup, true);
            incomeFileWriter.write("\nBackup date: " + localDateTime);
            incomeFileWriter.close();
        }
        else
            System.out.println("No income data to backup");

        System.out.println("Creating backup...");

        if(expBackup.exists() || incomeBackup.exists()) System.out.println("Data has been successfully backuped");

    }
}
