const http=require('http');
const app=require('./app')
const port=60001;
const server=http.createServer(app);
server.listen(port);
