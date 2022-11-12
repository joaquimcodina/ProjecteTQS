package com.example.projectetqs;
import com.example.projectetqs.controller.MainController;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

  //TDD = 15%
  //Particions equivalents = 15%
  //Valors limits i frontera = 15%
  //Pairwise testing = 10%
  //Mock object = 15%
  //Statement Coverage = 5%
  //Decision Coverage = 5%
  //Condition Coverage = 5%
  //Path Coverage = 5%
  //Loop Testing = 5%
  //Codi amb comentaris = 5%

  //FER TESTS dels validates, comprovant resultats esperats i resultats desijats, aprofitant les classes Visit
  MainController mainController1;
  MainController mainController2;
  MainController mainController3;
  TextField textHealthCode;

  @BeforeEach
  protected void setUp(){
    mainController1 = new MainController();
    textHealthCode.setText("HealthCard Code");
    //mainController1.setTextHealthCard(textHealthCode);

    mainController2 = new MainController();
    mainController3 = new MainController();

    assertNotNull(mainController1);
    assertNotNull(mainController2);
    assertNotNull(mainController3);
  }

  @Test
  void validationHealthCard() {
    assertTrue(mainController1.validationHealthCard());

  }

  @Test
  void validateName() {
    assertTrue(mainController1.validateName());
  }

  @Test
  void validateSurname() {
    assertTrue(mainController2.validateSurname());

  }
}