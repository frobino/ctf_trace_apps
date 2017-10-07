package org.frobino.ctf2json;

import java.util.Map;

import org.eclipse.tracecompass.ctf.core.CTFException;
import org.eclipse.tracecompass.ctf.core.trace.CTFTrace;
import org.eclipse.tracecompass.ctf.core.trace.CTFTraceReader;

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

    } catch (CTFException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
