var ws = require('method-web-socket-expose');
var router = require('method-web-socket-expose').Router();
var server = new ws.start({host:"localhost", port: 8000});
 
router.setMethod('serverMethod', function (_return) {
    _return(["What's Up!"], "Bye");
});
server.use(router);
