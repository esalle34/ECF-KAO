npm run test-ci --no-watch --no-progress --browsers=ChromeHeadless
npm run ng build
docker-compose up --build -d