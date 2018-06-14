Test af spring ldap til ny brugerbase

Brug af anden application.properties end default application.properties:

java -jar ldaptest.jar --spring.config.location=/home/svc/ldaptest/local.properties

# Starting 389ds using docker

```
git clone https://github.com/minkwe/389ds.git

cd 389ds

#Create docker image
docker-compose up

alias dockerldapsearch='ldapsearch -x -D "cn=Directory Manager" -h localhost -w Admin123'
dockerldapsearch -b "dc=example,dc=lan"
 
# You can also browse in 389ds using this tool:
http://directory.apache.org/studio/download/download-linux.html

# Make a LDAP connection to localhost:389 after your docker-image is up and running.

# Username:    cn=Directory Manager

# Password:         Admin123

#You can stop and start the docker-image with:
docker-compose stop
docker-compose start


# To reset the docker-image and start from scratch, do
docker-compose rm
docker-compose up

```
