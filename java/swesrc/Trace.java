#ifdef J2ME
#define JAVAME
#endif /* J2ME */
//#ifdef TRACE1
//#define TRACE0
//#endif /* TRACE1 */
//#ifdef TRACE0
package swisseph;

import java.io.PrintStream;

/**
* This class offers some static variables and methods for tracing the
* method calls and parameter contents of any called method in swisseph
* package. This is not suited multithreaded applications.
*/
public class Trace
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{


  static PrintStream out = System.err;


  // Change the output method to your likings here
  static void logText(String msg) {
    out.print(msg);
  }

  // Some logging methods: ///////////////////////////////////////////////////

  // How many spaces to insert before the trace log:
  static int level = 0;

  public static String fmtDbl(double val) {
    return cv.fmt("%23.16f", val) + "(" + dblToHex(val) + ")";
  }


  protected static void log(String method) {
    log(false, method);
  }
  protected static void log(boolean withMillis, String method) {
    for(int z = level; z > 0; z--) {
      logText(" ");
    }
    logTextNL((withMillis?(System.currentTimeMillis()+" "):"") + method);
  }

  public static void logDblArr(String name, double[] arr) {
    int len = SMath.max(0, 16 - name.length());
    String pad = "                                              ".substring(0,len);
    if (arr == null) {
      logText("    " + name + "[]: ");
      logTextNL("null");
    } else {
      for (int z = 0; z < arr.length; z++) {
        logText("    " + name + "[" + z + "]: ");
        logTextNL(pad + "/" + fmtDbl(arr[z]));
      }
    }
  }

  public static void logDblArr(String name, double[] arr, int offs, int cnt) {
    int len = SMath.max(0, 16 - name.length());
    String pad = "                                              ".substring(0,len);
    if (arr == null) {
      logText("    " + name + "[]: ");
      logTextNL("null");
    } else {
      for (int z = offs; z < arr.length && z < offs+cnt; z++) {
        logText("    " + name + "[" + (z-offs) + "]: ");
        logTextNL(pad + "/" + fmtDbl(arr[z]));
      }
    }
  }

  ////////////////////////////////////////////////////////////////////////////
  private static String dblToHex(double val) {
    long dbl = Double.doubleToLongBits(val);
    return cv.fmt("%.02X", (dbl & 0xff00000000000000L)>>>56) + " " +
           cv.fmt("%.02X", (dbl & 0x00ff000000000000L)>>48) + " " +
           cv.fmt("%.02X", (dbl & 0x0000ff0000000000L)>>40) + " " +
           cv.fmt("%.02X", (dbl & 0x000000ff00000000L)>>32) + " " +
           cv.fmt("%.02X", (dbl & 0x00000000ff000000L)>>24) + " " +
           cv.fmt("%.02X", (dbl & 0x0000000000ff0000L)>>16) + " " +
           cv.fmt("%.02X", (dbl & 0x000000000000ff00L)>> 8) + " " +
           cv.fmt("%.02X", (dbl & 0x00000000000000ffL));
  }


  static void logTextNL(String msg) {
    logText(msg + "\n");
  }

  static CFmt cv = new CFmt();
} // End of class Trace.
//#endif /* TRACE0 */
