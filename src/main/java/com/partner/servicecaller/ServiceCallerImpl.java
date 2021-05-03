package com.partner.servicecaller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.partner.common.Constants;
import com.partner.exception.IllegalResponsePath;
import com.partner.exception.NoResponseDataException;
import com.partner.model.Event;
import com.partner.model.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Service
public class ServiceCallerImpl implements IServiceCaller {

    private static Logger logger = LoggerFactory.getLogger(ServiceCallerImpl.class);

    private static final char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };




    public List<Event> getEvents(String url, String token)  {
        logger.trace("Getting all events from {} with token{}:", url, token);
        url = "http://localhost:8989/ticket/getEvents";
        token = "dGVzenQuY2VjaWxpYUBvdHBtb2JpbC5jb20mMzAwMCZFNjg1NjA4NzJCREIyREYyRkZFN0FEQzA5MTc1NTM3OA==";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(Constants.AUTH_HEADER, token);
        HttpEntity<ServiceResponse<List<Event>>> entity = new HttpEntity<>(headers);
        ResponseEntity<ServiceResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, ServiceResponse.class);
        boolean isValidResponse = false;
        try {
            isValidResponse = validateResponse(response);
        } catch (IllegalResponsePath | NoResponseDataException | NoSuchAlgorithmException illegalResponsePath) {
            logger.error("failure during response validation: {}",  illegalResponsePath.getMessage());
            return null;
        }
        if (!isValidResponse) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(response.getBody().getData(), new TypeReference<List<Event>>() {});

    }










    private boolean validateResponse(ResponseEntity<ServiceResponse> response) throws IllegalResponsePath, NoResponseDataException, NoSuchAlgorithmException {
        String key = Objects.requireNonNull(response.getHeaders().get(Constants.API_KEY_HEADER_PARAMETER)).get(0);
        if (key == null || key.length() == 0 || !Constants.AUTHENTICATION_HASH.equals(getStringFromSHA256(key))) {
            logger.error("INVALID RESPONSE");
            throw new IllegalResponsePath("The response is not from Ticket API");
        }
        if (response.getBody().getData() == null) {
            logger.error("Empty response data");
            throw new NoResponseDataException(response.getBody().getErrorMessage(), response.getBody().getErrorCode());
        }
        return true;
    }

    private static String byteArray2Hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for(final byte b : bytes) {
            sb.append(hex[(b & 0xF0) >> 4]);
            sb.append(hex[b & 0x0F]);
        }
        return sb.toString();
    }

    private static String getStringFromSHA256(String stringToEncrypt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(stringToEncrypt.getBytes());
        return byteArray2Hex(messageDigest.digest());
    }

}

