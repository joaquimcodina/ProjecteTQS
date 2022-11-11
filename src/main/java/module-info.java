module com.example.projectetqs {
  requires javafx.controls;
  requires javafx.fxml;
  requires org.json;
  requires com.fasterxml.jackson.databind;


  opens com.example.projectetqs to javafx.fxml;
  exports com.example.projectetqs;
  exports com.example.projectetqs.controller;
  opens com.example.projectetqs.controller to javafx.fxml;
  exports com.example.projectetqs.model;
  opens com.example.projectetqs.model to javafx.fxml;
}