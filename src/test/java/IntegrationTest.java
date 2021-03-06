import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void welcomePageIsCreatedTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Hair salon manage system");
  }

  @Test
  public void listOfStylists_ShowedOnMainPage() {
    Stylist newStylist = new Stylist("Harry", "Poter");
    newStylist.save();

    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Harry Poter");
  }

  @Test
  public void addedStylist_ShowedOnMainPage() {
    goTo("http://localhost:4567/");
    fill("#newfirstname").with("John");
    fill("#newlastname").with("Travolta");
    submit(".btn-block");
    assertThat(pageSource()).contains("John Travolta");
  }

  @Test
  public void updateStylist_ShowedOnStylistPageWithAllClients() {
    Stylist newStylist = new Stylist("Harry", "Poter");
    newStylist.save();
    Client client = new Client("Albert", "Enshtein", "5829", newStylist.getId());
    client.save();

    goTo("http://localhost:4567/stylist/" + newStylist.getId());
    fill("#newfirstname").with("John");
    fill("#newlastname").with("Travolta");
    submit(".btn-block");
    assertThat(pageSource()).contains("John Travolta");
    assertThat(pageSource()).contains("Albert Enshtein");
  }

  @Test
  public void stylistsClient_areShowedInStylistPage() {
    Stylist newStylist = new Stylist("Harry", "Poter");
    newStylist.save();
    Client client = new Client("Uma", "Turman", "(503) 888-5544", newStylist.getId());
    client.save();

    goTo("http://localhost:4567/stylist/" + newStylist.getId());
    assertThat(pageSource()).contains("Uma Turman");
    assertThat(pageSource()).contains("(503) 888-5544");
  }

  @Test
  public void clientList_isShowedOnClientsPage() {
    Stylist firstStylist = new Stylist("Dr", "House");
    firstStylist.save();
    Stylist secondStylist = new Stylist("Dr", "Watson");
    secondStylist.save();
    Client firstClient = new Client("Uma", "Turman", "(503) 888-5544", firstStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Harry", "Poter", "(503) 999-5544", secondStylist.getId());
    secondClient.save();

    goTo("http://localhost:4567/clients");
    assertThat(pageSource()).contains("Uma Turman");
    assertThat(pageSource()).contains("(503) 888-5544");
    assertThat(pageSource()).contains("Harry Poter");
    assertThat(pageSource()).contains("(503) 999-5544");
  }

  @Test
  public void addClient_SuccessMassegeShowes() {
    Stylist stylist = new Stylist("Mrs", "Smith");
    stylist.save();

    goTo("http://localhost:4567/clients");
    fill("#newfirstname").with("John");
    fill("#newlastname").with("Travolta");
    fill("#newphone").with("(097) 645-8891");
    fillSelect("#stylist").withText(stylist.getName());
    submit(".btn-block");
    assertThat(pageSource()).contains("succesfully added to stylist");
  }

  @Test
  public void updateClient_correctly() {
    Stylist stylist = new Stylist("Mrs", "Smith");
    stylist.save();
    Client client = new Client("Doctor", "House", "911", stylist.getId());
    client.save();

    goTo("http://localhost:4567/clients");
    click("a", withText("Doctor House"));
    fill("#updatefirstname").with("MD");
    submit(".btn-update");
    assertThat(pageSource()).contains("MD House");
  }

}
