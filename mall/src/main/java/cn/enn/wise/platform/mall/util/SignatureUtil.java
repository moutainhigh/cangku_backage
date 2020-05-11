package cn.enn.wise.platform.mall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map.Entry;
@Slf4j
public class SignatureUtil {
    public static final String CHARACTER_ENCODING = "UTF-8";
    public static String ALGORITHM = "HmacSHA256";


    public static final String SECRET_KEY ;

    static {
        String profile = SpringUtils.getActiveProfile();
        log.info("profile=>"+profile);
        if("prod".equals(profile)){
            SECRET_KEY = "e2ecdd281421d7a16d62f5b9c14e476b";
            log.info("使用生产环境的key");
        }else {
            SECRET_KEY = "e2ecdd281421d7a16d62f5b9c14e476b";
            log.info("使用其他环境的key");
        }
    }


    public static String checkSignature(HashMap<String, String> parameters) throws Exception {



        // Format the parameters as they will appear in final format
        // (without the signature parameter)
        String formattedParameters = calculateStringToSignV2(parameters);

        String signature = sign(formattedParameters, SECRET_KEY);

        return   urlEncode(signature);
    }


    /** If Signature Version is 2, string to sign is based on following:
     *
     *    1. The HTTP Request Method followed by an ASCII newline (%0A)
     *
     *    2. The HTTP Host header in the form of lowercase host,
     *       followed by an ASCII newline.
     *
     *    3. The URL encoded HTTP absolute path component of the URI
     *       (up to but not including the query string parameters);
     *       if this is empty use a forward '/'. This parameter is followed
     *       by an ASCII newline.
     *
     *    4. The concatenation of all query string components (names and
     *       values) as UTF-8 characters which are URL encoded as per RFC
     *       3986 (hex characters MUST be uppercase), sorted using
     *       lexicographic byte ordering. Parameter names are separated from
     *       their values by the '=' character (ASCII character 61), even if
     *       the value is empty. Pairs of parameter and values are separated
     *       by the '&' character (ASCII code 38).
     *
     */
    public static String calculateStringToSignV2(Map<String, String> parameters) throws SignatureException, URISyntaxException {
        // Sort the parameters alphabetically by storing
        // in TreeMap structure
        Map<String, String> sorted = new TreeMap<String, String>();
        sorted.putAll(parameters);

        // Create flattened (String) representation
        StringBuilder data = new StringBuilder();

        Iterator<Entry<String, String>> pairs =
                sorted.entrySet().iterator();
        while (pairs.hasNext()) {
            Entry<String, String> pair = pairs.next();
            if (pair.getValue() != null) {
                data.append(pair.getKey() + "=" + pair.getValue());
            } else {
                data.append(pair.getKey() + "=");
            }

            // Delimit parameters with ampersand (&)
            if (pairs.hasNext()) {
                data.append("&");
            }
        }

        return data.toString();
    }


    /**
     * Sign the text with the given secret key and convert to base64
     */
    public static String sign(String data, String secretKey)
            throws NoSuchAlgorithmException, InvalidKeyException,
            IllegalStateException, UnsupportedEncodingException {
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(new SecretKeySpec(secretKey.getBytes(CHARACTER_ENCODING),
                ALGORITHM));
        byte[] signature = mac.doFinal(data.getBytes(CHARACTER_ENCODING));
        String signatureBase64 = new String(Base64.encodeBase64(signature),
                CHARACTER_ENCODING);


        return new String(signatureBase64);
    }

    public static String urlEncode(String rawValue) {
        String value = (rawValue == null) ? "" : rawValue;
        String encoded = null;

        try {
            encoded = URLEncoder.encode(value, CHARACTER_ENCODING)
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Unknown encoding: " + CHARACTER_ENCODING);
            e.printStackTrace();
        }

        return encoded;
    }

    public static void main(String[] args) throws SignatureException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("productId","938");
        paramsMap.put("departureDate","2020-01-01");
        paramsMap.put("amount","1");
        paramsMap.put("phone","15303786335");
        paramsMap.put("distributorPhone","");
        paramsMap.put("orderPrice","100");
        paramsMap.put("isScenicService","");
        paramsMap.put("tourismName","白杰");

        String signString = calculateStringToSignV2(paramsMap);
        String sign = sign(signString, "lZ2UMP3WlPqLVV%2FGn9exYevLfLa4UVgzhItGrCfV1e4%3D");
        String encodeSign = urlEncode(sign);
        System.out.println(encodeSign);
    }

}



