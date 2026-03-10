package pe.challenge.literatura.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ConsumoApiService {

    public String obtenerDatos(String titulo) throws Exception {
        String tituloFormateado = titulo.replace(" ", "%20");

        String url = "https://gutendex.com/books/?search=" + tituloFormateado;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
