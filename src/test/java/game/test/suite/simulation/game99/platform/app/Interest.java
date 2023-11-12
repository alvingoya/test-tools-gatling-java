package game.test.suite.simulation.game99.platform.app;

import game.test.suite.AbstractSimulation;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import jodd.net.HttpMethod;

import java.time.Duration;
import java.util.List;

import static game.test.suite.Globals.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.status;

public class Interest extends AbstractSimulation {

    private static final String PARAMS_DIR = "params/game99/game/app/interest";

    private ScenarioBuilder incomeDetails() {
        String uri = "/incomeDetails";
        ChainBuilder builder = exec(
                getDefaultRequest(HttpMethod.POST, uri, resolveUri(uri))
                        .header("token", getTokenName())
                        .body(RawFileBody(PARAMS_DIR + "/incomeDetails.json"))
                        .check(status().is(200))
        );
        return createScenario(uri, builder);
    }

    private ScenarioBuilder summary() {
        String uri = "/summary";
        ChainBuilder builder = exec(
                getDefaultRequest(HttpMethod.POST, uri, resolveUri(uri))
                        .header("token", getTokenName())
                        .body(RawFileBody(PARAMS_DIR + "/summary.json"))
                        .check(status().is(200))
        );
        return createScenario(uri, builder);
    }

    private ScenarioBuilder getRules() {
        String uri = "/getRules";
        ChainBuilder builder = exec(
                getDefaultRequest(HttpMethod.GET, uri, resolveUri(uri))
                        .header("token", getTokenName())
                        .check(status().is(200))
        );
        return createScenario(uri, builder);
    }

    @Override
    protected List<PopulationBuilder> builders() {
        return List.of(
                incomeDetails().injectOpen(atOnceUsers(20)),
                getRules().injectOpen(rampUsersPerSec(10).to(20).during(Duration.ofSeconds(10))),
                summary().injectOpen(atOnceUsers(10))
        );
    }

    @Override
    protected String baseUri() {
        return GAME_APP + "/interest";
    }
}
