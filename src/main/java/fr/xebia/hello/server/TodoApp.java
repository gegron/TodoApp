package fr.xebia.hello.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fakemongo.Fongo;
import fr.xebia.hello.database.TodoPersistedMongoClient;
import fr.xebia.hello.domain.Todo;
import fr.xebia.hello.domain.TodoRepository;
import org.eclipse.jetty.http.HttpStatus;
import org.jongo.Jongo;

import static spark.Spark.*;
import static spark.SparkBase.staticFileLocation;

public class TodoApp implements Runnable {

    private TodoRepository todoRepository;
    private int port;

    public TodoApp(TodoRepository todoRepository, int port) {
        this.todoRepository = todoRepository;
        this.port = port;
    }

    public static void main(String[] args) {
        int port = 4567;
        TodoRepository todoRepository;

        if (System.getProperty("MONGODB_ADDON_URI") != null) {
            System.out.println("PERSIST MODE");
            todoRepository = new TodoRepository(TodoPersistedMongoClient.INSTANCE);
        } else {
            System.out.println("IN MEMORY MODE");
            todoRepository = new TodoRepository(() -> new Jongo(new Fongo("InMemoryMongoServer").getDB("TodoDB")));
        }

        if (System.getProperty("PORT") != null) {
            port = Integer.valueOf(System.getProperty("PORT"));
            System.out.println("Run on port: " + port);
        }

        new TodoApp(todoRepository, port).run();
    }

    @Override
    public void run() {
        port(port);
        staticFileLocation("/static");

        get("/ping", (req, res) -> "OK");

        get("/todos", (req, res) -> {
            System.out.println("Retrieve todo list");

            res.type("application/json");
            return new ObjectMapper().writeValueAsString(todoRepository.findAll());
        });

        post("/todos",
                (req, res) -> {
                    ObjectMapper mapper = new ObjectMapper();

                    Todo todo = Todo.createTodo(mapper.readValue(req.body(), Todo.class).getTask());

                    if (!todo.isValid()) {
                        res.status(HttpStatus.BAD_REQUEST_400);
                        return "";
                    }

                    Todo resultTodo = todoRepository.insert(todo);

                    res.status(HttpStatus.OK_200);
                    res.type("application/json");
                    return new ObjectMapper().writeValueAsString(resultTodo);
                });

        delete("/todos/:id",
                (req, res) -> {
                    todoRepository.delete(req.params("id"));

                    res.status(HttpStatus.OK_200);
                    return "";
                });

        post("/todos/:id/done",
                (req, res) -> {
                    todoRepository.isDone(req.params("id"));

                    res.status(HttpStatus.OK_200);
                    return "";
                });
    }
}
