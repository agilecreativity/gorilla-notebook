# docker build --rm -f docker/jre-8-python/Dockerfile -t pinkgorillawb/gorilla-notebook:jre-8-python .
FROM openjdk:8-jre
MAINTAINER Andreas Steffan <a.steffan@contentreich.de>
LABEL vendor="Pink Gorilla" \
      maintainer="a.steffan@contentreich.de" \
      description="Pink Gorilla Notebook - JRE 8 version with python" \
      version="1.0"

EXPOSE 9000

# instead of logging to stdout, you may log to file in /log. create volume or mount host volume to /log
# RUN mkdir /log && chown daemon /log

RUN apt-get update && apt-get install -y python3 python3-dev python3-pip \
        && rm -rf /var/lib/apt/lists/* \
        && python3 -m pip install numpy pandas

ADD target/gorilla-notebook-standalone-with-python.jar /gorilla-notebook-standalone.jar
ADD docker/gorilla-notebook.sh /

RUN mkdir /work
# RUN adduser --gecos "" --home /bar --uid 2000  --disabled-login gorilla
# USER gorilla

WORKDIR /work

CMD ["java", "-Dlog_level=info", "-jar", "../gorilla-notebook-standalone.jar"]
