package com.example.projectetqs.controller;
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
}