var ws = require('method-web-socket-expose').client;
var client = ws.connect('ws://localhost:8000');
 
client.on('ready', function(){
  client.serverMethod("tellmeSomething").then(function(res,err){
  	console.log(arguments);
  });
});
