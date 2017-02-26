package seryozha.hovhannisyan.synchronizers.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by Seryozha on 2/18/2017.
 */
public class Ferry {
    private static final CyclicBarrier BARRIER = new CyclicBarrier(3, new FerryBoat());
    //Инициализируем барьер на три потока и таском, который будет выполняться, когда
    //у барьера соберется три потока. После этого, они будут освобождены.

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 27; i++) {
            new Thread(new Car(i)).start();
            Thread.sleep(200);
        }
    }

    //Таск, который будет выполняться при достижении сторонами барьера
    public static class FerryBoat implements Runnable {

        public void run() {
            try {
                Thread.sleep(15000);
                System.out.println("FerryBoat Паром переправил автомобили!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Стороны, которые будут достигать барьера
    public static class Car implements Runnable {
        private int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }


        public void run() {
            try {
                System.out.printf("Start Автомобиль №%d подъехал к паромной переправе.\n", carNumber);
                //Для указания потоку о том что он достиг барьера, нужно вызвать метод await()
                //После этого данный поток блокируется, и ждет пока остальные стороны достигнут барьера
                BARRIER.await();
                System.out.printf("Done Автомобиль №%d продолжил движение.\n", carNumber);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}