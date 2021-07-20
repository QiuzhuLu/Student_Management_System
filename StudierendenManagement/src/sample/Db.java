package sample;

import javafx.event.ActionEvent;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Db {
    java.sql.Connection con;
    public Db() throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
    }
  public void login(String datenbankVerbindung, String user, String password) throws SQLException {
      con = DriverManager.getConnection("jdbc:oracle:thin:@ora14.informatik.haw-hamburg.de:1521:inf14", user, password);
  }

  //public String Select
  public void addStudent(Student st) throws SQLException { //insert
      Statement stm = con.createStatement();
      String befehl = String.format("INSERT INTO Student(ID, Name, Vorname, Geburtsdatum, Geburtsname, Telefonnummer, Email, Geschlecht) VALUES ('%s', '%s', '%s', date'%s', '%s', '%s', '%s', '%s')",
              st.id,st.name, st.vorname, st.geburtsdatum, st.geburtsname, st.telefonnummer, st.email, st.geschlecht);
      stm.executeUpdate(befehl);
      stm.close();
  }

  public Student searchStudent(int id) throws SQLException{
        Statement stm = con.createStatement();
        String befehl = String.format("Select * from Student where id = %d", id);
        ResultSet results =stm.executeQuery(befehl);
        Student searchedStu = null;
        while (results.next()){
            searchedStu = new Student(results.getInt("ID"),
                    results.getString("Name"),
                    results.getString("Vorname"),
                    results.getDate("Geburtsdatum").toLocalDate(),
                    results.getString("Geburtsname"),
                    results.getString("Telefonnummer"),
                    results.getString("Email"),
                    results.getString("Geschlecht")
                    );
        }
        stm.close();
        return searchedStu;
  }
    public int deleteStudent(int id) throws SQLException {
        Statement stm = con.createStatement();
        String befehl = String.format("Delete from Student where id = %d", id);
        int changedRowIndex = stm.executeUpdate(befehl);
        return changedRowIndex;
    }

    public void logout() throws SQLException {
        con.close();
        con = null;
    }
}
