package ServerSv.web.model.response;

import java.util.List;


public class ListOfUserResponses {

    private List<UserResponse> userResponses;

    public ListOfUserResponses() {
        this.userResponses = null;
    }

    public ListOfUserResponses(List<UserResponse> userResponses) {
        this.userResponses = userResponses;
    }

    public List<UserResponse> getUserResponses() {
        return userResponses;
    }

    public ListOfUserResponses setUserResponses(List<UserResponse> userResponses) {
        this.userResponses = userResponses;
        return this;
    }

    public void addUserResponse (UserResponse userResponses) {
        if (userResponses != null)
            this.userResponses.add(userResponses);
    }
}
