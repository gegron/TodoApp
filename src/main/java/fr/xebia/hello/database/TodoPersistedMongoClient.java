package fr.xebia.hello.database;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.jongo.Jongo;

import java.net.Socket;
import java.net.UnknownHostException;

public class TodoPersistedMongoClient implements TodoMongoClient {
    public final static TodoPersistedMongoClient INSTANCE;

    static {
        INSTANCE = new TodoPersistedMongoClient();
    }

    private final Jongo jongo;

    private TodoPersistedMongoClient() {
        String mongodbUrl = System.getProperty("MONGODB_ADDON_URI");

        MongoClientURI mongoClientURI = new MongoClientURI(mongodbUrl);
        DB db = null;

        try {
            db = new MongoClient(mongoClientURI).getDB(mongoClientURI.getDatabase());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        this.jongo = new Jongo(db);
    }

    public Jongo getJongo() {
        return jongo;
    }
}
