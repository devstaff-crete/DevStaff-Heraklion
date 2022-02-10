import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ImportDemo {
    public static void main(String... args) {
        List<String> list;
        ArrayList<String> al;

        Map<String, String> map = Map.of();
        Set<Integer> set = new ConcurrentSkipListSet<>();
        Lock lock = new ReentrantLock();
    }
}
