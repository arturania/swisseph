OBJ = platest.obj placalc.obj julday.obj revjul.obj d2l.obj csec.obj
OP = -mm
.c.obj:
	bcc -c  $(OP) $<

placalc.obj: placalc.h helconst.c placalc.c
	bcc $(OP) -c placalc.c

platest.obj: platest.c 
	bcc $(OP) -c platest.c

platest.exe: $(OBJ)
	bcc $(OP) $(OBJ)
	rm *.obj

housasp.obj: housasp.c housasp.h
	bcc $(OP) -c housasp.c

houstest.obj: houstest.c housasp.c housasp.h
	bcc $(OP) -c houstest.c

houstest.exe: houstest.obj housasp.obj outdeg.obj d2l.obj csec.obj
	bcc $(OP) houstest.obj housasp.obj outdeg.obj d2l.obj csec.obj

clean:
	del *.obj


FILES=astdata astrolib.h csec.c d2l.c dateconv.c deltat.c helconst.c \
      housasp.c housasp.h houstest.c houstest.exe julday.c makefile ourdef.h \
      outdeg.c placalc.c placalc.h platest.c platest.exe platest.prj \
      read.me revjul.c


zip:
	zip plac_src.zip $(FILES)

