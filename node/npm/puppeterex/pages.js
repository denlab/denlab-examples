const puppeteer = require('puppeteer');
var circular = require('circular');
var treeify = require('treeify');

process.on('unhandledRejection', function(m) {
    console.log('unhandledRejection:' + m);
});
async function run() {
    const browser = await puppeteer.connect({browserWSEndpoint: process.env.ws });
  // const browser = await puppeteer.launch();
    const pages = await browser.pages();
    const page = pages[0];
    console.log(JSON.stringify(page, circular()));
    // console.log(treeify.asTree(JSON.parse(JSON.stringify(page, circular()))));
    // console.log(Promise.resolve(page));
    // JSON.stringfy
    // console.log(treeify.asTree(Promise.resolve(page)));
    
  // await page.goto('https://github.com/features');
  // await page.screenshot({ path: 'screenshots/github.png' });
  
  // browser.close();
    process.exit();
}

run();
