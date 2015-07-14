package fr.xebia.hello.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

@Data
public class Todo {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @MongoId
    @MongoObjectId
    protected String _id;

    private String task;
    private Boolean done;

    public Todo() {
    }

    private Todo(String task) {
        this.task = task;
        this.done = false;
    }

    public static Todo createTodo(String task) {
        return new Todo(task);
    }

    @JsonIgnore
    public boolean isValid() {
        return task != null;
    }

    @JsonProperty("_id")
    public String getId() {
        return _id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this._id = id;
    }

}
