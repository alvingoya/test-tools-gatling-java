package game.test.suite.simulation.game99.platform.app;

import game.test.suite.AbstractSimulation;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import jodd.net.HttpMethod;

import java.time.Duration;
import java.util.List;

import static game.test.suite.Globals.PLATFORM_APP;
import static game.test.suite.Globals.getDefaultRequest;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.status;

public class Interest extends AbstractSimulation {

    private static final String INTEREST_URL = PLATFORM_APP + "/interest";
    private static final String PARAMS_DIR = "params/game99/platform/app/interest";

    private ScenarioBuilder incomeDetails() {
        String uri = "/incomeDetails";
        ChainBuilder builder = exec(session -> {
            getDefaultRequest(HttpMethod.POST, "asdfasdf", INTEREST_URL + uri)
                    .header("token", TOKEN)
                    .body(RawFileBody(PARAMS_DIR + "/incomeDetails.json"))
                    .check(status().is(200));
            return session;
        });
        return scenario(uri).exec(builder);
    }

    private ScenarioBuilder summary() {
        String uri = "/summary";
        ChainBuilder builder = exec(session -> {
            getDefaultRequest( HttpMethod.POST, uri, INTEREST_URL + uri)
                    .header("token", TOKEN)
                    .body(RawFileBody(PARAMS_DIR + "/summary.json"))
                    .check(status().is(200));
            return session;
        });
        return scenario(uri).exec(builder);
    }

    private ScenarioBuilder getRules() {
        String uri = "/getRules";
        ChainBuilder builder = exec(session -> {
            getDefaultRequest(HttpMethod.GET, uri, INTEREST_URL + uri)
                    .header("token", TOKEN)
                    .check(status().is(200));
            return session;
        });
        return scenario(uri).exec(builder);
    }

    @Override
    protected List<PopulationBuilder> builders() {
        return List.of(
                incomeDetails().injectOpen(atOnceUsers(20)),
                getRules().injectOpen(rampUsersPerSec(10).to(20).during(Duration.ofSeconds(10))),
                summary().injectOpen(atOnceUsers(100))
        );
    }
}
