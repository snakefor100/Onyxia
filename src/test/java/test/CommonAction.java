package test;

/**
 * @author niuniu
 * @version 1.0.0
 * @date 2018/2/10
 * @since 1.0.0
 */
public class CommonAction <T>{
    public int doAction(ClientCallback<T> clientCallback){
        System.out.println(111);
        clientCallback.doCallback();
        return 1;
    }

}
