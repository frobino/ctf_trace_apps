package org.frobino.ctf2json;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.tracecompass.tmf.core.event.ITmfEvent;
import org.eclipse.tracecompass.tmf.core.event.ITmfEventField;
import org.eclipse.tracecompass.tmf.core.event.ITmfEventType;
import org.eclipse.tracecompass.tmf.core.timestamp.ITmfTimestamp;
import org.eclipse.tracecompass.tmf.core.timestamp.TmfTimestamp;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;

public class MyTmfEvent implements ITmfEvent{
	
	private String fName = "myEvent";
	
	public MyTmfEvent() {
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITmfEventField getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NonNull String getName() {
		return fName;
	}

	@Override
	public long getRank() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public @NonNull ITmfTimestamp getTimestamp() {
		// TODO Auto-generated method stub
		return new TmfTimestamp() {
			@Override
			public long getValue() {
				return 0;
			}
			
			@Override
			public int getScale() {
				return 0;
			}
		};
	}

	@Override
	public @NonNull ITmfTrace getTrace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITmfEventType getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
