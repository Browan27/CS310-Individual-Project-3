package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConverterTest {
    private String csvString;
    private String jsonString;
    private Converter converter;

    @Before
    public void setUp() {
        converter = new Converter();
        
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        StringBuffer csvContents = new StringBuffer();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("grades.csv")))) {
            String line;
            while((line = reader.readLine()) != null) {
                csvContents.append(line + '\n');
            }
        }
        catch(IOException e) { e.printStackTrace(); }
        csvString = csvContents.toString();
        
        StringBuffer jsonContents = new StringBuffer();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("grades.json")))) {
            String line;
            while((line = reader.readLine()) != null) {
                jsonContents.append(line + '\n');
            }
        }
        catch(IOException e) { e.printStackTrace(); }
        jsonString = jsonContents.toString();
    }
    
    @Test
    public void testConvertCSVtoJSON() {
        // You should test using the files in src/test/resources.
        String convertJsonString = converter.csvToJson(csvString);
        
        assertTrue(converter.jsonStringsAreEqual(jsonString, convertJsonString));
    }

    @Test
    public void testConvertJSONtoCSV() {
        // You should test using the files in src/test/resources.
        String convertCsvString = converter.jsonToCsv(jsonString);
        
        assertTrue(converter.csvStringsAreEqual(csvString, convertCsvString));
    }
}







