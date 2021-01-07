#!/usr/local/bin/perl
use 5.008007;
use ExtUtils::MakeMaker;
# See lib/ExtUtils/MakeMaker.pm for details of how to influence
# the contents of the Makefile that is written.
WriteMakefile(
    NAME              => 'SwissEph',
    VERSION_FROM      => 'lib/SwissEph.pm', # finds $VERSION
    PREREQ_PM         => {}, # e.g., Module::Name => 1.1
    ($] >= 5.005 ?     ## Add these new keywords supported since 5.005
      (ABSTRACT_FROM  => 'lib/SwissEph.pm', # retrieve abstract from module
       AUTHOR         => 'Dieter <dieter@astro.ch>') : ()),
    #LIBS              => ['-lC:\sweph\bin\swedll32'], # e.g., '-lm'
    LIBS              => ['-L/usr/local/lib64/swe -lswe'], # e.g., '-lm'
    #LIBS              => ['-lswe'], # e.g., '-lm'
    DEFINE            => '', # e.g., '-DHAVE_SOMETHING'
    INC               => '-I.', # e.g., '-I. -I/usr/include/other'
#    DISTVNAME         => 'perl_swisseph', # 
	# Un-comment this if you add C files to link with later:
    # OBJECT            => '$(O_FILES)', # link all the C files too
);
