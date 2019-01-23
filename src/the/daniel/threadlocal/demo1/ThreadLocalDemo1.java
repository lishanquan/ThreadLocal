package the.daniel.threadlocal.demo1;

import java.util.Random;

/**
 * @Author: Daniel
 * @Date: 2019/1/23 11:12
 */
public class ThreadLocalDemo1 implements Runnable {

    private final static ThreadLocal<Student> STUDENT_THREAD_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) {
        ThreadLocalDemo1 tld = new ThreadLocalDemo1();
        Thread t1 = new Thread(tld, "ThreadLocalDemo3");
        Thread t2 = new Thread(tld, "b");

        t1.start();
        t2.start();
    }

    @Override
    public void run() {
        accessStudent();
    }

    private void accessStudent(){
        //currentThread
        String currentThreadName = Thread.currentThread().getName();
        System.out.println(currentThreadName + " is Running!");

        //generate ThreadLocalDemo3 random and print
        Random random = new Random();
        int age = random.nextInt(100);
        System.out.println("thread " + currentThreadName + " set age to " + age);

        //set Student age
        Student student = getStudent();
        student.setAge(age);
        System.out.println("thread " + currentThreadName + " first read age is " + student.getAge());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("thread " + currentThreadName + " second read age is " + student.getAge());
    }

    private Student getStudent(){
        //get Student from threadLocal
        Student student = STUDENT_THREAD_LOCAL.get();

        //init student
        if (student == null){
            student = new Student();
            STUDENT_THREAD_LOCAL.set(student);
        }
        return student;
    }

}
