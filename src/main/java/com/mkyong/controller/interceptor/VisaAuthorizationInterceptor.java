package com.mkyong.controller.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import java.io.IOException;
import java.math.BigInteger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import static com.mkyong.config.WebConfig.tls;
import static com.mkyong.config.WebConfig.host;
import static com.mkyong.config.WebConfig.port;
import static com.mkyong.config.WebConfig.contextRoot;


/**
 * @see https://riptutorial.com/spring/example/24622/setting-headers-on-spring-resttemplate-request
 *      https://developer.visa.com/pages/working-with-visa-apis/x-pay-token#sample_code_for_api_key__shared_secret_xpaytoken
 */
public class VisaAuthorizationInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        final String apiKey = "1111111111111111";
        final String sharedSecret = "VALUE_OF_YOUR_SHARED_SECRET";
        final String baseUrl = tls +  "://" + host + ":" + port + "/" + contextRoot;

        String resourcePath = request.getURI().toString().replace(baseUrl, "");
        try {
            request.getHeaders().add("X-PAY-TOKEN", generateXpaytoken(resourcePath, apiKey, new String(body, "UTF-8"), sharedSecret));
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        return execution.execute(request, body);
    }

    public static String generateXpaytoken(String resourcePath, String queryString, String requestBody, String sharedSecret) throws SignatureException {
        String timestamp = timeStamp();
        String beforeHash = timestamp + resourcePath + queryString + requestBody;
        String hash = hmacSha256Digest(beforeHash, sharedSecret);
        String token = "xv2:" + timestamp + ":" + hash;
        return token;
    }

    private static String timeStamp() {
        return String.valueOf(System.currentTimeMillis()/ 1000L);
    }

    private static String hmacSha256Digest(String data, String sharedSecret)
            throws SignatureException {
        return getDigest("HmacSHA256", sharedSecret, data, true);
    }

    private static String getDigest(String algorithm, String sharedSecret, String data,  boolean toLower) throws SignatureException {
        try {
            Mac sha256HMAC = Mac.getInstance(algorithm);
            SecretKeySpec secretKey = new SecretKeySpec(sharedSecret.getBytes(StandardCharsets.UTF_8), algorithm);
            sha256HMAC.init(secretKey);

            byte[] hashByte = sha256HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            String hashString = toHex(hashByte);

            return toLower ? hashString.toLowerCase() : hashString;
        } catch (Exception e) {
            throw new SignatureException(e);
        }
    }

    private static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }
}
