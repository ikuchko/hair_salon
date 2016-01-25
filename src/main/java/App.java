import java.util.ArrayList;
import java.util.HashMap;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;

public class App {
  public static void main(String[] args) {
      staticFileLocation("/public");
      String layout = "templates/layout.vtl";

      get("/", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/index.vtl");

        model.put("stylists", Stylist.all());

        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/stylist/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/stylist.vtl");

        Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
        model.put("stylist", stylist);
        model.put("clients", stylist.getClientList());

        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/deleteStylist/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/index.vtl");

        Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
        stylist.delete();
        model.put("stylists", Stylist.all());

        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/clients", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/clients.vtl");

        model.put("clients", Client.all());
        model.put("stylists", Stylist.all());

        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      post("/newStylist", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/index.vtl");

        Stylist stylist = new Stylist(request.queryParams("newfirstname"), request.queryParams("newlastname"));
        stylist.save();
        model.put("stylists", Stylist.all());

        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      post("/updateStylist/:id", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/stylist.vtl");

        Stylist stylist = Stylist.find(Integer.parseInt(request.params("id")));
        stylist.update(request.queryParams("newfirstname"), request.queryParams("newlastname"));
        model.put("stylist", stylist);
        model.put("clients", stylist.getClientList());


        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      post("/clients", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/clients.vtl");

        int stylist_id = Integer.parseInt(request.queryParams("stylist"));
        Client client = new Client(request.queryParams("newfirstname"), request.queryParams("newlastname"), request.queryParams("newphone"), stylist_id);
        client.save();
        model.put("client", client);
        model.put("clients", Client.all());
        model.put("stylist", Stylist.find(stylist_id));

        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

  }
}
