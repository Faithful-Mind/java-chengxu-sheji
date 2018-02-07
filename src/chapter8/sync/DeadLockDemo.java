package chapter8.sync;

class Worker {
    int id;

    public Worker(int id) {
        super();
        this.id = id;
    }

    synchronized void doTaskWithCooperator(Worker other) {
        try{Thread.sleep(500);}catch(Exception e) {e.printStackTrace();}
        synchronized(other) { // w1 需要等 w2锁释放，w2又在等 w1锁释放，所以互相等待形成死锁。
            System.out.println("doing" + id);
        }
    }
}
class DeadLockDemo{
    public static void main(String...strings) {
        Worker w1 = new Worker(1);
        Worker w2 = new Worker(2);
        Thread td1 = new Thread(() -> {
            w1.doTaskWithCooperator(w2);
        });
        Thread td2 = new Thread(() -> {
            w2.doTaskWithCooperator(w1);
        });
        td1.start();
        td2.start();        
    }
}