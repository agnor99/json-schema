package dev.agnor.schema;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }
    private void run() {
        JSONObject jsonSchema = new JSONObject(
                new JSONTokener(getClass().getResourceAsStream("/alloyschema.json")));
        JSONObject jsonSubject = new JSONObject(
                new JSONTokener(getClass().getResourceAsStream("/recipemissingoutput.json")));

        Schema schema = SchemaLoader.load(jsonSchema);
        try {
            schema.validate(jsonSubject);
        } catch (ValidationException validationException) {
            if (validationException.getCausingExceptions().size() > 1) {
                validationException.getCausingExceptions().forEach(causes -> System.out.println(causes.getMessage()));
            }
            validationException.printStackTrace();
        }
    }
}
