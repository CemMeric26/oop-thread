import java.util.ArrayList;
import java.util.List;

public class AssembleLine implements Runnable{
    List<Component> allComponents = new ArrayList<>();

    public AssembleLine(List<Component> comps){
        this.allComponents = comps;
    }

    @Override
    public void run() {
        while (true){
            //acquire all the locks
            for(Component component: allComponents){
                component.productLock.lock();
            }

            try{
                // check enough to assemble
                for (Component c: allComponents){
                    while(c.currenCount != c.requiredAmount){
                        c.enoughCondition.await();
                    }
                }

                // then start assemble
                Actions.startAssemblyLine();

                for (Component c: allComponents){
                    c.currenCount = 0;
                }

                Actions.assemble();

                // signal others to produce
                for (Component c: allComponents){
                    c.produceCondition.signalAll();
                }
                Actions.stopAssemblyLine();


            }catch (InterruptedException ex){
                ex.printStackTrace();
            }finally {
                // release all the locks
                for(Component component: allComponents){
                    component.productLock.unlock();
                }
            }
        }

    }
}
