import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void client_intitiatesCorrectly() {
    Stylist newStylist = new Stylist("John", "Travolta");
    newStylist.save();
    Client newClient = new Client("John", "Smith", "(603) 999-5544", newStylist.getId());
    assertEquals("John", newClient.getFirstName());
    assertEquals("Smith", newClient.getLastName());
    assertEquals("John Smith", newClient.getName());
    assertEquals(newStylist.getName(), Stylist.find(newClient.getStylistId()).getName());
    assertEquals("(603) 999-5544", newClient.getPhoneNumber());
  }

  @Test
  public void client_savesSuccesfully() {
    Stylist newStylist = new Stylist("Petr", "I");
    newStylist.save();
    Client savedClient = new Client("John", "Smith", "(603) 999-5544", newStylist.getId());
    savedClient.save();
    Client newClient = Client.find(savedClient.getId());
    assertTrue(newClient.equals(savedClient));
  }

  @Test
  public void client_returnListOfClients() {
    Stylist newStylist = new Stylist("Petr", "I");
    newStylist.save();
    Client firstClient = new Client("John", "Smith", "(603) 999-5544", newStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Dabby", "Smith", "(603) 999-5545", newStylist.getId());
    secondClient.save();
    Client [] clients = new Client [] {firstClient, secondClient};
    assertTrue(Client.all().containsAll(Arrays.asList(clients)));
  }

  @Test
  public void client_successfullyUpdateClientsData() {
    Stylist newStylist = new Stylist("Petr", "I");
    newStylist.save();
    Stylist oldStylist = new Stylist("Eddart", "Stark");
    oldStylist.save();
    Client firstClient = new Client("John", "Smith", "(603) 999-5544", newStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Barbara", "Wild", "(603) 888-5544", newStylist.getId());
    secondClient.save();
    Client thirdClient = new Client("Uma", "Turman", "(503) 888-5544", newStylist.getId());
    thirdClient.save();
    firstClient.update("Dabby", "Smith", "(603) 999-5544");
    secondClient.update(oldStylist.getId());
    thirdClient.update("Dabby", "Huston", "(603) 999-5544", oldStylist.getId());
    assertEquals("Dabby Smith", firstClient.getName());
    assertEquals(oldStylist.getId(), secondClient.getStylistId());
    assertEquals("Dabby Huston", thirdClient.getName());
    assertEquals(oldStylist.getId(), thirdClient.getStylistId());
  }

  @Test
  public void client_succesfullyDeletes() {
    Stylist oldStylist = new Stylist("Eddart", "Stark");
    oldStylist.save();
    Client firstClient = new Client("John", "Smith", "(603) 999-5544", oldStylist.getId());
    firstClient.save();
    firstClient.delete();
    assertEquals(Client.all().size(), 0);
  }

}
