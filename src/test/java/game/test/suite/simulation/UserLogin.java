package game.test.suite.simulation;

import static game.test.suite.Globals.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

//should be public
public class UserLogin extends Simulation {

    ChainBuilder login = exec(
            http("Login")
                    .post(PLATFORM_APP + "/login")
                    .asJson()
                    .headers(STATIC_HEADERS)
                    .header("dev", "2")
                    .body(RawFileBody("params/loginBody.json"))
                    .check(status().is(200))
    );

    ScenarioBuilder loginScn = scenario("loginScn").exec(login);

    {
        setUp(
                loginScn.injectOpen(atOnceUsers(1))
        ).protocols(DEFAULT_PROTOCOL);
    }

}
