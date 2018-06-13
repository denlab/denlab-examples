'use strict';

let changeColor = document.getElementById('changeColor');

var chCol = function(color) {
    changeColor.style.backgroundColor = data.color;
    changeColor.setAttribute('value', data.color);
};

var chPageCol=function(color) {
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        chrome.tabs.executeScript(
            tabs[0].id,
            {code: 'document.body.style.backgroundColor = "' + color + '";'});
    });
};

chrome.storage.sync.get('color', function(data) {
    let col=data.color;
    chCol(col);
    // changeColor.style.backgroundColor = col;
    // changeColor.setAttribute('value', col);
});

changeColor.onclick = function(element) {
    let color="violet";
    chPageCol(color);
    // chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
    //     chrome.tabs.executeScript(
    //         tabs[0].id,
    //         {code: 'document.body.style.backgroundColor = "' + color + '";'});
    // });
};

document.onLoad = function(element) {
    chPageCol("pink") ;
};
