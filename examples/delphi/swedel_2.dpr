{ you need to copy swedll32.dll to the delphi2 directory
for this sample to work.
Alois Treindl, 4-june-1998
}

program swedel_2;

uses
  Forms,
  sample2 in 'sample2.pas' {Form1};

{$R *.RES}

begin
  Application.Initialize;
  Application.CreateForm(TForm1, Form1);
  Application.Run;
end.
