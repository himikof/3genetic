LOCAL_CLASSPATH=
for i in lib/*.jar;
do
    LOCAL_CLASSPATH=$LOCAL_CLASSPATH:$i;
done
for i in ./*.jar;
do
    LOCAL_CLASSPATH=$LOCAL_CLASSPATH:$i;
done
for i in plugins/tasks/*.jar;
do
    LOCAL_CLASSPATH=$LOCAL_CLASSPATH:$i;
done

for i in plugins/individuals/*/*.jar;
do
    LOCAL_CLASSPATH=$LOCAL_CLASSPATH:$i;
done
    
java -Xmx512m -Xms512m -cp $LOCAL_CLASSPATH laboratory.core.Main
