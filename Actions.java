import java.util.concurrent.ThreadLocalRandom;
import java.lang.Thread;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Actions{
    private static void action(int min_time,int max_time, String msg){
        assert min_time <= max_time;

        if(max_time>0){
            try{
                Thread.sleep(ThreadLocalRandom.current().nextInt(min_time, max_time + 1));
            }catch(InterruptedException e){ }
        }

        System.out.println(msg);
    }

    static synchronized void startEngineProductionLine(){
        action(100,200,"Started the Engine Production Line");
    }
    static synchronized void stopEngineProductionLine(){
        action(100,200,"Stopped the Engine Production Line");
    }
    static synchronized void produceEngine(){
        action(300,3000,"Produced an Engine");
    }


    static synchronized void startWheelProductionLine(){
        action(100,200,"Started the Wheel Production Line");
    }
    static synchronized void stopWheelProductionLine(){
        action(100,200,"Stopped the Wheel Production Line");
    }
    static synchronized void produceWheel(){
        action(100,1000,"Produced a Wheel");
    }


    static synchronized void startGlassProductionLine(){
        action(100,200,"Started the Glass Production Line");
    }
    static synchronized void stopGlassProductionLine(){
        action(100,200,"Stopped the Glass Production Line");
    }
    static synchronized void produceGlass(){
        action(250,700,"Produced a Glass");
    }


    static synchronized void startAssemblyLine(){
        action(100,200,"Started the Assembly Line");
    }
    static synchronized void stopAssemblyLine(){
        action(100,200,"Stopped the Assembly Line");
    }
    static synchronized void assemble(){
        action(2000,2500,"Assembled a Car");
    }


}
