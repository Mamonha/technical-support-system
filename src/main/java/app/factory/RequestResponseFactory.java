package app.factory;

import app.dto.response.RequestResponse;

public class RequestResponseFactory {

    public static RequestResponse createDefaultRequestResponse() {
        RequestResponse requestResponse = new RequestResponse();
        requestResponse.setTicketId(1L);
        requestResponse.setDescription("Default request res");
        requestResponse.setUserId(1L);
        return requestResponse;
    }
}