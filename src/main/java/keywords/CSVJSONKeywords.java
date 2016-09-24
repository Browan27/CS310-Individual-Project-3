package keywords;

import edu.jsu.mcis.*;
import org.json.simple.parser.*;

public class CSVJSONKeywords {
    private Converter converter = new Converter();
    
    public String convertToJson(String csv) {
        return converter.csvToJson(csv);
    }
    
    public String convertToCsv(String json) {
        return converter.jsonToCsv(json);
    }
    
    public boolean jsonStringsAreEqual(String s, String t) {
        return converter.jsonStringsAreEqual(s, t);
    }
}