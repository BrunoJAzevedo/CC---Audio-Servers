pdu: src/PDU/PDUType.java
	mkdir build
	javac src/PDU/*.java -d build/

pdutest: src/PDUTest.java
	javac src/PDUTest.java -classpath build -d build

clean: build/
	rm -rf ./build
