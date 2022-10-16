package com.example.projectetqs;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.json.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MainController {
  @FXML
  private TextField textHealthCard;
  @FXML
  private TextField textName;
  @FXML
  private TextField textSurname;
  @FXML
  private DatePicker textDateVisit;
  @FXML
  private DatePicker textDateBirth;
  @FXML
  private RadioButton maleButton;
  @FXML
  private RadioButton femaleButton;

  @FXML
  private void addVisit() {
    String healthCard = textHealthCard.getText();
    String name = textName.getText();
    String surname = textSurname.getText();
    String male = maleButton.getText();
    String female = femaleButton.getText();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate dateBirth = textDateBirth.getValue();
    LocalDate dateVisit = textDateVisit.getValue();
    String dateBirthFormatter = dateBirth.format(formatter);
    String dateVisitFormatter = dateVisit.format(formatter);

    try{
      String resourceName = "data/visits.json";
      File file = new File(resourceName);
      JSONArray jsonArray = new JSONArray();
      JSONObject obj;
      ObjectMapper mapper = new ObjectMapper();

      if(file.exists()){
        InputStream is = new FileInputStream(file);
        if(is.available() > 0){
          JSONTokener tokener = new JSONTokener(is);
          obj = new JSONObject(tokener);
          jsonArray = obj.getJSONArray("visits");
          for(int i=0 ; i<jsonArray.length() ; i++){
            System.out.println(jsonArray.get(i));
          }
        }
      }
      FileWriter fileVisits = new FileWriter(file, false);
      obj = new JSONObject();
      obj.put("health_card", healthCard);
      obj.put("name", name);
      obj.put("surname", surname);
      if(maleButton.isSelected())
        obj.put("gender", male);
      if(femaleButton.isSelected())
        obj.put("gender", female);
      obj.put("date_birth", dateBirthFormatter);
      obj.put("date_visit", dateVisitFormatter);
      jsonArray.put(obj);
      obj = new JSONObject();
      obj.put("visits", jsonArray);
      fileVisits.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(obj.toString())));
      fileVisits.flush();
      fileVisits.close();

    } catch (IOException e){
      e.printStackTrace();
    }
    textHealthCard.clear();
    textName.clear();
    textSurname.clear();
    maleButton.isSelected();
    femaleButton.isSelected();
    textDateBirth.getEditor().clear();
    textDateVisit.getEditor().clear();
    maleButton.setSelected(false);
    femaleButton.setSelected(false);
  }
}