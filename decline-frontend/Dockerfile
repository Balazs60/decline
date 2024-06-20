FROM node:lts-alpine

WORKDIR /app

COPY package.json .

RUN npm install

COPY . . 
COPY assets /app/assets
CMD [ "npm" , "start" ]