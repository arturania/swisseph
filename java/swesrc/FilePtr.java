#ifdef J2ME
#define JAVAME
#endif /* J2ME */
#ifndef JAVAME
/*
   This is a port of the Swiss Ephemeris Free Edition, Version 2.00.00
   of Astrodienst AG, Switzerland from the original C Code to Java. For
   copyright see the original copyright notices below and additional
   copyright notes in the file named LICENSE, or - if this file is not
   available - the copyright notes at http://www.astro.ch/swisseph/ and
   following. 

   For any questions or comments regarding this port to Java, you should
   ONLY contact me and not Astrodienst, as the Astrodienst AG is not involved
   in this port in any way.

   Thomas Mack, mack@ifis.cs.tu-bs.de, 23rd of April 2001

   The code base for the use of the java.nio package is from Aaron Hunter,
   who does not impose any additional license restrictions.

   Thomas Mack, mack@ifis.cs.tu-bs.de, 25th of June 2007
*/
/* Copyright (C) 1997 - 2008 Astrodienst AG, Switzerland.  All rights reserved.

  License conditions
  ------------------

  This file is part of Swiss Ephemeris.

  Swiss Ephemeris is distributed with NO WARRANTY OF ANY KIND.  No author
  or distributor accepts any responsibility for the consequences of using it,
  or for whether it serves any particular purpose or works at all, unless he
  or she says so in writing.

  Swiss Ephemeris is made available by its authors under a dual licensing
  system. The software developer, who uses any part of Swiss Ephemeris
  in his or her software, must choose between one of the two license models,
  which are
  a) GNU public license version 2 or later
  b) Swiss Ephemeris Professional License

  The choice must be made before the software developer distributes software
  containing parts of Swiss Ephemeris to others, and before any public
  service using the developed software is activated.

  If the developer choses the GNU GPL software license, he or she must fulfill
  the conditions of that license, which includes the obligation to place his
  or her whole software project under the GNU GPL or a compatible license.
  See http://www.gnu.org/licenses/old-licenses/gpl-2.0.html

  If the developer choses the Swiss Ephemeris Professional license,
  he must follow the instructions as found in http://www.astro.com/swisseph/
  and purchase the Swiss Ephemeris Professional Edition from Astrodienst
  and sign the corresponding license contract.

  The License grants you the right to use, copy, modify and redistribute
  Swiss Ephemeris, but only under certain conditions described in the License.
  Among other things, the License requires that the copyright notices and
  this notice be preserved on all copies.

  Authors of the Swiss Ephemeris: Dieter Koch and Alois Treindl

  The authors of Swiss Ephemeris have no control or influence over any of
  the derived works, i.e. over software or services created by other
  programmers which use Swiss Ephemeris functions.

  The names of the authors or of the copyright holder (Astrodienst) must not
  be used for promoting any software, product or service which uses or contains
  the Swiss Ephemeris. This copyright notice is the ONLY place where the
  names of the authors can legally appear, except in cases where they have
  given special permission in writing.

  The trademarks 'Swiss Ephemeris' and 'Swiss Ephemeris inside' may be used
  for promoting such software, products or services.
*/
package swisseph;

import java.io.*;
import java.net.*;
#ifdef NIO
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.BufferOverflowException;
import java.nio.channels.FileChannel;
#endif /* NIO */


/**
* This class is meant to be a wrapper to some read functionality of the
* RandomAccessFile class. It adds the ability to read (and search and
* seek) in files using http://... access. This is needed to allow applets
#ifdef NO_JPL
* read access to files, in this case the Swiss Ephemeris data files.
#else
* read access to files, in this case the Swiss Ephemeris and JPL data
* files.
#endif /* NO_JPL */
*/
public class FilePtr
#ifndef JAVAME
		implements java.io.Serializable
