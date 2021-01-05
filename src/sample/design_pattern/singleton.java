package sample.design_pattern;

public class singleton {
    private static singleton single_instance = null;

    // variable of type String
    public String s;

    // private constructor restricted to this class itself
    private singleton()
    {
        s = "Hello I am a string part of Singleton class";
    }

    // static method to create instance of Singleton class
    public static singleton getInstance()
    {
        if (single_instance == null)
            single_instance = new singleton();

        return single_instance;
    }
}
