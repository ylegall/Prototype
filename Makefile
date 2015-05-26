
SRCDIR=src/
BINDIR=bin/
name=prototype
CP=.

.PHONY: clean jar build run

all: build jar

build:
	-if [ ! -d $(BINDIR) ]; then mkdir $(BINDIR); fi
	javac -sourcepath $(SRCDIR) -d $(BINDIR) -cp $(CP) $(SRCDIR)core/Prototype.java
	cp -r $(SRCDIR)resources/ $(BINDIR)resources/

jar: build
	jar cfm $(name).jar Manifest.txt $(BINDIR)

run: jar
	java -jar $(name).jar

clean:
	-rm -rf $(BINDIR)
	-rm $(name).jar

rebuild: clean all

