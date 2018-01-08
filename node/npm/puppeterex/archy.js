var archy = require('archy');
var s = archy({
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
});
console.log(s);
s = archy({
    a: 1, b: 2
});
console.log(s);
