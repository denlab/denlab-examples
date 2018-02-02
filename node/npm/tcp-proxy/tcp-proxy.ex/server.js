const http = require('http');
require("http-shutdown").extend();

var port = process.env.PORT;

var server = http.createServer((request, response) => {
        let body = [];
        request.on('data', (chunk) => {
            body.push(chunk);
        }).on('end', () => {
            body = Buffer.concat(body).toString();
            response.end(eval(body));
        });
}).listen(port).withShutdown();

function stop() {
    server.shutdown(function() {
        console.log('Everything is cleanly shutdown.');
    });
};

process.on( "SIGINT", function() {
    console.log('CLOSING [SIGINT]');
    stop();
} );
