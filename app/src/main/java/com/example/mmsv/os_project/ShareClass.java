package com.example.mmsv.os_project;

import java.util.LinkedList;

/**
 * Created by pragya on 19/4/18.
 */

public class ShareClass {

    final int BUFFER_MAX = 5;
    LinkedList<String> buffer;

    ShareClass(){
        buffer = new LinkedList<String>();
    }

    public synchronized void produce(String element){

        while(buffer.size() == BUFFER_MAX){
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        buffer.offer(element);

        notifyAll();
    }

    public synchronized String consume(){

        while(buffer.size() == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        String element = buffer.poll();
        notifyAll();
        return element;
    }

}
