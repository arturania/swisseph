import java.io.*;
import java.util.*;
import java.security.GeneralSecurityException;

/**
* A simple class to precompile Java source code similar to C precompilation.<p>
* The idea behind this is in having an option to generate similar sources
* out of one code base. So you can comment / uncomment different parts of
* the code via precompile switches in the source files or as arguments to
* this class (-D).<p>
* Syntax:<br>
* <blockquote>
* <code>
* java Precompile [-q] [-f] -i<InputDir> [-i<InputDir> ...] [-o<OutputDir> [-o<OutputDir> ...]] {-D<definedParameter>...}<br>
* java Precompile -l -i<InputDir> [-i<InputDir> ...]
* </code>
* </blockquote>
* In the first mode, you give this class at least one directory as parameter,
* this is the directory, where the input source files reside (-i). Optionally,
* you specify with the -o switch, where Precompile.java should put the
* precompiled source files. If the output directory does already exist, and
* it is not empty, Precompile will stop. Use the -f switch, to force
* overwriting existing files in the output directory.<br>
* Optionally, you give any amount of <i>defines</i> with
* <code>-D<i>define-name</i> -D<i>define-name</i> ...</code> as additional
* parameters.<p>
* In the second mode, Precompile will list (-l or --list) all used define
* switches in all the source files only.
* Supported precompile switches are:<br>
* <blockquote>
*  <ul>
*  <li><code>#define &lt;value&gt;</code><br>
* <blockquote>
* Only to define a switch for evaluation by <code>#ifdef</code> or
* <code>#ifndef</code>. You can also #define values from the command line
* by the -D switch. Locally defined switches are restricted to the file in
* which they occur.
* </blockquote>
*  <li><code>#undef &lt;value&gt;</code><br>
*  <li><code>#undefine &lt;value&gt;</code><br>
* <blockquote>
* Only to undefine a previously set switch. #undefine is also restricted to
* the file in which it appears.
* </blockquote>
*  <li><code>#if &lt;value&gt; ... #else ... #endif</code><br>
* <blockquote>
* This can be <i>#if 0</i> (==false) or <i>#if 1</i> (or any
* other Integer != 0: ==true). Otherwise it is interpreted identical to
* <i>#ifdef &lt;value&gt;</i>.
* </blockquote>
* <li><code>#ifdef &lt;value&gt; ... #else ... #endif</code><br>
* <blockquote>
* True, if &lt;value&gt; is defined prior to this line of code, false otherwise.
* </blockquote>
* <li><code>#ifndef &lt;value&gt; ... #else ... #endif</code><br>
* <blockquote>
* Equal to <code>#ifdef</code>, but with reversed logic.
* </blockquote>
*  </ul>
* </blockquote>
*/
public class Precompile {
  String[] inputFiles=null;
  String[] outputFiles=null;
  Vector vDefines=new Vector();
  Vector vGlDefines;             // A backup copy of the globally defined
                                 // switches.
  Vector vLevels=new Vector();
  Vector vWarnings=new Vector();
  String fname;                  // Current filename
  long lineCnt;
  Vector outDir=new Vector();
  Vector inDir=new Vector();
  boolean listDefines = false;
  boolean silent = false;
  boolean force = false;
  boolean cleanEmptyfiles = false;

