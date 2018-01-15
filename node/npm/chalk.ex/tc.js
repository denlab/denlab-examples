// Default chalk
let chalk = require('chalk');
console.log(chalk.supportsColor);
print(chalk);

// 256 colors chalk
let ch256 = new chalk.constructor({level: 2});
print(ch256);

// Truecolor chalk
let ch16m = new chalk.constructor({level: 3});
print(ch16m);

function print(c) {
    let result = '';
    for(let i =0; i < 60; i++){
        result += c.rgb(255 - 2*i, 0, 2*i)('█');
    }
    console.log('    ' + result);
}
