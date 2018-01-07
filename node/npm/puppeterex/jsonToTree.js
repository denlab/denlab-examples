var callback = console.log;

function traverse(obj) {
    if (obj instanceof Array) {
        for (var i=0; i<obj.length; i++) {
            if (typeof obj[i] == "object" && obj[i]) {
                callback(i);
                traverse(obj[i]);
            } else {
                callback(i, obj[i])
            }
        }
    } else {
        for (var prop in obj) {
            if (typeof obj[prop] == "object" && obj[prop]) {
                callback(prop);
                traverse(obj[prop]);
            } else {
                callback(prop, obj[prop]);
            }
        }
    }
}

traverse( JSON.parse(reqTemplate) );