  static final String syntax = "Syntax:\n" +
              "java Precompile [-q] [-f] [-c] -i<InputDir> [-i<InputDir> ...]\n" +
              "                [-o<OutputDir> [-o<OutputDir> ...]]\n" +
              "                {-D<definedParameter>...}\n" +
              " -q   quiet mode, don't output progress\n" +
              " -f   force mode, overwrite output files\n" +
              " -c   clean mode, remove empty resulting files\n" +
              " -i<InputDir> read files form input directory.\n" +
              "      You may have multiple -i parameters.\n" +
              " -o<OutputDir> output files to directory.\n" +
              "      Files are put in a directory derived from the package\n" +
              "      name by default. If you use the -o option, they have\n" +
              "      to equal the amount of -i options.\n" +
              " -D<definedParameter> defines a parameter to be true or set.\n" +
              "      You may have any count -D switches.\n\n" +
              "java Precompile -l -i<InputDir> [-i<InputDir> ...]\n" +
              " -l   list all switches from the Java files in <InputDir>.\n" +
              " -i<InputDir> read files form input directory.\n" +
              "      You may have multiple -i parameters.\n\n" +
              "java Precompile -h\n" +
              " -h   list this help. You may use -help or --help as well.\n";

static final String shortSyntax =
              "java Precompile [-h|-help|--help]\n" +
              "java Precompile [-q] [-f] [-c] -i<InputDir> [-i<InputDir> ...]\n" +
              "                [-o<OutputDir> [-o<OutputDir> ...]]\n"+
              "                {-D<definedParameter>...}\n"+
              "java Precompile -l -i<InputDir> [-i<InputDir> ...]";

// Error handling is a little limited so far!!!
  /**
  * This is the routine to precompile all *.java files in a given directory.
  * It takes directories as input parameter: in which directories do we look
  * for the source files. Optionally, you can say, into which directory to put
  * the precompiled sources. By default, Precompile.java will parse the input
  * file for a package statement, and output the file to that directory.<br>
  * @param inputDirs In which directories are the java source files? ALL Java
  * source files in these directories will be precompiled.
  * @param outputDirs In which directories to put the precompiled source files.
  * They do not have to exist, they will be created automatically if needed.<br>
  * This parameter may be null or empty. In this case, the method will try
  * to determine the output directory by parsing the input file for a
  * package statement. This method is not absolutely fool prove, but should
  * work in most cases, where the package statement is not wrapped up in
  * some unusual context.
  * @param force A boolean to force overwriting the contents of any existing
  * output directories before writing to them. If force is false, and an
  * output directory is not empty, a GeneralSecurityException is thrown.
  * @param switches Any &lt;DEFINES&gt; to be used as precompile options.
  * Can be null if none should be applied.
  * @return A String array with warnings. Each warning String consists of three
  * parts, first the filename, then the lineNumber and then the error message.
  * All three parts are separated by '\n'.
  */
  public String[] precompile(String[] inputDirs,
                             String[] outputDirs,
                             boolean force,
                             String[] switches)
      throws GeneralSecurityException,
             IOException {
    // Put all parameters into an array like the array of parameters
    // when running Precompile.java via the main() method:
    int len=0;
    if (switches!=null) { len=switches.length; }
    int cnt=inputDirs.length+(outputDirs==null?0:outputDirs.length);
    String[] sw=new String[len+cnt];
    int n=0;
    for (int i=0; i<inputDirs.length; i++) {
      sw[n]="-i" + inputDirs[i];
      n++;
    }
    if (outputDirs!=null && outputDirs.length>0) {
      for (int i=0; i<outputDirs.length; i++) {
        sw[n+i]="-o" + outputDirs[i];
        n++;
      }
    }
    for(int i=0;i<len;i++) {
      sw[n+i]="-D"+switches[i];
    }

    int ret = readParams(sw);
    if (ret != 0) {
      for(int dirs=0; dirs<inDir.size(); dirs++) {
        // Writes a file list to the global arrays inputFiles and outputFiles:
        getFiles(dirs);
        for(int i=0; i<inputFiles.length; i++) {
          fname=inputFiles[i];
          lineCnt=0;
          precompileFile(inputFiles[i],
                         outputFiles[i]);
          if (cleanEmptyfiles && new File(outputFiles[i]).length() == 0) {
            new File(outputFiles[i]).delete();
          }
        }
      }
      String[] aWarnings=new String[vWarnings.size()];
      for(int i=0; i<vWarnings.size(); i++) {
        aWarnings[i]=(String)vWarnings.elementAt(i);
      }
      return aWarnings;
    } else {
      return null;
    }
  }

