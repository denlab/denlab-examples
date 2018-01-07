
var treeify = require('treeify');

// var archy = require('archy');
var stdin = process.stdin,
    stdout = process.stdout,
    inputChunks = [];

stdin.resume();
stdin.setEncoding('utf8');

stdin.on('data', function (chunk) {
    inputChunks.push(chunk);
});

stdin.on('end', function () {
    var inputJSON = inputChunks.join();
        //     parsedData = JSON.parse(inputJSON),
        //     outputJSON = JSON.stringify(parsedData, null, '    ');
        // outputJSON = archy(JSON.parse(inputJSON));
        // outputJSON = JSON.parse(inputJSON);

    var jsonObject = JSON.parse(inputJSON);
    var tree = treeify.asTree(jsonObject);
    if (process.env.debug == "true" ) {
        stdout.write(JSON.stringify(jsonObject));
    }
    // var outputJSON = JSON.stringify(JSON.parse(inputJSON));

    stdout.write(tree);
    stdout.write('\n');
});

// var archy = require('archy');
// var s = archy({
//     label : 'beep',
//     nodes : [
//         'ity',
//         {
//             label : 'boop',
//             nodes : [
//                 {
//                     label : 'o_O',
//                     nodes : [
//                         {
//                             label : 'oh',
//                             nodes : [ 'hello', 'puny' ]
//                         },
//                         'human'
//                     ]
//                 },
//                 'party\ntime!'
//             ]
//         }
//     ]
// });
// console.log(s);
// s = archy({
//     a: 1, b: 2
// });
// console.log(s);
