package com.example.projectetqs.mockobject;
import com.example.projectetqs.model.Visit;

public interface VisitInterface {
  void assignSchedule(Visit visit);
  Visit loadVisitForHealthCard(String healthCard);
}
