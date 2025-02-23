package by.it.marchenko.jd02_01.utility;

import by.it.marchenko.jd02_01.exception.StoreException;
import by.it.marchenko.jd02_01.services.StoreWorker;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import static by.it.marchenko.jd02_01.constants.StoreConstant.*;

public class CustomerChecker extends Thread {
    public static final int SCALE = 2;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private final StoreWorker storeWorker;

    public CustomerChecker(StoreWorker storeWorker) {
        this.storeWorker = storeWorker;
    }

    public void run() {
        Delayer delayer = new Delayer();
        int index = 0;
        LinkedList<Integer> currentList = new LinkedList<>();
        while (storeWorker.isAlive()) {
            delayer.performDelay(REAL_ONE_SECOND);
            int totalCustomerCount = storeWorker.getTotalCustomerCount();
            int currentCustomerCount = storeWorker.getCurrentCustomerCount();
            //noinspection unused
            String checkResult = String.format(
                    "Time: %3ds, Total customer count: %3d, Current customer count: %3d",
                    index++, totalCustomerCount, currentCustomerCount);
            currentList.addLast(currentCustomerCount);
            //System.out.println(checkResult);
        }
        printResult(currentList);
        String filePath = FilePathFinder.getFilePath("CustomerCheckerResult.txt");
        try (PrintWriter out = new PrintWriter(filePath)) {
            printResult(out, currentList);
        } catch (FileNotFoundException e) {
            throw new StoreException("File not found", e);
        }
    }

    private void printResult(LinkedList<Integer> list) {

        int timeIndex = 0;
        for (Integer element : list) {
            System.out.printf("%4d", element);
            if (++timeIndex % 30 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("-".repeat(list.size()));
    }

    private void printResult(PrintWriter out, LinkedList<Integer> list) {
        int maxElement = 0;
        for (Integer element : list) {
            if (maxElement < element) {
                maxElement = element;
            }
        }

        ArrayList<Integer> arrayList = new ArrayList<>(list);
        int width = list.size();
        int height = (int) Math.ceil((double) maxElement / SCALE);
        StringBuilder outString = new StringBuilder();
        for (int i = height; i > 0; i--) {
            for (int j = 0; j < width; j++) {
                String setColor = (j == WORK_TIME) ? ANSI_YELLOW : "";
                int checkVal = arrayList.get(j) / (SCALE * i);
                String appendString = 0 < checkVal ? "█" : " ";
                outString.append(appendString);
                System.out.print(setColor + appendString);
            }
            System.out.println(ANSI_RESET);
            outString.append("\n");
        }
        out.println(outString);
    }
}
