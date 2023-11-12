package game.test.suite;

import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class TestCsvFeeder extends Simulation {

    private static final String PARAMS_DIR = "params/game99/platform/app/csv-feeder";

    HttpProtocolBuilder httpConf = http.baseUrl("https://game99.feiwindevelopment.com");

    /*
    FEEDER
     queue()    - default behavior: use an Iterator on the underlying sequence
     random()   - randomly pick an entry in the sequence
     shuffle()  - shuffle entries, then behave like queue
     circular() - go back to the top of the sequence once the end is reached
    */
    FeederBuilder.Batchable<String> csvFeeder = csv(PARAMS_DIR + "/request_bodies.csv").random();

    ScenarioBuilder myScenario = scenario("My Scenario")
            .feed(csvFeeder)
            .exec(session -> {//sample to set session variable
                String param1 = session.getString("startTime");
                String param2 = session.getString("endTime");
                String param3 = session.getString("account");

                String requestBody = "{\"startTime\": \"" + param1 + "\", \"endTime\": \"" + param2 + "\", \"account\": \"" + param3 + "\"}";

                //needs to create new Session since current session is immutable.
                Session newSession  = session.set("requestBody", requestBody);

                return newSession;
            })
            .exec(http("My Request")
                    .post("/game99-platform-app/getAllData")
                    .header("token", "${token}")
                    .body(StringBody("{\"startTime\": \"${startTime}\", \"endTime\": \"${endTime}\", \"account\": \"${account}\"}"))
                    .asJson()
                    .check(bodyString().saveAs("responseBody")) //save responseBody as session variable.
                    .check(jsonPath("$.code").ofInt().is(200)
                    )
            ).exec(session-> {
                String  requestBody = session.getString("requestBody");
                String  responseBody = session.getString("responseBody");
                System.out.println("Request body:" + requestBody);
                System.out.println("Response body:" + responseBody);
                System.out.println("Token:" + session.getString("token"));
                return session;
            });


    {
        setUp(
                myScenario.injectOpen(
                        rampUsers(10).during(5)
                )
        ).protocols(httpConf);
    }

}
