package fr.xebia.hello.database;

import org.jongo.Jongo;

public interface TodoMongoClient {
    Jongo getJongo();
}
