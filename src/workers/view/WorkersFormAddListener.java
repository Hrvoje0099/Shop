package workers.view;

import java.util.EventListener;

public interface WorkersFormAddListener extends EventListener {
	public void addWorker(WorkersTemp worker);
}

// formEventOccurred - hrv: obrazac dogaðaja koji se dogodio

// extends EventListener jer je to prirodni Swing naèin za to jer je to temelj za sve listeners