const getStdin  = require('get-stdin')   ;
const highlight = require('highlight.js');

getStdin().then(str => {
    console.log(highlight.highlightAuto(str).value);
});

// / var stdin = process.stdin;
// console.log(stdin);
