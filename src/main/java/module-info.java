module com.example.projectetqs {
  requires javafx.controls;
  requires javafx.fxml;


  opens com.example.projectetqs to javafx.fxml;
  exports com.example.projectetqs;
}