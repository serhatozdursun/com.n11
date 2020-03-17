package util;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReadTestData {

    public CSVReader loadCsv() throws IOException {
        Configuration configuration = new Configuration();
        return new CSVReader(new FileReader(configuration.getTesData()));
    }

    public String getDataFromCsv(int columnIndex, int cellIndex) throws IOException {
        BufferedReader br = null;
        String result = new String();
        try {
            CSVReader csvReader = loadCsv();
            List<String[]> list = csvReader.readAll();
            result = list.get(columnIndex)[cellIndex];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            new Throwable("File not found, please check file path.");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public String getEmail() throws IOException {
       return getDataFromCsv(1,0);
    }

    public String getPassword() throws IOException {
        return getDataFromCsv(1,1);
    }

    public String getUserName() throws IOException {
        return getDataFromCsv(1,2);
    }

    public String getSearchKeyword() throws IOException {
        return getDataFromCsv(1,3);
    }
}
