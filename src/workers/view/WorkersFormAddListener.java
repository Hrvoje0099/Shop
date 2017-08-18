package workers.view;

import java.util.EventListener;

public interface WorkersFormAddListener extends EventListener {
	public void addWorker(WorkersTemp worker);
}