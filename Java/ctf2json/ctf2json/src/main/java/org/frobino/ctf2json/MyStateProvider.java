package org.frobino.ctf2json;

import org.eclipse.tracecompass.statesystem.core.ITmfStateSystemBuilder;
import org.eclipse.tracecompass.tmf.core.event.ITmfEvent;
import org.eclipse.tracecompass.tmf.core.statesystem.AbstractTmfStateProvider;
import org.eclipse.tracecompass.tmf.core.statesystem.ITmfStateProvider;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;

public class MyStateProvider extends AbstractTmfStateProvider{

	public MyStateProvider(ITmfTrace trace) {
		super(trace, "org.frobino.ctf2json.MyStateProvider");
	}

	@Override
	public ITmfStateProvider getNewInstance() {
		return new MyStateProvider(getTrace());
	}

	@Override
	public int getVersion() {
		return 0;
	}

	@Override
	protected void eventHandle(ITmfEvent event) {
		System.out.println("Handling event with name: " + event.getName() + " at time: " + event.getTimestamp());
		final ITmfStateSystemBuilder ssb = getStateSystemBuilder();
		int nameQuark = ssb.getQuarkAbsoluteAndAdd(event.getName());
		ssb.modifyAttribute(event.getTimestamp().toNanos(), 1, nameQuark);
		// Yee! We have put something in the SS!
	}

	// Make eventHandle visible (I am breaking the API, to avoid dragging in a entire analysis module)
	public void myEventHandle(ITmfEvent event) {
		this.eventHandle(event);
	}
	
}
