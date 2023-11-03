package brickGame;


public class GameEngine {
    private OnAction onAction;
    private int fps = 15;
    private Thread updateThread;
    private Thread physicsThread;
    public boolean isStopped = false;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * @param fps set fps and we convert it to millisecond
     */
    public void setFps(int fps) {
        this.fps = (int) 1000 / fps;
    }

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

    private synchronized void PhysicsCalculation() {
        physicsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!physicsThread.isInterrupted()) {
                    if (!isStopped){
                        try {
                            onAction.onPhysicsUpdate();
                            Thread.sleep(fps);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        physicsThread.start();

    }

    public void start() {
        System.out.println("Engine Start");
        time = 0;
        Update();
        PhysicsCalculation();
        TimeStart();
        isStopped = false;
    }
    public void stop() {
        if (!isStopped) {
            isStopped = true;
        }
    }
    public void go(){
        if (isStopped){
            isStopped = false;
        }
    }

    private long time = 0;

    private Thread timeThread;

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

    public interface OnAction {
        void onUpdate();

        void onPhysicsUpdate();

        void onTime(long time);
    }

}
