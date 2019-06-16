FROM ubuntu:latest
MAINTAINER David Pätzel


ADD ./blackbox /


RUN chmod +x /blackbox


ENTRYPOINT ["/blackbox"]
