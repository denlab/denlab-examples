package com.ex

class getThing {
    static def main(args) {
        println("[${this.class}] main($args)")
        println "[${this.class}] hi" 
    }

    def getThingList() {
        println("[${this.class}] getThingList")
        return ["thing","thin2","thing3"]
    }
}
