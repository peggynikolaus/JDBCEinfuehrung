package de.telekom.sea7;

import java.sql.Connection; //Connection repräsentiert die Verbindung zur Datenbank und weiß, wie mit dieser kommuniziert werden kann.
import java.sql.DriverManager; //DriverManager liefert mit der statischen getConnection()-Methode die benötigte Connection-Instanz zurück. 
import java.sql.Statement; //Statement führt die SQL-Abfrage durch und liefert das Ergebnis in Form eines Objekts vom Typ ResultSet zurück. 
import java.sql.PreparedStatement;
import java.sql.ResultSet; //ResultSet speichert die Datenreihen, die durch die SQL-Abfrage erzeugt wurde. 
import java.sql.SQLException; //SQLException benötigen wir für das ExceptionHandling beim Umgang mit MySQL-Datenbanken.

public class Personen {

	// Variablen zur Bildung eines Strings für die DB-Connection
	private String url = "jdbc:mariadb://localhost:3306/dbquest";
	private String user = "admin";
	private String pass = "sea004";

	// Variablen zur Bildung eines Personen Eintrages in die DB
	private String firstname;
	private String lastname;
	private int age;

	// Eigener Constructor
	public Personen() {
		//
	}

	public void personenAusgeben() {
		try {
			// Datenbankverbindung aufbauen
			Connection con = DriverManager.getConnection(url, user, pass);
			System.out.println("Verbindung erfolgreich hergestellt");

			// SQL Abfrage durchführen und Result unter Variable ResultSet speichern
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM persons");
			System.out.println("Abfrage erfolgreich, Ergebnis wird ausgegeben.");

			// SQL Ergebnis verarbeiten
			System.out.println("-----------------------------------");
			String ueb1 = String.format("|%10s|", "Vorname");
			String ueb2 = String.format("%10s", "Nachname");
			String ueb3 = String.format("|%10s|", "Alter");
			System.out.println(ueb1 + ueb2 + ueb3);

			System.out.println("-----------------------------------");
			while (rs.next()) {
				String str1 = String.format("|%10s|", rs.getString(1));
				String str2 = String.format("%10s", rs.getString(2));
				String str3 = String.format("|%10d|", rs.getInt(3));
				System.out.println(str1 + str2 + str3);
				System.out.println("-------------------------------");
			}

			// Schließen der geöffneten Connections
			con.close(); // Connection
			rs.close(); // ResultSet
			statement.close(); // Statement
			System.out.println("Verbindung zur Datenbank wurde erfolgreich geschlossen.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
	}
	
	public void personenEintragen(String firstname, String lastname, int age) {

        try {
            // DB Connection aufbauen
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("Verbindung erfolgreich hergestellt.");

            // Bereiten den Eingabe-String vor und tragne die Daten ein
            String query = "INSERT INTO persons ( firstname, lastname, age) VALUES ( ?, ?, ? )";
            PreparedStatement pstmt = null;
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setInt(3, age);
            pstmt.executeUpdate();
            System.out.println("Die Daten wurden erfolgreich geschrieben.");


            // CleanUp
            pstmt.close();
            con.close(); // Generelle DB Connection
            System.out.println("Die Verbindungen zur Datenbank wurden erfolgreich geschlossen.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

