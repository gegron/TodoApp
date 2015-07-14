package fr.xebia.hello.domain;

import com.google.common.collect.Lists;
import com.mongodb.WriteConcern;
import fr.xebia.hello.database.TodoMongoClient;
import org.bson.types.ObjectId;

import java.util.List;

import static com.mongodb.WriteConcern.ACKNOWLEDGED;

public class TodoRepository {
    private static final String TODO_COLLECTION = "todos";

    public TodoMongoClient mongoClient;

    public TodoRepository(TodoMongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public List<Todo> findAll() {
        return Lists.newArrayList(
                mongoClient
                        .getJongo()
                        .getCollection(TODO_COLLECTION)
                        .find()
                        .as(Todo.class)
                        .iterator()
        );
    }

    public Todo insert(Todo todo) {
        ObjectId objectId = new ObjectId();
        todo.setId(objectId.toString());

        mongoClient
                .getJongo()
                .getCollection(TODO_COLLECTION)
                .withWriteConcern(ACKNOWLEDGED)
                .save(todo);

        return todo;
    }

    public void delete(String id) {
        mongoClient
                .getJongo()
                .getCollection(TODO_COLLECTION)
                .remove(new ObjectId(id));
    }

    public void isDone(String id) {
        mongoClient
                .getJongo()
                .getCollection(TODO_COLLECTION)
                .withWriteConcern(WriteConcern.SAFE)
                .update(new ObjectId(id))
                .with("{$set: {done: #}}", true);
    }
}
