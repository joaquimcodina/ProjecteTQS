package com.example.projectetqs.controller;
import com.example.projectetqs.AlertDialog;
import com.example.projectetqs.mockobject.MockVisit;
import com.example.projectetqs.model.Visit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;

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
  private Visit visit; //model

  //Copia una referencia a un objecte extern de tipus Visit
  private MockVisit mockVisit;

  public void setVisit(MockVisit visit){ this.mockVisit = visit; }

  public void changeDateVisit(String healthCard, LocalDate dateVisit){
    Visit visit = this.mockVisit.loadVisitForHealthCard(healthCard);
    visit.setDateTimeVisit(dateVisit.atStartOfDay());
    this.mockVisit.assignSchedule(visit);
  }

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
  public void addVisit() throws IOException {
    if (validationHealthCard(getHealthCard(), getFirstSurname(), getSecondSurname()) &&
        validateName(getName()) && validateSurname(getFirstSurname(), getSecondSurname()) &&
        validateDatesBirthVisit(getDateBirth(), getDateVisit())){

      String dateTime=(getDateVisit()+" "+getHour()+":"+getMinutes()).formatted(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      if((getHour()>=0 && getHour()<=9) && (getMinutes()>=0 && getMinutes()<=9))
        dateTime = (getDateVisit()+" "+"0"+getHour()+":"+"0"+getMinutes()).formatted(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      else{
        if(getHour()>=0 && getHour()<=9)
          dateTime = (getDateVisit()+" "+"0"+getHour()+":"+getMinutes()).formatted(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        if(getMinutes()>=0 && getMinutes()<=9)
          dateTime = (getDateVisit()+" "+getHour()+":"+"0"+getMinutes()).formatted(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      }
      visit = new Visit(getHealthCard(), getName(), getFirstSurname(), getSecondSurname(), getGender(),
          getDateBirth().atStartOfDay(),
          LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
      visit.saveVisitToJSON("data/visits.json", data);

      textHealthCard.clear();
      textName.clear();
      textFirstSurname.clear();
      textSecondSurname.clear();
      maleButton.isSelected();
      femaleButton.isSelected();
      textDateBirth.getEditor().clear();
      textDateBirth.setValue(null);
      textDateVisit.getEditor().clear();
      textDateVisit.setValue(null);
      valueFactorySpinner();
      setCellTable();
      loadData(); //Llegir dades de JSON, i afegir-les a la taulas
    }
  }
  @Override
  public void initialize(URL arg0, ResourceBundle arg1){
    valueFactorySpinner();
    data = FXCollections.observableArrayList();
    setCellTable();
    try {
      loadData(); //Llegir dades de JSON, i afegir-les a la taulas
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
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
  private void loadData() throws FileNotFoundException {
    data.clear();
    visit = new Visit();
    data = visit.loadDataFromJSON("./data/visits.json", data);
    tableVisits.setItems(data);
  }

  public boolean validationHealthCard(String healthCard, String firstSurname, String secondSurname) {
    if(healthCard.equals("")){
      AlertDialog.display("Error HeartCard", "HeartCard is required");
      return false;
    }

    if(healthCard.length() != 18){
      AlertDialog.display("Error HeartCard", "HearthCard ha de tenir exactament 18 caràcters (comptant lletres, numeros i espais) \n" + "o ha de complir el format XXYY 1 234567 89 0");
      return false;
    }

    if(!healthCard.matches("^\\s*[\\da-zA-Z][\\da-zA-Z\\s]*$")){
      AlertDialog.display("Error HeartCard", "HeartCard ha de ser alfanumeric");
      return false;
    }

    if(!healthCard.matches("\\w{4} \\d{1} \\d{6} \\d{2} \\d{1}")){
      AlertDialog.display("Error HeartCard", "HearthCard ha de complir el format XXYY 1 234567 89 0.\n" +"XX (les dues primeres lletres del primer cognom) i YY (les dues primeres lletres del segon cognom)");
      return false;
    }

    String prefixHealthCard = (healthCard.charAt(0))+""+(healthCard.charAt(1))+""+(healthCard.charAt(2))+""+(healthCard.charAt(3));
    if(!prefixHealthCard.equals(prefixHealthCard.toUpperCase())){
      AlertDialog.display("Error HeartCard", "El prefix ha de ser en majuscules");
      return false;
    }

    //validar cognoms (comprovar que no siguin nulls)
    if(validateSurname(firstSurname, secondSurname)){
      String prefixSurname = (firstSurname.charAt(0))+""+(firstSurname.charAt(1))+""+(secondSurname.charAt(0))+""+(secondSurname.charAt(1));

      if(!prefixHealthCard.equals(prefixHealthCard.toUpperCase())){
        AlertDialog.display("Error HeartCard", "El prefix HealthCard ha de ser en majuscules");
        return false;
      }

      if(!prefixHealthCard.equals(prefixSurname.toUpperCase())){
        AlertDialog.display("Error HeartCard", "El prefix HealthCard es diferent a les 2 lletres de cada cognom");
        return false;
      }
    }

    return true;
  }

  public boolean validateName(String name) {

    if(name.equals("")){
      AlertDialog.display("Error Name", "Name is required");
      return false;
    }

    if(!(name.length() >= 2 && name.length() <= 44)){
      AlertDialog.display("Error Name", "Name ha de tenir mínim 2 caràcters i màxim 44 caràcters");
      return false;
    }

    if(!(String.valueOf(name.charAt(0)).toUpperCase().equals(String.valueOf(name.charAt(0))))){
      AlertDialog.display("Error Name", "La primera lletra del Name ha de ser en majúscula");
      return false;
    }

    if(!name.matches("[a-zA-Z]+")){
      AlertDialog.display("Error Name", "Name ha de ser alfabètic");
      return false;
    }
    return true;
  }

  public boolean validateSurname(String firstSurname, String secondSurname) {
    if(firstSurname.equals("")){
      AlertDialog.display("Error First Surname", "First Surnames is required");
      return false;
    }

    if(secondSurname.equals("")){
      AlertDialog.display("Error Second Surname", "Second Surnames is required");
      return false;
    }

    if(!(firstSurname.length() >= 3 && firstSurname.length() <= 23)){
      AlertDialog.display("Error Firt Surname", "First Surname ha de tenir mínim 3 caràcters i màxim 23 caràcters");
      return false;
    }

    if(!(secondSurname.length() >= 3 && secondSurname.length() <= 23)){
      AlertDialog.display("Error Second Surname", "Second Surname ha de tenir mínim 3 caràcters i màxim 23 caràcters");
      return false;
    }

    if(!(String.valueOf(firstSurname.charAt(0)).toUpperCase().equals(String.valueOf(firstSurname.charAt(0))))){
      AlertDialog.display("Error First Surname", "La primera lletra del First Surname ha de ser en majúscula");
      return false;
    }

    if(!(String.valueOf(secondSurname.charAt(0)).toUpperCase().equals(String.valueOf(secondSurname.charAt(0))))){
      AlertDialog.display("Error Second Surname", "La primera lletra del Second Surname ha de ser en majúscula");
      return false;
    }

    if(!firstSurname.matches("[a-zA-Z]+")){
      AlertDialog.display("Error First Surname", "El First Surname ha de ser alfabètic");
      return false;
    }

    if(!secondSurname.matches("[a-zA-Z]+")){
      AlertDialog.display("Error Second Surname", "El Second Surname ha de ser alfabètic");
      return false;
    }
    return true;
  }

  public boolean validateDatesBirthVisit(LocalDate dateBirth, LocalDate dateVisit){
    if(dateBirth == null){
      AlertDialog.display("Error Date Birth", "Date Birth is require");
      return false;
    }

    if(dateVisit == null){
      AlertDialog.display("Error Date Visit", "Date Visit is require");
      return false;
    }

    if(!dateBirth.isBefore(LocalDate.now())){
      AlertDialog.display("Error Date Birth", "Date Birth es DESPRÉS de la Data Actual");
      return false;
    }

    if(!dateVisit.isAfter(LocalDate.now())){
      AlertDialog.display("Error Date Visit", "Date Visit es ABANS de la Data Actual");
      return false;
    }

    if(!dateBirth.isBefore(dateVisit)){
      AlertDialog.display("Error Date Birth", "Date Birth es DESPRÉS de la Data de Visita");
      return false;
    }

    if(!dateVisit.isAfter(dateBirth)){
      AlertDialog.display("Error Date Visit", "Date Visit es ABANS de la Data de naixement");
      return false;
    }

    return true;
  }
}