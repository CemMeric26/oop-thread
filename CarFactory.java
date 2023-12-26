import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CarFactory {

    public static void main(String[] args){

        // create all three comp. add them to a list
        Component engine = new Component(1,ProductType.ENGINE);
        Component wheel = new Component(4,ProductType.WHEEL);
        Component glass = new Component(6, ProductType.GLASS);
        List<Component> allComponents = new ArrayList<>();
        allComponents.add(engine);allComponents.add(wheel);allComponents.add(glass);

        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.execute(new EngineLine(engine));
        executor.execute(new WheelLine(wheel));
        executor.execute(new GlassLine(glass));
        executor.execute(new AssemblyLine(allComponents));
    }
}
