package com.example.projectetqs.mockobject;
import com.example.projectetqs.model.Visit;
import java.util.HashMap;

//La declarem filla de la classe que volem "imitar", en aquest cas, VisitInterface
public class MockVisit implements VisitInterface {

  //Definim i inicialitzem un hashtable per imitar el funcionament de la persistencia de dades
  private HashMap<String, Visit> visits = new HashMap<>();
  public void addVisit(String visitId, Visit visit){
    this.visits.put(visitId, visit);
  }

  //No hem res!! no ho necessitem doncs "no hi ha una persistencia de dades real"
  @Override
  public void assignSchedule(Visit visit){
    //do nothing
  }

  //Simulem una query en el nostre fitxer JSON per tal de recuperar les dades de les visites
  @Override
  public Visit loadVisitForHealthCard(String healthCard) {
    return this.visits.get(healthCard);
  }
}
