package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import au.com.bytecode.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    /*
        Consider a CSV file like the following:
        
        ID,Total,Assignment 1,Assignment 2,Exam 1
        111278,611,146,128,337
        111352,867,227,228,412
        111373,461,96,90,275
        111305,835,220,217,398
        111399,898,226,229,443
        111160,454,77,125,252
        111276,579,130,111,338
        111241,973,236,237,500
        
        The corresponding JSON file would be as follows (note the curly braces):
        
        {
            "colHeaders":["Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160","111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }  
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        csvString = csvString.replaceAll("\n", ",");
        CSVParser parser = new CSVParser();        
        try {
            String[] array = parser.parseLineMulti(csvString);
        
            JSONObject obj = new JSONObject();
            JSONArray col = new JSONArray();
            JSONArray row = new JSONArray();
            JSONArray data = new JSONArray();
            
            for(int i = 1; i < array.length-1; i++) {
                if(i < 5) { col.add(array[i]); }
                else if(array[i].length() > 3) { row.add(array[i]); }
                else if(array[i].length() <= 3){
                    data.add(array[i++] + ","
                           + array[i++] + ","
                           + array[i++] + ","
                           + array[i]);
                }
            }
            obj.put("colHeaders", col);
            obj.put("rowHeaders", row);
            obj.put("data", data);
            
            return obj.toString();
        }
        catch(IOException e) {
            return "";
        }
    }
    
    public static String jsonToCsv(String jsonString) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(jsonString);
            String csv = "";
            
            JSONArray col = (JSONArray) obj.remove("colHeaders");
            JSONArray row = (JSONArray) obj.remove("rowHeaders");
            JSONArray data = (JSONArray) obj.remove("data");
            String colString = col.toString();
            String[] rowArray = row.toString().split(",");
            String[] dataArray = data.toString().split(",");
            
            csv += "\"ID\"," + colString + "\n";
            for(int i = 0; i < rowArray.length; i++) {
                csv += rowArray[i] + ",\"" + dataArray[(4*(i))] + ",\""
                                         + dataArray[(4*(i))+1] + ",\"" 
                                         + dataArray[(4*(i))+2] + ",\""
                                         + dataArray[(4*(i))+3] + "\"\n";
            }
            
            csv = csv.replace("[","");
            csv = csv.replace("]","");
            return csv;
        }
        catch(ParseException e) {
            return "";
        }
    }
    
    public boolean csvStringsAreEqual(String s, String t) {        
        CSVParser parser = new CSVParser();
        try {
            String[] sArray = parser.parseLineMulti(s);
            String[] tArray = parser.parseLineMulti(t);
            return sArray.equals(tArray);
        }
        catch(IOException e) {
            return false;
        }
    }
    
    public boolean jsonStringsAreEqual(String s, String t) {        
        JSONParser parser = new JSONParser();
        try {
            Object sObj = parser.parse(s);
            Object tObj = parser.parse(t);
            return sObj.equals(tObj);
        }
        catch(ParseException e) {
            return false;
        }
    }


}













