package bkd.src.salus.api.Application.Utils.Request;

import bkd.src.salus.api.Domain.Interface.Application.HttpFactory.IHttpFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class HttpFactory implements IHttpFactory {

    private final BuildUri buildUri;
    private final HttpPostFactory postFactory;

    public HttpFactory(BuildUri buildUri, HttpPostFactory postFactory) {
        this.buildUri = buildUri;
        this.postFactory = postFactory;
    }

    @Override
    public URI buildUri(String baseUrl, String endpoint) throws IOException {
        return buildUri.buildUri(baseUrl, endpoint);
    }

    @Override
    public URI buildUri(String baseUrl, Map<String, String> parameters) throws IOException {
        return buildUri.buildUri(baseUrl, parameters);
    }

    @Override
    public URI buildUri(String baseUrl, String endpoint, Map<String, String> parameters) throws IOException {
        return buildUri.buildUri(baseUrl, endpoint, parameters);
    }

    @Override
    public <T> CompletableFuture<HttpResponse<String>> PostRequest(URI url, T body) throws JsonProcessingException {
        return postFactory.PostRequest(url, body);
    }

    @Override
    public <T> CompletableFuture<HttpResponse<String>> PostRequest(URI url, T body, Map<String, String> headers) throws JsonProcessingException {
        return postFactory.PostRequest(url, body, headers);
    }
}
