var archy = require('archy');
var o = {
    label : 'beep',
    nodes : [
        'ity',
        {
            label : 'boop',
            nodes : [
                {
                    label : 'o_O',
                    nodes : [
                        {
                            label : 'oh',
                            nodes : [ 'hello', 'puny' ]
                        },
                        'human'
                    ]
                },
                'party\ntime!'
            ]
        }
    ]
}
var j = JSON.stringify(o,null,2);
console.log(j);
var s = archy(o);
console.log(s);
s = archy({
    a: 1, b: 2
});
console.log(s);
