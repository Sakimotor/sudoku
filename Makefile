# LE BUT FINAL

# LES VARIABLES DONC LE JAVAC ET LES .CLASS
JFLAGS = -implicit:none
JC = javac
JAV = java
.SUFFIXES : .java .class
.java.class: 
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		Programme.java \
		Menu.java \
		Fenetrejeu.java \
		FenetreEditeur.java \
		Algorithme.java \
		Actions.java \
		Boutons.java \
		Affichage.java \
		Ecriture.java \
		Lecture.java

MAIN = Programme

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JAV) $(MAIN)

clean: $(RM) *.class
