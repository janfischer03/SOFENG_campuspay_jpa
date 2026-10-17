package ch.hslu.sofeng.campuspay_jpa.customer;

import jakarta.persistence.*;
@Entity // Persistente Klasse: eine Instanz entspricht einem Datensatz.
@Table(name = "campuspay_customers")
public class CampusPayCustomerEntity {

    @Id // Primärschlüssel der Tabelle.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // MySQL vergibt die ID; vor dem Speichern null.

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true) //Mail darf nur einmal existieren
    private String email;

    @Column(nullable = false)
    private int status;

    // WICHTIG JPA benötigt einen parameterlosen Konstruktor.
    // protected verhindert die übliche direkte Nutzung von aussen.
    // muss spezifisch erfasst sein
    protected CampusPayCustomerEntity() {}


    // Keine ID im Konstruktor: Sie wird von der Datenbank erzeugt.
    public CampusPayCustomerEntity(String firstName, String lastName,
                                   String email, int status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
    }

    public Long getId() { return id; } // Kein öffentlicher ID-Setter.
    public String getFirstName() { return firstName; }
    public void setFirstName(String value) { firstName = value; }
    public String getLastName() { return lastName; }
    public void setLastName(String value) { lastName = value; }
    public String getEmail() { return email; }
    public void setEmail(String value) { email = value; }
    public int getStatus() { return status; }
    public void setStatus(int value) { status = value; }

    @Override
    public String toString() {
        // Später keine Lazy-Beziehungen ausgeben: verhindert
        // ungewollte Abfragen und rekursive Objekt-Ausgaben.
        return "Customer{id=" + id + ", firstName='" + firstName
                + "', lastName='" + lastName + "', email='" + email
                + "', status=" + status + "}";
    }
}