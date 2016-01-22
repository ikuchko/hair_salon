import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class StylistTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void stylist_intitiateCorrectly(){
    Stylist newStylist = new Stylist("Leonardo", "Da Vinci");
    assertEquals("Leonardo", newStylist.getFirstName());
    assertEquals("Da Vinci", newStylist.getLastName());
    assertEquals("Leonardo Da Vinci", newStylist.getName());
  }

  @Test
  public void stylist_savesSuccesfully() {
    Stylist newStylist = new Stylist("Leonardo", "Da Vinci");
    newStylist.save();
    Stylist savedStylist = Stylist.find(newStylist.getId());
    assertTrue(savedStylist.equals(newStylist));
  }

  @Test
  public void stylist_returnListOfStylists() {
    Stylist firstStylist = new Stylist("Leonardo", "Da Vinci");
    firstStylist.save();
    Stylist secondStylist = new Stylist("Martyn", "MacCain");
    secondStylist.save();
    Stylist [] stylists = new Stylist [] {firstStylist, secondStylist};
    assertTrue(Stylist.all().containsAll(Arrays.asList(stylists)));
  }

  @Test
  public void stylist_updatesSuccsesfully() {
    Stylist newStylist = new Stylist("Aleksandr", "Pushkin");
    newStylist.save();
    newStylist.update("Aleksandr Sergeevich", "Pushkin");
    assertEquals("Aleksandr Sergeevich Pushkin", newStylist.getName());
  }

  @Test
  public void stylist_deletesSuccesfully() {
    Stylist newStylist = new Stylist("Aleksandr", "Pushkin");
    newStylist.save();
    newStylist.delete();
    assertEquals(0, Stylist.all().size());
  }



}
