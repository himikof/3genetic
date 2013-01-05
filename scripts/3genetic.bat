set LOCAL_CLASSPATH=
for %%i in (plugins/tasks/*.jar) do call lcp.bat plugins/tasks/%%i
for %%i in (plugins/individuals/**.jar) do call lcp.bat %%i
for %%i in (lib/*.jar) do call lcp.bat lib/%%i
java -Xmx512m -cp core.jar;common.jar;util.jar;plugins/individuals/ext-ant-full-table/ext-ant-full-table.jar;plugins/individuals/mealy/mealy.jar%LOCAL_CLASSPATH% laboratory.core.Main
