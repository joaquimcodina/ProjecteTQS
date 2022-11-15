package com.example.projectetqs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlertDialogTest {

  AlertDialog alert;

  @BeforeEach
  protected void setUp() {
    alert = new AlertDialog();
    assertNotNull(alert);
  }

  @Test
  void display() {
  }
}