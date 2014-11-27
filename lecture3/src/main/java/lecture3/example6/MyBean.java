package lecture3.example6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("myBean")
public class MyBean {

    private MyDependency myDependency;

    public void execute() {
        myDependency.foo(100);
        myDependency.foo(101);
        myDependency.bar();
    }

    @Autowired
    public void setMyDependency(MyDependency myDependency) {
        this.myDependency = myDependency;
    }
}
