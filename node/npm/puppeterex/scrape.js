const puppeteer = require('puppeteer');
process.on('unhandledRejection', function(m) {
    console.log('unhandledRejection:' + n);
});
async function run() {
    const browser = await puppeteer.connect({browserWSEndpoint: process.env.ws });
  // const browser = await puppeteer.launch();
  const page = await browser.newPage();
  
  await page.goto('https://github.com/features');
  await page.screenshot({ path: 'screenshots/github.png' });
  
  // browser.close();
    process.exit();
}

run();
