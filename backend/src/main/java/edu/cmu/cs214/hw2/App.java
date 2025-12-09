package edu.cmu.cs214.hw2;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import fi.iki.elonen.NanoHTTPD;

/**
 * Main application entry point for Santorini game
 */
public class App extends NanoHTTPD {
    public static void main(String[] args) {
        // TODO: Implement this method
    }
    private Game game;

    /**
     * Start the server at :8080 port.
     * @throws IOException if the port is already occupied
     */
    public App() throws IOException {
        super(8080);
        // TODO: Implement this method
    }
    @Override
    public Response serve(IHTTPSession session) {
        // TODO: Implement this method
        return null;
    }

}
