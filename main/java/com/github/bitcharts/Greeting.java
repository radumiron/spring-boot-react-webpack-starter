package com.github.bitcharts;

/**
 * Created by mironr on 10/28/2016.
 */
public class Greeting {
    private final long id;
    private final String content;

    public Greeting() {
        id = 0;
        content = null;
    }

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
