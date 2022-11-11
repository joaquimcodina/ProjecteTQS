package com.example.projectetqs;
import java.time.LocalDateTime;

public class Visit {
  private String healthCard;
  private String name;
  private String firstSurname;
  private String secondSurname;
  private String gender;
  private LocalDateTime dateBirth;
  private LocalDateTime dateTimeVisit;

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

  public LocalDateTime getDateTimeVisit() {
    return dateTimeVisit;
  }

  public LocalDateTime getDateBirth() {
    return dateBirth;
  }
}
