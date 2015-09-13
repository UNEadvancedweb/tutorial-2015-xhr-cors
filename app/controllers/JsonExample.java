package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Gibberish;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class JsonExample extends Controller {

    public static ObjectNode toJson(Gibberish g) {
        ObjectNode n = Json.newObject();
        n.put("subject", g.getSubject());
        n.put("adverb", g.getAdverb());
        n.put("verb", g.getVerb());
        n.put("adjective", g.getAdjective());
        n.put("object", g.getObj());
        return n;
    }

    public static Result getGibberish(int n) {
        ObjectNode[] arr = new ObjectNode[n];

        ArrayNode an = Json.newArray();
        for (int i = 0; i < n; i++) {
            an.add(toJson(new Gibberish()));
        }

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(an);
    }

    /**
     * Cross-origin POST requests with JSON content require an OPTIONS request first. The response must contain the
     * CORS header.
     */
    public static Result optionsGibberish() {
        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Access-Control-Allow-Headers", "content-type");
        return ok("");
    }

    public static Result postGibberish() {
        JsonNode json = request().body().asJson();
        if(json == null) {
            return badRequest("Expecting Json data");
        } else {
            Gibberish g = new Gibberish();
            g.setSubject(json.findPath("subject").textValue());
            g.setAdverb(json.findPath("adverb").textValue());
            g.setVerb(json.findPath("verb").textValue());
            g.setAdjective(json.findPath("adjective").textValue());
            g.setObj(json.findPath("object").textValue());

            System.out.println(g);

            response().setHeader("Access-Control-Allow-Origin", "*");
            return ok("printed it");
        }
    }


}
