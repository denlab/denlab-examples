#!/usr/bin/env groovy

package com

import com.ex.getThing

class printThing {
    static def main(args) {
        def thing = new getThing()
        println thing.getThingList()
        println "it seems to work"
    }
}
