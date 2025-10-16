package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public List<String> getSubBreeds(String breed) {
        String url = String.format("https://dog.ceo/api/breed/%s/list", breed);
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new BreedNotFoundException(breed);
            }

            JSONObject json = new JSONObject(response.body().string());
            if ("error".equals(json.getString("status"))) {
                throw new BreedNotFoundException(breed);
            }

            JSONArray arr = json.getJSONArray("message");
            List<String> subBreeds = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                subBreeds.add(arr.getString(i));
            }
            return subBreeds;
        } catch (IOException e) {
            throw new BreedNotFoundException(breed);
        }
    }
}