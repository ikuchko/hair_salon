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

  @Test
  public void stylist_deletesSuccesfullyWithAttachedClients() {
    Stylist newStylist = new Stylist("Aleksandr", "Pushkin");
    newStylist.save();
    Client client = new Client("Mr", "Watson", "999-99-99-999", newStylist.getId());
    client.save();
    newStylist.delete();
    assertEquals(0, Stylist.all().size());
  }

  @Test
  public void stylist_returnsListOfHisClients() {
    Stylist stylist = new Stylist("Sergei", "Zverev");
    stylist.save();
    Client firstClient = new Client("Lev", "Tolsoy", "(050) 345-1232", stylist.getId());
    firstClient.save();
    Client secondClient = new Client("Sergei", "Esenin", "(066) 345-1232", stylist.getId());
    secondClient.save();
    Client [] clients = new Client [] {firstClient, secondClient};
    assertTrue(stylist.getClientList().containsAll(Arrays.asList(clients)));
  }

  @Test
  public void stylist_findByStylistOrClientNameCorrectly() {
    Stylist stylist = new Stylist("Sergei", "Zverev");
    stylist.save();
    Client firstClient = new Client("Lev", "Tolstoy", "(050) 345-1232", stylist.getId());
    firstClient.save();
    assertEquals(stylist, Stylist.globalSearch("Tolstoy"));
    assertEquals(stylist, Stylist.globalSearch("Sergei"));
  }


}
