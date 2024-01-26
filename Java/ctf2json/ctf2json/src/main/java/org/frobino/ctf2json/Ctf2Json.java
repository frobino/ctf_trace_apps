package org.frobino.ctf2json;

import java.util.Map;

import org.eclipse.tracecompass.ctf.core.CTFException;
import org.eclipse.tracecompass.ctf.core.event.IEventDefinition;
import org.eclipse.tracecompass.ctf.core.trace.CTFTrace;
import org.eclipse.tracecompass.ctf.core.trace.CTFTraceReader;
import org.eclipse.tracecompass.statesystem.core.ITmfStateSystemBuilder;
import org.eclipse.tracecompass.statesystem.core.StateSystemFactory;
import org.eclipse.tracecompass.statesystem.core.backend.IStateHistoryBackend;
import org.eclipse.tracecompass.statesystem.core.backend.StateHistoryBackendFactory;
import org.eclipse.tracecompass.tmf.ctf.core.event.CtfTmfEvent;
import org.eclipse.tracecompass.tmf.ctf.core.event.CtfTmfEventFactory;
import org.eclipse.tracecompass.tmf.ctf.core.trace.CtfTmfTrace;

public class Ctf2Json
{

  public static void main(String[] args) {

    //========================================
    //
    //      LOAD CTF AND PRINT EVENTS
    //
    //========================================
    
    final String tracePath = "../traces/processes";

    System.out.println("CBB Stream generated and stored at " + tracePath);

    System.out.println("LOADING TRACE...");

    try {


      CTFTrace trace = new CTFTrace(tracePath);

      System.out.println("TRACE LOADED!");

      Map<String, String> traceEnv = trace.getEnvironment();

      System.out.println();
      System.out.println("Environment variables:");

      for (String envVar : traceEnv.values()) {
        System.out.println(envVar);
      }

      System.out.println();
      System.out.println("---------------------------------------------------------------------------------");
      System.out.println("-----------------------------  READING EVENTS  ----------------------------------");
      System.out.println("---------------------------------------------------------------------------------");
      System.out.println();

      CTFTraceReader traceReader = new CTFTraceReader(trace);

      int counter = 0;

      while(traceReader.hasMoreEvents()) {

        System.out.println("Event " + counter + ":");
        System.out.println("Event name: " + traceReader.getCurrentEventDef().getDeclaration().getName());
        System.out.println("Timestamp: " + traceReader.getCurrentEventDef().getTimestamp());
        System.out.println("Events fields: " + traceReader.getCurrentEventDef().getFields());
        System.out.println();
        
        traceReader.advance();
        counter++;
      }
      // Close the reader
      traceReader.close();

      System.out.println();
      System.out.println("---------------------------------------------------------------------------------");
      System.out.println("-----------------------------  SP READING MY TMF EVENT   ------------------------");
      System.out.println("---------------------------------------------------------------------------------");
      System.out.println();

      // Create fake trace :( would like to create it linked to the real one!
      CtfTmfTrace tmfTrace = new CtfTmfTrace();
      // Configure state provider, including State System
      MyStateProvider sp = new MyStateProvider(tmfTrace);
      IStateHistoryBackend backend = StateHistoryBackendFactory.createInMemoryBackend("Test", 0L);
      ITmfStateSystemBuilder stateSystem = StateSystemFactory.newStateSystem(backend);
      sp.assignTargetStateSystem(stateSystem);
      // Create a fake event :( would like to get those from the real trace!
      MyTmfEvent myEvent = new MyTmfEvent();
      sp.myEventHandle(myEvent);
      
      System.out.println();
      System.out.println("---------------------------------------------------------------------------------");
      System.out.println("-----------------------------  SP READING EVENTS  -------------------------------");
      System.out.println("---------------------------------------------------------------------------------");
      System.out.println();

      CtfTmfEventFactory factory = CtfTmfEventFactory.instance();
      traceReader = new CTFTraceReader(trace);
      // Configure state provider, including State System
      sp = new MyStateProvider(tmfTrace);
      backend = StateHistoryBackendFactory.createInMemoryBackend("Test", 0L);
      stateSystem = StateSystemFactory.newStateSystem(backend);
      sp.assignTargetStateSystem(stateSystem);
      // Read events from real trace
      while (traceReader.hasMoreEvents()) {
		IEventDefinition def = traceReader.getCurrentEventDef();
		CtfTmfEvent e = factory.createEvent(tmfTrace, def, "");
		sp.myEventHandle(e);
		traceReader.advance();
      }
      // Close the reader
      traceReader.close();

      
      
      /*
      // Create a fake trace. Inspired from CtfTmfCpuAspectTest
      tmfTrace = new CtfTmfTrace();
      sp = new MyStateProvider(tmfTrace);
      List<IEventDefinition> events = new ArrayList<>();
      EventDeclaration dec = new EventDeclaration();
      events.add(new EventDefinition(dec, 3, 0, null, null, null, null, null, null));
      factory = CtfTmfEventFactory.instance();
      CtfTmfEvent e = factory.createEvent(tmfTrace, events.get(0), "");
      sp.eventHandle(e);
      */
      
      /*
      CtfTmfTrace tmfTrace = new CtfTmfTrace();
      CtfTmfEventFactory fa = tmfTrace.getEventFactory();
      
      CTFTraceReader traceReader2 = new CTFTraceReader(trace);
      CTFStreamInputReader ir = traceReader2.getTopStream();
      IEventDefinition eventDefinition = ir.getCurrentEvent();
      */
      
      // we need a ITmfEvent to give to the state provider
      /*
       * - CTFTmfEventFactory.createEvent(... IEventDefinition...) can create a ITmfEvent
       */
      
    } catch (CTFException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