  /**
  * You can run the class stand alone via this method. As parameters, you
  * have to specify at least one directory, optional are further input
  * directories, output directories, and any number of switches for
  * precompilation.<p>
  * Parameters in detail:
  * <blockquote>
  * <ul>
  * <li><code>-i &lt;directory&gt;</code> The directory containing the input
  * source files. All *.java files in this directory will be precompiled. You
  * can give multiple directories with <code>-idir1 -idir2 -idir3 ...</code>.
  * <li><code>-o &lt;directory&gt;</code> (Optional) The directory to where
  * the precompiled source files will be written. You can give multiple
  * directories in the same way as it is explained above for the -i switch.<br>
  * If you don't give output directories, Precompile.java will try to find
  * a "package" statement in the source file, and use the package name as the
  * output directory for that file. The method of finding the package name should
  * be rather robust, but in case it fails, this is, you don't find the resulting
  * file in the correct directory, specify the output directories with the -o
  * switch(es). Also, if you prefer to put the output files into another directory,
  * than the package name implies, you have to use -o&nbsp;.
  * <li><code>-q</code> (Optional) Quiet mode. Don't output anything except for
  * errors.
  * <li><code>-f</code> (Optional) Force overwriting existing files in the
  * output directories. Precompile.java will not overwrite contents in the
  * output directory by default.<br>
  * When using Precompile.java without -o switch, you might first want to test
  * without -f to see, if Precompile.java outputs to the directories you think.
  * In following precompiles, you can then add the -f switch.
  * <li><code>-D&lt;switch&gt;</code> (Optional) Any switch to be used for
  * precompilation. You can have any number of -D options.
  * </ul>
  * </blockquote>
  * @param params An array of Strings. See above for a more detailed
  * explanation of possible parameters.
  */
  public static void main(String[] params) {
    Precompile p=new Precompile();
    try {
      if (p.readParams(params) == 0) { System.exit(0); }
    } catch (IllegalArgumentException ia) {
      System.err.println(ia.getMessage());
      System.exit(1);
    }
    if (p.listDefines) {
      p.listDefineSwitches(p.inDir);
    } else {
      for(int dirs=0; dirs<p.inDir.size(); dirs++) {
        // Writes a file list to the global arrays inputFiles and outputFiles:
        p.getFiles(dirs);
        for(int i=0; i<p.inputFiles.length; i++) {
          p.fname=p.inputFiles[i];
          p.lineCnt=0;
          p.precompileFile(p.inputFiles[i],
                           p.outputFiles[i]);
          if (p.cleanEmptyfiles && new File(p.outputFiles[i]).length() == 0) {
            new File(p.outputFiles[i]).delete();
          }
          for(int j=0; j<p.vWarnings.size(); j++) {
            p.printWarning((String)p.vWarnings.elementAt(j));
          }
          p.vWarnings.removeAllElements();
        }
      }
    }
  }

  // Only for the main() method...
  void printWarning(String w) {
    int idx1=w.indexOf("\n");
    int idx2=w.indexOf("\n",idx1+1);
    String file=w.substring(0,idx1);
    String line=w.substring(idx1+1,idx2);
    System.err.println(file+" ("+line+"): "+w.substring(idx2+1));
  }




  int readParams(String[] p) {
    boolean err=false;
    int i=0;
    for(; i<p.length; i++) {
      if (p[i].equals("-h") ||
          p[i].equals("-help") ||
          p[i].equals("--help")) {
        System.out.println(syntax);
        return 0;
      } else if (p[i].startsWith("-D")) {
        String def;
        if (p[i].length()>2) { def=p[i].substring(2);
        } else if (i+1<p.length) { def=p[++i];
        } else { err=true; break;
        }
        vDefines.addElement(def);
      } else if (p[i].startsWith("-o")) {
        if (p[i].length()>2) { outDir.addElement(p[i].substring(2));
        } else if (i+1<p.length) { outDir.addElement(p[++i]);
        } else { err=true; break;
        }
      } else if (p[i].startsWith("-i")) {
        if (p[i].length()>2) { inDir.addElement(p[i].substring(2));
        } else if (i+1<p.length) { inDir.addElement(p[++i]);
        } else { err=true; break;
        }
      } else if (p[i].equals("--list")) {
        listDefines = true;
      } else {
        if (p[i].charAt(0) != '-') {
          err=true; break;
        }
        for(int n = 1; n < p[i].length(); n++) {
          if (p[i].charAt(n) == 'f') {
            force = true;
          } else if (p[i].charAt(n) == 'q') {
            silent = true;
          } else if (p[i].charAt(n) == 'c') {
            cleanEmptyfiles = true;
          } else if (p[i].charAt(n) == 'l') {
            listDefines = true;
          } else if (p[i].charAt(n) == 'h') {
            System.out.println(syntax);
            return 0;
          } else {
            err=true; break;
          }
        }
        if (err) { break; }
      }
    }
    if (err) {
      throw new IllegalArgumentException(
              "Unknown parameter or missing value for " +
              "parameter '"+p[i]+"'.\n" + shortSyntax);
    }
    if (listDefines && inDir.size() == 0) {
      throw new IllegalArgumentException(
              "Missing parameter -i.\nSyntax:\n" + shortSyntax);
    }
    if (listDefines && outDir.size() != 0) {
      System.err.println("Ignoring parameter -o in list mode.\n" +
              "Syntax:\n" + shortSyntax);
    }
    if (listDefines && vDefines.size() != 0) {
      System.err.println("Ignoring parameter" + (vDefines.size()==1?"":"s") +
              " -D... in list mode.\nSyntax:\n" + shortSyntax);
    }
    if (!listDefines && inDir.size() == 0) {
      throw new IllegalArgumentException(
              "Missing parameter -i\nSyntax:\n" + shortSyntax);
    }
    if (!listDefines && inDir.size() != outDir.size() && outDir.size() != 0) {
      throw new IllegalArgumentException(
              "Number of input directories ("+inDir.size()+") and "+
              "number of output directories ("+outDir.size()+
              ")\nhave to match, or don't specify output directories!");
    }
    vGlDefines=(Vector)vDefines.clone();
    return 1;
  }

