public class WheelLine implements Runnable{

    Component wheel;

    public WheelLine(Component wheel){
        this.wheel = wheel;
    }

    @Override
    public void run() {
        while(true){
            //acquire the lock
            wheel.productLock.lock();
            try{
                // if enough produced wait next round
                while(wheel.currenCount == wheel.requiredAmount){
                    wheel.produceCondition.await();
                }
                // if not started
                if(wheel.currenCount==0){
                    Actions.startWheelProductionLine();
                }
                // produce
                Actions.produceWheel();
                wheel.currenCount++;

                //if now its enough
                if(wheel.currenCount == wheel.requiredAmount){
                    wheel.enoughCondition.signalAll();
                    Actions.stopWheelProductionLine();
                }else { // continue produce
                    wheel.produceCondition.signalAll();
                }

            }
            catch(InterruptedException ex){
                ex.printStackTrace();
            }
            finally{
                //release the lock
                wheel.productLock.unlock();
            }
        }

    }
}
