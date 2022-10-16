module com.example.projectetqs {
  requires javafx.controls;
  requires javafx.fxml;
  requires org.json;
  requires com.fasterxml.jackson.databind;


  opens com.example.projectetqs to javafx.fxml;
  exports com.example.projectetqs;
}