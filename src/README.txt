How to build the SWISSEPH demo programs for Windows:

The Microsoft Visual C 5.0 Workspace swep.dsw contains
all projects.
The project files *.dsp are in src/projects

1 Character mode executables
============================

1.1 swetest.exe (32-bit)
------------------------

files required:

swetest.c
sweph.c
swephlib.c
swejpl.c
swemmoon.c
swemplan.c
swedate.c

include files:
swemptab.c
sweph.h
swephexp.h
swephlib.h
swejpl.h
sweodef.h

define macro:

DOS_DEGREE
DOS32


2 DLLs
=======
2.1 swedll32.dll (32-bit)
-------------------------

files required:

swedllst.c
swephlib.c
swejpl.c
sweph.c
swemmoon.c
swemplan.c
swemptab.c
swedate.c
swehouse.c

sweph.h
swephexp.h
swephlib.h
swejpl.h
housasp.h
sweodef.h

define macros:
MAKE_DLL


3 Character mode executables that need a DLL
============================================
swete32.exe
files required:

swetest.c

swephexp.h
swedll.h
sweodef.h

define macros:

USE_DLL 
USE_DLL
DOS32
DOS_DEGREE


4 Windows applications
======================
4.2 (32-bit) with linked Swisseph library
-----------------------------------------

files required:

swewin.c
swephlib.c
swejpl.c
sweph.c
swemmoon.c
swemplan.c
swemptab.c
swedate.c
swehouse.c
d2l.c
csec.c

swewin.h
sweph.h
swephexp.h
swephlib.h
swejpl.h
housasp.h
sweodef.h
astrolib.h
resource.h


DOS32


5 Windows applications that use a DLL
=====================================
5.1 swewin32.exe
--------------

swewin.c
swedll16.lib
swewin.rc

swewin.h
swephexp.h
swedll.h
sweodef.h
resource.h

define macros:

USE_DLL 
USE_DLL16


8.2 swewin32.exe
----------------

same as swewin

define macro

USE_DLL
