package fr.xebia.hello.server;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

import java.io.IOException;

import static fr.xebia.hello.test.ServerTest.findAvailablePort;
import static spark.SparkBase.awaitInitialization;


public class TodoAppTest {
    private OkHttpClient httpClient;
    private int port;

    @Before
    public void setUp() throws Exception {
        httpClient = new OkHttpClient();
        port = Integer.valueOf(System.getProperty("PORT"));
    }

    @BeforeClass
    public static void beforeClass() throws InterruptedException, IOException {
        System.setProperty("PORT", "" + findAvailablePort());

        TodoApp.main(null);
        awaitInitialization();
    }

    @AfterClass
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void should_insert_todo() {
        // Given

        // When

        // Then
    }

    @Test
    public void should_get_todo_list() throws IOException {
        // Given

        // When
        Request request = new Request.Builder().url("http://localhost:" + port + "/todos").build();
        System.out.println(httpClient.newCall(request).execute().body().toString());

        // Then
    }

}