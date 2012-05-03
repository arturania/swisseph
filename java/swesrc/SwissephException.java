#ifdef J2ME
#define JAVAME
#endif /* J2ME */
package swisseph;

public class SwissephException extends RuntimeException
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{
  private double jdet=0;
  private int type=0;
  private int rc=0;

  /**
  * Default error code, if no other code had been specified.
  * Its current value is '0'.
  */
  public static final int UNDEFINED_ERROR               = 0;

  // FILE errors:
  /**
  * Any file error. Its current value is 1.
  */
  public static final int FILE_ERROR                    = 1;
  /**
  * Unspecified file error. Its current value is FILE_ERROR + 2.
  * @see #FILE_ERROR
  */
  public static final int UNSPECIFIED_FILE_ERROR        = FILE_ERROR | 2;
  /**
  * Error code for invalid filename. Its current value is FILE_ERROR + 4.
  * @see #FILE_ERROR
  */
  public static final int INVALID_FILE_NAME             = FILE_ERROR | 4;
  /**
  * Error code, when the file could not be found. Its current value is FILE_ERROR + 8.
  * @see #FILE_ERROR
  */
  public static final int FILE_NOT_FOUND                = FILE_ERROR | 8;
  /**
  * Error code, when the file could not be opened. Its current value is FILE_ERROR + 16.
  * @see #FILE_ERROR
  */
  public static final int FILE_OPEN_FAILED              = FILE_ERROR | 16;
  /**
  * Error code, when the file could not be read. Its current value is FILE_ERROR + 32.
  * @see #FILE_ERROR
  */
  public static final int FILE_READ_ERROR               = FILE_ERROR | 32;
  /**
  * Error code, when the file is damaged in any way, which normally means
  * that the content is not what it is expected to be. Its current value is FILE_ERROR + 64.
  * @see #FILE_ERROR
  */
  public static final int DAMAGED_FILE_ERROR            = FILE_ERROR | 64;
  /**
  * Error code, when the file is not a valid data file. Its current value
  * is FILE_ERROR + 128.
  * @see #FILE_ERROR
  */
  public static final int INVALID_FILE_ERROR            = FILE_ERROR | 128;

  // Parameter errors:
  /**
  * Error code, when a parameter is not valid. Its current value is 1024.
  */
  public static final int PARAM_ERROR                   = 1024;
  /**
  * Error code, when a calculation would be out of its supported time range.
  * Its current value is PARAM_ERROR + 2048.
  * @see #PARAM_ERROR
  */
  public static final int OUT_OF_TIME_RANGE             = PARAM_ERROR | 2048;
  /**
  * Error code, when the object is not supported or unknown.
  * Its current value is PARAM_ERROR + 4096.
  * @see #PARAM_ERROR
  */
  public static final int UNSUPPORTED_OBJECT            = PARAM_ERROR | 4096;
  /**
  * Error code, when the parameter combination is invalid.
  * Its current value is PARAM_ERROR + 8192.
  * @see #PARAM_ERROR
  */
  public static final int INVALID_PARAMETER_COMBINATION = PARAM_ERROR | 8192;
  /**
  * Error code, when the date is invalid
  * Its current value is PARAM_ERROR + 16384.
  * @see #PARAM_ERROR
  */
  public static final int INVALID_DATE                  = PARAM_ERROR | 16384;

  // User requested:
  /**
  * Error code, when a user requested limit has been reached.
  * Its current value is 262144.
  */
  public static final int USER_ERROR                    = 256*1024;
  /**
  * Error code, when a user requested time limit has been reached.
  * Its current value is 524288.
  * @see #USER_ERROR
  */
  public static final int BEYOND_USER_TIME_LIMIT        = USER_ERROR | 512*1024;



  public SwissephException(double jdet, int type, int rc, StringBuffer sb) {
    super(sb==null?null:sb.toString());
    this.jdet = jdet;
    this.type = type;
    this.rc = rc;
  }

  public SwissephException(double jdet, int type, int rc, String s) {
    super(s);
    this.jdet = jdet;
    this.type = type;
    this.rc = rc;
  }

  public SwissephException(double jdet, int type, String s) {
    super(s);
    this.jdet = jdet;
    this.type = type;
  }

  public SwissephException(double jdet, String s) {
    super(s);
    this.jdet = jdet;
    this.type = UNDEFINED_ERROR;
  }

  /**
  * Returns the julian day number as ET of the current process.
  * May return <i>Infinity</i>, if no date is available.
  */
  public double getJD() {
    return jdet;
  }

  /**
  * Returns error type.
  * @return error type
  * @see #UNDEFINED_ERROR
  * @see #FILE_ERROR
  * @see #UNSPECIFIED_FILE_ERROR
  * @see #INVALID_FILE_NAME
  * @see #FILE_NOT_FOUND
  * @see #FILE_OPEN_FAILED
  * @see #FILE_READ_ERROR
  * @see #DAMAGED_FILE_ERROR
  * @see #INVALID_FILE_ERROR
  * @see #PARAM_ERROR
  * @see #OUT_OF_TIME_RANGE
  * @see #UNSUPPORTED_OBJECT
  * @see #INVALID_PARAMETER_COMBINATION
  * @see #INVALID_DATE
  * @see #USER_ERROR
  * @see #BEYOND_USER_TIME_LIMIT
  */
  public int getType() {
    return type;
  }

  /**
  * Returns the return code from the underlying original
  * C-source code, which is SweConst.ERR normally. You should
  * not need to know this.
  * @return original error code
  */
  public int getRC() {
    return rc;
  }
}
