package game.test.suite;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.Map;

import static io.gatling.javaapi.http.HttpDsl.http;

public final class Globals {
    public static final String BASE_URL = "https://game99.feiwindevelopment.com";
    public static final String PLATFORM_APP = "/game99-platform-app";
    public static final Map<String, String> STATIC_HEADERS = Map.of(
            "version", "1.0",
            "frond-host", "http://localhost.com");
    public static final HttpProtocolBuilder DEFAULT_PROTOCOL = http.baseUrl(BASE_URL)
            .acceptHeader("application/json, text/html")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36");

}
