package com.example.projectetqs.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class VisitTest {

  Visit visit1;
  Visit visit2;
  Visit visit3;

  @BeforeEach
  protected void setUp(){
    visit1 = new Visit("COES 1 234567 89 0", "Joaquim", "Codina", "Espin", "male",
        LocalDateTime.of(1999, Month.JANUARY, 3, 0, 0, 0),
        LocalDateTime.of(2022, Month.NOVEMBER, 19, 12, 30, 0));

    visit2 = new Visit("GACO 1 234567 89 0", "Pep", "Garcia", "Cortes", "male",
        LocalDateTime.of(1986, Month.FEBRUARY, 6, 0, 0, 0),
        LocalDateTime.of(2022, Month.DECEMBER, 11, 19, 40, 0));

    visit3 = new Visit("LOMA 1 234567 89 0", "Carles", "Lozano", "Martin", "male",
        LocalDateTime.of(2009, Month.JANUARY, 10, 0, 0, 0),
        LocalDateTime.of(2023, Month.FEBRUARY, 19, 20, 30, 0));

    assertNotNull(visit1);
    assertNotNull(visit2);
    assertNotNull(visit3);
  }

  @Test
  void getHealthCard() {
    String healthCard1 = visit1.getHealthCard();
    String firstSurname1 = visit1.getFirstSurname();
    String secondSurname1 = visit1.getSecondSurname();

    String healthCard2 = visit2.getHealthCard();
    String firstSurname2 = visit2.getFirstSurname();
    String secondSurname2 = visit2.getSecondSurname();

    String healthCard3 = visit3.getHealthCard();
    String firstSurname3 = visit3.getFirstSurname();
    String secondSurname3 = visit3.getSecondSurname();

    //Comprovar que els camps no estiguin buits
    assertNotNull(healthCard1);
    assertNotNull(firstSurname1);
    assertNotNull(secondSurname1);

    assertNotNull(healthCard2);
    assertNotNull(firstSurname2);
    assertNotNull(secondSurname2);

    assertNotNull(healthCard3);
    assertNotNull(firstSurname3);
    assertNotNull(secondSurname3);

    //Comprovar que health card te 18 caracters (comptant lletres, numeros i espais)
    assertEquals(18, healthCard1.length());
    assertEquals(18, healthCard2.length());
    assertEquals(18, healthCard3.length());

    //Comprovar que health card no sobrepassi els 18 caracters, ni tingui menys de 18 caracters
    assertFalse(healthCard1.length() > 18);
    assertFalse(healthCard1.length() < 18);

    String prefixSurname = (firstSurname1.charAt(0))+""+(firstSurname1.charAt(1))+""+(secondSurname1.charAt(0))+""+(secondSurname1.charAt(1));
    String prefixHealthCard = (healthCard1.charAt(0))+""+(healthCard1.charAt(1))+""+(healthCard1.charAt(2))+""+(healthCard1.charAt(3));
    assertEquals(4, prefixHealthCard.length());             //Comprovar prefix health card tingui 4 lletres
    assertEquals(prefixHealthCard.toUpperCase(), prefixHealthCard); //Comprovar prefix health card en lletres majuscules
    assertEquals(prefixSurname.toUpperCase(), prefixHealthCard);    //Comprovar prefix health card sigui igual a les dues primeres lletres de cada cognom

    prefixSurname = (firstSurname2.charAt(0))+""+(firstSurname2.charAt(1))+""+(secondSurname2.charAt(0))+""+(secondSurname2.charAt(1));
    prefixHealthCard = (healthCard2.charAt(0))+""+(healthCard2.charAt(1))+""+(healthCard2.charAt(2))+""+(healthCard2.charAt(3));
    assertEquals(4, prefixHealthCard.length());             //Comprovar prefix health card tingui 4 lletres
    assertEquals(prefixHealthCard.toUpperCase(), prefixHealthCard); //Comprovar prefix health card en lletres majuscules
    assertEquals(prefixSurname.toUpperCase(), prefixHealthCard);    //Comprovar prefix health card sigui igual a les dues primeres lletres de cada cognom

    prefixSurname = (firstSurname3.charAt(0))+""+(firstSurname3.charAt(1))+""+(secondSurname3.charAt(0))+""+(secondSurname3.charAt(1));
    prefixHealthCard = (healthCard3.charAt(0))+""+(healthCard3.charAt(1))+""+(healthCard3.charAt(2))+""+(healthCard3.charAt(3));
    assertEquals(4, prefixHealthCard.length());              //Comprovar prefix health card tingui 4 lletres
    assertEquals(prefixHealthCard.toUpperCase(), prefixHealthCard);  //Comprovar prefix health card en lletres majuscules
    assertEquals(prefixSurname.toUpperCase(), prefixHealthCard);     //Comprovar prefix health card sigui igual a les dues primeres lletres de cada cognom

    //Comprovar que el camp health card es alfanumeric (amb espais)
    assertTrue(healthCard1.matches("^\\s*[\\da-zA-Z][\\da-zA-Z\\s]*$"));
    assertTrue(healthCard2.matches("^\\s*[\\da-zA-Z][\\da-zA-Z\\s]*$"));
    assertTrue(healthCard3.matches("^\\s*[\\da-zA-Z][\\da-zA-Z\\s]*$"));

    //Comprovar que es compleix el format Health Card = XXYY N NNNNNN NN N
    assertTrue(healthCard1.matches("\\w{4} \\d{1} \\d{6} \\d{2} \\d{1}"));
    assertTrue(healthCard2.matches("\\w{4} \\d{1} \\d{6} \\d{2} \\d{1}"));
    assertTrue(healthCard3.matches("\\w{4} \\d{1} \\d{6} \\d{2} \\d{1}"));
  }

  @Test
  void getName() {
    String name1 = visit1.getName();
    String name2 = visit2.getName();
    String name3 = visit3.getName();

    //Comprovar que el camp no estigui buit
    assertNotNull(name1);
    assertNotNull(name2);
    assertNotNull(name3);

    //Comprovar el rang de les lletres (que no sigui un nom d'una sola lletra, o que no sigui un nom molt llarg), seguir uns estandards
    assertTrue(name1.length() >= 2);     //El nom ha de tenir un minim de 2 lletres (exemple, ot)
    assertTrue (name1.length() <= 44);  //El nom ha de tenir un maxim de 44 lletres (exemple, Gargantilla del Lozoya y Pinilla de Buitrago)

    assertTrue(name2.length() >= 2);    //El nom ha de tenir un minim de 2 lletres (exemple, ot)
    assertTrue (name2.length() <= 44); //El nom ha de tenir un maxim de 44 lletres (exemple, Gargantilla del Lozoya y Pinilla de Buitrago)

    assertTrue(name3.length() >= 2);    //El nom ha de tenir un minim de 2 lletres (exemple, ot)
    assertTrue (name3.length() <= 44); //El nom ha de tenir un maxim de 44 lletres (exemple, Gargantilla del Lozoya y Pinilla de Buitrago)

    //Comprovar que la primera lletra del nom sigui en Majuscula
    String firstLetterName = String.valueOf(name1.charAt(0));
    assertEquals(firstLetterName.toUpperCase(), firstLetterName);

    firstLetterName = String.valueOf(name2.charAt(0));
    assertEquals(firstLetterName.toUpperCase(), firstLetterName);

    firstLetterName = String.valueOf(name3.charAt(0));
    assertEquals(firstLetterName.toUpperCase(), firstLetterName);

    //Comprovar que el camp sigui alfabetic (no permetre que sigui numèric ni alfanumeric)
    assertTrue(name1.matches("[a-zA-Z]+"));
    assertTrue(name2.matches("[a-zA-Z]+"));
    assertTrue(name3.matches("[a-zA-Z]+"));
  }

  @Test
  void getFirstSurname() {
    String firstSurname1 = visit1.getFirstSurname();
    String firstSurname2 = visit2.getFirstSurname();
    String firstSurname3 = visit3.getFirstSurname();

    //Comprovar que el camp no estigui buit
    assertNotNull(firstSurname1);
    assertNotNull(firstSurname2);
    assertNotNull(firstSurname3);

    //Comprovar el rang de les lletres (que no sigui un nom d'una sola lletra, o que no sigui un nom molt llarg), seguir uns estandards
    assertTrue(firstSurname1.length() >= 3);  //El cognom ha de tenir un minim de 3 lletres (exemple, Bas, Gil, Rio, Roa...)
    assertTrue(firstSurname1.length() <= 23); //El cognom ha de tenir un maxim de 23 lletres (exemple, Garroguerricaechevarria)

    assertTrue(firstSurname2.length() >= 3);  //El cognom ha de tenir un minim de 3 lletres (exemple, Bas, Gil, Rio, Roa...)
    assertTrue(firstSurname2.length() <= 23); //El cognom ha de tenir un maxim de 23 lletres (exemple, Garroguerricaechevarria)

    assertTrue(firstSurname3.length() >= 3);  //El cognom ha de tenir un minim de 3 lletres (exemple, Bas, Gil, Rio, Roa...)
    assertTrue(firstSurname3.length() <= 23); //El cognom ha de tenir un maxim de 23 lletres (exemple, Garroguerricaechevarria)

    //Comprovar que la primera lletra del cognom sigui en Majuscula
    String firstLetterSurname = String.valueOf(firstSurname1.charAt(0));
    assertEquals(firstLetterSurname.toUpperCase(), firstLetterSurname);

    firstLetterSurname = String.valueOf(firstSurname2.charAt(0));
    assertEquals(firstLetterSurname.toUpperCase(), firstLetterSurname);

    firstLetterSurname = String.valueOf(firstSurname3.charAt(0));
    assertEquals(firstLetterSurname.toUpperCase(), firstLetterSurname);

    //Comprovar que el camp sigui alfaberic (no permetre que sigui numèric ni alfanumeric)
    assertTrue(firstSurname1.matches("[a-zA-Z]+"));
    assertTrue(firstSurname2.matches("[a-zA-Z]+"));
    assertTrue(firstSurname3.matches("[a-zA-Z]+"));
  }

  @Test
  void getSecondSurname() {
    String secondSurname1 = visit1.getSecondSurname();
    String secondSurname2 = visit2.getSecondSurname();
    String secondSurname3 = visit3.getSecondSurname();

    //Comprovar que el camp no estigui buit
    assertNotNull(secondSurname1);
    assertNotNull(secondSurname2);
    assertNotNull(secondSurname3);

    //Comprovar el rang de les lletres (que no sigui un nom d'una sola lletra, o que no sigui un nom molt llarg), seguir uns estandards
    assertTrue(secondSurname1.length() >= 3);  //El cognom ha de tenir un minim de 3 lletres (exemple, Bas, Gil, Rio, Roa...)
    assertTrue(secondSurname1.length() <= 23); //El cognom ha de tenir un maxim de 23 lletres (exemple, Garroguerricaechevarria)

    assertTrue(secondSurname2.length() >= 3);  //El cognom ha de tenir un minim de 3 lletres (exemple, Bas, Gil, Rio, Roa...)
    assertTrue(secondSurname2.length() <= 23); //El cognom ha de tenir un maxim de 23 lletres (exemple, Garroguerricaechevarria)

    assertTrue(secondSurname3.length() >= 3);  //El cognom ha de tenir un minim de 3 lletres (exemple, Bas, Gil, Rio, Roa...)
    assertTrue(secondSurname3.length() <= 23); //El cognom ha de tenir un maxim de 23 lletres (exemple, Garroguerricaechevarria)

    //Comprovar que la primera lletra del cognom sigui en Majuscula
    String secondLetterSurname = String.valueOf(secondSurname1.charAt(0));
    assertEquals(secondLetterSurname.toUpperCase(), secondLetterSurname);

    secondLetterSurname = String.valueOf(secondSurname2.charAt(0));
    assertEquals(secondLetterSurname.toUpperCase(), secondLetterSurname);

    secondLetterSurname = String.valueOf(secondSurname3.charAt(0));
    assertEquals(secondLetterSurname.toUpperCase(), secondLetterSurname);

    //Comprovar que el camp sigui alfaberic (no permetre que sigui numèric ni alfanumeric)
    assertTrue(secondSurname1.matches("[a-zA-Z]+"));
    assertTrue(secondSurname2.matches("[a-zA-Z]+"));
    assertTrue(secondSurname3.matches("[a-zA-Z]+"));

  }

  @Test
  void getGender() {
    String gender = visit1.getGender();

    //Comprovar que el camp no estigui buit
    assertNotNull(gender);

    //Comprovar que el camp sigui igual a male o female (que no tingui un genere random)
    assertTrue(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"));
  }

  @Test
  void getDateBirth() {
    LocalDateTime dateBirth = visit1.getDateBirth();
    LocalDateTime dateTimeVisit = visit1.getDateTimeVisit();

    //Comprovar que el camp no estigui buit
    assertNotNull(dateBirth);
    assertNotNull(dateTimeVisit);

    //Comprovar que la data de naixement sigui ABANS O IGUAL a la data de visita
    //(Pot ser un nado que tingui visita el mateix dia del seu naixement)
    assertTrue(dateBirth.isBefore(dateTimeVisit) || dateBirth.isEqual(dateTimeVisit));

    //deshabilitar dies a partir de la data actual (es a dir, no hi pot haver una data de naixement 11-11-2022, estant
    //en la data actual 9-11-2022)
  }

  @Test
  void getDateTimeVisit() {
    LocalDateTime dateTimeVisit = visit1.getDateTimeVisit();
    LocalDateTime dateBirth = visit1.getDateBirth();

    //Comprovar que el camp no estigui buit
    assertNotNull(dateTimeVisit);
    assertNotNull(dateBirth);

    //Comprovar que la data de visita sigui DESPRÉS O IGUAL a la data de naixement
    //(Pot ser un nado que tingui visita el mateix dia del seu naixement)
    assertTrue(dateTimeVisit.isAfter(dateBirth) || dateTimeVisit.isEqual(dateBirth));
    assertTrue(dateTimeVisit.isAfter(LocalDateTime.now()) || dateTimeVisit.isEqual(LocalDateTime.now()));

    dateTimeVisit = visit2.getDateTimeVisit();
    dateBirth = visit2.getDateBirth();

    //Comprovar que el camp no estigui buit
    assertNotNull(dateTimeVisit);
    assertNotNull(dateBirth);

    //Comprovar que la data de visita sigui DESPRÉS O IGUAL a la data de naixement
    //(Pot ser un nado que tingui visita el mateix dia del seu naixement)
    assertTrue(dateTimeVisit.isAfter(dateBirth) || dateTimeVisit.isEqual(dateBirth));
    assertTrue(dateTimeVisit.isAfter(LocalDateTime.now()) || dateTimeVisit.isEqual(LocalDateTime.now()));

    dateTimeVisit = visit3.getDateTimeVisit();
    dateBirth = visit3.getDateBirth();

    //Comprovar que el camp no estigui buit
    assertNotNull(dateTimeVisit);
    assertNotNull(dateBirth);

    //Comprovar que la data de visita sigui DESPRÉS O IGUAL a la data de naixement
    //(Pot ser un nado que tingui visita el mateix dia del seu naixement)
    assertTrue(dateTimeVisit.isAfter(dateBirth) || dateTimeVisit.isEqual(dateBirth));
    assertTrue(dateTimeVisit.isAfter(LocalDateTime.now()) || dateTimeVisit.isEqual(LocalDateTime.now()));

    //deshabilitar dies fins la data actual (es a dir, no hi pot haver una data de visita 2-11-2022, estant
    //en la data actual 9-11-2022)

    //Particio d'equivalencia INVALIDA: Abans de la data actual fins la data actual (ex. som a 6-11-2022, i li programem una visita pel 3-10-2021 o 5-11-2022)
    //Particio d'equivalencia VALIDA: Des de la data actual fins a un màxim de 1 any d'espera (ex. som a 6-11-2022, i li progarem una visita pel 15-11-2022 o 6-11-2023. Pot coincidir que el pacient hagi nascut en la data actual.)
    //Partició d'equivalencia INVALIDA: Més de 1 ANY D'espera fins a 2099 (ex. programar una visita pel 2040 i neix en 1975)

    //Valors frontera: data actual (6-11-2022), 1 ANY MAXIM D'ESPERA (6-11-2023)
    //Valors limit: (5-11-2022)
    //              (7-11-2022)
    //              (5-11-2023)
    //              (7-11-2023)

    /*assertTrue(healthCard, "Valor limit valid");
    assertTrue(any2021 < anyFrontera2, "Valor limit valid");
    assertFalse(any1979 > anyFrontera1, "Valor limit NO valid");
    assertFalse(any2023 < anyFrontera2, "Valor limit NO valid");

    //Particio equivalent: 1999, 2010, 2011
    assertTrue(any1999 >= anyFrontera1 && any1999 <= anyFrontera2, "Partició equivalent valid");
    assertTrue(any2010 >= anyFrontera1 && any2010 <= anyFrontera2, "Partició equivalent valid");
    assertTrue(any2011 >= anyFrontera1 && any2011 <= anyFrontera2, "Partició equivalent valid");

    long test1980 = secondsCalculator.anysFins(anyFrontera1);
    long test2022 = secondsCalculator.anysFins(anyFrontera2);
    long test1999 = secondsCalculator.anysFins(any1999);
    long test2010 = secondsCalculator.anysFins(any2010);
    long test2011 = secondsCalculator.anysFins(any2011);

    //Valors negatius
    assertTrue(test1980 >= -1);
    assertTrue(test2022 >= -1);
    assertTrue(test1999 >= -1);
    assertTrue(test2010 >= -1);
    assertTrue(test2011 >= -1);

    assertEquals(test1980, 0); //1980 - 1980 -> 0 anys
    assertEquals(test2022, 42); //1980 - 2022 -> 42 anys
    assertEquals(test1999, 19); //1980 - 1999 -> 19 anys
    assertEquals(test2010, 30); //1980 - 2010 -> 30 anys
    assertEquals(test2011, 31); //1980 - 2011 -> 31 anys*/
  }

  @Test
  void saveVisitToJSON() throws IOException {
    assertTrue(visit1.saveVisitToJSON("data/visits.json"));
    assertTrue(visit1.saveVisitToJSON("data/visits1.json"));
  }

  @Test
  void loadDataFromJSON() {
  }
}