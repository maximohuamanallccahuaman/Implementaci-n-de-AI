package services;

import com.google.gson.Gson;
import java.io.IOException;
import model.Titanic;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

// AzureML
public class Services {

    public static JSONObject obtenerJSon(Titanic modelo) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"Inputs\": {\r\n    \"input1\": [\r\n      {\r\n        \"PassengerId\": 1,\r\n        \"Survived\": 1,\r\n        \"Pclass\": " + modelo.getNclase() + ",\r\n        \"Name\": \"\",\r\n        \"Sex\": \"\",\r\n        \"Age\": " + modelo.getEdad() + ",\r\n        \"SibSp\": 1,\r\n        \"Parch\": 1,\r\n        \"Ticket\": \"\",\r\n        \"Fare\": 1,\r\n        \"Cabin\": \"\",\r\n        \"Embarked\": \"\"\r\n      }\r\n    ]\r\n  },\r\n  \"GlobalParameters\": {}\r\n}");
        Request request = new Request.Builder()
                .url("https://ussouthcentral.services.azureml.net/workspaces/4fed246b61ac4cce8ea9733fd5b89ca7/services/de6888c3b4954ebc90a10d944eca0ea3/execute?api-version=2.0&format=swagger")
                .method("POST", body)
                .addHeader("Authorization", "Bearer kKJKOkaqi+ZA85KgV0ca3+ICNPOyX6gRITNyjscyUS1RcXUiSHwEDkMSBXv9rYwSZ6BBrNSQ8dgGtUXZ3tQAfA==")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        Gson gson = new Gson();
        // Convierte la cadena body en un objeto jsonObject
        JSONObject jsonObject = new JSONObject(response.body().string());
        JSONObject cadenaJson = jsonObject.getJSONObject("Results")
                .getJSONArray("output1")
                .getJSONObject(0);
        return cadenaJson;
    }

    public static void main(String[] args) throws IOException {
        try {
            Titanic titanic = new Titanic();
            titanic.setSobrevivio(1);
            titanic.setEdad(20);
            titanic.setNclase(1);
            titanic.setSexo("male");
            System.out.println("Lista " + Services.obtenerJSon(titanic));
            JSONObject cadenaJson = Services.obtenerJSon(titanic);
            System.out.println("Scored " + cadenaJson.getString("Scored Labels"));
            System.out.println("Probabilities " + cadenaJson.getDouble("Scored Probabilities"));
        } catch (Exception e) {
            System.out.println("error en " + e.getMessage());
        }
    }
}

//    Request-Response
//    /execute?api-version=2.0&format=swagger      
//    
//    {
//      "Inputs": {
//        "input1": [
//          {
//            "Embarazos": 3,
//            "Glucosa": 110,
//            "Presión sanguínea": 75,
//            "Pliegue cutáneo": 21,
//            "Insulina": 81,
//            "Índice de masa corporal": 32.168,
//            "Pedigrí diabetes": 0.42354,
//            "Edad": 33,
//            "Diabetes": "",
//            "Medicación previa": "",
//            "Observaciones": "",
//            "Fecha de diagnóstico": ""
//          }
//        ]
//      },
//      "GlobalParameters": {}
//    }
