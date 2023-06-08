package edu.fiuba.algo3.modelo.lectorJSON;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Lector{

    public static JSONArray leer(String archivo, String tipoJson){
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = null;
        try (FileReader reader = new FileReader(archivo)) {
            Object obj = jsonParser.parse(reader);
            try{
                return (JSONArray) obj;
            } catch (IncompatibleClassChangeError | Exception e ) {
                JSONObject jsonObject = (JSONObject) obj;
               jsonArray = iterarJsonObject((JSONObject) jsonObject.get(tipoJson));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
    private static JSONArray iterarJsonObject(JSONObject jsonObject) {
        Iterator x = jsonObject.keySet().iterator();
        JSONArray jsonArray = new JSONArray();
        while (x.hasNext()) {
            String key = (String) x.next();
            jsonArray.add(jsonObject.get(key));
        }
        return jsonArray;
    }
}


