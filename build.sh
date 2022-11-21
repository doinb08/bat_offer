mvn -U clean package -Dmaven.test.skip=true
cp target/doinb-1.0.0.jar deploy/bat/bat.jar
cd deploy
docker-compose down
docker-compose up -d --build