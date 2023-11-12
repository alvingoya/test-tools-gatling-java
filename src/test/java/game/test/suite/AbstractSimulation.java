package game.test.suite;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.List;

import static game.test.suite.Globals.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public abstract class AbstractSimulation extends Simulation {

    protected static String TOKEN;
    private static final String TOKEN_NAME = "tokenString";

    private static final ChainBuilder LOGIN_EXEC =
            exec(http("Login")
                    .post(PLATFORM_APP + "/login")
                    .asJson()
                    .headers(STATIC_HEADERS)
                    .header("dev", "2")
                    .body(RawFileBody("params/loginBody.json"))
                    .check(status().is(200))
                    .check(jmesPath("data.token").ofString().saveAs("token"))
            ).exitHereIfFailed().exec(session -> {
                TOKEN = session.getString("token");
                return session;
            });

    ScenarioBuilder loginScn = scenario("/login").exec(LOGIN_EXEC);

    protected abstract List<PopulationBuilder> builders();
    protected abstract String baseUri();

    protected HttpProtocolBuilder getProtocol() {
        return DEFAULT_PROTOCOL;
    }

    protected boolean useLogin() {
        return true;
    }

    public String resolveUri(String uri) {
        return baseUri() + uri;
    }

    public ScenarioBuilder createScenario(String name, ChainBuilder builder) {
        return useLogin() ? scenario(name).exec(session -> session.set(TOKEN_NAME, TOKEN)).exec(builder)
                : scenario(name).exec(builder);
    }

    public String getTokenName() {
        return "#{" + TOKEN_NAME + "}";
    }

    {
        if(useLogin()) {
            setUp(loginScn.injectOpen(atOnceUsers(1)).andThen(builders().toArray(PopulationBuilder[]::new)))
                    .protocols(getProtocol());
        } else {
            setUp(builders().toArray(PopulationBuilder[]::new)).protocols(getProtocol());
        }
    }
}
