package com.util;

import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * session监听器
 * @author 冯志宇 2018/5/3
 */
@WebListener
@Component
public class MySessionListener implements HttpSessionListener {
    public static HashMap<String,HttpSession> sessionMap = new HashMap();

    /**
     * 添加session，session的id作为key，session对象作为value，
     * session过期之后，value就会变为null，
     * 所以要清理所以value为null的键值对
     */
    public static void addSession(HttpSession session) {
        if (session != null) {
            //添加方法保持线程安全，放在同步代码块中
            synchronized (MySessionListener.class){
                sessionMap.put(session.getId(), session);
            }
        }
    }

    /**
     * 手动删除session
     * */
    public static synchronized void delSession(HttpSession session) {
        if (session != null) {
            sessionMap.remove(session.getId());
        }
    }

    /**
     * 根据session_id取得session对象
     * */
    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null)
            return null;
        return (HttpSession) sessionMap.get(session_id);
    }

    /**
     * 清除过期的session键值对，即value变为null
     * */
    public static void removeNullValue(HashMap<String,HttpSession> map){
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            Object key = (Object) iterator.next();
            Object value =(Object)map.get(key);
            remove(value, iterator);
        }
    }

    /**
     * Iterator 是工作在一个独立的线程中，并且拥有一个 mutex 锁。
     * Iterator 被创建之后会建立一个指向原来对象的单链索引表，当原来的对象数量发生变化时，这个索引表的内容不会同步改变，
     * 所以当索引指针往后移动的时候就找不到要迭代的对象，所以按照 fail-fast 原则 Iterator 会马上抛出 java.util.ConcurrentModificationException 异常。
     * 所以 Iterator 在工作的时候是不允许被迭代的对象被改变的。
     * 但你可以使用 Iterator 本身的方法 remove() 来删除对象， Iterator.remove() 方法会在删除当前迭代对象的同时维护索引的一致性。
     * @param obj
     * @param iterator
     */
    private static void remove(Object obj,Iterator iterator){
        if(obj==null){
            iterator.remove();
        }
    }

    /**
     * 开启一个新的线程，用来清理sessionMap中value为null的对象
     */
    private void clearSessionMap(){
        //启用另一个线程，开始清理sessionmap，把value为空的键值对删掉
        ExecutorService executor = Executors.newCachedThreadPool();
        FutureTask<String> future = new FutureTask<String>(new Callable<String>() {
            public String call() throws Exception{ //建议抛出异常
                try {
                    removeNullValue(sessionMap);
                    System.out.println("异步清理sessionMap完成");
                    return "OK";
                }
                catch(Exception e) {
                    throw new Exception("Callable terminated with Exception!"); // call方法可以抛出异常
                }
            }
        });
        executor.execute(future);
    }

    /**
     * 创建session事件，客户端首次访问
     * 将创建的session保存在sessionMap中
     * @param httpSessionEvent
     */
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println("session被创建");
        /**
         * 设置session的有效时间为
         */
        HttpSession session = httpSessionEvent.getSession();
        session.setMaxInactiveInterval(7200);
        addSession(session);
    }

    /**
     * session销毁
     * @param httpSessionEvent
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("session被销毁");
        HttpSession session = httpSessionEvent.getSession();
        delSession(session);
        clearSessionMap();
    }
}
