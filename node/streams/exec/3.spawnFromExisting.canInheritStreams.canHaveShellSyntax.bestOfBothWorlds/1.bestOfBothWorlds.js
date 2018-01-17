const {spawn} = require('child_process');


var cmd = 'set -xv && find . -type f | tee >(t.recolor 128 \'.*\') | wc -l';
console.log(cmd);
const child = spawn(cmd, {
    // const child = spawn('echo $$ && set -xv && find . -type f | tee >(t.recolor 128 \'.*\') | wc -l', {
    stdio: 'inherit',
    shell: true
});
