public class GlassLine implements Runnable{

    Component glass;

    public GlassLine(Component component){
        this.glass = component;
    }
    @Override
    public void run() {
        while(true){
            //acquire the lock
            glass.productLock.lock();
            try{
                // enough produced
                while(glass.currenCount == glass.requiredAmount){
                    glass.produceCondition.await();
                }
                // not started yet
                if(glass.currenCount == 0){
                    Actions.startGlassProductionLine();

                }
                Actions.produceGlass();
                glass.currenCount++;

                //now if its enough wake assembly if no  wake other produce threads
                if(glass.currenCount == glass.requiredAmount){
                    glass.enoughCondition.signalAll();
                    Actions.stopGlassProductionLine();
         
                }else{
                    glass.produceCondition.signalAll();
                }

            }catch (InterruptedException ex){
                ex.printStackTrace();
            }finally {
                //release the lock
                glass.productLock.unlock();
            }
        }

    }
}
