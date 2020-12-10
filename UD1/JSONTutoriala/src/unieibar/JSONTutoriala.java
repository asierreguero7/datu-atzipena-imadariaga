/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unieibar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonString;
import java.io.StringWriter;
import java.util.ArrayList;
import static javax.json.Json.createArrayBuilder;
import static javax.json.Json.createObjectBuilder;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;

/**
 *
 * @author reguero.asier
 */
public class JSONTutoriala {

    public static void main(String[] args) throws FileNotFoundException {
        JsonReader reader = Json.createReader(new FileReader("jsondata.json"));
        JsonStructure jsonst = reader.read();

        JsonArray jsonArray = createArrayBuilder()
            .add(Json.createObjectBuilder()
                .add("izena", "Anboto")
                .add("altuera", 1331)
                .add("probintzia", "Bizkaia")
            ).add(Json.createObjectBuilder()
                .add("izena", "Teide")
                .add("altuera", 3174)
                .add("probintzia", "Lanzarote")
            ).add(Json.createObjectBuilder()
                .add("izena", "Kilimanjaro")
                .add("altuera", 7856)
                .add("probintzia", "Nigger")
            )
                .build();
        navigateTree(jsonArray, null);
        
        System.out.println("-----------------------------------------");
        JsonArrayBuilder jsonArrayBuilder = createArrayBuilder();
        
        for (int i=0;i<jsonArray.size();i++){
            jsonArrayBuilder.add(jsonArray.get(i));
        }
        
        jsonArrayBuilder.add(Json.createObjectBuilder()
                .add("izena", "Everest")
                .add("altuera", 8678)
                .add("probintzia", "Nepal"));
        
        JsonArray jsonArray2 = jsonArrayBuilder.build();
        
        navigateTree(jsonArray2, null);

        FileOutputStream os = new FileOutputStream("jsondata.json");
        
        JsonWriter jsonWriter = Json.createWriter(os);
        jsonWriter.writeArray(jsonArray2);
        jsonWriter.close();
    }

    public static void navigateTree(JsonValue tree, String key) {
        if (key != null) {
            System.out.print("Key " + key + ": ");
        }
        switch (tree.getValueType()) {
            case OBJECT:
                //System.out.println("OBJECT");
                JsonObject object = (JsonObject) tree;
                for (String name : object.keySet()) {
                    navigateTree(object.get(name), name);
                }
                System.out.println("-------");
                break;
            case ARRAY:
                //System.out.println("ARRAY");
                JsonArray array = (JsonArray) tree;
                for (JsonValue val : array) {
                    navigateTree(val, null);
                }
                break;
            case STRING:
                JsonString st = (JsonString) tree;
                System.out.println(/*"STRING " +*/ st.getString());
                break;
            case NUMBER:
                JsonNumber num = (JsonNumber) tree;
                System.out.println(/*"NUMBER " +*/ num.toString());
                break;
            case TRUE:
            case FALSE:
            case NULL:
                System.out.println(tree.getValueType().toString());
                break;
        }
    }
}
