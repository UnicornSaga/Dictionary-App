package controller;

public class DAOManagerSingleton {
    public static ThreadLocal<DAOManager> instance;
    static {
        ThreadLocal<DAOManager> dm;
        try {
            dm = new ThreadLocal<DAOManager>() {
                @Override protected DAOManager initialValue() {
                    try {
                        return new DAOManager();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
            dm = null;
        }
        instance = dm;
    }
}