package workers.view;

import java.util.EventListener;

public interface WorkersFormAddListener extends EventListener {
	public void addWorker(WorkersTemp worker);
}

// formEventOccurred - hrv: obrazac doga�aja koji se dogodio

// extends EventListener jer je to prirodni Swing na�in za to jer je to temelj za sve listeners