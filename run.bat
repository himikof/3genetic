echo %JAVA_HOME%
call ant
copy scripts\\3genetic.bat deploy
copy scripts\\lcp.bat deploy
mkdir deploy\\lib
copy lib\\*.jar deploy\\lib
cd deploy
call 3genetic.bat