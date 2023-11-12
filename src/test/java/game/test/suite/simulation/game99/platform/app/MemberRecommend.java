package game.test.suite.simulation.game99.platform.app;

import game.test.suite.AbstractSimulation;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import jodd.net.HttpMethod;

import java.util.List;

import static game.test.suite.Globals.PLATFORM_APP;
import static game.test.suite.Globals.getDefaultRequest;
import static io.gatling.javaapi.core.CoreDsl.*;

public class MemberRecommend extends AbstractSimulation {

    private static final String PARAMS_DIR = "params/game99/platform/app/member-recommend";

    private ScenarioBuilder getAllData() {
        String uri = "/getAllData";
        ChainBuilder builder = exec(
                getDefaultRequest(HttpMethod.POST, uri, resolveUri(uri))
                        .header("token", getTokenName())
                        .body(RawFileBody(PARAMS_DIR + "/getAllData.json"))
                        .check(jsonPath("$.code").ofInt().is(200))
        );
        return createScenario(uri, builder);
    }

    @Override
    protected List<PopulationBuilder> builders() {
        return List.of(
                getAllData().injectOpen(atOnceUsers(20))
        );
    }

    @Override
    protected String baseUri() {
        return PLATFORM_APP;
    }
}
