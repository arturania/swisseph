SwissEph version 2.056
=====================

The README is used to introduce the module and provide instructions on
how to install the module, any machine dependencies it may have (for
example C compilers and installed libraries) and any other information
that should be provided before the module is installed.

INSTALLATION

On Linux:
=========

  Before installing this module, you have to install a shared library of
  the Swiss Ephemeris functions. 
  Unpack http://www.astro.com/ftp/swisseph/swe_unix_src_2.06.tar.gz
  or whatever the latest version is, or the one you want to use.
  There will be a src directory in the unpacked files. Go there
  and run
  make libswe.so to build the shared library.  Copy this file to 
  /usr/local/lib64/swe 

  Usually, this will also require that you create a file (as root)
  /etc/ld.so.conf.d/swisseph.conf
  with the content line
  /usr/local/lib64/swe

  Now run ldconfig(8) (as root) in order to generate the
  symbolic links required.

  Now you can install the Perl module for the Swiss Ephemeris.
  Type the following:

    perl Makefile.PL
    make
    make test
    make install	(requires root)

On Windows:
===========

  Before installing this module, you have to install the following things
  on your computer:

  - the Swiss Ephemeris DLL swedll32.dll. If haven't done this yet,
    download the Swiss Ephemeris from the download area at 
    www.astro.com/swisseph and unpack it.

  - Visual C++ Express Edition, which can be downloaded for free from the
    http://www.microsoft.com/express/download/.

  After that you can install the Perl module. In the current directory 
  (where you read this README file), open the file Makefile.PL and
  fix the LIBS parameter. It must contain the directory to the Swiss Ephemeris
  DLL. After that run:

    perl Makefile.PL
    nmake
    nmake test
    nmake install


COPYRIGHT AND LICENCE

Copyright (C) 2017 Astrodienst, Zurich, Switzerland.

This library is free software; you can redistribute it and/or modify
it under the same terms as Perl itself, either Perl version 5.8.7 or,
at your option, any later version of Perl 5 you may have available.

-----------------------
Update 23-march-2016, example how to use PerlSwissEph-2.04.tar.gz

download PerlSwissEph-2.04.tar.gz
unpack it with
tar xzvf PerlSwissEph-2.04.tar.gz

it creates a directory SwissEph-2.04
cd SwissEph-2.04
perl Makefile.PL        (to create Makefile)

there is a warning:
Warning: the following files are missing in your kit:
    /usr/local/lib64/swe/libswe.so.2.04

The tarball contains ./usr/local/lib64/swe/libswe.so.2.04
you must copy or move this to /usr/local/lib64/swe/libswe.so.2.04 by hand.

If your architecture is not 64-bit Linux, you may have to create libswe.so.2.04
from the Swisseph C source distribution yourself, and then place it properly.

Then you must tell the Linux system how to find the dynamic library.
On Redhat RHEL 6 this goes like this:
as root, cd /etc/ld.so.conf.d
edit or create a file swisseph.conf with this line in it
/usr/local/lib64/swe

now run as root: ldconfig
afterwards, you should find in /usr/local/lib64/swe/
something like this
lrwxrwxrwx. 1 root  root     14 Mar 23 17:44 libswe.so -> libswe.so.2.04
lrwxrwxrwx. 1 root  root     14 Mar 23 17:43 libswe.so.1 -> libswe.so.2.04
-rwxr-xr-x. 1 root root  847686 Mar 23 17:10 libswe.so.2.04

Now, as normal user, go back to
cd SwissEph-2.04
make
make test

as root, (unless you can write in the install target directories)
make install

