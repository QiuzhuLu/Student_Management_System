package sample;

import java.time.LocalDate;

public class Student {
    public int id;
    public String name;
    public String vorname;
    public LocalDate geburtsdatum;
    public String geburtsname;
    public String telefonnummer;
    public String email;
    public String geschlecht;

    public Student(int id, String name, String vorname, LocalDate geburtsdatum, String geburtsname, String telefonnummer, String email, String geschlecht) {
        this.id = id;
        this.name = name;
        this.vorname = vorname;
        this.geburtsdatum = geburtsdatum;
        this.geburtsname = geburtsname;
        this.telefonnummer = telefonnummer;
        this.email = email;
        this.geschlecht = geschlecht;
    }
}
