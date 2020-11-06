# revoverflow-backend-question-service
Question service for RevOverflow




To build a docker container:

`docker build -t revoverflow-backend-question-service .`

Run consul before running your container:

`docker run --net=host  --name=consul -d consul`

`docker images`

To run your container:

`docker run --net=host --name=revoverflow-question-service -d <image-id>`


