const chalk = require('chalk');
const {spawn} = require('child_process');

console.log("waiting on stdin ...");
const child = spawn('wc');
process.stdin.pipe(child.stdin);

child.stdout.on('data', (data) => {
  console.log(`child stdout:\n${data}`);
});
