package com.example.projectetqs;
import com.example.projectetqs.controller.MainController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {
  MainController mainController;

  @BeforeEach
  protected void setUp(){
    mainController = new MainController();
    assertNotNull(mainController);
  }

  @Test
  void validationHealthCard() {
    assertTrue(mainController.validationHealthCard("COES 1 234567 89 0", "Codina", "Espin"));
    assertTrue(mainController.validationHealthCard("GAMA 2 222222 22 2", "Garcia", "Marquez"));
    assertTrue(mainController.validationHealthCard("LOMA 1 234567 89 0", "Lopez", "Martinez"));
  }

  @Test
  void validateName() {
    assertTrue(mainController.validateName("Joaquim"));
    assertTrue(mainController.validateName("Bernat"));
    assertTrue(mainController.validateName("Carles"));
  }

  @Test
  void validateSurname() {
    assertTrue(mainController.validateSurname("Codina", "Espin"));
    assertTrue(mainController.validateSurname("Garcia", "Marquez"));
    assertTrue(mainController.validateSurname("Lopez", "Martinez"));
  }

  @Test
  void validateDatesBirthVisit() {
    assertTrue(mainController.validateDatesBirthVisit(LocalDate.of(1999, Month.JULY, 15),
        LocalDate.of(2023, Month.JANUARY, 30)));

    assertTrue(mainController.validateDatesBirthVisit(LocalDate.of(2004, Month.FEBRUARY, 9),
        LocalDate.of(2022, Month.NOVEMBER, 21)));

    assertTrue(mainController.validateDatesBirthVisit(LocalDate.of(1957, Month.OCTOBER, 3),
        LocalDate.of(2022, Month.DECEMBER, 1)));
  }
}