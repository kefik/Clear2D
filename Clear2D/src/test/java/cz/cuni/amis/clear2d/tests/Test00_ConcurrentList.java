package cz.cuni.amis.clear2d.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import cz.cuni.amis.clear2d.engine.collections.ConcurrentList;

public class Test00_ConcurrentList {
	
	private static AtomicLong totalOperations = new AtomicLong(0);

	private static class Worker implements Runnable {
		
		private String id;
		private List<Integer> list;
		private int operations;
		private Random random;
		private CyclicBarrier barrier;
		private boolean log;
		
		private long timeStart;
		
		private long listInvocations = 0;

		public Worker(String id, List<Integer> list, int operations, CyclicBarrier barrier, long randomSeed, boolean log) {
			this.id = id;
			this.list = list;
			this.operations = operations;
			this.barrier = barrier;
			random = new Random(randomSeed);
			this.log = log;
		}
		
		public void log(int oper, String msg) {
			if (log) logForce(oper, msg);
		}
		
		public void logForce(int oper, String msg) {
			System.out.println("[" + id + "/" + oper + "] " + msg);
		}
		
		@Override
		public void run() {
			try {
				log(0, "BARRIER AWAIT!");
				barrier.await();
			} catch (Exception e) {
				log(0, "INTERRUPTED!");
				return;
			}
			try {
				timeStart = System.currentTimeMillis();
				for (int i = 1; i <= operations; ++i) {
					switch (random.nextInt(5)) {
					case 0: {
						int size = list.size();
						if (size > 0) { 
							int index = random.nextInt(size);
							log(i, "remove(" + index + ") = " + list.remove(index));
							++listInvocations;
							break;
						} else {
							// DO NOT BREAK, add instead!
							//break;
						}					
					}
					
					case 1: {
						int size = list.size();
						if (size > 0) { 
							int next = random.nextInt();
							int index = random.nextInt(size);
							log(i, "add(" + index + ", " + next + ")");
							list.add(index, next);
							++listInvocations;
							break;
						} else {
							// DO NOT BREAK, add instead!
							//break;
						}						
					}
					
					case 2: {
						int next = random.nextInt();
						log(i, "add(" + next + ")");
						list.add(next);
						++listInvocations;
						break;
					}
					
					case 3: {					
						StringBuffer items = new StringBuffer();
						log(i, "iterator()...");
						Iterator<Integer> iterator = list.iterator();
						++listInvocations;
						boolean first = true;
						while (iterator.hasNext()) {
							if (first) first = false;
							else items.append(",");
							items.append(iterator.next());
							++listInvocations;
						}
						log(i, "iteration result: " + items.toString());
						break;
					}
					case 4: {					
						StringBuffer items = new StringBuffer();
						log(i, "for (Integer element : list)...");
						boolean first = true;
						for (Integer element : list) {
							if (first) first = false;
							else items.append(",");
							items.append(element);
							++listInvocations;
						}
						log(i, "for cycle result: " + items.toString());
						break;
					}
					}
				}
				logForce(operations, "TIME: " + listInvocations + " synced operations in " + (System.currentTimeMillis() - timeStart) + "ms");
				totalOperations.addAndGet(listInvocations);
			} catch (Exception e) {
				synchronized(list) {
					log(0, "EXCEPTION!");
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		
	}
	
	@Test
	public void test00() {
		totalOperations.set(0);
		
		int threadCount = 1;
		int operCount = 10000;
		boolean logOperations = false;
		
		System.out.println("Creating barrier(" + threadCount + ")...");
		CyclicBarrier barrier = new CyclicBarrier(threadCount);
		
		List<Integer> list = new ArrayList<Integer>();
		
		List<Thread> threads = new ArrayList<Thread>();
		
		System.out.println("Creating " + threadCount + " worker threads...");
		for (int i = 0; i < threadCount; ++i) {
			threads.add(new Thread(new Worker("W" + i, list, operCount, barrier, i, logOperations)));
		}
		
		System.out.println("Starting " + threadCount + " worker threads...");
		long timeStart = System.currentTimeMillis();
		
		for (Thread thread : threads) {
			thread.start();
		}
		
		System.out.println("Running...");
		
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.out.println("INTERRUPTED!");
				System.exit(1);
			}
		}
		
		long testTime = (System.currentTimeMillis() - timeStart);
		
		System.out.println("TOTAL: " + totalOperations.get() + " in " + testTime + "ms ~ " + ((double)testTime / (double)totalOperations.get()) + "ms per list operation...");
		System.out.println("---/// DONE ///---");
	}
	
	@Test
	public void test01() {
		totalOperations.set(0);
		
		int threadCount = 10;
		int operCount = 1000;
		boolean logOperations = false;
		
		System.out.println("Creating barrier(" + threadCount + ")...");
		CyclicBarrier barrier = new CyclicBarrier(threadCount);
		
		ConcurrentList<Integer> list = new ConcurrentList<Integer>();
		
		List<Thread> threads = new ArrayList<Thread>();
		
		System.out.println("Creating " + threadCount + " worker threads...");
		for (int i = 0; i < threadCount; ++i) {
			threads.add(new Thread(new Worker("W" + i, list, operCount, barrier, i, logOperations)));
		}
		
		System.out.println("Starting " + threadCount + " worker threads...");
		long timeStart = System.currentTimeMillis();
		
		for (Thread thread : threads) {
			thread.start();
		}
		
		System.out.println("Running...");
		
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.out.println("INTERRUPTED!");
				System.exit(1);
			}
		}
		
		long testTime = (System.currentTimeMillis() - timeStart);
		
		System.out.println("TOTAL: " + totalOperations.get() + " in " + testTime + "ms ~ " + ((double)testTime / (double)totalOperations.get()) + "ms per list operation...");
		System.out.println("---/// DONE ///---");
	}
	
	public static void main(String[] args) {
		new Test00_ConcurrentList().test01();
	}
	
}
