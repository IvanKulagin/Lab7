package response;

import java.io.Serializable;

public class Response implements Serializable {

    private static final long serialVersionUID = -8196292752409057994L;
    private final ResponseType responseType;
    private String message;
    private Boolean success;
    private Exception exception;

    public Response(String message) {
        this.message = message;
        responseType = ResponseType.MESSAGE;
    }

    public Response(Boolean success) {
        this.success = success;
        responseType = ResponseType.ACCESS;
    }

    public Response(Exception exception) {
        this.exception = exception;
        responseType = ResponseType.EXCEPTION;
    }

    public Response() {
        responseType = ResponseType.DATA;
    }

    public String getMessage() {
        return message;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Exception getException() {
        return exception;
    }
}