  void getFiles(int index) {
    String in = (String)(inDir.elementAt(index));
    String out = null;
    if (outDir != null && outDir.size() > 0) {
      out = (String)(outDir.elementAt(index));
      if (inDir.equals(out)) {
        throw new IllegalArgumentException(
                           "Input directory and output directory have to "+
                           "be different ('"+inDir+"')!");
      }
    }

    File fInDir=new File(in);
    if (!fInDir.isDirectory()) {
      throw new IllegalArgumentException("Directory '"+inDir+"' not found.");
    }
    if (!listDefines && out != null) {
      File fOutDir=new File(out);
      if (fOutDir.exists() && !fOutDir.isDirectory()) {
        throw new IllegalArgumentException(
                                     "Cannot create directory '"+out+"'.");
      } else if (!fOutDir.exists() && !fOutDir.mkdirs()) {
        throw new IllegalArgumentException(
                                     "Cannot create directory '"+out+"'.");
      }
    }

    inputFiles = fInDir.list(new JavaFilter());
    outputFiles = fInDir.list(new JavaFilter());
    if (inputFiles.length<1) {
      throw new IllegalArgumentException(
                         "No source files (\""+inDir+File.separator+
                         "*.java\") found.");
    }
    // Add the path to the file names:
    for(int i=0; i<inputFiles.length; i++) {
      inputFiles[i] = in + File.separator + inputFiles[i];
    }

    // Add the path to the output file names.
    // If no output directory is given, read them from each files'
    // package name:
    if (!listDefines && out == null) {
      for(int k=0; k<outputFiles.length; k++) {
        String pkgDir = getPackageDir(in + File.separator + outputFiles[k]);
        File fOutDir=new File(pkgDir);
        if (fOutDir.exists() && !fOutDir.isDirectory()) {
          throw new IllegalArgumentException(
                                     "Cannot create directory '"+out+"'.");
        } else if (!fOutDir.exists() && !fOutDir.mkdirs()) {
          throw new IllegalArgumentException(
                                       "Cannot create directory '"+out+"'.");
        }
        outputFiles[k] = pkgDir + File.separator + outputFiles[k];
        if (!force && new File(outputFiles[k]).exists()) {
          System.err.println("Output file '" + outputFiles[k] + "' exists.\n" +
                             "Remove file or use option '-f' to force overwriting!\n" +
                             "Aborting...\n");
          System.exit(1);
        }
      }
    } else {
      for(int k=0; k<outputFiles.length; k++) {
        outputFiles[k] = out+ File.separator + outputFiles[k];
      }
    }

    // Clean up output directory
    if (force) {
      for(int k=0; k<outputFiles.length; k++) {
//System.out.println("Deleting "+outputFiles[k]);
        new File(outputFiles[k]).delete();
      }
    }
  }

