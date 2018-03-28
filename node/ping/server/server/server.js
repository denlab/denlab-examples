const http = require('http');
require("http-shutdown").extend();
var circular = require('circular');


var port = process.env.PORT;
port = 80;

function log(data) {
    console.log(data);
}


log(`- starting server on port: ${port}`);

var server = http.createServer((request, response) => {
    log(`- starting server on port: ${port}`);
    let body = [];
    request.on('data', (chunk) => {
        body.push(chunk);
    }).on('end', () => {
        body = Buffer.concat(body).toString();
        body = JSON.stringify(request,circular());
        response.end(body);
    });
}).listen(port).withShutdown();

log(`- server started on port: ${port}`);

function stop() {
    server.shutdown(function() {
        console.log('Everything is cleanly shutdown.');
    });
};

process.on( "SIGINT", function() {
    console.log('CLOSING [SIGINT]');
    stop();
} );
