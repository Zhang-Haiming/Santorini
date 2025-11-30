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
        System.out.println("Starting Santorini Game...");
        try{
            new App();
        } catch (IOException e){
            System.err.println("Couldn't start server:\n" + e.getMessage());
        }
    }
    private Game game;

    /**
     * Start the server at :8080 port.
     * @throws IOException if the port is already occupied
     */
    public App() throws IOException {
        super(8080);
        this.game = new Game();
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("Server started on port 8080");
    }
    @Override
    public Response serve(IHTTPSession session) {
        Map<String, List<String>> params = session.getParameters();
        String uri = session.getUri();
        // if(this.game.getCurrentPhase()==TurnPhase.END_GAME){
        //     System.out.println("Game Over! Winner: Player " + this.game.getWinner().toString());
        //     GameState gameState=GameState.forGame(this.game);
        //     return newFixedLengthResponse(gameState.toString());
        // }
        
        if (uri.equals("/newgame")){
            System.out.println("Starting a new game...");
            this.game = new Game();
        } else if (uri.equals("/choosegod")){
            String godCard=params.get("god").get(0);
            System.out.println("Player " + (this.game.getCurrentPlayerIndex() + 1) + " choosing god card: " + godCard);
            this.game=this.game.chooseGodCard(godCard);
        } else if (uri.equals("/pass")){
            System.out.println("Player " + (this.game.getCurrentPlayerIndex() + 1) + " passing turn.");
            this.game=this.game.pass();
        }else if (uri.equals("/play")){
            int x=Integer.parseInt(params.get("x").get(0));
            int y=Integer.parseInt(params.get("y").get(0));
            switch (this.game.getCurrentPhase()){
                case TurnPhase.PLAYER1_CHOOSE_GODCARD:
                case TurnPhase.PLAYER2_CHOOSE_GODCARD:
                    // God card choosing is handled in /chooseGodCard endpoint
                    break;
                case TurnPhase.PLAYER1_INITIAL_WORKER1:
                    System.out.println("Player 1 placing initial worker 1 at (" + x + ", " + y + ")");
                    this.game=this.game.placeInitialWorker(0,0,x,y);
                    break;
                case TurnPhase.PLAYER1_INITIAL_WORKER2:
                    System.out.println("Player 1 placing initial worker 2 at (" + x + ", " + y + ")");
                    this.game=this.game.placeInitialWorker(0,1,x,y);
                    break;
                case TurnPhase.PLAYER2_INITIAL_WORKER1:
                    System.out.println("Player 2 placing initial worker 1 at (" + x + ", " + y + ")");
                    this.game=this.game.placeInitialWorker(1,0,x,y);
                    break;
                case TurnPhase.PLAYER2_INITIAL_WORKER2:
                    System.out.println("Player 2 placing initial worker 2 at (" + x + ", " + y + ")");
                    this.game=this.game.placeInitialWorker(1,1,x,y);
                    break;
                case TurnPhase.CHOOSE_WORKER:
                    System.out.println("Player " + (this.game.getCurrentPlayerIndex() + 1) + " choosing worker at (" + x + ", " + y + ")");
                    this.game=this.game.chooseWorker(x, y);
                    break;
                case TurnPhase.MOVE:
                    System.out.println("Player " + (this.game.getCurrentPlayerIndex() + 1) + " moving worker to (" + x + ", " + y + ")");
                    this.game=this.game.move(x, y);
                    break;
                case TurnPhase.BUILD:
                    System.out.println("Player " + (this.game.getCurrentPlayerIndex() + 1) + " building tower at (" + x + ", " + y + ")");
                    this.game=this.game.firstBuildTower(x, y);
                    break;
                case TurnPhase.SECOND_BUILD:
                    System.out.println("Player " + (this.game.getCurrentPlayerIndex() + 1) + " performing second build at (" + x + ", " + y + ")");
                    this.game=this.game.secondBuildTower(x, y);
                    break;
                case TurnPhase.END_GAME:
                    System.out.println("Game Over! Winner: Player " + this.game.getWinner().toString());
                    // this.game=this.game;
                    // Game is over, no further actions allowed
                    break;
            }
        } else {
            return newFixedLengthResponse("Unknown command");
        }
        GameState gameState=GameState.forGame(this.game);
        return newFixedLengthResponse(gameState.toString());
    }

}
