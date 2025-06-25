FROM node:24.3.0-alpine

WORKDIR /usr/app
COPY ./ /usr/app
RUN npm install

EXPOSE 3000

CMD ["npm", "start"]