  String getPackageDir(String file) {
    // Strip comments '//' or '/*' until a '*/' and empty lines
    // Next line has to be a package statement, as far as I know. If not,
    // -> no package, so we return "."
    int lineCnt = 0;
    BufferedReader fIn=null;
    try {
      fIn=new BufferedReader(new FileReader(file));
      boolean inCComment = false;
      boolean pkgNameInNextLine = false;
      String line;
      while((line=fIn.readLine())!=null) {
        lineCnt++;
        // We don't have to care about comment strings in string constants in
        // the moment, as the package statement has to occur before any string
        // constants, as far as I understand...
        int idxJComment = line.indexOf("//");
        int idxCComment = line.indexOf("/*");
        int idxCCommentEnd = line.indexOf("*/");
        int idxPrecompileSwitch = line.indexOf("#");

        // End of C-comment is ALWAYS(!) ending a C comment:
        if (inCComment && idxCCommentEnd >= 0) {
          line = line.substring(0,idxCComment+1);
          inCComment = false;
        }
        if (inCComment) {
          continue;
        }

        // After that, a Java comment ALWAYS strips the rest of line:
        if (idxJComment >= 0) {
          line = line.substring(0,idxJComment);
        }

        // Don't care about #... precompile options in this state.
        // Should we care???
        if (idxPrecompileSwitch >= 0) {
          continue;
        }

        // Now look for a C style comment beginning:
        if (idxCComment >= 0 && line.length() > idxCComment) {
          line = line.substring(0,idxCComment);
          inCComment = true;
        }
        line = line.trim();
        if ("".equals(line)) { continue; }
        if (!pkgNameInNextLine && !line.startsWith("package")) {
          line = "";
          break;
        }
        if (!pkgNameInNextLine) {
          line = line.substring("package".length()).trim();
        }
        if ("".equals(line)) {
          pkgNameInNextLine = true;
          continue;
        }
        break;
      }

      if (!"".equals(line)) {
        // Strip trailing ';' from the package name:
        if (line.endsWith(";")) {
          line = line.substring(0,line.length()-1).trim();
        }

        // Now convert the package name to a file system path:
        int idxDot = 0;
        while((idxDot = line.indexOf('.')) >= 0) {
          line = line.substring(0, idxDot) + File.separator + line.substring(idxDot+1);
        }
        try { fIn.close(); } catch (Exception e2) { }
        return line;
      }

    } catch (IOException io) {
      vWarnings.addElement(file+"\n"+lineCnt+"\nIOException: "+io);
    }
    try { fIn.close(); } catch (Exception e2) { }

    return ".";
  }

Vector vDefNames = new Vector();

  void listDefineSwitches(Vector inDir) {
    BufferedReader fIn=null;
    for(int dirs=0; dirs<inDir.size(); dirs++) {
      String in = (String)(inDir.elementAt(dirs));
      File fInDir=new File(in);
      if (!fInDir.isDirectory()) {
        throw new IllegalArgumentException("Directory '"+inDir+"' not found.");
      }
      String[] files=fInDir.list(new JavaFilter());
      if (files.length<1) {
        throw new IllegalArgumentException(
                       "No source files (\""+inDir+File.separator+
                       "*.java\") found.");
      }
      for(int i=0; i<files.length; i++) {
        try {
          fIn=new BufferedReader(new FileReader(in+File.separator+files[i]));
          boolean found;
          String line;
          while((line=fIn.readLine())!=null) {
            if (line.startsWith("//#if") || line.startsWith("#if")) {
              int idx = line.indexOf(" ");
              String define = line.substring(idx).trim();
              idx = define.indexOf(" ");
              if (idx > 0) {
                define = define.substring(0,idx).trim();
              }
              found = false;
              for (int j = 0; j < vDefNames.size(); j++) {
                if (((String)vDefNames.elementAt(j)).equals(define)) {
                  found = true;
                  break;
                }
              }
              if (!found && !"0".equals(define) && !"1".equals(define)) {
                vDefNames.addElement(define);
              }
            }
          }
          fIn.close();
        } catch (IOException io) {
          try { fIn.close(); } catch (Exception e2) { }
          vWarnings.addElement(fname+"\n"+lineCnt+"\nIOException: "+io);
        }
      }
    }
    for (int i = 0; i < vDefNames.size(); i++) {
      System.out.println(vDefNames.elementAt(i));
    }
  }

