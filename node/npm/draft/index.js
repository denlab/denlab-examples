var marked = require('marked');
const getStdin  = require('get-stdin')   ;
var TerminalRenderer = require('marked-terminal');
const highlight = require('highlight.js');


marked.setOptions({
    // Define custom renderer
    renderer: new TerminalRenderer()
});

// Show the parsed data
console.log(marked('# Hello \n This is **markdown** printed in the `terminal`'));
var markdownString = '```js\n console.log("hello"); \n```';

function printColor(s) {
    marked.setOptions({
        highlight: function (code) {
            return require('highlight.js').highlightAuto(code).value;
        }
    });
    // console.log(marked( '``` '+s+' ```'));
    console.log(marked(s));
}

   getStdin().then(str => {
    // console.log(highlight.highlightAuto(str).value);
    printColor(str);
});

