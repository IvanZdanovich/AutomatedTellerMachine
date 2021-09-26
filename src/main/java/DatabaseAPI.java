import com.opencsv.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseAPI {
    private static final String VISA_DATABASE_FILE_PATH
            = "src/main/resources/VisaDatabase.csv";
    private static final String MASTERCARD_DATABASE_FILE_PATH
            = "src/main/resources/MastercardDatabase.csv";
    private static final String ATM_DATABASE_FILE_PATH
            = "src/main/resources/ATMDatabase.csv";

    public static List<String[]> readDatabase(String databasePath) {
        List<String[]> strings = new ArrayList<>();
        try {
            FileReader filereader = new FileReader(databasePath);
            CSVParser parser = new CSVParserBuilder().withSeparator(' ').build();
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withCSVParser(parser)
                    .build();
            strings = csvReader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strings;
    }

    public static List<VisaCardAccount> readVisaDatabase() {
        List<VisaCardAccount> database = new ArrayList<>();
        for (String[] stringsElement : readDatabase(VISA_DATABASE_FILE_PATH)) {
            database.add(new VisaCardAccount().fromStringArray(stringsElement));
        }
        return database;
    }

    public static List<MastercardCardAccount> readMastercardDatabase() {
        List<MastercardCardAccount> database = new ArrayList<>();
        for (String[] stringsElement : readDatabase(MASTERCARD_DATABASE_FILE_PATH)) {
            database.add(new MastercardCardAccount().fromStringArray(stringsElement));
        }
        return database;
    }

    public static List<ATM> readATMDatabase() {
        List<ATM> database = new ArrayList<>();
        for (String[] stringsElement : readDatabase(ATM_DATABASE_FILE_PATH)) {
            database.add(new ATM().fromStringArray(stringsElement));
        }
        return database;
    }

    public static void writeDatabase(List<String[]> database, String databasePath) {
        File file = new File(databasePath);
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile, ' ',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            writer.writeAll(database);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeVisaDatabase(List<String[]> visaDatabase) {
        writeDatabase(visaDatabase, VISA_DATABASE_FILE_PATH);
    }

    public static void writeMastercardDatabase(List<String[]> mastercardDatabase) {
        writeDatabase(mastercardDatabase, MASTERCARD_DATABASE_FILE_PATH);
    }

    public static void writeATMDatabase(List<String[]> atmDatabase) {
        writeDatabase(atmDatabase, ATM_DATABASE_FILE_PATH);
    }
}


