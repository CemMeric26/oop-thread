public class DoorLine implements Runnable{
    Component door;

    public DoorLine(Component door){this.door = door;}

    @Override
    public void run() {
        while(true){
            //acquire the lock
            door.productLock.lock();
            try {
                // check enough
                while (door.currenCount == door.requiredAmount){
                    // wait for the can produce cndition
                    door.produceCondition.await();
                }

                // if zero start the line
                if(door.currenCount == 0){
                    Actions.startEngineProductionLine();
                }
                Actions.produceGlass();
                door.currenCount++;

                //check if its enough amount
                if(door.currenCount == door.requiredAmount){
                    door.enoughCondition.signalAll();
                    Actions.stopGlassProductionLine();
                }else{
                    door.produceCondition.signalAll();
                }


            }catch (InterruptedException ex){
                ex.printStackTrace();
            }finally {
                door.productLock.unlock();
            }
        }
    }
}
