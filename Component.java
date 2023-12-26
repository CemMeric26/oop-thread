import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Component {

    public int requiredAmount;
    public int currenCount;

    public ProductType type;

    public Lock productLock;

    public Condition produceCondition;
    public Condition enoughCondition;

    public Component(int requiredAmount,ProductType type){
        this.requiredAmount = requiredAmount;
        this.currenCount = 0;
        this.productLock = new ReentrantLock();
        this.produceCondition = productLock.newCondition();
        this.enoughCondition = productLock.newCondition();
        this.type = type;

    }

}
