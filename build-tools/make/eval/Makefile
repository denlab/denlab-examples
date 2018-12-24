define ONERULE
$(TARGET): $(PREREQS)
	echo $(PREREQ) > $(TARGET)
endef

TARGET=t1
PREREQS=p1 p2
$(eval $(ONERULE))

TARGET=p1
PREREQS=
$(eval $(ONERULE))

TARGET=p2
PREREQS=
$(eval $(ONERULE))



