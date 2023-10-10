package game.test.suite.simulation.game99.platform.app;

import game.test.suite.LoginSimulation;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;

import java.util.List;

import static game.test.suite.Globals.PLATFORM_APP;
import static game.test.suite.Globals.getDefaultRequest;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.status;

public class MemberRecommend extends LoginSimulation {

    private static final String PARAMS_DIR = "params/game99/platform/app/member-recommend";

    private ScenarioBuilder getAllData() {
        String uri = "/getAllData";
        ChainBuilder builder = exec(session -> {
            getDefaultRequest(uri, PLATFORM_APP + uri)
                    .header("token", TOKEN)
                    .body(RawFileBody(PARAMS_DIR + "/incomeDetails.json"))
                    .check(status().is(200));
            return session;
        });
        return scenario(uri).exec(builder);
    }

    @Override
    protected List<PopulationBuilder> builders() {
        return List.of(
                getAllData().injectOpen(atOnceUsers(20))
        );
    }
}
