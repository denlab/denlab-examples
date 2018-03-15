FROM alpine AS stage1
RUN echo stage1 > /stage
CMD echo "This is stage: `cat /stage`"

FROM stage1 AS stage2
RUN echo stage2 > /stage