  void precompileFile(String fNameIn, String fNameOut) {
    if (!silent) {
      System.out.println("Processing " + fNameIn + " to " + fNameOut);
    }
    BufferedReader fIn=null;
    BufferedWriter fOut=null;

    // Initialize the #defines to the state of calling the precompile routine:
    vDefines=(Vector)vGlDefines.clone();

    try {
      fIn=new BufferedReader(new FileReader(fNameIn));
      fOut=new BufferedWriter(new FileWriter(fNameOut));
      String line="";
      StringTokenizer tk=null;
      Boolean write=new Boolean(true);
      int max=0;
      int lastmax=0;
      boolean update=false;

      while((line=fIn.readLine())!=null) {
        line.trim();
        lineCnt++;
        lastmax=max;
        max=vLevels.size()-1;
        if ((max>=0 && lastmax!=max) || update) {
          update=false;
          write=new Boolean(true);
          for (int i=0; i<=max; i++) {
            if (vLevels.elementAt(i).toString().equals("false")) {
              write=new Boolean(false);
              break;
            }
          }
        } else if (lastmax!=max) {
          write=new Boolean(true);
        }
        if (line.startsWith("//#")) { // Allow for both //#... and #... 
          line = line.substring(2);
        }
        if (line.startsWith("#")) {
          line = "#" + line.substring(1).trim();
          tk=new StringTokenizer(line);
          String cmd=tk.nextToken();
          if (cmd.startsWith("#if")) {
            update=true;
            boolean b=evaluate(cmd, tk, write.booleanValue());
            write=new Boolean(b);
            vLevels.addElement(write);
          } else if (cmd.equals("#endif")) {
            if (max<0) {
              vWarnings.addElement(fname+"\n"+lineCnt+"\n#endif found "+
                                   "without previous #if or #ifdef or "+
                                   "#ifndef.");
            } else {
              vLevels.removeElementAt(max);
            }
          } else if (cmd.equals("#else")) {
            if (max<0) {
              vWarnings.addElement(fname+"\n"+lineCnt+"\n#else found "+
                                   "without previous #if or #ifdef or "+
                                   "#ifndef.");
            } else {
              write=new Boolean(!(write.booleanValue()));
              vLevels.setElementAt(write, max);
              update=true;
            }
          } else if (cmd.equals("#define") && write.booleanValue()) {
            try {
              vDefines.addElement(tk.nextToken());
            } catch (NoSuchElementException nex) {
              vWarnings.addElement(fname+"\n"+lineCnt+"\n#define found "+
                                   "without any value.");
            }
          } else if (cmd.equals("#undef") && write.booleanValue()) {
            try {
              String val=tk.nextToken();
              if (!vDefines.removeElement(val)) {
// It's ok, if you #undefine without having a #define before...
//                vWarnings.addElement(fname+"\n"+lineCnt+"\n#undef "+val+
//                                     " found without "+val+" being previously "+
//                                     "defined.");
              }
            } catch (NoSuchElementException nex) {
              vWarnings.addElement(fname+"\n"+lineCnt+"\n#undef found "+
                                   "without any value.");
            }
          } else if (cmd.equals("#undefine") && write.booleanValue()) {
            try {
              String val=tk.nextToken();
              if (!vDefines.removeElement(val)) {
// It's ok, if you #undefine without having a #define before...
//                vWarnings.addElement(fname+"\n"+lineCnt+"\n#undefine "+val+
//                                     " found without "+val+" being previously "+
//                                     "defined.");
              }
            } catch (NoSuchElementException nex) {
              vWarnings.addElement(fname+"\n"+lineCnt+"\n#undefine found "+
                                   "without any value.");
            }
          } else if (write.booleanValue()) {
            vWarnings.addElement(fname+"\n"+lineCnt+"\nUnknown directive '"+
                                 cmd+"' found.");
          }
        } else if (write.booleanValue()) {
          fOut.write(line+"\n");
        }
      }
      fIn.close();
      fOut.close();
    } catch (IOException i) {
      try { fIn.close(); } catch (Exception e2) { }
      try { fOut.close(); } catch (Exception e3) { }
      vWarnings.addElement(fname+"\n"+lineCnt+"\nIOException: "+i);
    }
    int miss=vLevels.size();
    if (miss>0) {
      vWarnings.addElement(fname+"\n"+lineCnt+"\nMissing "+miss+" '#endif' "+
                           "statement"+(miss>1?"s":"")+" on end of file!");
      vLevels.removeAllElements();
    }
  }

  boolean evaluate(String cmd, StringTokenizer tk, boolean w) {
    String name;
    if (cmd.equals("#if")) {
      if (!w) {
        return false;
      } else if (!tk.hasMoreTokens()) {
        vWarnings.addElement(fname+"\n"+lineCnt+"\n#if found without any "+
                             "value.");
        return false;
      }
      String val=tk.nextToken();
      try {
        return (Integer.parseInt(val)!=0);
      } catch (NumberFormatException nfe) {
        name=val;
      }
    } else {
      name=tk.nextToken();
    }
    boolean found=false;
    for(int i=0; i<vDefines.size(); i++) {
      found=found || ((String)vDefines.elementAt(i)).equals(name);
    }
    if (cmd.equals("#ifdef") ||
        cmd.equals("#if")) { // The logic for #if is not identical to the
                               // logic of #ifdef in C, but here and in this
                               // case, it is...
       return found;
    } else if (cmd.equals("#ifndef")) {
       return !found;
    } else {
       return false;
    }
  }
}



class JavaFilter implements FilenameFilter {
  public boolean accept(File dir, String name) {
     return name.endsWith(".java");
  }
}
