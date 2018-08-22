import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 *
 * @author
 */
public class VrmsLogTest
{
    public static void addTest2(final List<? extends Number> l)
    {
        // l.add(1); // 编译报错
        // l.add(1.1); //编译报错
        l.add(null);
        l.get(0);
    }

    public static void addNumbers(final List<? super Integer> list)
    {
        for (int i = 1; i <= 10; i++)
        {
            list.add(i);
        }
    }

    @Test
    public void test01()
    {
        final List<Object> list1 = new ArrayList<>();
        addNumbers(list1);
    }

    // @Test
    // public void test() throws InterruptedException
    // {        // VrmsLog log = new VrmsLog();
    //     VrmsLog.operationLog("xiejieyi",
    //             "10.154.4.20",
    //             "edit user",
    //             "0",
    //             "edit user success");
    //     // log.operationLog("xiejieyi","128.0.0.1","add user", "success","");
    //     // new Thread(new Runnable() {
    //     //     @Override
    //     //     public void run() {
    //     //         new Timer().schedule(new TimerTask() {
    //     //             @Override
    //     //             public void run() {
    //     //
    //     //                 try
    //     //                 {
    //     //                     log.operationLog("xiejieyi","xiejieyi");
    //     //                 } catch (InterruptedException e)
    //     //                 {
    //     //                     e.printStackTrace();
    //     //                 }
    //     //             }
    //     //         },1,11);
    //     //     }
    //     // }).start();
    //     //
    //     // new Thread(new Runnable() {
    //     //     @Override
    //     //     public void run() {
    //     //         new Timer().schedule(new TimerTask() {
    //     //             @Override
    //     //             public void run() {
    //     //
    //     //                 try
    //     //                 {
    //     //                     log.operationLog("lihui","lihui");
    //     //                 } catch (InterruptedException e)
    //     //                 {
    //     //                     e.printStackTrace();
    //     //                 }
    //     //             }
    //     //         },4,8);
    //     //     }
    //     // }).start();
    //     //
    //     // while(true){
    //     //     Thread.sleep(1000);
    //     // }
    // }
}
