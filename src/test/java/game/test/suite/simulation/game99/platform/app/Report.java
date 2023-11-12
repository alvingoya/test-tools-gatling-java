package game.test.suite.simulation.game99.platform.app;

import game.test.suite.AbstractSimulation;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import jodd.net.HttpMethod;

import java.util.List;

import static game.test.suite.Globals.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.status;

public class Report extends AbstractSimulation {
    private static final String PARAMS_DIR = "params/game99/platform/app/report";

    private ScenarioBuilder accountList() {
        String uri = "/account/list";
        ChainBuilder builder = exec(
                getDefaultRequest(HttpMethod.POST, uri, resolveUri(uri))
                .header("token", getTokenName())
                .body(RawFileBody(PARAMS_DIR + "/list.json"))
                .check(status().is(200))
        );
        return createScenario(uri, builder);
    }

    @Override
    protected List<PopulationBuilder> builders() {
        return List.of(
                accountList().injectOpen(atOnceUsers(20))
        );
    }

    @Override
    protected String baseUri() {
        return PLATFORM_APP;
    }

}
