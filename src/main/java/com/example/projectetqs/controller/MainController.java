package com.example.projectetqs.controller;
import com.example.projectetqs.AlertDialog;
import com.example.projectetqs.model.Visit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    if (validationHealthCard() && validateSurname() && validateName()){
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
      visit.saveVisitToJSON();

      textHealthCard.clear();
      textName.clear();
      textFirstSurname.clear();
      textSecondSurname.clear();
      maleButton.isSelected();
      femaleButton.isSelected();
      textDateBirth.getEditor().clear();
      textDateVisit.getEditor().clear();
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
    loadData(); //Llegir dades de JSON, i afegir-les a la taulas
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
  private void loadData(){
    data.clear();
    visit = new Visit();
    data = visit.loadDataFromJSON(data);
    tableVisits.setItems(data);
  }

  public boolean validationHealthCard() {
    if(getHealthCard().equals("")){
      AlertDialog.display("Error HeartCard", "HeartCard is required");
      return false;
    }

    if(getHealthCard().length() != 18){
      AlertDialog.display("Error HeartCard", "HearthCard ha de tenir exactament 18 caràcters (comptant lletres, numeros i espais) \n" +
          "o ha de complir el format XXYY 1 234567 89 0");
      return false;
    }

    if(!getHealthCard().matches("^\\s*[\\da-zA-Z][\\da-zA-Z\\s]*$")){
      AlertDialog.display("Error HeartCard", "HeartCard ha de ser alfanumeric");
      return false;
    }

    if(!getHealthCard().matches("\\w{4} \\d{1} \\d{6} \\d{2} \\d{1}")){
      AlertDialog.display("Error HeartCard", "HearthCard ha de complir el format XXYY 1 234567 89 0.\n" +
          "XX (les dues primeres lletres del primer cognom) i YY (les dues primeres lletres del segon cognom)");
      return false;
    }

    String prefixHealthCard = (getHealthCard().charAt(0))+""+(getHealthCard().charAt(1))+""+(getHealthCard().charAt(2))+""+(getHealthCard().charAt(3));
    if(!prefixHealthCard.equals(prefixHealthCard.toUpperCase())){
      AlertDialog.display("Error HeartCard", "El prefix ha de ser en majuscules");
      return false;
    }

    //validar cognoms (comprovar que no siguin nulls)
    if(validateSurname()){
      String prefixSurname = (getFirstSurname().charAt(0))+""+(getFirstSurname().charAt(1))+""+(getSecondSurname().charAt(0))+""+(getSecondSurname().charAt(1));

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

  public boolean validateName() {

    if(getName().equals("")){
      AlertDialog.display("Error Name", "Name is required");
      return false;
    }

    if(!(getName().length() >= 2 && getName().length() <= 44)){
      AlertDialog.display("Error Name", "Name ha de tenir mínim 2 caràcters i màxim 44 caràcters");
      return false;
    }

    if(!(String.valueOf(getName().charAt(0)).toUpperCase().equals(String.valueOf(getName().charAt(0))))){
      AlertDialog.display("Error Name", "La primera lletra del First Surname ha de ser en majúscula");
      return false;
    }

    if(!getName().matches("[a-zA-Z]+")){
      AlertDialog.display("Error Name", "Name ha de ser alfabètic");
      return false;
    }

    return true;
  }

  public boolean validateSurname() {
    if(getFirstSurname().equals("")){
      AlertDialog.display("Error First Surname", "First Surnames is required");
      return false;
    }

    if(getSecondSurname().equals("")){
      AlertDialog.display("Error Second Surname", "Second Surnames is required");
      return false;
    }

    if(!(getFirstSurname().length() >= 3 && getFirstSurname().length() <= 23)){
      AlertDialog.display("Error Firt Surname", "First Surname ha de tenir mínim 3 caràcters i màxim 23 caràcters");
      return false;
    }

    if(!(getSecondSurname().length() >= 3 && getSecondSurname().length() <= 23)){
      AlertDialog.display("Error Second Surname", "Second Surname ha de tenir mínim 3 caràcters i màxim 23 caràcters");
      return false;
    }

    if(!(String.valueOf(getFirstSurname().charAt(0)).toUpperCase().equals(String.valueOf(getFirstSurname().charAt(0))))){
      AlertDialog.display("Error First Surname", "La primera lletra del First Surname ha de ser en majúscula");
      return false;
    }

    if(!(String.valueOf(getSecondSurname().charAt(0)).toUpperCase().equals(String.valueOf(getSecondSurname().charAt(0))))){
      AlertDialog.display("Error Second Surname", "La primera lletra del Second Surname ha de ser en majúscula");
      return false;
    }

    if(!getFirstSurname().matches("[a-zA-Z]+")){
      AlertDialog.display("Error First Surname", "El Firt Surname ha de ser alfabètic");
      return false;
    }

    if(!getSecondSurname().matches("[a-zA-Z]+")){
      AlertDialog.display("Error Second Surname", "El Second Surname ha de ser alfabètic");
      return false;
    }
    return true;
  }
}