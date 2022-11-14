package com.example.projectetqs.mockobject;
import com.example.projectetqs.model.Visit;
import java.util.HashMap;

//La declarem filla de la classe que volem "imitar", que en aquest cas es VisitInterface
public class MockVisit implements VisitInterface {

  //Definim i inicialitzem un hashtable per imitar el funcionament de la persistència de dades
  private HashMap<String, Visit> visits = new HashMap<>();
  public void addVisit(String visitId, Visit visit){
    this.visits.put(visitId, visit);
  }

  //No fem res!! no ho necessitem, per tant, "no hi ha una persistència de dades real"
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
