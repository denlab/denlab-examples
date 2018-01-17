const chalk = require('chalk');


// let ch16m = new chalk.constructor({level: 3});

// let chalk = require('chalk');

const {spawn} = require('child_process');
const child = spawn('wc');

process.stdin.pipe(child.stdin);

child.stdout.on('data', (data) => {
  console.log(`child stdout:\n${data}`);
});
