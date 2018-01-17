const {spawn} = require('child_process');


var cmd = 'ls -alrth | head';
console.log(cmd);
const child = spawn(cmd, {
    stdio: 'inherit',
    shell: true, 
    cwd: `${process.env.HOME}`
});
