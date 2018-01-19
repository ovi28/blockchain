#!/bin/bash 
docker build -f Dockerfile1 -t blockchain1 .
docker build -f Dockerfile2 -t blockchain2 .
docker build -f Dockerfile3 -t blockchain3 .
docker build -f Dockerfile4 -t blockchain4 .
docker network create bcnetwork
docker run --detach -p 8080:8080 --name b1 --network bcnetwork -t blockchain1
docker run --detach -p 8081:8081 --name b2 --network bcnetwork -t blockchain2
docker run --detach -p 8082:8082 --name b3 --network bcnetwork -t blockchain3
docker run --detach -p 8083:8083 --name b4 --network bcnetwork -t blockchain4
echo "Please wait"
echo "The clients are starting up"
sleep 2m
echo "The tests will begin"
echo "First client block: "
curl http://127.0.0.1:8080/client/block
echo ""
echo "Second client block: "
curl http://127.0.0.1:8081/client/block
echo ""
echo "Third client block: "
curl http://127.0.0.1:8082/client/block
echo ""
echo "Fourth client block: "
curl http://127.0.0.1:8083/client/block
echo ""
echo "Client 1 mined"
curl http://127.0.0.1:8080/client/mine
echo "First client block: "
curl http://127.0.0.1:8080/client/block
echo ""
echo "Second client block: "
curl http://127.0.0.1:8081/client/block
echo ""
echo "Third client block: "
curl http://127.0.0.1:8082/client/block
echo ""
echo "Fourth client block: "
curl http://127.0.0.1:8083/client/block
echo ""
echo "Client 2 mined"
curl http://127.0.0.1:8081/client/mine
echo "First client block: "
curl http://127.0.0.1:8080/client/block
echo ""
echo "Second client block: "
curl http://127.0.0.1:8081/client/block
echo ""
echo "Third client block: "
curl http://127.0.0.1:8082/client/block
echo ""
echo "Fourth client block: "
curl http://127.0.0.1:8083/client/block
echo ""
echo "Client 3 mined"
curl http://127.0.0.1:8082/client/mine
echo "First client block: "
curl http://127.0.0.1:8080/client/block
echo ""
echo "Second client block: "
curl http://127.0.0.1:8081/client/block
echo ""
echo "Third client block: "
curl http://127.0.0.1:8082/client/block
echo ""
echo "Fourth client block: "
curl http://127.0.0.1:8083/client/block
echo ""
echo "Client 4 mined"
curl http://127.0.0.1:8083/client/mine
echo "First client block: "
curl http://127.0.0.1:8080/client/block
echo ""
echo "Second client block: "
curl http://127.0.0.1:8081/client/block
echo ""
echo "Third client block: "
curl http://127.0.0.1:8082/client/block
echo ""
echo "Fourth client block: "
curl http://127.0.0.1:8083/client/block
echo ""
