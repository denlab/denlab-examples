'use strict';

const puppeteer = require('puppeteer');

(async() => {
    const browser = await puppeteer.connect(
        {browserWSEndpoint: ' ws://127.0.0.1:9222/devtools/browser/894eb556-2d3e-4cfe-bdd9-0c04bced2953' })
)
