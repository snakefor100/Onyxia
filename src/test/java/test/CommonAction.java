package test;

/**
 * @author niuniu
 * @version 1.0.0
 * @since 1.0.0
 */
public class CommonAction <T>{
    public int doAction(ClientCallback<T> clientCallback){
        System.out.println(111);
        clientCallback.doCallback();
        return 1;
    }

}
