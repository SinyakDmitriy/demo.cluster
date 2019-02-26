FROM java:8-jre
ADD files/* /opt/app/
ADD build/libs/*.jar /opt/app/
RUN ["chmod", "755", "/opt/app/start.sh"]
CMD exec /opt/app/start.sh