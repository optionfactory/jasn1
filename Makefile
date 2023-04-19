build: FORCE
	cd projects/jasn1-compiler/antlr && ./compile.sh
	./gradlew assemble --stacktrace
	mkdir -p build/libs-all
	cp ./projects/jasn1/build/libs/jasn1-1.7.2.jar build/libs-all/
	cp ./projects/jasn1-compiler/build/libs/jasn1-compiler-1.7.2.jar build/libs-all/
	cp ./projects/jasn1-compiler/antlr/antlr-2.7.7.jar build/libs-all/

install: build
	mvn install:install-file -Dfile=projects/jasn1/build/libs/jasn1-1.7.2.jar -Dsources=projects/jasn1/build/libs/jasn1-1.7.2-sources.jar -Djavadoc=projects/jasn1/build/libs/jasn1-1.7.2-javadoc.jar -DgroupId=jasn1 -DartifactId=jasn1-runtime -Dversion=1.7.2-SNAPSHOT -Dpackaging=jar
clean:
	./gradlew clean
	mkdir -p build/libs-all

FORCE:
