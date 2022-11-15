package com.example.projectetqs.controller;
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
    
    assertFalse(mainController.validationHealthCard("LOMA 1 234567 89 0", "Lopez", ""));
    assertFalse(mainController.validationHealthCard("", "Lopez", "Martinez"));
    assertFalse(mainController.validationHealthCard("LOMA 1 234567 89 0", "", "Martinez"));
    assertFalse(mainController.validationHealthCard("", "", ""));
  }

  @Test
  void validateName() {
    assertTrue(mainController.validateName("Joaquim"));
    assertTrue(mainController.validateName("Bernat"));
    assertTrue(mainController.validateName("Carles"));
    
    assertFalse(mainController.validateName("A"));  //menys de 2 caracters
    assertFalse(mainController.validateName("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));    //mes de 44 caracters
    assertTrue(mainController.validateName("Victor"));   //entre 2 i 44 caracters
  }

  @Test
  void validateSurname() {
    assertTrue(mainController.validateSurname("Codina", "Espin"));
    assertTrue(mainController.validateSurname("Garcia", "Marquez"));
    assertTrue(mainController.validateSurname("Lopez", "Martinez"));
    
    assertFalse(mainController.validateSurname("Al"));  //menys de 3 caracters
    assertFalse(mainController.validateSurname("Alaaaaaaaaaaaaaaaaaaaaaaa"));    //mes de 23 caracters
    assertTrue(mainController.validateSurname("Garcia"));   //entre 3 i 23 caracters
  }

  @Test
  void validateDatesBirthVisit() {
    assertTrue(mainController.validateDatesBirthVisit(LocalDate.of(1999, Month.JULY, 15),
        LocalDate.of(2023, Month.JANUARY, 30)));

    assertTrue(mainController.validateDatesBirthVisit(LocalDate.of(2004, Month.FEBRUARY, 9),
        LocalDate.of(2022, Month.NOVEMBER, 21)));

    assertTrue(mainController.validateDatesBirthVisit(LocalDate.of(1957, Month.OCTOBER, 3),
        LocalDate.of(2022, Month.DECEMBER, 1)));
    
    assertFalse(mainController.validateDatesBirthVisit(LocalDate.of((2007, Month.OCTOBER, 3),
        LocalDate.of(2002, Month.DECEMBER, 1)));
  }
}