#endif /* JAVAME */
		{
  public static final String useragent = "swisseph-java-" + SwephData.SE_JAVA_VERSION;

  //Allocate a 1K buffer for line reading
  private static final int STRING_BUFFER_SIZE = 200;

  private static final int MAX_FAILURES = 100;

  transient RandomAccessFile fp;
#ifdef NIO
  transient FileChannel fc;
  private transient MappedByteBuffer mbb = null;
  private transient CharBuffer cb = null;
#endif /* NIO */
  transient Socket sk;
  transient InputStream is;
  transient BufferedOutputStream os;
  String fnamp;
  private long fpos = 0;
  private String host;
  private int port;
  private int BUFSIZE;
  // Holds max. 1 BUFSIZE byte chunks of read data: startidx, endidx, data:
  private long startIdx = -1;  // long, as it holds the file pointer pos.
  private long endIdx = -1;
  private byte[] data;
  private byte inbuf[];
  private long savedLength = -1;

  private boolean bigendian = true;

  private boolean useHTTP;

  /**
  * Creates a new FilePtr instance. Well, the parameters are rather
  * &quot;funny&quot; for now, but there were reasons for it. I will
  * change it later (hopefully)...<br>
  * If you do not need to read randomly and you have access to the file
  * directly, you should use the BufferedInputStream etc. -classes, as
  * they are MUCH faster than the RandomAccessFile class that is used
  * here.
  */
  public FilePtr(RandomAccessFile fp,
                 Socket sk,
                 InputStream is,
                 BufferedOutputStream os,
                 String fnamp,
                 long fileLength,
                 int bufsize) throws IOException {
    this.fp = fp;
    this.sk = sk;
    this.is = is;
    this.os = os;
    this.fnamp = fnamp;
    this.savedLength = fileLength;
    this.BUFSIZE = bufsize;
    useHTTP = (fnamp.startsWith("http://"));
    data = new byte[BUFSIZE];
    inbuf = new byte[BUFSIZE];
    if (useHTTP && fp == null) { // RandomAccessFile, try file access via http:
#ifdef NIO
      fc = null;
#endif /* NIO */
      try {
        URL u = new URL(fnamp);
        host = u.getHost();
        port = u.getPort();
        if (port<0) { port = 80; } // Default port for http...
      } catch ( MalformedURLException me) {
        throw new IOException("Malformed URL '"+fnamp+"'");
      }
#ifdef NIO
    } else {
      fc = fp.getChannel();
      mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, length());
      cb = CharBuffer.allocate( STRING_BUFFER_SIZE );
#endif /* NIO */
    }
  }


  public void setBigendian(boolean bigendian) {
#ifdef NIO
    if (fc != null) {
      mbb.order(bigendian?ByteOrder.BIG_ENDIAN:ByteOrder.LITTLE_ENDIAN);
    } else {
      this.bigendian = bigendian;
    }
#else
    this.bigendian = bigendian;
#endif /* NIO */
  }


  /**
  * Reads one (signed) byte.
  * @return One signed 8 bit byte.
  * @throws IOException if an I/O error occurs.
  * @throws EOFException if the end of file is reached <i>before</i> the
  * byte could be read.
  */
  public byte readByte() throws IOException, EOFException {
#ifdef NIO
    if (fc != null) {
      return mbb.get();
    }

#endif /* NIO */
    if (startIdx<0 || fpos<startIdx || fpos>endIdx) {
      readToBuffer();
    }
    fpos++;
    return data[(int)(fpos-1-startIdx)];
  }

  /**
  * Reads one <i>unsigned</i> byte.
  * @return One unsigned 8 bit byte as an integer.
  * @throws IOException if an I/O error occurs.
  * @throws EOFException if the end of file is reached <i>before</i> the
  * byte could be read.
  */
  public int readUnsignedByte() throws IOException, EOFException {
    return ((int)readByte()) & 0xff;
  }


  /**
  * Reads a (signed) short value. This is a two byte read (16 bits) with
  * highest byte first.
  * @return A signed 2 byte (16 bit) value as a short.
  * @throws IOException if an I/O error occurs.
  * @throws EOFException if the end of file is reached before the
  * 2 bytes could be read completely.
  */
  public short readShort() throws IOException, EOFException {
#ifdef NIO
    if (fc != null) {
      return mbb.getShort();
    }

#endif /* NIO */
    if (bigendian) {
      return (short)((readByte()<<8)+readUnsignedByte());
    }
    return (short)(readUnsignedByte()+(readByte()<<8));
  }

  /**
  * Reads a (signed) integer value. This is a four byte read (32 bits) with
  * highest bytes first.
  * @return A signed 4 byte (32 bit) value as an integer.
  * @throws IOException if an I/O error occurs.
  * @throws EOFException if the end of file is reached before the
  * 4 bytes could be read completely.
  */
  public int readInt() throws IOException, EOFException {
#ifdef NIO
    if (fc != null) {
      return mbb.getInt();
    }

#endif /* NIO */
    if (bigendian) {
      return (((int)readByte())<<24)+
             (((int)readUnsignedByte())<<16)+
             (((int)readUnsignedByte())<<8)+
             (int)readUnsignedByte();
    }
    return (int)readUnsignedByte()+
           (((int)readUnsignedByte())<<8)+
           (((int)readUnsignedByte())<<16)+
           (((int)readByte())<<24);
  }

  /**
  * Reads a double value. This is an 8 byte read (64 bits) with highest
  * bytes first.
  * @return A double value.
  * @throws IOException if an I/O error occurs.
  * @throws EOFException if the end of file is reached before the
  * 8 bytes could be read completely.
  */
  public double readDouble() throws IOException, EOFException {
#ifdef NIO
    if (fc != null) {
      return mbb.getDouble();
    }

#endif /* NIO */
    long ldb = (bigendian?
                   (
                       (((long)readUnsignedByte())<<56)+
                       (((long)readUnsignedByte())<<48)+
                       (((long)readUnsignedByte())<<40)+
                       (((long)readUnsignedByte())<<32)+
                       (((long)readUnsignedByte())<<24)+
                       (((long)readUnsignedByte())<<16)+
                       (((long)readUnsignedByte())<<8)+
                       (long)readUnsignedByte()
                   ):
                   (
                       (long)readUnsignedByte()+
                       (((long)readUnsignedByte())<<8)+
                       (((long)readUnsignedByte())<<16)+
                       (((long)readUnsignedByte())<<24)+
                       (((long)readUnsignedByte())<<32)+
                       (((long)readUnsignedByte())<<40)+
                       (((long)readUnsignedByte())<<48)+
                       (((long)readUnsignedByte())<<56)
                   )
               );
    return Double.longBitsToDouble(ldb);
  }


  /**
  * Reads a complete line from the file. The line is seen to end on a
  * newline character ('\n') or on an end of file. The returned String
  * will include the newline character if any.
  * 
  * @return A String containing a complete line. The line may be 0 characters
  * long.
  * @throws IOException if an I/O error occurs.
  * @throws EOFException if the end of file is reached before even one
  * character (byte) could be read.
  */
  public String readLine() throws IOException, EOFException {
#ifdef NIO
    if (fc != null) {
      cb.clear();

      char ch;
      while (true) {
        try {
          while ((ch=(char)readUnsignedByte() ) != '\n') {
            cb.put( ch );
          }
          cb.put( ch );
          break;
        } catch (BufferOverflowException boe) {
System.err.println("Re-allocating CharBuffer to size of " + (2 * cb.capacity()) + " characters.");
          cb.flip();
          String lineStart = cb.toString();
          cb = CharBuffer.allocate(cb.capacity() * 2);
          cb.clear();
          cb.put(lineStart);
        }
      }
      cb.flip();
      return cb.toString();
    }

#endif /* NIO */
    StringBuffer sout = new StringBuffer(200);
    try {
      char ch;
      while ((ch = (char)readUnsignedByte()) != '\n') {
        sout.append(ch);
      }
      sout.append(ch);
    } catch (EOFException e) {
      if (sout.length() == 0) {
        throw e;
      }
    }

    return sout.toString();
  }

  /**
  * Closes the connection to the file. This includes any Streams as well
  * as the Socket and the RandomAccessFile from the constructor.
  * @throws IOException if an I/O error occurs.
  */
  public void close() throws IOException {
    try {
      fnamp = "";
      if (fp != null) { fp.close(); }
      fp = null;
      if (sk != null) { sk.close(); }
      sk = null;
      is = null;
      os = null;
    } catch (IOException ie) {
      try {
        if (sk != null) { sk.close(); }
        sk = null;
        is = null;
        os = null;
      } catch (IOException ies) {
        throw ies;
      }
      throw ie;
    }
  }

  /**
  * Returns the current position of the file pointer.
  * @return the current position of the file pointer.
  */
  public long getFilePointer() {
#ifdef NIO
    if (fc != null) {
      return mbb.position();
    }

#endif /* NIO */
    return fpos;
  }

  /**
  * Returns the length of the file.
  * @return the length of the file in bytes.
  * @throws IOException if an I/O error occurs.
  */
  public long length() throws IOException {
#ifdef NIO
    if (fc != null) {
      return fc.size();
    }

#endif /* NIO */
    if (fp != null && savedLength < 0) { savedLength = fp.length(); }
    if (fp != null || savedLength >= 0 || !useHTTP) {
      return savedLength;
    }

    long len = 0;
    String s = "HEAD " + fnamp + " HTTP/1.1\r\n"+
             "User-Agent: " + useragent + "\r\n"+
             "Host: " + host + ":" + port + "\r\n\r\n";
    String sout = "";
    int failures = 0;
    int rc = 0;
    while (true) {
      try {
        URLwrite(os,s);
        sout = URLread(is);
      } catch (IOException ioe) {
        if (++failures >= MAX_FAILURES) {
          throw new IOException("(java.net.SocketException) "+ioe.getMessage());
        }
        reconnect();
        continue;
      }
      rc = checkHeader(sout);
      if (rc<0) { // What has happened? Invalid header?
        if (++failures >= MAX_FAILURES) {
          throw new IOException("Failed to read a valid / complete header.");
        }
        reconnect();
        continue;
      }
      break;
    }
    int idx = sout.indexOf("Content-Length:");
    if (rc == 200 && sout.indexOf("Content-Length:") >= 0) {
      sout = sout.substring(idx+"Content-Length:".length());
      sout = sout.substring(0,sout.indexOf("\n")).trim();
      len = Long.parseLong(sout);
    } else {
      throw new IOException("Can't determine length of (HTTP-)file '"+fnamp+
                            "'. HTTP error code: "+rc);
    }
    return len;
  }

  /**
  * Positions the file pointer.
  * @param pos the new position in the file. The position is seen zero based.
  */
  public void seek(long pos) {
#ifdef NIO
    if (fc != null) {
      mbb.position((int)pos);
    }

#endif /* NIO */
    fpos = pos;
  }

