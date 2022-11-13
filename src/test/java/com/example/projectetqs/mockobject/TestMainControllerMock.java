package com.example.projectetqs.mockobject;
import com.example.projectetqs.controller.MainController;
import com.example.projectetqs.model.Visit;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;

class TestMainControllerMock {

  @Test
  public void testScheduleVisitOK(){
    //Declaracio Mock Object
    MockVisit mockVisit = new MockVisit();

    //Setup del Mock Object
    Visit visit1 = new Visit("GACO 1 234567 89 0", "Pep", "Garcia", "Cortes", "male",
        LocalDateTime.of(1986, Month.FEBRUARY, 6, 0, 0, 0),
        LocalDateTime.of(2022, Month.DECEMBER, 11, 19, 40, 0));

    Visit visit2 = new Visit("LOMA 1 234567 89 0", "Carles", "Lozano", "Martin", "male",
        LocalDateTime.of(2009, Month.JANUARY, 10, 0, 0, 0),
        LocalDateTime.of(2023, Month.FEBRUARY, 19, 20, 30, 0));

    mockVisit.addVisit("GACO 1 234567 89 0", visit1);
    mockVisit.addVisit("LOMA 1 234567 89 0", visit2);

    //Creacio i setup de MainController
    MainController visitController = new MainController();
    visitController.setVisit(mockVisit);

    //Prova i validacio de MainController
    visitController.changeDateVisit("GACO 1 234567 89 0", LocalDate.of(2022, 11, 15));
    visitController.changeDateVisit("LOMA 1 234567 89 0", LocalDate.of(2022, 11, 16));

    assertEquals(String.valueOf(LocalDate.of(2022, 11, 15)), (visit1.getDateTimeVisit().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
    assertEquals(String.valueOf(LocalDate.of(2022, 11, 16)), (visit2.getDateTimeVisit().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
  }
}