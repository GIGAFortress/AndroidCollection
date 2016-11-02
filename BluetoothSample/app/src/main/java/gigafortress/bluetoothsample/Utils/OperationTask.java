package gigafortress.bluetoothsample.Utils;

public class OperationTask {

	private Thread mCurrenThread;
	private static OperationTask mOperationTask;
	
	public static OperationTask getInstance() {
		synchronized (OperationTask.class) {
			if (mOperationTask == null) {
				mOperationTask = new OperationTask();
			}
			return mOperationTask;
		}
	}
	
	public Thread getCurrenThread() {
		return mCurrenThread;
	}
	
	public void start(Runnable runnable) {
		mCurrenThread = new Thread(runnable);
		mCurrenThread.start();
	}
	//测试用
	public Thread newThreadTest(Runnable runnable) {
		mCurrenThread = new Thread(runnable);
		return mCurrenThread;
	}
}
