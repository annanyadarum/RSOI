package ServerSv.web.model.request;

import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class LibraryRequest {
    private String name;
    private String location;

    public LibraryRequest() {
        this.name = "";
        this.location = "";
    }

    public LibraryRequest(String name, String location, List<Long> books, List<Long> visitors) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("location", location)
                .toString();
    }
}