// RFC 2068: Response = Status-Line
//                      * ( general-header
//                        | response-header
//                        | entity-header )
//                     CRLF
//                     [ message-body ]
//
// Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
//
// Status-Code should (has to?) be 206
//


  private String URLread(InputStream is) throws IOException {
    StringBuffer sret = new StringBuffer(BUFSIZE);
    int av = is.read();
    if (av == -1) {
      throw new IOException("No bytes available.");
    }
    sret.append((char)av);
    while (is.available()>0) {
      sret.append((char)is.read());
    }
    return sret.toString();
  }

  private void URLwrite(BufferedOutputStream os, String s) throws IOException {
    for(int n = 0; n < s.length(); n++) {
      os.write((byte)s.charAt(n));
    }
    os.flush();
  }

  // Returns the data part of the html response in String s
  private String htmlStrip(String s) {
    int idx = s.indexOf("\r\n\r\n");
    if (idx >= 0) {
      return s.substring(idx+4);
    }
    return "";
  }

  // Returns the http return code or -1, if not available
  private int checkHeader(String s) {
    try {
      int ix1 = s.indexOf(" ");
      int ix2 = s.indexOf(" ",ix1+1);
      if (ix1 < 0 || ix2 < 0 || ix1 < 8 || ix1+4 != ix2) { return -1; }
      return Integer.parseInt(s.substring(ix1+1,ix2));
    } catch (NumberFormatException nf) {
      return -1;
    }
  }


  // Reads a chunk of data to the buffer data[]
  private void readToBuffer() throws IOException, EOFException {
#ifndef NIO
    // Directly reading a file:
    if (fp != null) {
      fp.seek(fpos);
      int cnt = fp.read(inbuf);
// Probably, RandomAccessFile.read(byte[n]) performes n read operations???
      if (cnt == -1) {
        throw new EOFException("Filepointer position " + fpos + " exceeds file"+
                               " length by " + (fpos-length()+1) + " byte(s).");
      }
      for(int n = 0; n < cnt; n++) {
        data[n] = inbuf[n];
      } 
      startIdx = fpos;
      endIdx = fpos+cnt-1;
      return;
    }

#endif /* NIO */
    if (fpos >= length()) {
      throw new EOFException("Filepointer position " + fpos + " exceeds file " +
                             "length by " + (fpos-length()+1) + " byte(s).");
    }
    if (!useHTTP) {
      return;
    }

    // Reading via http:
    String s = "GET " + fnamp + " HTTP/1.1\r\n" +
             "User-Agent: " + useragent + "\r\n" +
             "Host: " + host + ":" + port + "\r\n" +
             "Range: bytes=" + fpos + "-" +
                                SMath.min(length()-1, fpos+BUFSIZE-1) + "\r\n\r\n";
    String sout = "";
    int slen = 0;
    int failures = 0;
    while (true) {
      try {
        URLwrite(os,s);
        sout = URLread(is);
      } catch (IOException ioe) {
        if (++failures >= MAX_FAILURES) {
          throw new IOException("(java.net.SocketException) "+ioe.getMessage());
        }
        reconnect();
        continue;
      }
      int rc = checkHeader(sout);
      if (rc<0) { // What has happened?
        if (++failures >= MAX_FAILURES) { // Too many failures in a row, abort:
          throw new IOException("Failed to read successfully from address\n'"+
                                fnamp+"'. The http reply from the server was "+
                                sout.length()+
                                " bytes long and it's content is:\n\n"+sout);
        }
        continue;
      }
      sout = htmlStrip(sout); // keep data part of http response only
      slen = sout.length();
      if ((rc != 200 /* OK */ && rc != 206 /* Partial content */) ||
          slen > BUFSIZE ||
          (slen < BUFSIZE && savedLength >= 0 && fpos+slen != savedLength)) {
        if (++failures >= MAX_FAILURES) {
          throw new IOException("HTTP read failed with HTTP response "+rc+
                                ". Read "+slen+" bytes, requested "+BUFSIZE+
                                " bytes.");
        }
        continue;
      }
      if (slen == 0) { // How is this to happen???
        if (++failures >= MAX_FAILURES) {
          throw new EOFException("Filepointer position "+fpos+" exceeds file "+
                                 "length by "+(fpos-length()+1)+" byte(s).");
        }
        continue;
      }
      break;
    }
    startIdx = fpos;
    endIdx = fpos + slen - 1;
    for(int n = 0; n < slen; n++) {
      data[n] = (byte)sout.charAt(n);
    }
  }

  private void reconnect() throws IOException {
System.err.println("reconnecting...");
    sk.close();
    sk = new Socket(host,port);
    sk.setSoTimeout(5000);
    is = sk.getInputStream();
    os = new BufferedOutputStream(sk.getOutputStream());
  }
}
#endif /* JAVAME */
