
const chalk = require('chalk');

console.log(chalk.blue('Hello world!'));
const log = console.log;

// Combine styled and normal strings
log(chalk.blue('Hello') + 'World' + chalk.red('!'));

// Compose multiple styles using the chainable API
log(chalk.blue.bgRed.bold('Hello world!'));

// Pass in multiple arguments
log(chalk.blue('Hello', 'World!', 'Foo', 'bar', 'biz', 'baz'));

// Nest styles
log(chalk.red('Hello', chalk.underline.bgBlue('world') + '!'));

// Nest styles of the same type even (color, underline, background)
log(chalk.green(
    'I am a green line ' +
    chalk.blue.underline.bold('with a blue substring') +
    ' that becomes green again!'
));

// ES2015 template literal
log(`
CPU: ${chalk.red('90%')}
RAM: ${chalk.green('40%')}
DISK: ${chalk.yellow('70%')}
`);

// Use RGB colors in terminal emulators that support it.
log(chalk.keyword('orange')('Yay for orange colored text!'));
log(chalk.rgb(123, 45, 67).underline('Underlined reddish color'));
log(chalk.hex('#DEADED').bold('Bold gray!'));



const error = chalk.bold.red;
const warning = chalk.keyword('orange');

console.log(error('Error!'));
console.log(warning('Warning!'));


const name = 'Sindre';
console.log(chalk.green('Hello %s'), name);
//=> 'Hello Sindre'




const miles = 18;
const calculateFeet = miles => miles * 5280;

console.log(chalk`
  There are {bold 5280 feet} in a mile.
  In {bold ${miles} miles}, there are {green.bold ${calculateFeet(miles)} feet}.
`);
console.log(chalk.bold.rgb(10, 100, 200)('Hello!'));
console.log(chalk`{bold.rgb(10,100,200) Hello!}`);




var gradient = require('gradient-string');

console.log(gradient('cyan', 'pink')('Hello world!'));
// Initialize a gradient
// Using varargs
var coolGradient = gradient('red', 'green', 'blue');

// Using array
coolGradient = gradient(['#FF0000', '#00FF00', '#0000FF']);
// The colors are parsed with TinyColor, multiple formats are accepted.

// Use a gradient
let coolString = coolGradient('This is a fancy string!');
console.log(coolString);
// Built-in gradients
// Usage
gradient = require('gradient-string');

// Use the rainbow gradient
console.log(gradient.rainbow('I love gradient-strings!'))
