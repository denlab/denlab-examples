#!/usr/bin/env groovy

import com.ex.getThing

println "[${this.class}]"
def thing = new getThing()
println thing.getThingList()
println "[${this.class}] it seems to work"
