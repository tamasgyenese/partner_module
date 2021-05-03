package com.partner.servicecaller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.partner.common.Constants;
import com.partner.exception.IllegalResponsePath;
import com.partner.exception.NoResponseDataException;
import com.partner.model.Event;
import com.partner.model.Reservation;
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


    /**
     * Call the given URL (GET) which returns a list of Event
     * @param url
     * @param token
     * @return List<Event>
     * @throws NoResponseDataException
     */
    public List<Event> getEvents(String url, String token) throws NoResponseDataException {
        logger.trace("Getting all events from {} with token{}:", url, token);
        url = url + Constants.URL_PATH_GET_EVENTS;
        ResponseEntity<ServiceResponse> response = generateResponseEntity(url, token, HttpMethod.GET);
        if (response == null) {
            logger.error("Failure during get all event  url: {}", url);
            throw new NoResponseDataException("Empty response");
        }
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

    /**
     * Call the given URL (GET) which return a details of Event by EventID
     * @param url
     * @param token
     * @param eventId
     * @return Event
     * @throws NoResponseDataException
     */
    public Event getEvent(String url, String token, long eventId) throws NoResponseDataException {
        url = url + Constants.URL_PATH_GET_EVENT_BY_ID + "/" + eventId;
        ResponseEntity<ServiceResponse> response = generateResponseEntity(url, token, HttpMethod.GET);
        if (response == null) {
            logger.error("Failure during get event for event: {} url: {}", eventId, url);
            throw new NoResponseDataException("Empty response");
        }
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
        return mapper.convertValue(response.getBody().getData(), new TypeReference<Event>() {});
    }

    /**
     * Call the given URL (POST) to reserve a seat for an event
     * @param url
     * @param token
     * @param eventId
     * @param seatId
     * @param cardId
     * @return
     * @throws NoResponseDataException
     */
    public ServiceResponse<Reservation> reserve(String url, String token,long eventId, String seatId, String cardId) throws NoResponseDataException {
        url = url + Constants.URL_PATH_POST_RESERVATION_BASE
                  + Constants.URL_PATH_POST_RESERVATION_EVENT_ID + "/" + eventId
                  + Constants.URL_PATH_POST_RESERVATION_SEAT_ID + "/" + seatId
                  + Constants.URL_PATH_POST_RESERVATION_CARD_ID + "/" + cardId;
        ResponseEntity<ServiceResponse> response = generateResponseEntity(url, token, HttpMethod.POST);
        if (response == null) {
            logger.error("Failure during reservation for event: {}. seat: {} with card: {} url: {}",eventId, seatId, cardId, url);
            throw new NoResponseDataException("Empty response");
        }
        boolean isValidResponse = false;
        try {
            isValidResponse = validateResponse(response);
        } catch (IllegalResponsePath | NoResponseDataException | NoSuchAlgorithmException illegalResponsePath ) {
            logger.error("failure during response validation: {}",  illegalResponsePath.getMessage());
            return null;
        }
        if (!isValidResponse) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(response.getBody(), new TypeReference<ServiceResponse<Reservation>>() {});

    }

    /**
     * Generate ResponseEntity<ServiceResponse> with a given url and HttpMethod
     * @param url
     * @param token
     * @param httpMethod
     * @return
     */
    private  ResponseEntity<ServiceResponse> generateResponseEntity(String url, String token, HttpMethod httpMethod) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(Constants.AUTH_HEADER, token);
        HttpEntity<ServiceResponse> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<ServiceResponse> response = restTemplate.exchange(url, httpMethod, entity, ServiceResponse.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response;
            }
            else {
                logger.error("Status for url: {} response NOT OK", url);
                return null;
            }
        } catch (Exception e) {
            logger.error("Error during reach url: {}", url);
            return null;
        }
    }


    /**
     * validate the response it comes from the Ticket module when we store the KEY and send it within response header
     * using hashing method on the given key, it must be the same like the stored value, if not the response does not come from Ticket module
     * @param response
     * @return boolean
     * @throws IllegalResponsePath
     * @throws NoResponseDataException
     * @throws NoSuchAlgorithmException
     */
    private boolean validateResponse(ResponseEntity<ServiceResponse> response) throws IllegalResponsePath, NoResponseDataException, NoSuchAlgorithmException {
        String key = Objects.requireNonNull(response.getHeaders().get(Constants.API_KEY_HEADER_PARAMETER)).get(0);
        if (key == null || key.length() == 0 || !Constants.AUTHENTICATION_HASH.equals(getStringFromSHA256(key))) {
            logger.error("INVALID RESPONSE");
            throw new IllegalResponsePath("The response is not from Ticket API");
        }
        if (response.getBody().getData() == null && response.getBody().getErrorMessage() == null) {
            logger.error("Empty response data");
            throw new NoResponseDataException(response.getBody().getErrorMessage(), response.getBody().getErrorCode());
        }
        return true;
    }

    /**
     * For hashing
     * @param bytes
     * @return
     */
    private static String byteArray2Hex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for(final byte b : bytes) {
            sb.append(hex[(b & 0xF0) >> 4]);
            sb.append(hex[b & 0x0F]);
        }
        return sb.toString();
    }

    /**
     * For hashing
     * @param stringToEncrypt
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static String getStringFromSHA256(String stringToEncrypt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(stringToEncrypt.getBytes());
        return byteArray2Hex(messageDigest.digest());
    }

}

