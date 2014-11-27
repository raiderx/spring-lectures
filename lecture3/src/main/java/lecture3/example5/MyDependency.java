package lecture3.example5;

public class MyDependency {

    public void foo(int value) {
        System.out.println("foo(int): " + value);
    }

    public void bar() {
        System.out.println("bar()");
    }
}
