public class EngineLine implements Runnable{
    Component engine;

    public EngineLine(Component engine){
        this.engine = engine;
    }


    @Override
    public void run() {
        while(true){
            // acquire the lock
            engine.productLock.lock();
            try{
                // check if enough
                while(engine.currenCount == engine.requiredAmount){
                    // wait for the can produce signal
                    engine.produceCondition.await();
                }
                // begining 0
                if(engine.currenCount == 0){
                    Actions.startEngineProductionLine();
                }
                // produce one
                engine.currenCount++;
                Actions.produceEngine();

                // enough engine
                if(engine.currenCount == engine.requiredAmount){
                    engine.enoughCondition.signalAll();
                    Actions.stopEngineProductionLine();
                }
                else { // wake others
                    engine.produceCondition.signalAll();
                }

            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            finally {
                // release the locks
                engine.productLock.unlock();
            }
        }
    }
}
