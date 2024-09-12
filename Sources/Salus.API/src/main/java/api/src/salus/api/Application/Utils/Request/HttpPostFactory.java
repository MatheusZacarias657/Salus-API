package api.src.salus.api.Application.Utils.Request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
class HttpPostFactory {

    private final ObjectMapper objectMapper;

    @Autowired
    HttpPostFactory() {
        this.objectMapper = new ObjectMapper();
    }

    <T> CompletableFuture<HttpResponse<String>> PostRequest(URI url, T body) throws JsonProcessingException {
        return PostRequest(url, body, null);
    }

    <T> CompletableFuture<HttpResponse<String>> PostRequest(URI url, T body, Map<String, String> headers) throws JsonProcessingException {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = objectMapper.writeValueAsString(body);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json");

        if (headers != null) {
            headers.forEach(requestBuilder::header);
        }

        HttpRequest request = requestBuilder.build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }
}
