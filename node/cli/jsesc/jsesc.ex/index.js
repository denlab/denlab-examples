const jsesc = require('jsesc');
console.log(jsesc('Ich ♥ Bücher'));
console.log(jsesc('foo 𝌆 bar'));

function l() {
    console.log.apply(this,arguments);
}
l("hi");
l(jsesc({ 'Ich ♥ Bücher': 'foo 𝌆 bar' }, {
    'compact': false,
    'indent': '\t' // this is the default
}));
l(jsesc(['a', ['b', 'x','y',['zz1','zz2']], 'c'], {
    'compact': false,
    'indentLevel': 2
}));
