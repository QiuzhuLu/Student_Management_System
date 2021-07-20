package sample;

import javafx.css.Match;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    public VBox loginLayout;
    @FXML
    public TextField userField;
    @FXML
    public TextField passwordField;
    @FXML
    public TextArea resultField;
    @FXML
    public TextField dbConnectionField;
    @FXML
    public VBox workingLayout;
    private Db db;
    private String StudentFormat = "1, Li, Lisa, 1994-12-12, Lisa, 004912345, lisali@gmail.com, weblich";
    private static final Pattern IdPattern = Pattern.compile("(?<id>[\\d]+)");
    private static final Pattern StudentPattern =
            Pattern.compile("(?<id>[\\d]+), (?<name>[\\wäÄöÖüÜß]+), (?<vorname>[\\wäÄöÖüÜß]+), (?<geburtsdatum>\\d{4}-\\d{2}-\\d{2}), " +
                    "(?<geburtsname>[\\wäÄöÖüÜß]*), (?<telefonnummer>\\w+), (?<email>[\\w@\\.äÄöÖüÜß]+), (?<geschlecht>[\\wäÄöÖüÜß]+)");
    public void login() {
        print(String.format("Logging in using databank connection %n%s%n and user %s", dbConnectionField.getText(),
                userField.getText()));
        try {
            db = new Db();
            db.login(dbConnectionField.getText(), userField.getText(), passwordField.getText());
            showDB();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
//int id, String name, String vorname, LocalDate geburtsdatum, String geburtsname, String telefonnummer, String email, String geschlecht) {
    public void addStudent(ActionEvent actionEvent) {
        String text = resultField.getText();
        try {
            Matcher regex = StudentPattern.matcher(text);
            if(regex.matches()){
                Student stu = new Student(Integer.parseInt(regex.group("id")), regex.group("name"), regex.group("vorname"),
                        LocalDate.parse(regex.group("geburtsdatum")), regex.group("geburtsname"),
                        regex.group("telefonnummer"), regex.group("email"), regex.group("geschlecht"));
                db.addStudent(stu);
                print(String.format("Neue Studierende erfolgreich aufnehmen:%nName:%s%nID%s", regex.group("name"), regex.group("id")));
            }else{
                print(String.format(" %s%nFalsches Format, Input muss das folgende Format haben:%n%s", text, StudentFormat));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void searchStudent() {
        String text = resultField.getText();
        try {
                Integer id = Integer.parseInt(text);
                if (db.searchStudent(id) != null){
                    print(db.searchStudent(id));
                }else {
                    print("Dieses ID ist nicht vorhanden.");
                };
            } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void deleteStudent() {
        String text = resultField.getText();
        try {
            int deletedRowIndex = db.deleteStudent(Integer.parseInt(text));
            if(deletedRowIndex !=0){
                print(String.format("Gelöschte Zeileindex : %d", deletedRowIndex));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void logout() {
        try {
            db.logout();
            showLogin();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void showLogin() {
        loginLayout.setVisible(true);
        workingLayout.setVisible(false);
    }

    private void showDB(){
        loginLayout.setVisible(false);
        workingLayout.setVisible(true);
    }

    private void print(String s){
        resultField.setText(s);
    }
    private void print(Student e) {
        print(String.format("%d\t %s, %s, %s, %s", e.id, e.name, e.vorname, e.email, e.telefonnummer));
    }
}
