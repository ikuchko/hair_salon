import java.util.*;
import org.sql2o.*;

public class Client{
  private String mFirstName;
  private String mLastName;
  private String mPhoneNumber;
  private int mId;
  private int mStylistId;

  public Client(String firstName, String lastName, String phoneNumber, int stylistId) {
    this.mFirstName = firstName;
    this.mLastName = lastName;
    this.mPhoneNumber = phoneNumber;
    this.mStylistId = stylistId;
  }

  public String getFirstName(){
    return mFirstName;
  }
  public String getLastName(){
    return mLastName;
  }
  public String getName(){
    return mFirstName + " " + mLastName;
  }
  public String getPhoneNumber(){
    return mPhoneNumber;
  }
  public int getStylistId(){
    return mStylistId;
  }
  public int getId(){
    return mId;
  }

  @Override
  public boolean equals(Object otherClient) {
    if (!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return (this.getName().equals(newClient.getName())) &&
             (this.getId() == newClient.getId()) &&
             (this.getPhoneNumber().equals(newClient.getPhoneNumber())) &&
             (this.getStylistId() == newClient.getStylistId());
    }
  }

  public void save() {
    String sql = "INSERT INTO clients (first_name, last_name, phone_number, stylist_id) VALUES (:firstName, :lastName, :phoneNumber, :stylist_id)";
    try(Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("firstName", mFirstName)
        .addParameter("lastName", mLastName)
        .addParameter("phoneNumber", mPhoneNumber)
        .addParameter("stylist_id", mStylistId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Client find(int id) {
    String sql = "SELECT id AS mId, first_name AS mFirstName, last_name AS mLastName, phone_number AS mPhoneNumber, stylist_id AS mStylistId FROM clients WHERE id=:id";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
    }
  }

  public static List<Client> all() {
    String sql = "SELECT id AS mId, first_name AS mFirstName, last_name AS mLastName, phone_number AS mPhoneNumber, stylist_id AS mStylistId FROM clients";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .executeAndFetch(Client.class);
    }
  }

  public void update(String firstName, String lastName, String phoneNumber) {
    String sql = "UPDATE clients SET first_name = :firstName, last_name = :lastName, phone_number = :phoneNumber WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("firstName", firstName)
        .addParameter("lastName", lastName)
        .addParameter("phoneNumber", phoneNumber)
        .addParameter("id", mId)
        .executeUpdate();
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mPhoneNumber = phoneNumber;
    }
  }
  public void update(String firstName, String lastName, String phoneNumber, int stylistId) {
    String sql = "UPDATE clients SET first_name = :firstName, last_name = :lastName, phone_number = :phoneNumber, stylist_id = :stylistId WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("firstName", firstName)
        .addParameter("lastName", lastName)
        .addParameter("phoneNumber", phoneNumber)
        .addParameter("stylistId", stylistId)
        .addParameter("id", mId)
        .executeUpdate();
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mPhoneNumber = phoneNumber;
        this.mStylistId = stylistId;
    }
  }
  public void update(int stylistId) {
    String sql = "UPDATE clients SET stylist_id = :stylistId WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("stylistId", stylistId)
        .addParameter("id", mId)
        .executeUpdate();
        this.mStylistId = stylistId;
    }
  }

  public void delete() {
    String sql = "DELETE FROM clients WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("id", mId)
      .executeUpdate();
    }
  }

}
