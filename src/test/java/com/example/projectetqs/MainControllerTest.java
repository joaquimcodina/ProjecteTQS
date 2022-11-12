package com.example.projectetqs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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

  //MainController mainController;
  Visit visit1;
  Visit visit2;
  Visit visit3;

  @BeforeEach
  protected void setUp(){
    //mainController = new MainController();
    //DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    visit1 = new Visit("COES 1 234567 89 0", "Joaquim", "Codina", "Espin", "male",
        LocalDateTime.of(1999, Month.JANUARY, 3, 0, 0, 0),
        LocalDateTime.of(2022, Month.NOVEMBER, 10, 12, 30, 0));

    visit2 = new Visit("COES 1 234567 89 0", "Joaquim", "Codina", "Espin", "male",
        LocalDateTime.of(1999, Month.JANUARY, 3, 0, 0, 0),
        LocalDateTime.of(2022, Month.NOVEMBER, 10, 12, 30, 0));

    visit3 = new Visit("COES 1 234567 89 0", "Joaquim", "Codina", "Espin", "male",
        LocalDateTime.of(1999, Month.JANUARY, 3, 0, 0, 0),
        LocalDateTime.of(2022, Month.NOVEMBER, 10, 12, 30, 0));

    assertNotNull(visit1);
    //assertNotNull(mainController);
    assertTrue(true);
  }

  @Test
  void getHealthCard() {
    //String healthCard = mainController.getHealthCard();
    //String firstSurname = mainController.getFirstSurname(); //agafar les dues priemeres lletres, pel format de health card
    //String secondSurname = mainController.getSecondSurname(); //agafar les dues primeres lletres, pel format de health card

    String healthCard = visit1.getHealthCard();
    String firstSurname = visit1.getFirstSurname();
    String secondSurname = visit1.getSecondSurname();

    //Comprovar que els camps no estiguin buits
    assertNotNull(healthCard);
    assertNotNull(firstSurname);
    assertNotNull(secondSurname);

    //Comprovar prefix de health card, exemple si hem dic Codina Espin, el prefix ha de ser COES
    String prefixSurname = (firstSurname.charAt(0))+""+(firstSurname.charAt(1))+""+(secondSurname.charAt(0))+""+(secondSurname.charAt(1));
    assertEquals("COES", prefixSurname.toUpperCase());

    String prefixHealthCard = (healthCard.charAt(0))+""+(healthCard.charAt(1))+""+(healthCard.charAt(2))+""+(healthCard.charAt(3));
    assertEquals("COES", prefixHealthCard.toUpperCase());

    //Comprovar que es compleix el format Health Card = XXYY N NNNNNN NN N
    assertTrue(healthCard.matches("\\w{4} \\d{1} \\d{6} \\d{2} \\d{1}"));

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
  void getName() {
    //String name = mainController.getName();
    String name = visit1.getName();

    //Comprovar que el camp no estigui buit
    assertNotNull(name);

    //Comprovar el rang de les lletres (que no sigui un nom d'una sola lletra, o que no sigui un nom molt llarg), seguir uns estandards
    assertTrue(name.length() >= 2); //el nom ha de tenir un minim de 2 lletres (exemple, ot)
    assertTrue (name.length() <= 44); //el nom ha de tenir un maxim de 44 lletres (exemple, Gargantilla del Lozoya y Pinilla de Buitrago)

    //Comprovar que el camp sigui alfabetic (no permetre que sigui numèric ni alfanumeric)
    assertTrue(name.matches("[a-zA-Z]+"));
  }

  @Test
  void getFirstSurname() {
    //String firstSurname = mainController.getFirstSurname();
    String firstSurname = visit1.getFirstSurname();

    //Comprovar que el camp no estigui buit
    assertNotNull(firstSurname);

    //Comprovar el rang de les lletres (que no sigui un nom d'una sola lletra, o que no sigui un nom molt llarg), seguir uns estandards
    assertTrue(firstSurname.length() >= 3); //el cognom ha de tenir un minim de 3 lletres (exemple, Bas, Gil, Rio, Roa...)
    assertTrue(firstSurname.length() <= 23); //el cognom ha de tenir un maxim de 23 lletres (exemple, Garroguerricaechevarria)

    //Comprovar que el camp sigui alfaberic (no permetre que sigui numèric ni alfanumeric)
    assertTrue(firstSurname.matches("[a-zA-Z]+"));
  }

  @Test
  void getSecondSurname() {
    //String secondSurname = mainController.getSecondSurname();
    String secondSurname = visit1.getSecondSurname();

    //comprovar que el camp no estigui buit
    assertNotNull(secondSurname);

    //comprovar el rang de les lletres (que no sigui un nom d'una sola lletra, o que no sigui un nom molt llarg), seguir uns estandards
    assertTrue(secondSurname.length() >= 3); //el cognom ha de tenir un minim de 3 lletres (exemple, Bas, Gil, Rio, Roa...)
    assertTrue(secondSurname.length() <= 23); //el cognom ha de tenir un maxim de 23 lletres (exemple, Garroguerricaechevarria)

    //comprovar que el camp sigui alfaberic (no permetre que sigui numèric ni alfanumeric)
    assertTrue(secondSurname.matches("[a-zA-Z]+"));

  }

  @Test
  void getGender() {
    //String gender = mainController.getGender();
    String gender = visit1.getGender();

    //comprovar que el camp no estigui buit
    assertNotNull(gender);
  }

  @Test
  void getDateBirth() {
    //LocalDateTime dateBirth = mainController.getDateBirth();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String dateBirth = visit1.getDateVisit().format(format);

    //Comprovar que el camp no estigui buit
    assertNotNull(dateBirth);

    //Comprovar que la data de naixement no sigui superior a la data de visita o data actual
  }

  @Test
  void getDateVisit() {
    //LocalDateTime dateVisit = mainController.getDateVisit();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String dateTimeVisit = visit1.getDateVisit().format(format);

    //Comprovar que el camp no estigui buit
    assertNotNull(dateTimeVisit);

    //Comprovar que la data de visita NO sigui anterior a la data actual (exemple, som 18 d'octubre, no podem programar una visita
    //el dia 17 d'octubre, un altre cosa es que es programi el 18 d'octubre i que aquesta visita es mantingui emmagatzemada en la llista
    //durant dies, més endavant aplicar un mètode que esborri automàticament les visites pasat el dia)

    //Comprovar que la data de visita NO sigui anterior a la data de naixement d'un pacient
  }

  @Test
  void getHour() {
    //LocalDateTime hour = mainController.getHour();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH");
    String dateTimeVisit = visit1.getDateVisit().format(format);
    
    //Comprovar que el camp no estigui buit
    assertNotNull(dateHour);
  }

  @Test
  void getMinutes() {
  }

  @Test
  void addVisit() {

  }
}
