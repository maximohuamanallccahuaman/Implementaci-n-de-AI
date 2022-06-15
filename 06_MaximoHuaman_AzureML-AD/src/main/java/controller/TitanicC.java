package controller;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import lombok.Data;
import model.Titanic;
import org.primefaces.json.JSONObject;
import services.Services;

@Named(value = "titanicC")
@SessionScoped
@Data
public class TitanicC implements Serializable {

    Titanic titanic;

    public TitanicC() {
        titanic = new Titanic();
        titanic.setEdad(20);
        titanic.setNclase(1);
    }

    public  void obtenerDatos() throws IOException, InterruptedException {
        try {            
            JSONObject cadenaJson = Services.obtenerJSon(titanic);  
            titanic.setCategory(cadenaJson.getString("Scored Labels"));
            titanic.setProbability(cadenaJson.getDouble("Scored Probabilities"));
            if (titanic.getCategory().equals("0")) {
                titanic.setResult("Lo siento pero has facellido");
            } else {
                titanic.setResult("Que bien, has sobrevivido");
            }
        } catch (Exception e) {
            System.out.println("Error en obtenerDatosC: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
