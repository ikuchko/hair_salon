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
    String sql = "UPDATE stylists SET first_name = :firstName, last_name = :lastName WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("firstName", firstName)
        .addParameter("lastName", lastName)
        .addParameter("id", mId)
        .executeUpdate();
        this.mFirstName = firstName;
        this.mLastName = lastName;
    }
  }

  public void delete() {
    if (this.getClientList().size() > 0) {
      String sqlDeleteClients = "DELETE FROM clients WHERE stylist_id = :id";
    }
    String sql = "DELETE FROM clients WHERE stylist_id = :id; DELETE FROM stylists WHERE id = :id";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public List<Client> getClientList() {
    String sql = "SELECT id AS mId, first_name AS mFirstName, last_name AS mLastName, phone_number AS mPhoneNumber, stylist_id AS mStylistId FROM clients WHERE stylist_id = :id";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", mId)
        .executeAndFetch(Client.class);
    }
  }

  public static Stylist globalSearch(String query) {
    String sql = "SELECT st.id AS mId, st.first_name AS mFirstName, st.last_name AS mLastName " +
                 "FROM stylists AS st INNER JOIN clients ON st.id = clients.stylist_id " +
                 "WHERE st.first_name LIKE :query OR st.last_name LIKE :query OR clients.first_name LIKE :query OR clients.last_name LIKE :query";
    try (Connection con = DB.sql2o.open()){
      return con.createQuery(sql)
        .addParameter("query", "%"+query+"%")
        .addParameter("query", "%"+query+"%")
        .addParameter("query", "%"+query+"%")
        .addParameter("query", "%"+query+"%")
        .executeAndFetchFirst(Stylist.class);
    }
  }

}


//SELECT st.id, st.first_name, st.last_name FROM stylists AS st INNER JOIN clients ON st.id = clients.stylist_id WHERE st.first_name LIKE :qeury OR st.last_name LIKE query OR clients.first_name LIKE query OR clients.last_name LIKE query
