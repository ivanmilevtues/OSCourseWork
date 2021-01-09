public class HelloWorld {
    public String sayHi() {
        return "Hello, World!";
    }

    @Override
    public String toString() {
        return sayHi();
    }
}
