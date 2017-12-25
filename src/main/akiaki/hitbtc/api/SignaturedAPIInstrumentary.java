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

import com.google.gson.JsonSyntaxException;
import main.akiaki.hitbtc.api.interfaces.NonceGenerator;
import main.akiaki.hitbtc.exceptions.HitBTCAccessDenied;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SignaturedAPIInstrumentary extends APIInstrumentary {
    private final String apiKey, apiSecret;
    private NonceGenerator nonceGenerator = new NonceCounter();

    public SignaturedAPIInstrumentary(String apiKey, String apiSecret, String apiEndPoint, String httpEndPoint) {
        super(apiEndPoint, httpEndPoint);
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public <T> T loadGETURL(String url, Map<String, String> data, Type type) throws IOException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, HitBTCAccessDenied {
        String result = loadGETURLAsString(url, data);
        return this.getGson().fromJson(result, type);
    }

    public String loadGETURLAsString(String url, Map<String, String> data) throws IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        URIBuilder b = new URIBuilder(this.httpEndPoint + this.apiEndpoint + url);
        b.addParameter("nonce", this.nonceGenerator.nextNonce() + "");
        b.addParameter("apikey", this.apiKey);
        for (Map.Entry<String, String> e : data.entrySet()) {
            b.addParameter(e.getKey(), e.getValue());
        }

        URL obj = b.build().toURL();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(obj.toURI());

        //System.out.println(obj.toURI());
        request.addHeader("X-Signature", hmacDigest(obj.toURI().toString().substring(this.httpEndPoint.length()), this.apiSecret));
        request.addHeader("User-Agent", "Mozilla/5.0");
        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        int responseCode = response.getStatusLine().getStatusCode();
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        //System.out.println(result.toString());
        if (responseCode == 403) {
            throw new HitBTCAccessDenied(result.toString());
        }
        if (responseCode == 500) {
            throw new IOException("hitbtc internal error");
        }
        return result.toString();
    }

    public <T> T loadPOSTURL(String url, Map<String, String> data, Class<T> classOfT) throws JsonSyntaxException, IOException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {

        String result = loadPOSTURLAsString(url, data);
        return this.getGson().fromJson(result, classOfT);
    }

    public String loadPOSTURLAsString(String url, Map<String, String> data) throws JsonSyntaxException, IOException, InvalidKeyException, NoSuchAlgorithmException, HitBTCAccessDenied {
        String message = this.apiEndpoint + url + "?nonce=" + this.nonceGenerator.nextNonce() + "&apikey=" + this.apiKey;
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(this.httpEndPoint + message);

        post.setHeader("User-Agent", "Mozilla/5.0");
        if (!data.isEmpty()) {
            List<NameValuePair> urlParameters = new ArrayList<>();

            for (Map.Entry<String, String> e : data.entrySet()) {
                urlParameters.add(new BasicNameValuePair(e.getKey(), e.getValue()));
            }

            UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(urlParameters, "utf-8");

            BufferedReader postReader = new BufferedReader(new InputStreamReader(postEntity.getContent()));

            String postLine;

            while ((postLine = postReader.readLine()) != null) {


                message = message + postLine;

            }

            post.setEntity(postEntity);
        }
        String hmac = hmacDigest(message, this.apiSecret);
        post.addHeader("X-Signature", hmac);
        HttpResponse response = client.execute(post);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();

        String line;

        while ((line = rd.readLine()) != null) {

            result.append(line);

        }
        //System.out.println(result.toString());
        int responseCode = response.getStatusLine().getStatusCode();
        if (responseCode == 403) {
            throw new HitBTCAccessDenied(result.toString());
        }
        if (responseCode == 500) {
            throw new IOException(result.toString());
        }
        return result.toString();
    }


    public static String hmacDigest(String message, String secretKey) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {

        String digest = null;

        String algo = "HmacSHA512";

        SecretKeySpec key = new SecretKeySpec((secretKey).getBytes("UTF-8"), algo);

        Mac mac = Mac.getInstance(algo);

        mac.init(key);

        byte[] bytes = mac.doFinal(message.getBytes("UTF-8"));

        StringBuilder hash = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {

            String hex = Integer.toHexString(0xFF & bytes[i]);

            if (hex.length() == 1) {

                hash.append('0');

            }

            hash.append(hex);

        }

        digest = hash.toString();

        return digest;

    }

    public void registerExternalNonceGenerator(NonceGenerator nonceGenerator) {
        this.nonceGenerator = nonceGenerator;
    }
}
