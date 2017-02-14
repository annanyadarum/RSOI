package ServerSv.web.model.response;

/**
 * Created by grigory on 11/29/16.
 */
public class ErrorResponse {

    private String Message;

    public ErrorResponse() { }

    public ErrorResponse(String message) {
        this.Message = message;
    }

    public String getMessage() {
        return Message;
    }

    public ErrorResponse setMessage(String message) {
        this.Message = message;
        return this;
    }
}
