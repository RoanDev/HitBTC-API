/*
 * Mykhailo Pietielin 2017.
 * https://github.com/RoanDev
 * roanworkbox@gmail.com
 * GPL c:
 */

/*
 * Mykhailo Pietielin 2017.
 * https://github.com/RoanDev
 * roanworkbox@gmail.com
 * GPL c:
 */

package main.akiaki.hitbtc.api;

import com.google.gson.Gson;
import main.akiaki.hitbtc.exceptions.HitBTCAccessDenied;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

abstract class APIInstrumentary {
    private final Gson gson;
    final String apiEndpoint, httpEndPoint;

    public APIInstrumentary(String apiEndPoint, String httpEndPoint) {
        this.gson = new Gson();
        this.apiEndpoint = apiEndPoint;
        this.httpEndPoint = httpEndPoint;
    }

    public Gson getGson() {
        return gson;
    }

    public <T> T loadGETURL(String url, Class<T> classOfT) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        return this.loadGETURL(url, new HashMap<>(), classOfT);
    }

    public <T> T loadGETURL(String url, Type e) throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        return this.loadGETURL(url, new HashMap<>(), e);
    }

    public <T> T loadGETURL(String url, Map<String, String> data, Class<T> classOfT) throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        return this.loadGETURL(url, data, (Type) classOfT);
    }

    public <T> T loadGETURL(String url, Map<String, String> data, Type type) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {

        URIBuilder b = new URIBuilder(this.httpEndPoint + this.apiEndpoint + url);
        for (Map.Entry<String, String> e : data.entrySet()) {
            b.addParameter(e.getKey(), e.getValue());
        }
        URL obj = b.build().toURL();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(obj.toURI());

        request.addHeader("User-Agent", "Mozilla/5.0");
        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        int responseCode = response.getStatusLine().getStatusCode();
        if (responseCode != 200) {
            throw new IOException(this.httpEndPoint + this.apiEndpoint + url + " " + result.toString());
        }
        return this.getGson().fromJson(result.toString(), type);
    }
}
