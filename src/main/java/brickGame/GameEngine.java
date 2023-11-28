package brickGame;

/**
 * The GameEngine class manages the game loop, time, and actions in the game.
 */
public class GameEngine {
    private OnAction onAction;
    private int fps = 15;
    private Thread updateThread;
    public boolean isStopped = false;
    /**
     * Sets the OnAction listener for the game engine.
     *
     * @param onAction The OnAction listener to set.
     */
    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }
    /**
     * Sets the frames per second (FPS) for the game engine.
     *
     * @param fps The desired frames per second.
     */
    public void setFps(int fps) {
        this.fps = (int) 1000 / fps;
    }
    /**
     * Initiates a separate thread to run the game loop.
     * The onUpdate callback in the provided OnAction listener is invoked
     * during each update cycle, controlling the main logic of the game.
     */
    private synchronized void Update() {
        updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!updateThread.isInterrupted()) {
                    if (!isStopped){
                        try {
                            onAction.onUpdate();
                            Thread.sleep(fps);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        updateThread.start();
    }
    /**
     * Starts the game engine, initiating the game loop and time tracking.
     */
    public void start() {
        System.out.println("Engine Start");
        time = 0;
        Update();
        TimeStart();
        isStopped = false;
    }
    /**
     * Stops the game engine, pausing the game loop.
     */
    public void stop() {
        if (!isStopped) {
            isStopped = true;
        }
    }
    /**
     * Resumes the game engine, allowing the game loop to continue.
     */
    public void go(){
        if (isStopped){
            isStopped = false;
        }
    }
    private long time = 0;
    private Thread timeThread;
    /**
     * Starts a separate thread to track elapsed time in the game.
     * The onTime callback in the provided OnAction listener is invoked
     * every second with the updated elapsed time.
     */
    private void TimeStart() {
        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        if (!isStopped){
                            time++;
                            onAction.onTime(time);
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        timeThread.start();
    }
    /**
     * Interface for defining actions in the game engine.
     */
    public interface OnAction {
        /**
         * Called when the game engine is initialized.
         */
        void onInit();
        /**
         * Called during each update cycle of the game loop.
         */
        void onUpdate();
        /**
         * Called to update the elapsed time in the game.
         *
         * @param time The elapsed time in seconds.
         */
        void onTime(long time);
    }

}
