package game.test.suite;

import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.gatling.javaapi.http.HttpRequestActionBuilder;
import jodd.net.HttpMethod;

import java.util.Map;

import static io.gatling.javaapi.http.HttpDsl.http;

public final class Globals {

    public static final String BASE_URL = "http://localhost";
    public static final String PLATFORM_APP = ":18811";
    public static final String GAME_APP = ":18813";
//    public static final String BASE_URL = "https://game99.feiwindevelopment.com";
//    public static final String PLATFORM_APP = "/game99-platform-app";
//    public static final String GAME_APP = "/game99-game-app";
    public static final Map<String, String> STATIC_HEADERS = Map.of(
            "version", "1.0",
            "dev", "2",
            "frond-host", "http://localhost.com");
    public static final HttpProtocolBuilder DEFAULT_PROTOCOL = http.baseUrl(BASE_URL)
            .acceptHeader("application/json, text/html")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/117.0.0.0 Safari/537.36");

    public static HttpRequestActionBuilder getDefaultRequest(HttpMethod method, String name, String url) {
        HttpRequestActionBuilder httpBuilder = switch (method) {
            case GET -> http(name).get(url);
            case POST -> http(name).post(url);
            case PUT -> http(name).put(url);
            case DELETE -> http(name).delete(url);
            default -> throw new IllegalStateException("Unexpected value: " + method);
        };

        return httpBuilder.asJson().headers(STATIC_HEADERS);
    }
}
