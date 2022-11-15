package com.example.projectetqs.model;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Visit {
  private String healthCard;
  private String name;
  private String firstSurname;
  private String secondSurname;
  private String gender;
  private LocalDateTime dateBirth;
  private LocalDateTime dateTimeVisit;

  public Visit() {
    this.healthCard = "";
    this.name = "";
    this.firstSurname = "";
    this.secondSurname = "";
    this.gender = "";
    this.dateBirth = null;
    this.dateTimeVisit = null;
  }

  public Visit(String healthCard, String name, String firstSurname, String secondSurname,
               String gender, LocalDateTime dateBirth, LocalDateTime dateTimeVisit) {
    this.healthCard = healthCard;
    this.name = name;
    this.firstSurname = firstSurname;
    this.secondSurname = secondSurname;
    this.gender = gender;
    this.dateBirth = dateBirth;
    this.dateTimeVisit = dateTimeVisit;
  }

  public String getHealthCard() {
    return healthCard;
  }

  public String getName() {
    return name;
  }

  public String getFirstSurname() {
    return firstSurname;
  }

  public String getSecondSurname() {
    return secondSurname;
  }

  public String getGender() {
    return gender;
  }

  public void setDateTimeVisit(LocalDateTime dateTimeVisit) { this.dateTimeVisit = dateTimeVisit; }
  public LocalDateTime getDateTimeVisit() {
    return dateTimeVisit;
  }

  public LocalDateTime getDateBirth() {
    return dateBirth;
  }

  public boolean saveVisitToJSON(String resourceName) throws IOException {

    File file = new File(resourceName);
    JSONArray jsonArray = new JSONArray();
    JSONObject obj = null;
    ObjectMapper mapper = new ObjectMapper();
    if(file.exists()){
      InputStream is = new FileInputStream(file);
      if(is.available() > 0){
        JSONTokener tokener = new JSONTokener(is);
        obj = new JSONObject(tokener);
        jsonArray = obj.getJSONArray("visits");
      }
      FileWriter fileVisits = new FileWriter(file, false);
      obj = new JSONObject();
      obj.put("health_card", getHealthCard());
      obj.put("name", getName());
      obj.put("first_surname", getFirstSurname());
      obj.put("second_surname", getSecondSurname());
      obj.put("gender", getGender());
      obj.put("date_birth", getDateBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
      obj.put("date_time_visit", getDateTimeVisit().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
      jsonArray.put(obj);
      obj = new JSONObject();
      obj.put("visits", jsonArray);
      fileVisits.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(obj.toString())));
      fileVisits.flush();
      fileVisits.close();
    }
    return true;
  }
  public ObservableList<Visit> loadDataFromJSON(ObservableList<Visit> data){
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
    return data;
  }
}
