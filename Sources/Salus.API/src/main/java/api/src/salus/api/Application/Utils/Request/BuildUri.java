package api.src.salus.api.Application.Utils.Request;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class BuildUri {

    URI buildUri(String baseUrl, String endpoint) throws IOException {
        return buildUri(baseUrl, endpoint, null);
    }

    URI buildUri(String baseUrl, Map<String, String> parameters) throws IOException {
        return buildUri(baseUrl, null, parameters);
    }

    URI buildUri(String baseUrl, String endpoint, Map<String, String> parameters) throws IOException {
        StringBuilder builder = new StringBuilder(baseUrl);

        if (endpoint != null && !endpoint.isEmpty()) {
            if (!baseUrl.endsWith("/") && !endpoint.startsWith("/")) {
                builder.append('/');
            }
            builder.append(endpoint);
        }

        if (parameters != null && !parameters.isEmpty()) {
            builder.append('?');
            builder.append(parameters.entrySet().stream()
                    .map(p -> String.format("%s=%s", urlEncode(p.getKey()), urlEncode(p.getValue())))
                    .collect(Collectors.joining("&")));
        }

        return URI.create(builder.toString());
    }

    private String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
