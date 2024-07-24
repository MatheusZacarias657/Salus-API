package api.src.salus.api.Domain.Interface.Application.HttpFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface IHttpFactory {
    URI buildUri(String baseUrl, String endpoint) throws IOException;

    URI buildUri(String baseUrl, Map<String, String> parameters) throws IOException;

    URI buildUri(String baseUrl, String endpoint, Map<String, String> parameters) throws IOException;

    <T> CompletableFuture<HttpResponse<String>> PostRequest(URI url, T body) throws JsonProcessingException;

    <T> CompletableFuture<HttpResponse<String>> PostRequest(URI url, T body, Map<String, String> headers) throws JsonProcessingException;
}
