import java.util.*;
import org.sql2o.*;


public class Stylist{
  private String mFirstName;
  private String mLastName;
  private int mId;

  public Stylist(String firstName, String lastName){
    this.mFirstName = firstName;
    this.mLastName = lastName;
  }

  public String getFirstName() {
    return mFirstName;
  }
  public String getLastName() {
    return mLastName;
  }
  public String getName() {
    return mFirstName + " " + mLastName;
  }
  public int getId() {
    return mId;
  }

  @Override
  public boolean equals(Object otherStylist) {
    if (!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return (this.getName().equals(newStylist.getName())) &&
             (this.getId() == newStylist.getId());
    }
  }

  public void save() {
    String sql = "INSERT INTO stylists (first_name, last_name) VALUES (:mFirstName, :mLastName)";
    try (Connection con = DB.sql2o.open()) {
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("mFirstName", mFirstName)
        .addParameter("mLastName", mLastName)
        .executeUpdate()
        .getKey();
    }
  }

  public static Stylist find(int id) {
    String sql = "SELECT id AS mId, first_name AS mFirstName, last_name AS mLastName FROM stylists WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
    }
  }

  public static List<Stylist> all() {
    String sql = "SELECT id AS mId, first_name AS mFirstName, last_name AS mLastName FROM stylists";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .executeAndFetch(Stylist.class);
    }
  }

  public void update(String firstName, String lastName) {
    String sql = "UPDATE stylists SET first_name = :firstName, last_name = :lastName";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("firstName", firstName)
        .addParameter("lastName", lastName)
        .executeUpdate();
        this.mFirstName = firstName;
        this.mLastName = lastName;
    }
  }

  public void delete() {
    String sql = "DELETE FROM stylists WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

}
