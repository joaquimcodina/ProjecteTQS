package com.example.projectetqs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import org.json.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MainController implements Initializable {
  @FXML
  public Spinner<Integer> hours;
  @FXML
  public Spinner<Integer> minutes;
  @FXML
  private TextField textHealthCard;
  @FXML
  private TextField textName;
  @FXML
  private TextField textFirstSurname;
  @FXML
  private TextField textSecondSurname;
  @FXML
  private DatePicker textDateVisit;
  @FXML
  private DatePicker textDateBirth;
  @FXML
  private RadioButton maleButton;
  @FXML
  private RadioButton femaleButton;
  @FXML
  public TableView<Visit> tableVisits;
  @FXML
  public TableColumn<?, ?> columnHealthCard;
  @FXML
  public TableColumn<?, ?> columnName;
  @FXML
  public TableColumn<?, ?> columnFirstSurname;
  @FXML
  public TableColumn<?, ?> columnSecondSurname;
  @FXML
  public TableColumn<?, ?> columnGender;
  @FXML
  public TableColumn<?, ?> columnDateBirth;
  @FXML
  public TableColumn<?, ?> columnDateVisit;

  private ObservableList<Visit> data;

  public void valueFactorySpinner(){
    hours.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
    minutes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
  }
  public String getHealthCard(){
    return textHealthCard.getText();
  }

  public String getName(){
    return textName.getText();
  }

  public String getFirstSurname(){
    return textFirstSurname.getText();
  }

  public String getSecondSurname(){
    return textSecondSurname.getText();
  }

  public String getGender(){
    String gender="";
    if(maleButton.isSelected())
      gender = maleButton.getText();

    else
      gender = femaleButton.getText();
    return gender;
  }
  public LocalDate getDateBirth(){
    return textDateBirth.getValue();
  }

  public LocalDate getDateVisit(){
    return textDateVisit.getValue();
  }

  public int getHour(){
    return hours.getValue();
  }

  public int getMinutes(){
    return minutes.getValue();
  }

  @FXML
  public void addVisit() {
    String dateBirthFormatter = getDateBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String dateTime=(getDateVisit()+" "+getHour()+":"+getMinutes()).formatted(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    if((getHour()>=0 && getHour()<=9) && (getMinutes()>=0 && getMinutes()<=9))
      dateTime = (getDateVisit()+" "+"0"+getHour()+":"+"0"+getMinutes()).formatted(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    else{
      if(getHour()>=0 && getHour()<=9)
        dateTime = (getDateVisit()+" "+"0"+getHour()+":"+getMinutes()).formatted(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

      if(getMinutes()>=0 && getMinutes()<=9)
        dateTime = (getDateVisit()+" "+getHour()+":"+"0"+getMinutes()).formatted(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

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
        }
      }
      FileWriter fileVisits = new FileWriter(file, false);
      obj = new JSONObject();
      obj.put("health_card", getHealthCard());
      obj.put("name", getName());
      obj.put("first_surname", getFirstSurname());
      obj.put("second_surname", getSecondSurname());
      obj.put("gender", getGender());
      obj.put("date_birth", dateBirthFormatter+" 00:00");
      obj.put("date_time_visit", dateTime);
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
    textFirstSurname.clear();
    textSecondSurname.clear();
    maleButton.isSelected();
    femaleButton.isSelected();
    textDateBirth.getEditor().clear();
    textDateVisit.getEditor().clear();
    maleButton.setSelected(false);
    femaleButton.setSelected(false);
    valueFactorySpinner();
    setCellTable();
    loadDataFromJSON(); //actualitzar la llista de visites
  }
  @Override
  public void initialize(URL arg0, ResourceBundle arg1){
    valueFactorySpinner();
    data = FXCollections.observableArrayList();
    setCellTable();
    loadDataFromJSON(); //llegir dades de JSON, i afegir-les a la taulas
  }
  private void setCellTable(){
    columnHealthCard.setCellValueFactory(new PropertyValueFactory<>("healthCard"));
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnFirstSurname.setCellValueFactory(new PropertyValueFactory<>("firstSurname"));
    columnSecondSurname.setCellValueFactory(new PropertyValueFactory<>("secondSurname"));
    columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    columnDateBirth.setCellValueFactory(new PropertyValueFactory<>("dateBirth"));
    columnDateVisit.setCellValueFactory(new PropertyValueFactory<>("dateTimeVisit"));
  }
  private void loadDataFromJSON(){
    data.clear();
    String path = "./data/visits.json";
    InputStream is = null;
    try {
      is = new FileInputStream(path);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }

    JSONTokener tokener = new JSONTokener(is);
    JSONObject object = new JSONObject(tokener);
    JSONArray visits = object.getJSONArray("visits");

    for (int i = 0; i < visits.length(); i++) {
      JSONObject jsonObject = visits.getJSONObject(i);
      data.add(new Visit(jsonObject.getString("health_card"), jsonObject.getString("name"),
          jsonObject.getString("first_surname"), jsonObject.getString("second_surname"), jsonObject.getString("gender"),
          LocalDateTime.parse(jsonObject.getString("date_birth"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
          LocalDateTime.parse(jsonObject.getString("date_time_visit"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
    }
    tableVisits.setItems(data);
  }
}