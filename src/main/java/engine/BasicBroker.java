package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * the basic broker class which store the common function of all three brokers
 * @author yalei
 *
 * @param <T>
 */
public class BasicBroker<T> implements Broker<T> {
	protected HashMap<String, ArrayList<Subscriber<T>>> subList;
	protected boolean ifOpen;
	protected ReentrantReadWriteLock lock;
	
	/**
	 * the constructor of basic broker
	 * just initiate the array list of subscribers and the boolean of ifOpen
	 */
	public BasicBroker() {
		this.subList = new HashMap<String, ArrayList<Subscriber<T>>>();
		this.lock = new ReentrantReadWriteLock();
		this.ifOpen = true;
	}
	
	/**
	 * basic broker publish method
	 * no comment, need to be implemented in the specific broker
	 */
	@Override
	public void publish(T item) {
		// TODO Auto-generated method stub	
	}

	/**
	 * add the subscrber into the subscriber list
	 */
	@Override
	public void subscribe(Subscriber<T> subscriber, String tag) {
		// TODO Auto-generated method stub
		lock.writeLock().lock();
		if(!subList.containsKey(tag)) {
			ArrayList<Subscriber<T>> list = new ArrayList<Subscriber<T>>();
			list.add(subscriber);
			subList.put(tag, list);
		}else {
			subList.get(tag).add(subscriber);
		}
		lock.writeLock().unlock();
	}

	/**
	 * shut down need to be initiated in the specific broker
	 */
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
	}

}
