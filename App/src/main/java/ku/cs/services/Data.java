package ku.cs.services;

public class Data {
    private static Data data;

    public static Data getInstance() {
        if (data == null) data = new Data();
        return data;
    }


}
