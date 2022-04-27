import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        ArrayList<Integer> list = new ArrayList<>();
        List<Integer> syncList = Collections.synchronizedList(list);
        int y = 1000;
        AtomicInteger y2 = new AtomicInteger(1000);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Runnable runnable = () -> {
            for (int i = 0; i < 100; i++) {
                y2.decrementAndGet();
            }
        };
        executorService.submit(runnable);
        executorService.submit(runnable);
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println(y2.get());


        String path = System.getProperty("user.dir") + File.separator + "newFile.txt";
        File file1 = new File(path);
        if (!file1.exists()) {
            try {
                boolean result = file1.createNewFile();
                System.out.println(result ? "Файл создан" : "Файл не создан");
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileOutputStream outputStream2 = null;
        try {
            inputStream = new FileInputStream(file1);
            byte[] mass = new byte[inputStream.available()];
            inputStream.read(mass);
            System.out.println(new String(mass, "UTF-8"));
            outputStream = new FileOutputStream(new File(System.getProperty("user.dir") + File.separator + "newFile2.txt"));
            outputStream.write(mass);

            String path2 = System.getProperty("user.dir") + File.separator + "testFolder";
            File file22 = new File(path2);
            file22.mkdir();
            File file2 = new File(path2 + File.separator + "newNEWFILE22.txt");

            FileOutputStream outputStream1 = new FileOutputStream(file2);
            //   outputStream1 = new FileOutputStream((new File(System.getProperty("user.dir") + File.separator + "testFolder" + File.separator + "newNEWFILE22.txt")));
            outputStream1.write(mass);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            outputStream.close();
            outputStream2.close();

        }


    }
}
