# java -cp sort-tool-1.0-SNAPSHOT.jar:gson-2.2.4.jar com.saturn.sort.merge.MergeSorter -c ./config.json >>1.log &

java -cp .:./lib/* com.saturn.sort.merge.MergeSorter -c ./config.json >>1.log &