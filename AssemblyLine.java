import java.util.ArrayList;
import java.util.List;

public class AssemblyLine implements Runnable {
    List<Component> allComponents = new ArrayList<>();

    public AssemblyLine(List<Component> allComponents){
        this.allComponents = allComponents;
    }

    @Override
    public void run() {
        while(true){
            for(Component component: allComponents){
                component.productLock.lock();
            }
            try {

                for(Component component: allComponents){
                    while(component.currenCount != component.requiredAmount ){
                        component.enoughCondition.await();
                    }
                }

                Actions.startAssemblyLine();

                for(Component component: allComponents){
                    component.currenCount = 0;
                }
                Actions.assemble();

                for(Component component: allComponents){
                    component.produceCondition.signalAll();
                }
                Actions.stopAssemblyLine();


            }
            catch (InterruptedException e){
                e.printStackTrace();

            }
            finally {
                for (Component component : allComponents){
                    component.productLock.unlock();
                }

            }
        }

    }
}
